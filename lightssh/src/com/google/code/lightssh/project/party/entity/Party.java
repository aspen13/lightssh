package com.google.code.lightssh.project.party.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.model.Nameable;
import com.google.code.lightssh.common.model.Sequenceable;
import com.google.code.lightssh.common.util.StringUtil;

/**
 * 社体
 * @author Aspen
 *
 */
//@MappedSuperclass
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Party implements Persistence<String>,Sequenceable,Cloneable,Nameable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * CODE
	 */
	@Id
	@Column( name="ID" )
	private String id;
	
	/**
	 * 名称
	 */
	@Column( name="NAME",unique=false,length=100 )
	private String name;
	
	/**
	 * enabled
	 */
	@Column( name="ENABLED" )
	protected Boolean enabled;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	protected String description;
	
	@Override
	public String getIdentity() {
		return this.id;
	}

	@Override
	public String getSequenceKey() {
		return "PARTY";
	}

	@Override
	public int getSequenceLength() {
		return 5;
	}

	@Override
	public int getSequenceStep() {
		return 1;
	}
	
	public boolean isInsert( ){
		return StringUtil.clean(this.id) == null;
	}
	
	public void postInsertFailure( ){
		this.id = null;
	}
	
	public Party clone(){
		 try{
			 return (Party)super.clone();
		 }catch( CloneNotSupportedException e ){
			 return null;
		 }
	}
	
	/**
	 * 是否可用
	 * @return boolean
	 */
	public boolean isEnabled( ){
		return Boolean.TRUE.equals( this.enabled );
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Party other = (Party) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
