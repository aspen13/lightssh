package com.google.code.lightssh.project.message.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.common.model.Period;

/**
 * 消息订阅
 * @author Aspen
 * 
 */
@Entity
@Table(name="T_MSG_SUBSCRIPTION")
public class Subscription extends UUIDModel{

	private static final long serialVersionUID = 3826073871651109841L;
	
	/**
	 * 消息类型
	 */
	@ManyToOne
	@JoinColumn(name="CATALOG_ID")
	private Catalog catalog;
	
	/**
	 * 订阅类型
	 */
	@Column(name="REC_TYPE",length=20)
	@Enumerated(EnumType.STRING)
	private ReceiveType recType;
	
	/**
	 * 订阅类型值
	 */
	@Column(name="REC_VALUE",length=255)
	private String recValue;
	
	/**
	 * 创建人
	 */
	@Column(name="CREATOR",length=50)
	private String creator;
	
	/**
	 * 有效期
	 */
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="start", column=@Column(name="PERIOD_START")),
		@AttributeOverride(name="end", column=@Column(name="PERIOD_END"))})
	private Period period;
	
	/**
	 * 备注
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public ReceiveType getRecType() {
		return recType;
	}

	public void setRecType(ReceiveType recType) {
		this.recType = recType;
	}

	public String getRecValue() {
		return recValue;
	}

	public void setRecValue(String recValue) {
		this.recValue = recValue;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

}
