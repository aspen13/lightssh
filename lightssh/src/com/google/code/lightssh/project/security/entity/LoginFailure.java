package com.google.code.lightssh.project.security.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 登录失败记录
 * @author YangXiaojin
 *
 */
@Entity
@Table(name="T_SECURITY_LOGIN_FAILURE")
public class LoginFailure extends UUIDModel{

	private static final long serialVersionUID = -4589723187557143853L;
	
	/**
	 * 关联登录帐户
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="USER_ID")
	private LoginAccount account;
	
	/**
	 * 登录名称
	 */
	@Column(name="LOGIN_NAME",length=50)
	private String loginName;
	
	/**
	 * IP地址
	 */
	@Column(name="IP",length=20)
	private String ip;
	
	/**
	 * 失败次数
	 */
	@Column(name="FAILURE_COUNT")
	private Integer failureCount;
	
	/**
	 * Session ID
	 */
	@Column(name="SESSION_ID",length=50)
	private String sessionId;
	
	/**
	 * 最后一次更新时间
	 */
	@Column( name="LAST_UPDATE_TIME",columnDefinition="DATE" )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar lastUpdateTime;
	
	/**
	 * 增加失败次数
	 */
	public void incFailureCount(){
		if( this.failureCount == null )
			this.failureCount = 1;
		else
			this.failureCount++;
	}

	public LoginAccount getAccount() {
		return account;
	}

	public void setAccount(LoginAccount account) {
		this.account = account;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
