package com.google.code.lightssh.project.log.entity;

/**
 * 
 * @author YangXiaojin
 *
 */
public class Field{
	
	/**
	 * 属性名
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 原值
	 */
	private Object originalValue;
	
	/**
	 * 新值
	 */
	private Object newValue;
	
	
	private FieldType type;
	
	public Field(){}
	
	public Field(FieldType type,String name,String description, Object o, Object n) {
		super();
		this.name = name;
		this.description = description;
		this.originalValue = o;
		this.newValue = n;
		this.type = type;
	}
	
	public boolean isChange( ){
		return (originalValue==null && newValue!=null) || 
			( originalValue!=null && !originalValue.equals(newValue));
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(Object originalValue) {
		this.originalValue = originalValue;
	}
	public Object getNewValue() {
		return newValue;
	}
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
	public FieldType getType() {
		return type;
	}
	public void setType(FieldType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public enum FieldType{
		ADD_FIELD("添加属性")
		,NO_CHANGE("无变化")
		,REMOVE_FIELD("删除属性")
		,CHANGED("更改属性");
		
		private String value;
		
		FieldType( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString(){
			return this.value;
		}
		
	}
	
}

