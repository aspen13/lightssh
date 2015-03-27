package com.google.code.lightssh.project.security.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.cxf.common.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.json.annotations.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.CryptographyUtil;
import com.google.code.lightssh.common.util.IoSerialUtil;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.common.web.SessionKey;
import com.google.code.lightssh.project.mail.service.EmailSendManager;
import com.google.code.lightssh.project.party.service.PartyManager;
import com.google.code.lightssh.project.security.entity.AuthorizedTicket;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.LoginAccountAudit;
import com.google.code.lightssh.project.security.entity.LoginAccountChange;
import com.google.code.lightssh.project.security.entity.Permission;
import com.google.code.lightssh.project.security.service.AuthorizedResourceManager;
import com.google.code.lightssh.project.security.service.AuthorizedTicketManager;
import com.google.code.lightssh.project.security.service.LoginAccountAuditManager;
import com.google.code.lightssh.project.security.service.LoginAccountChangeManager;
import com.google.code.lightssh.project.security.service.LoginAccountManager;
import com.google.code.lightssh.project.security.service.PermissionManager;
import com.google.code.lightssh.project.util.constant.AuditResult;
import com.google.code.lightssh.project.util.constant.WorkflowConstant;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * LoginAccount Action
 * 
 * @author YangXiaojin
 *
 */
@Component( "loginAccountAction" )
@Scope("prototype")
public class LoginAccountAction extends GenericAction<LoginAccount>{
	
	private static final long serialVersionUID = 2391150894472042768L;

	private static Logger log = LoggerFactory.getLogger( LoginAccountAction.class );
	
	private final String[] AUTH_TYPE_VALUES={"hasAuth","noneAuth","canAuth","genAuth"};
	
	@Resource( name = "partyManager" )
	private PartyManager partyManager;

	@Resource( name = "emailSendManager" )
	private EmailSendManager emailSendManager;
	
	@Resource( name = "loginAccountChangeManager" )
	private LoginAccountChangeManager loginAccountChangeManager;
	
	@Resource( name = "loginAccountAuditManager" )
	private LoginAccountAuditManager loginAccountAuditManager;
	
	@Resource( name = "permissionManager" )
	private PermissionManager permissionManager;
	
	@Resource( name = "authorizedResourceManager" )
	private AuthorizedResourceManager authorizedResourceManager;
	
	@Resource( name = "authorizedTicketManager" )
	private AuthorizedTicketManager authorizedTicketManager;
	
	private LoginAccount account;
	
	private LoginAccountChange accountChange;
	
	private LoginAccountAudit accountAudit;
	
	private List<String> passwords = new ArrayList<String>();

	private boolean passed;
	
	private ListPage<LoginAccountChange> lacPage;
	
	private ListPage<LoginAccountAudit> laaPage;

	/**
	 * 安全值,提示信息(JSON)
	 */
	private String safeMessage;
	
	/**
	 * 授权JSON返回
	 */
	private String authType,ticket;
	
	@Resource( name = "loginAccountManager" )
	public void setLoginAccountManager( LoginAccountManager manager ) {
		super.manager = manager;
	}

	public LoginAccountManager getManager() {
		return ( LoginAccountManager ) super.manager;
	}

	public List<String> getPasswords() {
		return passwords;
	}

	public void setPasswords( List<String> passwords ) {
		this.passwords = passwords;
	}

	public LoginAccount getAccount() {
		this.account = super.model;
		return account;
	}

	public void setAccount( LoginAccount account ) {
		this.account = account;
		super.model = this.account;
	}

	@JSON(name="message")
	public String getSafeMessage() {
		return safeMessage;
	}

	public void setSafeMessage( String safeMessage ) {
		this.safeMessage = safeMessage;
	}

	@JSON(name="passed")
	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public ListPage<LoginAccountChange> getLacPage() {
		return lacPage;
	}

	public void setLacPage(ListPage<LoginAccountChange> lacPage) {
		this.lacPage = lacPage;
	}

