package com.google.code.lightssh.project.security.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 授权令牌
 * @author YangXiaojin
 *
 */
@Entity
@Table(name="T_SECURITY_AUTH_TICKET")
public class AuthorizedTicket extends UUIDModel{

	private static final long serialVersionUID = 6230671142737496554L;
	
	/**
	 * 令牌作用范围
	 */
	public enum Scope{
		ONCE("一次"),
		TIMES("次数"),
		PERIOD("一段时间"),
		SESSION("会话有效");
		
		private String value;
		
		Scope( String value ){
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
	 * 资源URL
	 */
	@Column(name="URL",length=300)
	private String url;
	
	/**
	 * 权限令牌
	 */
	@Column( name="TOKEN",length=100 )
	private String token;
	
	/**
	 * 授权范围
	 */
	@Column( name="SCOPE",length=20 )
	@Enumerated(EnumType.STRING)
	private Scope scope;
	
	/**
	 * SESSION ID值
	 */
	@Column( name="SESSION_ID",length=100 )
	private String sessionId;
	
	/**
	 * 授权用户
	 */
	@Column( name="LICENSOR",length=20 )
	private String licensor;
	
	/**
	 * 被授权用户
	 */
	@Column( name="GRANTOR",length=20 )
	private String grantor;
	
	/**
	 * 失效时间
	 */
	@Column(name="VALID_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar validTime;
	
	/**
	 * 最大授权次数
	 */
	@Column( name="MAX_COUNT",nullable=true )
	public Long maxCount;
	
	/**
	 * 实际授权次数
	 */
	@Column( name="AUTH_COUNT",nullable=true )
	public Long authCount;
	
	/**
	 * 是否有效
	 */
	public boolean isEffective( ){
		return isEffective(Calendar.getInstance());
	}
	
	/**
	 * 是否有效
	 */
	public boolean isEffective(Calendar now){
		if( Scope.TIMES.equals(scope) ){
			return this.authCount < this.maxCount;
		}else{
			return validTime == null || ( now != null 
				&& validTime.getTimeInMillis() >= now.getTimeInMillis());
		}
	}
	
	/**
	 * 是否一次有效
	 */
	public boolean isOnceScope(){
		return Scope.ONCE.equals(this.getScope());
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLicensor() {
		return licensor;
	}

	public void setLicensor(String licensor) {
		this.licensor = licensor;
	}

	public String getGrantor() {
		return grantor;
	}

	public void setGrantor(String grantor) {
		this.grantor = grantor;
	}

	public Calendar getValidTime() {
		return validTime;
	}

	public void setValidTime(Calendar validTime) {
		this.validTime = validTime;
	}

	public Long getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Long maxCount) {
		this.maxCount = maxCount;
	}

	public Long getAuthCount() {
		return authCount;
	}

	public void setAuthCount(Long authCount) {
		this.authCount = authCount;
	}

}
