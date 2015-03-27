package com.google.code.lightssh.project.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SECURITY_PERMISSION")
public class Permission implements Persistence<String>{

	private static final long serialVersionUID = 1649939616381932770L;
	
	/**
	 * 权限名称
	 */
	@Id
	@Column( name="TOKEN",length=100 )
	private String token;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	private String description;
	
	/**
	 * TOKEN控制的资源(URL)
	 */
	@Column( name="URL",length=300 )
	private String url;
	
	public Permission(){
		
	}
	
	public Permission( String token ){
		this.token = token;
	}
	
	@Override
	public String getIdentity() {
		return this.token;
	}
	
	public void preInsert( ){
		//do nothing
	}
	
	public void postInsertFailure( ){
		//do nothing
	}
	
	public boolean isInsert( ){
		return this.token == null;
	}
	
	//-- getters and setters ---------------------------------------------------

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
