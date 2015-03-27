package com.google.code.lightssh.project.param.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.common.model.Period;

/**
 * 系统参数
 *
 */
@Entity
@Table( name="T_SYSTEM_PARAM",uniqueConstraints={
		@UniqueConstraint(columnNames={"GROUP_NAME","NAME"})} )
public class SystemParam extends UUIDModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 默认组名
	 */
	public static final String DEFAULT_GROUP_NAME = "DEFAULT";
	
	/**
	 * 子系统名称
	 */
	@Column( name="SYSTEM",length=20 )
	private String system;

	/**
	 * 组名
	 */
	@Column( name="GROUP_NAME",length=100 )
	private String group;
	
	/**
	 * 参数名
	 */
	@Column( name="NAME",length=100 )
	private String name;
	
	/**
	 * 参数值
	 */
	@Column( name="VALUE",length=4000 )
	private String value;
	
	/**
	 * 是否有效
	 */
	@Column( name="ENABLED" )
	private Boolean enabled;
	
	/**
	 * 使用期限
	 */
	@Embedded
	private Period period;
	
	/**
	 * 最后更新时间
	 */
	@Column( name="LAST_UPDATE_TIME",columnDefinition="DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTime;
	
	/**
	 * 备注
	 */
	@Column( name="DESCRIPTION",length=500 )
	private String description;
	
	/**
	 * 只读
	 */
	@Column( name="READONLY" )
	private Boolean readonly;
	
	/**
	 * 是否只读
	 */
	public boolean isReadonly( ){
		return Boolean.TRUE.equals( this.readonly );
	}
	
	/**
	 * 是否有效
	 */
	public boolean isEnabled( ){
		return Boolean.TRUE.equals( this.enabled );
	}
	
	/**
	 * 是否过期
	 */
	public boolean isExpired( ){
		return period==null || !period.isExpired(new Date() );
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result
				+ ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemParam other = (SystemParam) obj;
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
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SystemParam [description=" + description + ", enabled="
				+ enabled + ", group=" + group + ", lastUpdateTime="
				+ lastUpdateTime + ", name=" + name + ", period=" + period
				+ ", value=" + value + "]";
	}

}