	public LoginAccountChange getAccountChange() {
		return accountChange;
	}

	public void setAccountChange(LoginAccountChange accountChange) {
		this.accountChange = accountChange;
	}

	public LoginAccountAudit getAccountAudit() {
		return accountAudit;
	}

	public void setAccountAudit(LoginAccountAudit accountAudit) {
		this.accountAudit = accountAudit;
	}

	public ListPage<LoginAccountAudit> getLaaPage() {
		return laaPage;
	}

	public void setLaaPage(ListPage<LoginAccountAudit> laaPage) {
		this.laaPage = laaPage;
	}

	@JSON(name="authType")
	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@JSON(name="ticket")
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String list() {
		if ( page == null )
			page = new ListPage<LoginAccount>();
		page.addAscending( "createDate" );

		if ( this.isPost() )
			cacheRequestParams();
		
		page = manager.list( page , account );
		
		return SUCCESS;
	}
	
	/**
	 * 弹出窗口
	 * @return
	 */
	public String popup( ){
		if ( page == null )
			page = new ListPage<LoginAccount>();
		page.addAscending( "createDate" );
		page.setSize(10); //每页10条
		
		page = manager.list( page , account );
		
		if( "true".equalsIgnoreCase(request.getParameter("multi")) )
			return "multi"; //多选
		
		return SUCCESS;
	}

