package com.google.code.lightssh.project.mail.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/** 
 * @author YangXiaojin
 * @date 2012-11-13
 * @description：TODO
 */
@Entity
@Table(name="T_EMAIL_CONTENT")
public class EmailContent extends UUIDModel{

	private static final long serialVersionUID = -6430768751256802750L;
	
	/**
	 * 邮件类型
	 */
	public enum Type{
		TEXT("文本"),
		HTML("超文本")
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
	
	/**
	 * 状态
	 */
	public enum Status{
		NEW("新建"),
		PROCESSING("处理中"),
		FAILURE("失败"),
		RESEND("重发"),
		SUCCESS("成功"),
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
	 * 发件人
	 */
	@Column(name="SENDER",length=100,nullable=true)
	private String sender;
	
	/**
	 * 收件人
	 * 多个收件人之间半角逗号(,)分隔
	 */
	@Column(name="ADDRESSEE",length=500,nullable=false)
	private String addressee;
	
	/**
	 * 抄送人
	 * 多个抄送人之间半角逗号(,)分隔
	 */
	@Column(name="CC",length=500,nullable=true)
	private String cc;
	
	/**
	 * 邮件标题
	 */
	@Column(name="SUBJECT",length=200,nullable=false)
	private String subject;
	
	/**
	 * 邮件内容
	 */
	@Column(name="CONTENT",length=4000,nullable=false)
	private String content;
	
	/**
	 * 邮件内容类型
	 */
	@Column(name="TYPE")
	@Enumerated(value=EnumType.STRING)
	private Type type;
	
	/**
	 * 状态
	 */
	@Column(name="STATUS")
	@Enumerated(value=EnumType.STRING)
	private Status status;
	
	/**
	 * 失败次数
	 */
	@Column(name="FAILURE_COUNT")
	private Integer failureCount;
	
	/**
	 * 发送完成时间
	 */
	@Column(name="FINISHED_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar finishedTime;
	
	/**
	 * 异常信息
	 */
	@Column(name="ERR_MSG",length=200)
	private String errMsg;
	
	/**
	 * 收件人
	 */
	public String[] getAddressees(){
		if( this.addressee == null )
			return null;
		
		return addressee.split(",");
	}
	
	/**
	 * 抄送人
	 */
	public String[] getCcs(){
		if( this.cc == null )
			return null;
		
		return cc.split(",");
	}
	
	/**
	 * 取内容行
	 */
	public String[] getContentLines( ){
		if( this.content == null )
			return null;
		
		return this.content.split("\\n");
	}
	
	/**
	 * 增加失败次数
	 */
	public void incFailureCount( ){
		incFailureCount( 1 );
	}
	
	/**
	 * 增加失败次数
	 */
	public void incFailureCount( Integer fc ){
		if( fc == null )
			fc = 0;
		
		if( this.failureCount == null )
			this.failureCount = 0;
		
		this.failureCount +=fc;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Calendar getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Calendar finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
