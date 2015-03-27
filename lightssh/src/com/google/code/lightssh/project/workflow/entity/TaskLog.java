package com.google.code.lightssh.project.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.project.workflow.model.ExecutionType;

/**
 * 任务日志
 * @author Aspen
 * @date 2013-8-27
 * 
 */
@Entity
@Table( name="T_FLOW_TASK_LOG" )
public class TaskLog extends UUIDModel{

	private static final long serialVersionUID = -2347566895886457457L;
	
	/**
	 * 流程实例ID
	 */
	@Column(name="ACT_PROC_INST_ID",length=64)
	private String actProcInstId;
	
	/**
	 * 任务实例ID
	 */
	@Column(name="ACT_TASK_INST_ID",length=64)
	private String actTaskInstId;
	
	/**
	 * 操作人
	 */
	@Column(name="OPERATOR",length=50)
	private String operator;
	
	/**
	 * 类型
	 */
	@Column( name="TYPE",length=20 )
	@Enumerated(value=EnumType.STRING)
	private ExecutionType type;
	
	/**
	 * 描述
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;

	public String getActProcInstId() {
		return actProcInstId;
	}

	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}

	public String getActTaskInstId() {
		return actTaskInstId;
	}

	public void setActTaskInstId(String actTaskInstId) {
		this.actTaskInstId = actTaskInstId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ExecutionType getType() {
		return type;
	}

	public void setType(ExecutionType type) {
		this.type = type;
	}


}