	public String edit() {
		if ( request.getParameter( "password" ) != null )
			return "password";

		if( account != null && account.getId() != null ){
			setAccount(this.getManager().get(account));
			if( account != null && !StringUtils.isEmpty(account.getPartyId()) ){
				account.setParty(partyManager.get( account.getPartyId() ));
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 显示登录账户信息
	 */
	public String view( ){
		if( account == null || (account.getIdentity() == null 
				&& StringUtil.clean(account.getLoginName()) == null) )
			return INPUT;
		
		if( account.getIdentity() != null )
			setAccount( getManager().get( account ));
		else
			setAccount(getManager().get( account.getLoginName() ));
		
		if( account == null )
			return INPUT;
		
		return SUCCESS;
	}
	
	/**
	 * 工作流显示业务数据
	 */
	public String workflowView(){
		String bizKey = request.getParameter( WorkflowConstant.BIZ_KEY_NAME );
		
		//this.setRole( this.getManager().get(bizKey));
		if( StringUtils.isEmpty(bizKey) ){
			this.addActionError("业务参数为空！");
			return SUCCESS;
		}
		
		LoginAccountChange rc = loginAccountChangeManager.get(bizKey);
		if( rc == null ){
			this.addActionError("登录帐号数据["+bizKey+"]不存在！");
			return SUCCESS;
		}
		
		this.setAccountChange(rc);
		todoAudit();
		
		return SUCCESS;
	}

	public String save() {
		if( account == null ){
			this.addActionError("参数错误！");
			return INPUT;
		}
		
		if ( account.getPeriod() != null ) {
			if( account.getPeriod().getEnd() != null ){
				// 结束时间 yyyy-MM-dd 23:59:59
				Calendar calendar = Calendar.getInstance();
				calendar.setTime( account.getPeriod().getEnd() );
				calendar.add( Calendar.DAY_OF_MONTH, 1 );
				calendar.add( Calendar.MILLISECOND, -1 );
				account.getPeriod().setEnd( calendar.getTime() );
			}
			
			if( account.getPeriod().getStart() == null 
					&& account.getPeriod().getEnd() == null )
				account.setPeriod(null);
		}

		if ( account.getParty() != null )
			account.setPartyId( account.getParty().getId() );
		
		try{
			this.getManager().save(account,getLoginAccount());
			this.saveSuccessMessage("变更登录帐户，需要在审核通过后生效！");
		}catch( Exception e ){
			addActionError("保存异常："+e.getMessage());
			return INPUT;
		}
		
		String saveAndNext = request.getParameter("saveAndNext");
        if( saveAndNext != null && !"".equals( saveAndNext.trim() ) ){
        	return NEXT;
        }else{        	
        	return SUCCESS;
        }
	}
	
    public String delete( ){
        if( account == null || account.getIdentity() == null ){
        	this.saveErrorMessage("参数错误！");
            return INPUT;
        }
       
        try{
        	this.getManager().remove( account,getLoginAccount(),null );
        	saveSuccessMessage( "删除操作已受理，审核通过后生效！" );
        }catch( Exception e ){ 
            saveErrorMessage( "删除发生异常：" + e.getMessage() );
            return INPUT;
        } 
       
        return SUCCESS;
    }
	
	/**
	 * 待审核列表
	 */
	public String todoAuditList(){
		if(lacPage == null )
			lacPage = new ListPage<LoginAccountChange>();
		
		lacPage.addDescending("createdTime");
		lacPage = loginAccountChangeManager.listTodoAudit(lacPage,accountChange);
		return SUCCESS;
	}
	
	/**
	 * 待审核
	 */
	public String todoAudit(){
		this.accountChange = loginAccountChangeManager.get(accountChange);
		if( accountChange == null ){
			this.saveErrorMessage("参数错误，无法查找到变更信息！");
			return INPUT;
		}
		this.setAccount(accountChange.getLoginAccount());
		
		byte[] originalObject = accountChange.getOriginalObject();
		if( originalObject != null )
			request.setAttribute("originalObject", IoSerialUtil.deserialize(originalObject));
		byte[] newObject = accountChange.getNewObject();
		if( newObject != null )
			request.setAttribute("newObject", IoSerialUtil.deserialize(newObject));
		
		return this.edit();
	}
	
	/**
	 * 审核
	 */
	public String audit(){
		if( accountAudit == null || accountAudit == null ){
			this.saveErrorMessage("审核参数为空！");
			return ERROR;
		}
		
		accountAudit.setLoginAccountChange(accountChange);//关联角色变更
		
		accountAudit.setUser(this.getLoginAccount());
		boolean passed = request.getParameter("passed")!=null;
		accountAudit.setResult(passed?AuditResult.LAST_AUDIT_PASSED:AuditResult.LAST_AUDIT_REJECT);
		
		try{
			this.loginAccountAuditManager.audit(accountAudit,accountChange);
		}catch(Exception e ){
			this.saveErrorMessage("审核操作异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 审核结果
	 */
	public String auditList( ){
		if( laaPage == null )
			laaPage = new ListPage<LoginAccountAudit>();
		
		laaPage.addDescending("createdTime");
		loginAccountAuditManager.list(laaPage,accountAudit);
		return SUCCESS;
	}

	/**
	 * update password
	 */
	public String password(){
		if( super.isGet() )
			return INPUT;
		
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		
		if( loginName == null )
			return LOGIN;
		
		try{
			this.getManager().updatePassword( loginName,
					passwords.get( 0 ), passwords.get( 1 ) );
			super.saveSuccessMessage("修改密码成功！");
		}catch( Exception e ){
			super.saveErrorMessage( e.getMessage() );
		}
		
		return INPUT;
	}

	/**
	 * 查询当前登录帐号信息
	 */
	public String myprofile( ){
		if( this.getLoginUser() == null )
			return LOGIN;
		
		this.setAccount( getManager().get( getLoginUser() ) );
		if( this.account == null )
			return LOGIN;
		
		return SUCCESS;
	}
	
	/**
	 * 重置密码
	 */
	public String prereset(){
		if( account == null || account.getLoginName() == null ){
			this.saveErrorMessage("重置密码参数错误！");
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 重置密码
	 */
	public String resetpassword(){
		if( account == null || account.getLoginName() == null
				|| passwords == null || passwords.isEmpty() ){
			this.addActionError("请求参数错误！");
			return INPUT;
		}
		
		if( this.getLoginUser().equals(account.getLoginName()) ){
			this.addActionError("不能重置当前登录用户的密码！");
			return INPUT;
		}
		
		try{
			getManager().resetPassword(account.getLoginName(),passwords.get(0));
		}catch( Exception e ){
			super.addActionError("重置密码异常："+e.getMessage());
		}
		
		saveSuccessMessage("帐号["+account.getLoginName()+"]登录密码已被成功重置！");
		log.info("操作员[{}]成功重置帐号[{}]登录密码！"
				,getLoginUser(),account.getLoginName());
		
		return SUCCESS;
	}
	
	/**
	 * 帐号恢复
	 */
	public String recovery(){
		SecurityUtils.getSubject().logout();
		
		//clean Session
		request.getSession().setAttribute(
				SessionKey.LOGIN_ACCOUNT,null);
		return SUCCESS;
	}
	
	/**
	 * 忘记密码
	 */
	public String forgotpassword(){
		if( this.isGet() )
			return INPUT;
		
		if( account == null || account.getLoginName() == null )
			return INPUT;
		
		String subject = account.getLoginName();
		if( subject.indexOf("@") > 0 )
			setAccount( getManager().getByEmail(subject) );
		else
			setAccount( getManager().get(subject) );
		
		if( account ==  null ){
			this.saveErrorMessage("没有找到与该登录用户名或电子邮件地址关联的帐户信息！");
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 通过邮箱找回密码
	 */
	public String emailrecovery( ){
		if( account == null || account.getLoginName() == null )
			return INPUT;
		
		cleanCaptcha();//防止重复提交
		
		LoginAccount la = this.getManager().get(account.getLoginName());
		if( la == null ){
			this.addActionError("无法找回与用户"+account.getLoginName()+"匹配的帐号信息！");
			return INPUT;
		}
		
		this.setAccount(la);
		StringBuffer url = new StringBuffer();
		url.append("HTTP/1.1".equals(request.getProtocol())?"http://":"https://");
		url.append( request.getRemoteHost());
		url.append( request.getLocalPort()!=80?":"+request.getLocalPort():"");
		url.append( request.getContextPath() );
		
		url.append("/security/recovery/emailretrieve.do");
		url.append("?account.loginName="+account.getLoginName());
		url.append("&message=");
		url.append(CryptographyUtil.hashSha1Hex(account.getEmail()+account.getPassword()));
		try{
			emailSendManager.forgotPassword(url.toString(),account);
		}catch( Exception e ){
			this.addActionError("找回密码异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 邮件重置密码
	 */
	public String emailretrieve(){
		String message = request.getParameter("message");
		if( account == null || account.getLoginName() == null || message == null ){
			this.addActionError("错误的链接地址！");
			return INPUT;
		}
		
		LoginAccount la = this.getManager().get(account.getLoginName());
		if( la == null ){
			this.addActionError("非法或过期的链接地址！");
			return INPUT;
		}
		this.setAccount(la);
		
		String checkMsg = CryptographyUtil.hashSha1Hex(
				account.getEmail()+account.getPassword());
		if( !message.equals(checkMsg) ){
			this.addActionError("非法或过期的链接地址！");
			return INPUT;
		}
		
		//用于重置密码的"安全值"
		safeMessage = CryptographyUtil.hashSha1Hex(
				request.getSession().getId() 
				+ account.getLoginName() + account.getPassword());
		
		return SUCCESS;
	}
	
	/**
	 * 重置密码
	 */
	public String retrieve( ){
		if( safeMessage == null || account == null 
				|| account.getLoginName() == null )
			return ERROR;
		
		setAccount( this.getManager().get(account.getLoginName()) );
		if( account == null )
			return ERROR;
		
		if( passwords == null || passwords.isEmpty() ){
			this.addFieldError("passwords[0]","未设置新密码！");
			return INPUT;
		}

		//用于重置密码的"安全值"
		String checkValue = CryptographyUtil.hashSha1Hex(
					request.getSession().getId() 
					+ account.getLoginName() + account.getPassword());
		
		if( !safeMessage.equals(checkValue) ){
			return ERROR;
		}
		
		try{
			getManager().resetPassword(account.getLoginName(),passwords.get(0));
			
			login(account);//登录
			this.cleanCaptcha(); //防止重复登录
		}catch( Exception e ){
			log.error("重设帐号[{}]密码异常：",account.getLoginName(),e);
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 忘记用户名
	 */
	public String forgotusername(){
		if( this.isGet() )
			return INPUT;
		
		cleanCaptcha();//防止重复提交
		
		if( account == null || account.getEmail() == null )
			return INPUT;
		
		try{
			
			String email = account.getEmail();
			setAccount(getManager().getByEmail( email ));
			
			if( account != null ){
				//send username to email address
				emailSendManager.forgotUsername(account.getLoginName(),email);
			}
		}catch( Exception e ){
			log.warn("找回用户名过程出现异常：",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 释放登录锁
	 */
	public String releaselock( ){
		if( account == null || account.getIdentity() == null )
			return INPUT;
		
		try{
			this.getManager().releaseLockTime(account);
		}catch( Exception e ){
			this.saveErrorMessage("释放登录锁异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 校验当前登录用户密码
	 */
	public String validatePassword(){
		String password = request.getParameter("password");
		if( getLoginAccount() != null && getLoginAccount().getPassword() != null 
				&& getLoginAccount().getPassword().equals( password ) ){
			this.passed = true;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 验证授权
	 */
	public String validateAuth(){
		String targetUrl = request.getParameter("targetUrl");
		//{"hasAuth"(存在权限),"noneAuth"无权限,"canAuth"可以授权,"genAuth"已授权}
		safeMessage = "";
		authType = AUTH_TYPE_VALUES[1];
		
		if( !StringUtils.isEmpty(targetUrl)){
			Permission permission = permissionManager.getByUrlWithRegexp(targetUrl);
			if( permission != null){
				String token = permission.getToken();
				if(SecurityUtils.getSubject().isPermitted( token ) ){
					authType = AUTH_TYPE_VALUES[0];
				}else if( authorizedResourceManager.canAuthorized(targetUrl) ){
					authType = AUTH_TYPE_VALUES[2];
					
					//是否已存在授权
					AuthorizedTicket ticket = authorizedTicketManager.get(
							token,targetUrl,request.getSession().getId() );
					if( ticket != null && ticket.isEffective() ){
						authType = AUTH_TYPE_VALUES[3];
						this.ticket = ticket.getId();
					}
				}
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 授权资源
	 */
	public String authResource(){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String targetUrl = request.getParameter("targetUrl");
		if( StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ){
			this.safeMessage = "用户名或密码为空！";
			return SUCCESS;
		}
		
		if( StringUtils.isEmpty(targetUrl) ){
			this.safeMessage = "授权资源为空！";
			return SUCCESS;
		}
		Permission per = permissionManager.getByUrlWithRegexp(targetUrl);
		if( per == null ){
			this.safeMessage = "授权资源为非保护，可直接访问！";
			this.passed = true;
			return SUCCESS;
		}
		
		account = getManager().get(username);
		if( account == null || !account.getPassword().equals(password) ){
			this.safeMessage = "错误的用户或密码！";
			return SUCCESS;
		}
		
		if(!account.isEffective() || !account.isExpired() ){
			this.safeMessage = "用户被禁用或已过期！";
			return SUCCESS;
		}
		
		//是否有权限
		if( account.hasPermission(per.getToken()) ){
			this.passed = true; 
			try{
				AuthorizedTicket ticket = authorizedTicketManager.authTicket(
						targetUrl,username,per.getToken(),AuthorizedTicket.Scope.ONCE
						,request.getSession().getId(),getLoginUser() );
				this.ticket = ticket.getId();
			}catch( Exception e ){
				this.safeMessage = "授权异常：" + e.getMessage();
			}
		}else{
			this.safeMessage = "授权用户无权限！";
		}
		
		return SUCCESS;
	}


}
