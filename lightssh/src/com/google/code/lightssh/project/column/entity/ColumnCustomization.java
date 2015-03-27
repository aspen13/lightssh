package com.google.code.lightssh.project.column.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 用户定制化列
 * @author Aspen
 * @date 2013-10-24
 * 
 */
@Entity
@Table(name="T_COLUMN_CUSTOMIZATION")
public class ColumnCustomization extends UUIDModel{
	
	private static final long serialVersionUID = 5650569139163210417L;
	
	/**
	 * 登录用户
	 */
	@ManyToOne
	@JoinColumn(name="LOGINACCOUNT_ID")
	private LoginAccount account;
	
	/**
	 * 定制化列
	 */
	@ManyToOne
	@JoinColumn(name="COLUMN_ITEM_ID")
	private ColumnItem item;
	
	/**
	 * 顺序
	 */
	@Column(name="SEQUENCE",nullable=false)
	private Integer sequence;	

	/**
	 * 是否可见
	 */
	@Column(name="VISIBLE",nullable=false)
	private Boolean visible;

	/**
	 * 备注
	 */
	@Column(name="DESCRIPTION")
	private String description;

	public LoginAccount getAccount() {
		return account;
	}

	public void setAccount(LoginAccount account) {
		this.account = account;
	}

	public ColumnItem getItem() {
		return item;
	}

	public void setItem(ColumnItem item) {
		this.item = item;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
