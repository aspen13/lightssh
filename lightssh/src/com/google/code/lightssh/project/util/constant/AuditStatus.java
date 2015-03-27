
package com.google.code.lightssh.project.util.constant;

/**
 * 审核状态
 * @author YangXiaojin
 *
 */
public enum AuditStatus {
	
	NEW("待审核"),
	AUDIT_REJECT("审核拒绝"),
	EFFECTIVE("有效"),
	DELETE("删除"),
	;
	
	private String value;
	
	AuditStatus( String value ){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString(){
		return this.value;
	}

}
