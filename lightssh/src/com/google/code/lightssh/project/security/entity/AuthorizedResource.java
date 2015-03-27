package com.google.code.lightssh.project.security.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 授权资源
 * @author YangXiaojin
 *
 */
@Entity
@Table(name="T_SECURITY_AUTH_RES")
public class AuthorizedResource implements Persistence<String>{

	private static final long serialVersionUID = 7062629970630696987L;
	
	/**
	 * 资源URL
	 */
	@Id
	@Column(name="URL",length=300)
	private String url;
	
	/**
	 * 是否可用
	 */
	@Column(name="ENABLED")
	private Boolean enabled;
	
	/**
	 * 创建日期
	 */
	@Column(name="CREATED_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar createdTime;
	
	/**
	 * 备注
	 */
	@Column(name="DESCRIPTION",length=300)
	private String description;
	
	/**
	 * 是否可用
	 */
	public boolean isEnabled(){
		return Boolean.TRUE.equals(this.enabled);
	}

	@Override
	public String getIdentity() {
		return this.url;
	}

	@Override
	public boolean isInsert() {
		return this.url == null ;
	}

	@Override
	public void postInsertFailure() {
	}

	@Override
	public void preInsert() {
		if( this.createdTime == null )
			this.createdTime = Calendar.getInstance();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
