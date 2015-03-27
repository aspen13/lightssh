package com.google.code.lightssh.project.security.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.CryptographyUtil;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.log.entity.EntityChange;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.security.dao.LoginAccountDao;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.LoginAccount.LoginAccountType;
import com.google.code.lightssh.project.security.entity.LoginAccountChange;
import com.google.code.lightssh.project.security.entity.Permission;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.util.constant.AuditStatus;
import com.google.code.lightssh.project.workflow.model.WorkflowType;
import com.google.code.lightssh.project.workflow.service.WorkflowManager;

/**
 * LoginAccount Manager implement
 * @author YangXiaojin
 *
 */
@Component("loginAccountManager")
public class LoginAccountManagerImpl extends BaseManagerImpl<LoginAccount>
	implements LoginAccountManager{
	
	private static final long serialVersionUID = 8212430389472262891L;

	private static Logger log = LoggerFactory.getLogger(LoginAccountManagerImpl.class);
	
	/** 管理员账号 */
	public static final String ROOT_LOGIN_NAME="root";
	
	public static final String DEFAULT_PASSWORD = "123456";
	
	@Resource(name="roleManager")
	private RoleManager roleManager;
	
	@Resource(name="loginAccountChangeManager")
	private LoginAccountChangeManager loginAccountChangeManager;
	
	@Resource(name="workflowManager")
	private WorkflowManager workflowManager;
	
	@Resource(name="loginAccountDao")
	public void setDao(Dao<LoginAccount> dao) {
		this.dao = dao;
	}
	
	private LoginAccountDao getDao(){
		return (LoginAccountDao)super.dao;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	public LoginAccount get( String name ){
		return getDao().get( name );
	}
	
	/**
	 * 根据电子邮箱查登录帐号
	 */
	public LoginAccount getByEmail(String email){
		return getDao().getByEmail(email);
	}
	
	public LoginAccount getLight( String name ){
		return getDao().getWithPartyIdentity( name ); //TODO
	}
	
	public LoginAccount getWithParty( String name ){
		LoginAccount result = getDao().getWithPartyIdentity( name );
		/*
		if( result != null && result.getParty_id() != null ){
			Member member = memberManager.getLightMember( result.getParty_id() );
			Party party = member;
			if( party == null ){
				party = partyManager.getOrganization(result.getParty_id());
			}else{
				//取会员业务角色
				member.setPartyRoles( partyRoleManager.list( member ));
			}
			result.setParty(party);
		}
		*/
		return result;
	}

	@Override
	public void initLoginAccount() {
		LoginAccount root = getDao().get( ROOT_LOGIN_NAME );
		if( root == null ){
			root = new LoginAccount( );
			root.setCreateDate( new Date() );
			root.setLoginName(ROOT_LOGIN_NAME);
			root.setStatus(AuditStatus.EFFECTIVE);
			root.setType(LoginAccountType.ADMIN);
			Role superRole = roleManager.initRole(true);
			root.addRole(superRole); 
			root.setPassword(CryptographyUtil.hashMd5Hex(DEFAULT_PASSWORD ) );
			root.setDescription("系统初始化自动创建。");
			
			try{
				save(root);
				log.info("成功初始化系统管理员[{}]！",ROOT_LOGIN_NAME);
			}catch( Exception e ){
				String msg = "初始化系统账户异常："+e.getMessage();
				log.error( msg );
				throw new ApplicationException( msg );
			}
		}
	}
	
	/**
	 * 更新密码
	 */
	public void updatePassword( String name,String password,String newPassword ){
		if( password == null || newPassword == null  )
			throw new ApplicationException("原密码或新密码为空！");
		
		LoginAccount account = getDao().get(name);
		if( account == null )
			throw new ApplicationException("找不到名称为"+name+"的账号！");
		
		String hash_pwd =  CryptographyUtil.hashMd5Hex( password );
		String hash_new_pwd = CryptographyUtil.hashMd5Hex( newPassword );
		
		if( !account.getPassword().equals( hash_pwd ))
			throw new ApplicationException("原密码不正确！");
		
		account.setPassword( hash_new_pwd  );
		account.setLastUpdatePasswordTime(Calendar.getInstance());//密码更新时间
		super.dao.update( account );
	}
	
	/**
	 * 重置密码
	 * @param name 登录帐号
	 * @param newPassword 新密码
	 */
	public void resetPassword( String name,String newPassword ){
		LoginAccount account = getDao().get(name);
		if( account == null )
			throw new ApplicationException("找不到名称为"+name+"的账号！");
		
		String hash_new_pwd = CryptographyUtil.hashMd5Hex( newPassword );

		account.setPassword( hash_new_pwd  );
		account.setLastUpdatePasswordTime(Calendar.getInstance());//密码更新时间
		super.dao.update( account );
		
		log.info("系统登录帐号[{}]密码被重置！",name);
	}
	
	public void save( LoginAccount account,LoginAccount operator ){
		if( account == null )
			throw new ApplicationException( "数据不完整，LoginAccount 为空！" );
		
		boolean inserted = account.isInsert();
		if( inserted ){
			account.setStatus(AuditStatus.NEW);
			account.setCreateDate( new Date() );
			account.setPassword( CryptographyUtil.hashMd5Hex( DEFAULT_PASSWORD ) );
			if( account.getType() == null )
				account.setType(LoginAccount.LoginAccountType.ADMIN);
		}
		
		if( account.getRoles() != null ){
			Set<Role> dbRoles = new HashSet<Role>();
			for( Role role:account.getRoles() ){
				if( role == null )
					continue;
				Role dbRole = roleManager.get(role);
				if( dbRole == null )
					throw new ApplicationException( "所选角色["+role.getId()+"]已不存在！" );
				dbRoles.add(dbRole);
				
			}
			account.setRoles(dbRoles);
		}
		
		LoginAccount dbAcc = getDao().get( account.getLoginName() );
		LoginAccount originalAcc = null,newAcc = account;
		if( dbAcc != null )
			originalAcc = dbAcc.clone();
		
		if( dbAcc == null && !inserted )
			throw new ApplicationException( "登录账号已不存在，不能进行修改操作！" );
		
		if( dbAcc != null && !dbAcc.getIdentity().equals(account.getIdentity()))
			throw new ApplicationException( "登录账号名'"+account.getLoginName()+"'已存在！" );
		
		if( dbAcc != null ){
			if( !dbAcc.isEffective() ){
				dbAcc.setPartyId(account.getPartyId());
				dbAcc.setDescription( account.getDescription() );
				dbAcc.setPeriod( account.getPeriod() );
				dbAcc.setMobile(account.getMobile());
				dbAcc.setEmail(account.getEmail());
				dbAcc.setRoles( account.getRoles() );
				getDao().update( dbAcc );
			}
			newAcc.setStatus( dbAcc.getStatus() );
			newAcc.setCreateDate(dbAcc.getCreateDate());
		}else if( inserted ){
			getDao().create( account );
		}
		
		//变更记录
		String remark = null;
		AuditStatus status = account.getStatus();
		EntityChange.Type type = EntityChange.Type.CREATE;
		if( AuditStatus.NEW.equals( status )){
			type = EntityChange.Type.CREATE;
		}else if(AuditStatus.EFFECTIVE.equals(status) ){
			type = EntityChange.Type.UPDATE;
			remark = "变更登录帐户";
		}
		
		LoginAccountChange lac =loginAccountChangeManager.save(
				operator,type,originalAcc,newAcc,remark);
		
		//启动工作流
		startWorkflow(lac,newAcc,operator);
	}
	
	/**
	 * 启动工作流
	 */
	protected void startWorkflow(LoginAccountChange lac
			,LoginAccount account,LoginAccount operator){
		String bizName = "登录账号审核流程_"+account.getLoginName()
				+"_"+operator.getLoginName();
		ProcessInstance pi = workflowManager.start(WorkflowType.SEC_ACCT.getName()
				,lac.getId(),bizName,operator.getLoginName(),null);
		
		//有经办人，直接提交到下一个处理流程（启动流程后，根据 bizKey 查询Task，然后提交 ）
		workflowManager.claimAndComplete(pi.getProcessInstanceId(), operator.getLoginName() );
	}
	
	public void remove( Serializable identity ){
		LoginAccount db_account = dao.read(identity);
		if( db_account != null && ROOT_LOGIN_NAME.equals(db_account.getLoginName()) )
			throw new ApplicationException("系统超级管理员账户不允许删除！");

		//String name = db_account.getLoginName();
		super.remove(identity);
	}
	
	public void remove(LoginAccount account,LoginAccount operator,String remark) {
		LoginAccount dbAcc = dao.read(account.getId());
		if( dbAcc == null )
			throw new ApplicationException("登录帐号不存在！");
		
		if( ROOT_LOGIN_NAME.equals(dbAcc.getLoginName()) )
			throw new ApplicationException("系统超级管理员账户不允许删除！");
		
		if( remark == null )
			remark = "删除角色";
		LoginAccount newAcc = dbAcc.clone();
		newAcc.setStatus(AuditStatus.DELETE);
		loginAccountChangeManager.save(operator
				,EntityChange.Type.DELETE,dbAcc, newAcc,remark);
	}
	
	public void remove( LoginAccount account ){
		if( account != null )
			remove( account.getIdentity() );
	}
	
	public List<LoginAccount> listAdmin( ){
		ListPage<LoginAccount> page = new ListPage<LoginAccount>( 1024 );
		LoginAccount t = new LoginAccount();
		t.setType(LoginAccountType.ADMIN);
		page = getDao().listLight(page,t);
		return page.getList();
	}

	@Override
	public List<LoginAccount> listByPermission(Permission permission) {
		return getDao().listByPermission(permission);
	}
	
	public List<LoginAccount> listByPermission(String token ){
		Permission p = new Permission(token);
		return listByPermission( p );
	}
	
	/**
	 * 根据角色ID查询有效登录帐户
	 * @param id 角色ID
	 * @return 有效登录帐户
	 */
	public List<LoginAccount> listByRole(String id ){
		Role role = new Role();
		role.setId(id);
		
		return getDao().listByRole( AuditStatus.EFFECTIVE,role );
	}
	
	@Override
	public ListPage<LoginAccount> list(ListPage<LoginAccount> page,
			LoginAccount la) {
		return getDao().list(page, la);
	}
	
	public void toggleCa( LoginAccount account ,Access access ){
		if( account == null || account.getIdentity() == null )
			return ;
		
		LoginAccount old = this.get(account);
		if( old == null ){
			log.warn("开启或禁用CA，找不到相关登录账户！");
			throw new ApplicationException("找不到相关数据！");
		}
		
		boolean close = old.isUseCa();
		old.setUseCa( !close );
		
		this.dao.update( old );
		
		String desc = "成功"+(close?"禁用":"启用")+"帐号["+old.getLoginName()+"]CA登录！";
		log.info( desc );
		if( access != null ){
			//access.setType( AccessType.SECURITY_ACCOUNT_CA );
			//access.setDescription(desc);
			//this.accessManager.save( access );
		}
	}
	@Override
	public List<LoginAccount> listByParty(Party party) {
		LoginAccount account = new LoginAccount();
		account.setParty( party );
		
		ListPage<LoginAccount> page = new ListPage<LoginAccount>();
		page.setSize( Integer.MAX_VALUE );
		dao.list(page,account);
		
		return page.getList();
	}
	
	/**
	 * 根据ID查询有效登录帐户
	 * @param ids 登录帐户ID
	 */
	public List<LoginAccount> listByIds(Object[] ids ){
		if( ids == null )
			return null;
		
		ListPage<LoginAccount> page = new ListPage<LoginAccount>(100);
		SearchCondition sc = new SearchCondition();
		sc.in("id", ids);
		
		return getDao().list(page, sc).getList();
	}
	
	/**
	 * 登录失败锁定时间
	 */
	public boolean updateLockTime( Long id ){
		if( id == null)
			throw new ApplicationException("参数错误！");
		
		return getDao().updateLockTime(id,Calendar.getInstance()) > 0;
	}
	
	/**
	 * 解除登录失败锁定时间
	 */
	public void releaseLockTime( LoginAccount la ){
		if( la == null || la.getIdentity() == null )
			throw new ApplicationException("参数错误！");
		
		this.getDao().cleanLockTime(la.getId());
	}
}
