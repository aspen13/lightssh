package com.google.code.lightssh.project.scheduler.entity;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import javax.persistence.Version;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 执行计划明细
 * @author YangXiaojin
 *
 */
@Entity
@Table(name="T_SCHEDULER_PLAN_DETAIL")
public class PlanDetail implements Persistence<String>{

	private static final long serialVersionUID = -1746418305740573176L;
	
	/**
	 * 状态
	 */
	public enum Status{
		NEW("新建"),
		PROCESSING("处理中"),
		FAILURE("失败"),
		SUCCESS("成功"),
		WAITING_FOR_REPLY("等待回复"),
		SUSPEND("挂起"),
		EXCEPTION("异常"),
		SKIP("跳过"),
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
	
	public PlanDetail() {
		super();
	}

	public PlanDetail(SchedulerType type,String group,Integer sequence,boolean synTask) {
		super();
		this.group = group;
		this.type = type;
		this.sequence = sequence;
		this.failureCount=0;
		this.status = Status.NEW;
		this.synTask = synTask;
		this.createdTime = Calendar.getInstance(); 
	}
	
	public PlanDetail(SchedulerType type,String group,Integer sequence) {
		this(type,group,sequence,false);
	}

	/**
	 * ID
	 */
	@Id
	@Column(name="ID")
	protected String id;
	
	/**
	 * 执行计划
	 */
	@ManyToOne
	@JoinColumn(name="PLAN_ID")
	private Plan plan;
	
	/**
	 * 前置条件
	 */
	@ManyToOne
	@JoinColumn(name="PRE_DETAIL_ID")
	private PlanDetail precondition;
	
	/**
	 * 分组
	 */
	@Column(name="GROUP_NAME")
	private String group;
	
	/**
	 * 类型
	 */
	@ManyToOne
	@JoinColumn(name="TYPE_ID",nullable=false)
	private SchedulerType type;
	
	/**
	 * 顺序
	 */
	@Column(name="SEQUENCE",nullable=false)
	private Integer sequence;
	
	/**
	 * 实际执行时间
	 */
	@Column(name="FIRE_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar fireTime;
	
	/**
	 * 执行完成时间
	 */
	@Column(name="FINISH_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar finishTime;
	
	/**
	 * 异步任务
	 */
	@Column(name="SYN_TASK")
	private Boolean synTask;
	
	/**
	 * 状态
	 */
	@Column(name="STATUS",nullable=false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	/**
	 * 失败次数
	 */
	@Column(name="FAILURE_COUNT",nullable=false)
	private Integer failureCount;
	
	/**
	 * 异常信息
	 */
	@Column(name="ERR_MSG",length=500)
	private String errMsg;
	
	/**
	 * 描述
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;
	
	/**
	 * 创建日期
	 */
	@Column(name="CREATED_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar createdTime;
	
	/**
	 * 版本号
	 */
	@Version
	@Column( name="VERSION" )
	private Integer version; 
	
	/**
	 * 是否异步任务
	 */
	public boolean isSynTask(){
		return Boolean.TRUE.equals( this.synTask );
	}
	
	/**
	 * 是否完成
	 */
	public boolean isFinished(){
		return Status.SUCCESS.equals(status);
	}
	
	/**
	 * 是否告警
	 */
	public boolean isWarning(){
		return Status.EXCEPTION.equals(status) || Status.FAILURE.equals(status);
	}
	
	/**
	 * 依赖是否完成
	 */
	public boolean isPreconditionFinished(){
		return precondition == null || precondition.isFinished();
	}
	
	/**
	 * 是否依赖
	 */
	public boolean isRelyOn(){
		return this.precondition != null;
	}
	
	/**
	 * 增加失败次数
	 */
	public void incFailureCount(){
		incFailureCount(1);
	}
	
	/**
	 * 增加失败次数
	 */
	public void incFailureCount( int count ){
		if( this.failureCount == null )
			this.failureCount = 0;
		this.failureCount += count;
	}
	
	/**
	 * 排序
	 */
	public static List<PlanDetail> sort( List<PlanDetail> details ){
		if( details == null || details.isEmpty() )
			return details;
		
		Collections.sort(details,new Comparator<PlanDetail>(){
			@Override
			public int compare(PlanDetail o1, PlanDetail o2) {
				return o1.sequence.compareTo(o2.sequence);
			}
		});
		
		return details;
	}

	public Calendar getFireTime() {
		return fireTime;
	}

	public void setFireTime(Calendar fireTime) {
		this.fireTime = fireTime;
	}

	public Calendar getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Calendar finishTime) {
		this.finishTime = finishTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public PlanDetail getPrecondition() {
		return precondition;
	}

	public void setPrecondition(PlanDetail precondition) {
		this.precondition = precondition;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public Boolean getSynTask() {
		return synTask;
	}

	public void setSynTask(Boolean synTask) {
		this.synTask = synTask;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getIdentity() {
		return this.id;
	}

	@Override
	public boolean isInsert() {
		return false;
	}

	@Override
	public void postInsertFailure() {
	}

	@Override
	public void preInsert() {
		this.createdTime = Calendar.getInstance();		
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
