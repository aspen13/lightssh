package com.google.code.lightssh.project.security.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.base.BaseModel;
import com.google.code.lightssh.common.model.Loggable;
import com.google.code.lightssh.common.model.Period;
import com.google.code.lightssh.common.support.shiro.User;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.util.constant.AuditStatus;

/**
 * 登录账号
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SECURITY_LOGINACCOUNT" )
public class LoginAccount extends BaseModel implements User,Cloneable,Loggable{

	private static final long serialVersionUID = 8280727772996743600L;
	
	/**
	 * 登录帐户类型
	 */
	public enum LoginAccountType{
		ADMIN("交易所")
		,MEMBER("会员");
		
		private String value;
		
		LoginAccountType( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString(){
			return this.value;
		}
	}

	/**
	 * 登录名
	 */
	@Column(name="LOGIN_NAME",unique=true,nullable=false)
	private String loginName;
	
	/**
	 * 密码
	 */
	@Column(name="PASSWORD",unique=false,nullable=false)
	private String password;
	
	/**
	 * 手机号
	 */
	@Column(name="MOBILE",unique=true,length=20)
	private String mobile;
	
	/**
	 * 邮箱
	 */
	@Column(name="EMAIL",unique=true,length=255)
	private String email;
	
	/**
	 * 状态
	 */
	@Column( name="STATUS",length=50 )
	@Enumerated(EnumType.STRING)
	private AuditStatus status;
	
	/**
	 * 创建时间
	 */
	@Column( name="CREATE_DATE",nullable=false,columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Date createDate;
	
	/**
	 * 使用期限
	 */
	@Embedded
	private Period period;
	
	/**
	 * 权限角色
	 */
	@ManyToMany( fetch=FetchType.EAGER )
	@JoinTable( name="T_REF_LOGINACCOUNT_ROLE", 
			joinColumns=@JoinColumn( name="LOGINACCOUNT_ID"),
			inverseJoinColumns=@JoinColumn( name="ROLE_ID"))	
	private Set<Role> roles;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	private String description;
	
	/**
	 * 所属组织或人员
	 */
	//@ManyToOne( fetch=FetchType.LAZY )
	//@JoinColumn( name="PARTY_ID")
	@Transient
	private Party party;
	
	@Column( name="PARTY_ID")
	private String partyId;
	
	/**
	 * 登录账号类型
	 */
	@Column( name="TYPE",length=20 )
	@Enumerated(value=EnumType.STRING)
	private LoginAccountType type;
	
	/**
	 * 是否使用CA登录
	 */
	@Column( name="USE_CA" )
	private Boolean useCa;
	
	/**
	 * 最后一次更新密码时间
	 */
	@Column( name="LAST_UPDATE_PASSWORD_TIME" )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar lastUpdatePasswordTime;
	
	/**
	 * 最近一次登录错误锁定时间
	 */
	@Column( name="LAST_LOGIN_LOCK_TIME",columnDefinition="DATE" )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar lastLoginLockTime;
	
	/**
	 * 最近一次登录时间
	 */
	@Column( name="LAST_LOGIN_TIME",columnDefinition="DATE" )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar lastLoginTime;
	
	/**
	 * 使用期限
	 */
	@Transient
	private Period _createDatePeriod;
	
	public LoginAccount() {
		super();
	}
	
	public LoginAccount(Long id,String name) {
		this.loginName = name;
		this.id = id;
	}
	
	public String getUserId(){
		return this.getIdentity().toString();
	}
	
	public String getUserName(){
		return this.getLoginName();
	}

	/**
	 * 是否过期
	 * @return 过期返回false
	 */
	public boolean isExpired( ){
		return period!=null && period.isExpired(new Date() );
	}
	
	/**
	 * 是否有效
	 */
	public boolean isEnabled( ){
		return isEffective();
	}
	
	/**
	 * 是否有效
	 */
	public boolean isEffective( ){
		return AuditStatus.EFFECTIVE.equals(this.status);
	}
	
	/**
	 * 是否标记删除
	 */
	public boolean isRemoved(){
		return AuditStatus.DELETE.equals(this.status);
	}
	
	/**
	 * 设置时间区间
	 * @param start 起始时间
	 * @param end 结束时间
	 */
	public void setPeriod(Date start,Date end ){
		this.period = new Period(start,end);
	}
	
	/**
	 * 是否会员
	 */
	public boolean isMember(){
		return LoginAccountType.MEMBER.equals( this.type );
	}
	
	/**
	 * 添加角色
	 */
	public void addRole( Role role ){
		if( role == null )
			return;
		
		if( this.roles == null )
			roles = new HashSet<Role>();
		
		roles.add(role);
	}
	
	public String getSubjectMessage(){
		StringBuilder sb = new StringBuilder("");
		if( this.party != null){
			sb.append( party.getName() );
			if( !LoginAccountType.ADMIN.equals(this.type) )
				sb.append( "(" + party.getIdentity() + ")" );
		}
		
		return sb.toString();
	}
	
	/**
	 * 是否拥有某个角色
	 */
	public boolean hasRole( String roleName ){
		if( roleName == null || this.getRoles() == null 
				|| this.getRoles().isEmpty() )
			return false;
		
		for( Role role:this.roles ){
			if( role.getName().equals( roleName ) )
				return true;
		}
		
		return false;
	}
	
	/**
	 * 是否拥有某个权限
	 */
	public boolean hasPermission( String per ){
		if( per == null || this.getRoles() == null 
				|| this.getRoles().isEmpty() )
			return false;
		
		Collection<String> allPers = new ArrayList<String>();
		for( Role role:this.roles ){
			allPers.addAll(role.getPermissionsAsString());
		}
		
		for( String item:allPers )
			if( item.equals(per) )
				return true;
		
		return false;
	}
	
	/**
	 * 是否需要CA登录
	 */
	public boolean isUseCa( ){
		return Boolean.TRUE.equals(this.useCa);
	}
	
	public LoginAccount clone(){
		 try{
			 return (LoginAccount)super.clone();
		 }catch( CloneNotSupportedException e ){
			 return null;
		 }
	}
	
	//-- getters and setters --------------------------------------------------
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public LoginAccountType getType() {
		return type;
	}

	public void setType(LoginAccountType type) {
		this.type = type;
	}

	public Boolean getUseCa() {
		return useCa;
	}

	public void setUseCa(Boolean useCa) {
		this.useCa = useCa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile( String mobile ) {
		this.mobile = mobile;
	}

	public Calendar getLastUpdatePasswordTime() {
		return lastUpdatePasswordTime;
	}

	public void setLastUpdatePasswordTime(Calendar lastUpdatePasswordTime) {
		this.lastUpdatePasswordTime = lastUpdatePasswordTime;
	}

	public Calendar getLastLoginLockTime() {
		return lastLoginLockTime;
	}

	public void setLastLoginLockTime(Calendar lastLoginLockTime) {
		this.lastLoginLockTime = lastLoginLockTime;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public Calendar getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Calendar lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Period get_createDatePeriod() {
		return _createDatePeriod;
	}

	public void set_createDatePeriod(Period _createDatePeriod) {
		this._createDatePeriod = _createDatePeriod;
	}

	@Override
	public String logMessage() {
		return "登录帐号 [邮箱=" + email + ",状态=" + this.status
			+ ", 登录名=" + loginName + ",手机=" + mobile
			+ ", 部门编号=" + partyId + ",有效期=" + period + ",拥有角色="
			+ roles + "]";
	}

	@Override
	public String toString() {
		return "LoginAccount [email=" + email + ", enabled=" + this.isEnabled()
				+ ", loginName=" + loginName + ", mobile=" + mobile
				+ ", partyId=" + partyId + ", period=" + period + ", roles="
				+ roles + "]";
	}

}
