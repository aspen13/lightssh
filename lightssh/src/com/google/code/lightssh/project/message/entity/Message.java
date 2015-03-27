package com.google.code.lightssh.project.message.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 消息
 * @author Aspen
 * 
 */
@Entity
@Table(name="T_MSG_MESSAGE")
public class Message extends UUIDModel{

	private static final long serialVersionUID = -8919984997998833164L;
	
	/**
	 * 优先级
	 */
	public enum Priority{
		BLOCKER("致命")			
		,CRITICAL("严重")			
		,MAJOR("一般")	
		,MINOR("较小")	
		,TRIVIAL("轻微");	
		
		private String value;
		
		Priority( String value ){
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
	 * 状态
	 */
	public enum Status{
		DRAFT("草稿")			
		,REMOVE("删除")			
		,PUBLISH("发布");	
		
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
	 * 订阅类型
	 */
	@Column(name="REC_TYPE",length=20)
	@Enumerated(EnumType.STRING)
	private ReceiveType recType;
	
	/**
	 * 订阅类型值
	 */
	@Column(name="REC_VALUE",length=255)
	private String recValue;
	
	/**
	 * 消息类型
	 */
	@ManyToOne
	@JoinColumn(name="CATALOG_ID")
	private Catalog catalog;
	
	/**
	 * 状态
	 */
	@Column(name="STATUS",length=20)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	/**
	 * 优先级
	 */
	@Column(name="PRIORITY",length=20)
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	/**
	 * 标题
	 */
	@Column(name="TITLE",length=100,nullable=false)
	private String title;
	
	/**
	 * 内容
	 */
	@Column(name="CONTENT",length=2000,nullable=false)
	private String content;
	
	/**
	 * 是否可链接
	 */
	@Column(name="LINKABLE",nullable=false)
	private Boolean linkable;
	
	/**
	 * 链接地址
	 */
	@Column(name="URL",length=255)
	private String url;
	
	/**
	 * 是否可转发
	 */
	@Column(name="FORWARD",nullable=false)
	private Boolean forward;
	
	/**
	 * 发布条数
	 */
	@Column(name="PUBLISHED_COUNT",nullable=false)
	public Integer publishedCount;
	
	/**
	 * 删除发布条数
	 */
	@Column(name="DELETED_COUNT",nullable=false)
	public Integer deletedCount;
	
	/**
	 * 消息点击次数
	 */
	@Column(name="HIT_COUNT",nullable=false)
	public Integer hitCount;
	
	/**
	 * 阅读者数
	 */
	@Column(name="READER_COUNT",nullable=false)
	public Integer readerCount;
	
	/**
	 * 是否可转发
	 */
	@Column(name="TODO_CLEAN",nullable=false)
	private Boolean todoClean;
	
	/**
	 * 阅读时间
	 */
	@Column(name="TODO_CLEAN_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar todoCleanTime;

	/**
	 * 创建人
	 */
	@Column(name="CREATOR",length=50)
	private String creator;
	
	/**
	 * 读者
	 */
	@Transient
	public LoginAccount reader;
	
	/**
	 * 增加删除数
	 */
	public void incDeletedCount(){
		if( deletedCount == null )
			deletedCount = 0;
		
		this.deletedCount ++;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getLinkable() {
		return linkable;
	}

	public void setLinkable(Boolean linkable) {
		this.linkable = linkable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getForward() {
		return forward;
	}

	public void setForward(Boolean forward) {
		this.forward = forward;
	}

	public Integer getPublishedCount() {
		return publishedCount;
	}

	public void setPublishedCount(Integer publishedCount) {
		this.publishedCount = publishedCount;
	}

	public Integer getDeletedCount() {
		return deletedCount;
	}

	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Integer getReaderCount() {
		return readerCount;
	}

	public void setReaderCount(Integer readerCount) {
		this.readerCount = readerCount;
	}

	public Boolean getTodoClean() {
		return todoClean;
	}

	public void setTodoClean(Boolean todoClean) {
		this.todoClean = todoClean;
	}

	public Calendar getTodoCleanTime() {
		return todoCleanTime;
	}

	public void setTodoCleanTime(Calendar todoCleanTime) {
		this.todoCleanTime = todoCleanTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public LoginAccount getReader() {
		return reader;
	}

	public void setReader(LoginAccount reader) {
		this.reader = reader;
	}

	public ReceiveType getRecType() {
		return recType;
	}

	public void setRecType(ReceiveType recType) {
		this.recType = recType;
	}

	public String getRecValue() {
		return recValue;
	}

	public void setRecValue(String recValue) {
		this.recValue = recValue;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
