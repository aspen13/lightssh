package com.google.code.lightssh.project.message.entity;


/**
 * 
 * @author Aspen
 * @date 2013-9-5
 * 
 */
public enum ReceiveType {
	
	ALL("所有用户")			
	,ROLE("角色")			
	,DEPARTMENT("部门")			
	,PERSON("人员")	
	,POSITION("岗位")	
	,USER("用户");		

	private String value;
	
	ReceiveType( String value ){
		this.value = value;
	}
	
	/**
	 * 支持的类型
	 */
	public static ReceiveType[] supportedValues( ){
		return new ReceiveType[]{DEPARTMENT,USER,ALL};
	}

	public String getValue() {
		return value;
	}
	
	public String toString(){
		return this.value;
	}

}
