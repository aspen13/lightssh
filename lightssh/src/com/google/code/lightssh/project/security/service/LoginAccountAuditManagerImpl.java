
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
import com.google.code.lightssh.project.security.dao.LoginAccountAuditDao;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.LoginAccountAudit;
import com.google.code.lightssh.project.security.entity.LoginAccountChange;
import com.google.code.lightssh.project.util.constant.AuditResult;
import com.google.code.lightssh.project.util.constant.AuditStatus;
import com.google.code.lightssh.project.workflow.model.ExecutionType;

/** 
 * @author YangXiaojin
 * @date 2013-2-23
 * @description：TODO
 */
@Component("loginAccountAuditManager")
public class LoginAccountAuditManagerImpl extends BaseManagerImpl<LoginAccountAudit> implements LoginAccountAuditManager{

	private static final long serialVersionUID = 1710020013089142879L;
	
	@Resource(name="loginAccountManager")
	private LoginAccountManager loginAccountManager;
	
	@Resource(name="loginAccountChangeManager")
	private LoginAccountChangeManager loginAccountChangeManager;
	
	@Resource(name="loginAccountAuditDao")
	public void setDao(LoginAccountAuditDao dao){
		this.dao = dao;
	}
	
	public LoginAccountAuditDao getDao(){
		return (LoginAccountAuditDao)this.dao;
	}
	
	/**
	 * 审核
	 */
	public void audit(LoginAccountAudit audit,LoginAccountChange change ){
		if( audit == null )
			throw new ApplicationException("参数为空！");
		
		if( change == null || StringUtils.isEmpty(change.getId()) )
			throw new ApplicationException("登录帐户变更信息为空！");
		
		LoginAccountChange dbChange = this.loginAccountChangeManager.get(change);
		if( dbChange == null )
			throw new ApplicationException("登录帐户变更信息["+change.getId()+"]不存在！");
		
		LoginAccount dbAcc = loginAccountManager.get(change.getLoginAccount());
		LoginAccount newAcc = null;
		byte[] newObject = dbChange.getNewObject();
		if( newObject != null )
			newAcc = (LoginAccount)IoSerialUtil.deserialize(newObject);
		if( newObject == null || newAcc == null )
			throw new ApplicationException("数据异常，变更登录帐户信息为空！");
		
		boolean passed = AuditResult.LAST_AUDIT_PASSED.equals(audit.getResult());
		if( passed && EntityChange.Type.DELETE.equals(dbChange.getType()) ){//删除
			dbAcc.setStatus( AuditStatus.DELETE );
		}else if( passed && AuditStatus.EFFECTIVE.equals(newAcc.getStatus())){
			dbAcc.setPartyId( newAcc.getPartyId() );
			dbAcc.setPeriod(newAcc.getPeriod());
			dbAcc.setEmail( newAcc.getEmail());
			dbAcc.setRoles( newAcc.getRoles());
			dbAcc.setDescription( newAcc.getDescription() );
		}else if( passed ){
			dbAcc.setStatus( AuditStatus.EFFECTIVE );
		}else if( !passed && AuditStatus.NEW.equals( newAcc.getStatus() )){
			dbAcc.setStatus( AuditStatus.AUDIT_REJECT );
		}
		
		loginAccountManager.update(dbAcc);
		//dao.create(audit);//TODO 改为工作流，不需要记录审核日志
		
		loginAccountChangeManager.updateStatus(change.getId()
				,EntityChange.Status.NEW,EntityChange.Status.FINISHED);
	}
	
	public ListPage<LoginAccountAudit> list(ListPage<LoginAccountAudit> page,LoginAccountAudit t){
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( t.getLoginAccountChange() != null ){
				LoginAccount account = t.getLoginAccountChange().getLoginAccount();
				if( account != null ){
					if( !StringUtils.isEmpty(account.getLoginName()) ){
						sc.like("loginAccountChange.loginAccount.loginName", account.getLoginName().trim());
					}
				}
			}
		}
		
		return dao.list(page, sc);
	}
	
	@Override
	public void process(ExecutionType type, String procDefKey,
			String procInstId, String bizKey) {
		
		LoginAccountChange lac = this.loginAccountChangeManager.get(bizKey);
		
		LoginAccountAudit accAudit = new LoginAccountAudit();
		accAudit.setLoginAccountChange( lac );//关联变更
		
		accAudit.setUser( null );
		boolean passed = ExecutionType.SUBMIT.equals(type);
		accAudit.setResult(passed?AuditResult.LAST_AUDIT_PASSED:AuditResult.LAST_AUDIT_REJECT);
		
		this.audit(accAudit, lac);
	}

}
