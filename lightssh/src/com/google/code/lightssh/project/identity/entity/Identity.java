package com.google.code.lightssh.project.identity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_IDENTITY",uniqueConstraints={@UniqueConstraint(columnNames={"type","value"})} )
public class Identity implements Persistence<Long>{
	
	private static final long serialVersionUID = 1L;
	
	public static final String CLASS_SEPARATOR = "#";
	
	/**
	 * identity
	 */
	@Id
	@Column( name="ID" )
	@SequenceGenerator(name="identity_id_seq", sequenceName="T_IDENTITY_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="identity_id_seq")
	protected Long id;

	public Long getIdentity() {
		return this.id;
	}
	
	public boolean isInsert( ){
		return this.getIdentity() == null;
	}
	
	public void postInsertFailure( ){
		this.id = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void preInsert( ){
		//do nothing
	}
		
	/**
	 * 类型
	 */
	@Column(name="TYPE")
	@Enumerated(value=EnumType.STRING)
	private IdentityType type;
	
	/**
	 * 扩展名
	 */
	@Column(name="EXT_NAME")
	private String extName;
	
	/**
	 * 值
	 */
	@Column(name="VALUE")
	private String value;
	
	public Identity() {
		super();
	}
	
	public Identity( Persistence<?> model) {
		this.type = IdentityType.CLASS;
		this.value = model.getClass().getCanonicalName() + 
			CLASS_SEPARATOR + model.getIdentity();
	}

	public IdentityType getType() {
		return type;
	}

	public void setType(IdentityType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	@Override
	public String toString() {
		return "Identity [type=" + type + ", extName=" + extName +", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extName == null) ? 0 : extName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Identity other = (Identity) obj;
		if (extName == null) {
			if (other.extName != null)
				return false;
		} else if (!extName.equals(other.extName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}	
	
}
