package com.google.code.lightssh.project.workflow.model;

import java.io.Serializable;

import com.google.code.lightssh.common.model.Period;

/**
 * 流程VO，用于查询
 * @author Aspen
 * @date 2013-8-15
 * 
 */
public class MyProcess implements Serializable{

	private static final long serialVersionUID = -929576196717544808L;
	
	/**
	 * 流程ID/实例
	 */
	private String id;
	
	/**
	 * 是否完成
	 */
	private Boolean finish;
	
	/**
	 * 流程创建者
	 */
	private String owner;
	
	/**
	 * 代理人
	 */
	private String assignee;
	
	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;
	
	/**
	 * 流程定义Key
	 */
	private String processDefinitionKey;
	
	/**
	 * 流程定义Name
	 */
	private String processDefinitionName;
	
	/**
	 * 流程ID
	 */
	private String processInstanceId;
	
	/**
	 * 流程属性名
	 */
	private String processAttributeName;
	
	/**
	 * 流程创建时间区间
	 */
	private Period startPeriod;
	
	/**
	 * 流程结束时间区间
	 */
	private Period endPeriod;

	public Boolean getFinish() {
		return finish;
	}

	public void setFinish(Boolean finish) {
		this.finish = finish;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Period getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(Period startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Period getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Period endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessAttributeName() {
		return processAttributeName;
	}

	public void setProcessAttributeName(String processAttributeName) {
		this.processAttributeName = processAttributeName;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

}
