package com.google.code.lightssh.project.workflow.model;

/**
 * 执行类型
 * @author Aspen
 * @date 2013-8-28
 * 
 */
public enum ExecutionType {
	EDIT("编辑")	
	,CLAIM("认领")	
	,TERMINATE("终止")	
	,SUBMIT("提交")	
	,REVOKE("拒绝")	
	,FALLBACK("回退")	
	,NOTICE("转发")	
	,CONFIRM("确认")	
	,SIGN("会签");	
	
	private String value;
	
	ExecutionType( String value ){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public String toString(){
		return this.value;
	}
	
}
