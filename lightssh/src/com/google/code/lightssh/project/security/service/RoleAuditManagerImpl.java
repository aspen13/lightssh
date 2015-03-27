package com.google.code.lightssh.project.security.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.IoSerialUtil;
import com.google.code.lightssh.project.log.entity.EntityChange;
import com.google.code.lightssh.project.security.dao.RoleAuditDao;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.security.entity.RoleAudit;
import com.google.code.lightssh.project.security.entity.RoleChange;
import com.google.code.lightssh.project.util.constant.AuditResult;
import com.google.code.lightssh.project.util.constant.AuditStatus;
import com.google.code.lightssh.project.workflow.model.ExecutionType;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("roleAuditManager")
public class RoleAuditManagerImpl extends BaseManagerImpl<RoleAudit> implements RoleAuditManager{

	private static final long serialVersionUID = -8219134739997237462L;
	
	@Resource(name="roleChangeManager")
	private RoleChangeManager roleChangeManager;
	
	@Resource(name="roleManager")
	private RoleManager roleManager;
	
	@Resource(name="roleAuditDao")
	public void setDao(RoleAuditDao dao){
		this.dao = dao;
	}
	
	public RoleAuditDao getDao(){
		return (RoleAuditDao)this.dao;
	}
	
	/**
	 * 审核
	 */
	public void audit(RoleAudit ra,RoleChange roleChange ){
		if( ra == null )
			throw new ApplicationException("参数为空！");
		
		if( roleChange == null || StringUtils.isEmpty(roleChange.getId()) )
			throw new ApplicationException("角色变更信息为空！");
		
		RoleChange rc = this.roleChangeManager.get(roleChange);
		if( rc == null )
			throw new ApplicationException("角色变更信息["+roleChange.getId()+"]不存在！");
		
		Role db_role = roleManager.get(roleChange.getRole());
		Role newRole = null;
		byte[] newObject = rc.getNewObject();
		if( newObject != null )
			newRole = (Role)IoSerialUtil.deserialize(newObject);
		if( newObject == null || newRole == null )
			throw new ApplicationException("数据异常，变更角色信息为空！");
		
		boolean passed = AuditResult.LAST_AUDIT_PASSED.equals(ra.getResult());
		if( passed && EntityChange.Type.DELETE.equals(rc.getType()) ){//删除
			db_role.setStatus( AuditStatus.DELETE );
		}else if( passed && AuditStatus.EFFECTIVE.equals(newRole.getStatus())){
			db_role.setName( newRole.getName() );
			db_role.setDescription( newRole.getDescription() );
			db_role.setPermissions( newRole.getPermissions() );
		}else if( passed ){
			db_role.setStatus( AuditStatus.EFFECTIVE );
		}else if( !passed && AuditStatus.NEW.equals( newRole.getStatus() )){
			db_role.setStatus( AuditStatus.AUDIT_REJECT );
		}
		
		roleManager.update(db_role);
		//dao.create(ra); TODO
		
		roleChangeManager.updateStatus(rc.getId()
				,EntityChange.Status.NEW,EntityChange.Status.FINISHED);
	}
	
	public ListPage<RoleAudit> list(ListPage<RoleAudit> page,RoleAudit ra){
		SearchCondition sc = new SearchCondition();
		if( ra != null ){
			if( ra.getRoleChange() != null ){
				RoleChange rc = ra.getRoleChange();
				if( rc.getRole() != null ){
					if( !StringUtils.isEmpty(rc.getRole().getName()) ){
						sc.like("roleChange.role.name", rc.getRole().getName().trim());
					}
				}
			}
		}
		
		return dao.list(page, sc);
	}
	
	@Override
	public void process(ExecutionType type, String procDefKey,
			String procInstId, String bizKey) {
		
		RoleChange rc = this.roleChangeManager.get(bizKey);
		
		RoleAudit roleAudit = new RoleAudit();
		roleAudit.setRoleChange(rc);//关联角色变更
		
		roleAudit.setUser( null );
		boolean passed = ExecutionType.SUBMIT.equals(type);
		roleAudit.setResult(passed?AuditResult.LAST_AUDIT_PASSED:AuditResult.LAST_AUDIT_REJECT);
		
		this.audit(roleAudit, rc);
	}

}
