
package com.google.code.lightssh.project.security.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.IoSerialUtil;
import com.google.code.lightssh.project.log.entity.EntityChange;
import com.google.code.lightssh.project.security.dao.RoleChangeDao;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.security.entity.RoleChange;

/** 
 * @author YangXiaojin
 * @date 2013-2-17
 * @description：TODO
 */
@Component("roleChangeManager")
public class RoleChangeManagerImpl extends BaseManagerImpl<RoleChange> implements RoleChangeManager{

	private static final long serialVersionUID = 965045405616354133L;
	
	@Resource(name="roleChangeDao")
	public void setDao(RoleChangeDao dao ){
		this.dao = dao;
	}
	
	public RoleChangeDao getDao(){
		return (RoleChangeDao)this.dao;
	}
	
	public RoleChange save(LoginAccount user,EntityChange.Type type
			,Role originalRole,Role newRole,String remark){
		if( originalRole == null && newRole == null )
			throw new ApplicationException("原始角色或新角色为空！");
		
		RoleChange rc = new RoleChange();
		rc.setStatus(EntityChange.Status.NEW);
		rc.setOperator(user);
		rc.setType(type);
		rc.setDescription(remark);
		
		if( newRole != null )
			rc.setRole(newRole);
		else
			rc.setRole(originalRole);
		
		if( newRole != null )
			rc.setNewObject( IoSerialUtil.serialize(newRole) );
		
		if( originalRole != null )
			rc.setOriginalObject(IoSerialUtil.serialize(originalRole) );
		
		dao.create(rc);
		
		return rc;
	}
	
	public ListPage<RoleChange> listTodoAudit(ListPage<RoleChange> page,RoleChange t ){
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( t.getRole() != null ){
				if( !StringUtils.isEmpty(t.getRole().getId()) ){
					sc.equal("role.id",t.getRole().getId().trim());
				}
				if( !StringUtils.isEmpty(t.getRole().getName()) ){
					sc.like("role.name",t.getRole().getName().trim());
				}
			}
		}
		sc.in("status",RoleChange.Status.NEW.name(),RoleChange.Status.PROCESSING.name());
		//sc.equal("status",RoleChange.Status.NEW);
		
		return getDao().list(page,sc);
	}
	
	/**
	 * 查询角色关联的所有变更信息
	 */
	public List<RoleChange> list(Role role ){
		if( role == null || StringUtils.isEmpty(role.getId() ))
			return null;
		
		ListPage<RoleChange> page = new ListPage<RoleChange>();
		SearchCondition sc = new SearchCondition();
		if( !StringUtils.isEmpty(role.getId()) ){
			sc.like("role.id",role.getId().trim());
		}
		
		return getDao().list(page,sc).getList();
	}
	
	public void updateStatus(String id,EntityChange.Status originalStatus,EntityChange.Status newStatus){
		getDao().update("id",id,"status",originalStatus, newStatus);
	}

}
