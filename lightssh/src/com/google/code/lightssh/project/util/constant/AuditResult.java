package com.google.code.lightssh.project.util.constant;

/**
 * 审核结果
 * @author YangXiaojin
 *
 */
public enum AuditResult {
	FIRST_AUDIT_PASSED("初审通过"),
	FIRST_AUDIT_REJECT("初审拒绝"),
	LAST_AUDIT_PASSED("终审通过"),
	LAST_AUDIT_REJECT("终审拒绝"),
	;
	
	private String value;
	
	AuditResult( String value ){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString(){
		return this.value;
	}

}
