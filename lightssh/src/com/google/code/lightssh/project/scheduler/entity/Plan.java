package com.google.code.lightssh.project.scheduler.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.model.Period;
import com.google.code.lightssh.common.model.Sequenceable;

/**
 * 执行计划
 * @author YangXiaojin
 *
 */
@Entity
@Table(name="T_SCHEDULER_PLAN")
public class Plan implements Persistence<String>,Sequenceable{

	private static final long serialVersionUID = -1746418305740573176L;
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyMMdd");
	
	public static final String SEQUENCE_KEY = "SCHEDULER_PLAN_";
	
	/**
	 * ID
	 */
	@Id
	@Column(name="ID")
	protected String id;
	
	/**
	 * 类型
	 */
	@ManyToOne
	@JoinColumn(name="TYPE_ID",nullable=false)
	private SchedulerType type;
	
	/**
	 * 计划执行时间
	 */
	@Column(name="PLAN_FIRE_TIME",columnDefinition="DATE",nullable=false)
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar planFireTime;
	
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
	 * 执行完成
	 */
	@Column(name="FINISHED",nullable=false)
	private Boolean finished;
	
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
	 * 工作日期
	 */
	@Column(name="WORKDATE",length=8)
	private String workdate;
	
	/**
	 * 时间区间用于查询
	 */
	@Transient
	private Period _pftPeriod;

	public Calendar getPlanFireTime() {
		return planFireTime;
	}

	public void setPlanFireTime(Calendar planFireTime) {
		this.planFireTime = planFireTime;
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

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SchedulerType getType() {
		return type;
	}

	public void setType(SchedulerType type) {
		this.type = type;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkdate() {
		return workdate;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

	@Override
	public String getIdentity() {
		return this.id;
	}

	@Override
	public boolean isInsert() {
		return id == null || id.trim() == null;
	}

	@Override
	public void postInsertFailure() {
	}

	@Override
	public void preInsert() {
		this.createdTime = Calendar.getInstance();
	}

	@Override
	public String getSequenceKey() {
		return type.getId()+SDF.format(new Date());
	}

	@Override
	public int getSequenceLength() {
		return 4;
	}

	@Override
	public int getSequenceStep() {
		return 1;
	}

	public Period get_pftPeriod() {
		return _pftPeriod;
	}

	public void set_pftPeriod(Period pftPeriod) {
		_pftPeriod = pftPeriod;
	}

}
