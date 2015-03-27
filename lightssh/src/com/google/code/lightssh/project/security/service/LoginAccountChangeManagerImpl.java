
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
import com.google.code.lightssh.project.security.dao.LoginAccountChangeDao;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.LoginAccountChange;
import com.google.code.lightssh.project.security.entity.RoleChange;

/** 
 * @author YangXiaojin
 * @date 2013-2-23
 * @description：TODO
 */
@Component("loginAccountChangeManager")
public class LoginAccountChangeManagerImpl extends BaseManagerImpl<LoginAccountChange> implements LoginAccountChangeManager {

	private static final long serialVersionUID = 9088587913492811809L;
	
	@Resource(name="loginAccountChangeDao")
	public void setDao(LoginAccountChangeDao dao){
		this.dao = dao;
	}
	
	public LoginAccountChangeDao getDao(){
		return (LoginAccountChangeDao)this.dao;
	}
	
	public LoginAccountChange save(LoginAccount user,EntityChange.Type type
			,LoginAccount originalAcc,LoginAccount newAcc,String remark){
		if( originalAcc == null && newAcc == null )
			throw new ApplicationException("原始登录帐户或新登录帐户为空！");
		
		LoginAccountChange ec = new LoginAccountChange();
		ec.setStatus(EntityChange.Status.NEW);
		ec.setOperator(user);
		ec.setType(type);
		ec.setDescription(remark);
		
		if( newAcc != null ){
			ec.setLoginAccount(newAcc);
			ec.setNewObject( IoSerialUtil.serialize(newAcc) );
		}else{
			ec.setLoginAccount(originalAcc);
		}
		
		if( originalAcc != null )
			ec.setOriginalObject(IoSerialUtil.serialize(originalAcc) );
		
		dao.create(ec);
		
		return ec;
	}
	
	public ListPage<LoginAccountChange> listTodoAudit(ListPage<LoginAccountChange> page,LoginAccountChange t ){
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( t.getLoginAccount() != null ){
				if( !StringUtils.isEmpty(t.getLoginAccount().getLoginName()) ){
					sc.like("loginAccount.loginName",t.getLoginAccount().getLoginName().trim());
				}
			}
		}
		sc.in("status",RoleChange.Status.NEW.name(),RoleChange.Status.PROCESSING.name());
		//sc.equal("status",RoleChange.Status.NEW);
		
		return getDao().list(page,sc);
	}
	
	public void updateStatus(String id,EntityChange.Status originalStatus,EntityChange.Status newStatus){
		getDao().update("id",id,"status",originalStatus, newStatus);
	}

}
