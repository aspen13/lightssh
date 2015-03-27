package com.google.code.lightssh.project.scheduler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * Job 执行时间间隔
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SCHEDULER_JOB_INTERVAL")
public class JobInterval implements Persistence<String>{
	
	private static final long serialVersionUID = 6796712794507578931L;

	/**
	 * Job Detail
	 */
	@Id
	@Column( name="TRIGGER_NAME",length=50 )
	private String triggerName;

	/**
	 * Cron Expression
	 */
	@Column( name="CRON_EXPRESSION",unique=false,length=100 )
	private String cronExpression;
	
	/**
	 * 是否有效
	 */
	@Column( name="ENABLED" )
	private Boolean enabled;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	private String description;
	
	public void preInsert( ){
		//do nothing
	}
	
	public void postInsertFailure( ){
		//do nothing
	}
	
	public boolean isInsert( ){
		return triggerName == null;
	}
	
	public boolean isEnabled(){
		return Boolean.TRUE.equals( this.enabled );
	}
	
	public String getIdentity( ){
		return this.triggerName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
