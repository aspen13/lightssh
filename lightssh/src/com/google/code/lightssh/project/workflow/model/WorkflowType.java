package com.google.code.lightssh.project.workflow.model;

/**
 * 流程类型类型
 * @author YangXiaojin
 * @date 2014-7-3
 * 
 */
public enum WorkflowType {
	SEC_ACCT("security_account_audit","登录账号审核")	
	,SEC_ROLE("security_role_audit","角色审核");	
	
	private String name;
	private String value;
	
	WorkflowType(String name, String value ){
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString(){
		return this.value;
	}
	
}
