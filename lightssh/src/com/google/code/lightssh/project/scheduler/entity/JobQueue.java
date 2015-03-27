package com.google.code.lightssh.project.scheduler.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import javax.persistence.UniqueConstraint;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/** 
 * 工作任务队列
 */
@Entity
@Table(name="T_SCHEDULER_JOB_QUEUE",uniqueConstraints={
		@UniqueConstraint(name="UK_T_SJQ_TID_RID",columnNames={"TYPE_ID","REF_ID"})})
public class JobQueue extends UUIDModel{

	private static final long serialVersionUID = -1904633459821865817L;
	
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
	 * 工作任务类型
	 */
	@ManyToOne
	@JoinColumn(name="TYPE_ID",nullable=false)
	private SchedulerType type;
	
	/**
	 * 关联业务ID
	 */
	@Column(name="REF_ID",length=100,nullable=false)
	private String refId;
	
	/**
	 * 执行时间
	 */
	@Column(name="INVOKE_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	protected Calendar invokeTime;
	
	/**
	 * 状态
	 */
	@Column(name="STATUS",nullable=false)
	@Enumerated(value=EnumType.STRING)
	private Status status;
	
	/**
	 * 失败次数
	 */
	@Column(name="FAILURE_COUNT",nullable=false)
	private Integer failureCount;
	
	/**
	 * 最大发送次数
	 */
	@Column(name="MAX_SEND_COUNT",nullable=false)
	private Integer maxSendCount;
	
	/**
	 * 异常信息
	 */
	@Column(name="ERR_MSG",length=200)
	private String errMsg;
	
	/**
	 * 描述
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;
	
	@Transient
	private List<Status> _inQueryStatus;
	
	/**
	 * 增加失败次数
	 */
	public void incFailureCount(){
		if( this.failureCount == null )
			this.failureCount = 1;
		else
			this.failureCount += 1;
	}
	
	/**
	 * 是否可移除
	 */
	public boolean isDeletable(){
		return getMaxSendCount()<=getFailureCount()+1;
	}
	
	/**
	 * 添加查询状态
	 */
	public void addQueryStatus( Status status ){
		if( _inQueryStatus == null )
			_inQueryStatus = new ArrayList<Status>();
		
		this._inQueryStatus.add(status);
	}

	public SchedulerType getType() {
		return type;
	}

	public void setType(SchedulerType type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public Integer getMaxSendCount() {
		return maxSendCount;
	}

	public void setMaxSendCount(Integer maxSendCount) {
		this.maxSendCount = maxSendCount;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Calendar getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(Calendar invokeTime) {
		this.invokeTime = invokeTime;
	}

	public List<Status> get_inQueryStatus() {
		return _inQueryStatus;
	}

	public void set_inQueryStatus(List<Status> inQueryStatus) {
		_inQueryStatus = inQueryStatus;
	}

}
