package com.google.code.lightssh.project.workflow.model;

import com.google.code.lightssh.common.model.Period;

/**
 * 
 * @author Aspen
 * @date 2013-8-22
 * 
 */
public class MyTask {
	
	/**
	 * Task id
	 */
	private String id;
	
	/**
	 * 签收用户
	 */
	private String assignee;
	
	/**
	 * 待签用户
	 */
	private String candidateUser;
	
	/**
	 * 拥有者
	 */
	private String owner;
	
	/**
	 * 流程启动人
	 */
	private String procInstStartUser;
	
	/**
	 * 是否完成
	 */
	private Boolean finish;
	
	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;
	
	/**
	 * 流程定义Key
	 */
	private String processDefinitionKey;
	
	/**
	 * 流程定义名称
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
	 * 消息
	 */
	private String message;
	
	/**
	 * 类型
	 */
	private ExecutionType type;
	
	/**
	 * 任务到达时间
	 */
	private Period startPeriod;
	
	/**
	 * 流程开始时间
	 */
	private Period procStartPeriod;

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(String candidateUser) {
		this.candidateUser = candidateUser;
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

	public Boolean getFinish() {
		return finish;
	}

	public void setFinish(Boolean finish) {
		this.finish = finish;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ExecutionType getType() {
		return type;
	}

	public void setType(ExecutionType type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProcInstStartUser() {
		return procInstStartUser;
	}

	public void setProcInstStartUser(String procInstStartUser) {
		this.procInstStartUser = procInstStartUser;
	}

	public Period getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(Period startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Period getProcStartPeriod() {
		return procStartPeriod;
	}

	public void setProcStartPeriod(Period procStartPeriod) {
		this.procStartPeriod = procStartPeriod;
	}
}
