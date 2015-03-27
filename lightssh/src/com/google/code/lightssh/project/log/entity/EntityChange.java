package com.google.code.lightssh.project.log.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 实体变更抽象类
 * @author YangXiaojin
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class EntityChange extends UUIDModel{

	private static final long serialVersionUID = 4975640723111067418L;
	
	public enum Type {		
		CREATE("创建")
		,UPDATE("更新")
		,DELETE("删除")
		;
		
		private String value;
			
		Type( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString(){
			return this.value;
		}
	}
	
	public enum Status {		
		NEW("新建")
		,PROCESSING("处理中")
		,FINISHED("完成")
		;
		
		private String value;
		
		Status( String value ){
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public String toString(){
			return this.value;
		}
	}
	
	/**
	 * 操作员
	 */
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private LoginAccount operator;
	
	/**
	 * 类型
	 */
	@Column(name="TYPE",length=20)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	/**
	 * 状态
	 */
	@Column(name="STATUS",length=20)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	/**
	 * 原始值
	 */
	@Column( name="ORIGINAL_OBJECT",columnDefinition="BLOB")
	private byte[] originalObject;
	
	/**
	 * 新值
	 */
	@Column( name="NEW_OBJECT",columnDefinition="BLOB")
	private byte[] newObject;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	private String description;

	public LoginAccount getOperator() {
		return operator;
	}

	public void setOperator(LoginAccount operator) {
		this.operator = operator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public byte[] getOriginalObject() {
		return originalObject;
	}

	public void setOriginalObject(byte[] originalObject) {
		this.originalObject = originalObject;
	}

	public byte[] getNewObject() {
		return newObject;
	}

	public void setNewObject(byte[] newObject) {
		this.newObject = newObject;
	}

}
