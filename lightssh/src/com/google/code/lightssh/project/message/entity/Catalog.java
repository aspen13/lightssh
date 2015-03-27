package com.google.code.lightssh.project.message.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 消息种类
 * @author Aspen
 * 
 */
@Entity
@Table(name="T_MSG_CATALOG")
public class Catalog implements Persistence<String>{
	
	private static final long serialVersionUID = 5765293589440052230L;
	
	public static final String DEFAULT_INFO_ID = "INFO-DEF";

	/**
	 * 类型
	 */
	public enum Type{
		ERROR("异常")			
		,INFO("信息")			
		,TODO("待办");		
		
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

	/**
	 * 编号
	 */
	@Id
	@Column(name="ID",length=50)
	private String id;
	
	/**
	 * 上级分类
	 */
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private Catalog parent;
	
	/**
	 * 类型
	 */
	@Column(name="TYPE",length=20)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	/**
	 * 序号
	 */
	@Column(name="SEQUENCE")
	private Integer sequence;
	
	/**
	 * 是否可订阅
	 */
	@Column(name="SUBSCRIBE")
	private Boolean subscribe;
	
	/**
	 * 是否可转发
	 */
	@Column(name="FORWARD")
	private Boolean forward;
	
	/**
	 * 是否只读
	 */
	@Column(name="READ_ONLY")
	private Boolean readOnly;
	
	/**
	 * 创建日期
	 */
	@Column(name="CREATED_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	protected Calendar createdTime;
	
	/**
	 * 备注
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;

	@Override
	public void preInsert() {
	}

	@Override
	public void postInsertFailure() {
		this.id = null;
	}

	@Override
	public boolean isInsert() {
		return StringUtils.isEmpty(this.id) ;
	}

	@Override
	public String getIdentity() {
		return this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Catalog getParent() {
		return parent;
	}

	public void setParent(Catalog parent) {
		this.parent = parent;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	public Boolean getForward() {
		return forward;
	}

	public void setForward(Boolean forward) {
		this.forward = forward;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
