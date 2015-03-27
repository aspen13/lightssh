package com.google.code.lightssh.project.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 流程属性
 * @author Aspen
 * @date 2013-8-30
 * 
 */
@Entity
@Table(name="T_FLOW_PROCESS_ATTR")
public class ProcessAttribute extends UUIDModel{

	private static final long serialVersionUID = -7947103188705644506L;
	
	/**
	 * 流程定义Key
	 */
	@Column(name="ACT_PROC_DEF_KEY",length=255)
	private String actProcDefKey;
	
	/**
	 * 流程实例ID
	 */
	@Column(name="ACT_PROC_INST_ID",length=64)
	private String actProcInstId;
	
	/**
	 * 业务关键字
	 */
	@Column(name="BIZ_KEY",length=255)
	private String bizKey;
	
	/**
	 * 业务名称
	 */
	@Column(name="BIZ_NAME",length=500)
	private String bizName;
	
	/**
	 * 操作人
	 */
	@Column(name="OWNER",length=50)
	private String owner;
	
	/**
	 * 描述
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;

	public String getActProcDefKey() {
		return actProcDefKey;
	}

	public void setActProcDefKey(String actProcDefKey) {
		this.actProcDefKey = actProcDefKey;
	}

	public String getActProcInstId() {
		return actProcInstId;
	}

	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
