package com.google.code.lightssh.project.scheduler.entity;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;

/**
 * 定时任务包裹类
 * @author YangXiaojin
 *
 */
public class TriggerWrap {

	private Trigger trigger;
	
	private TriggerState state;

	public boolean isPause() {
		return TriggerState.PAUSED.equals(state);
	}

	public TriggerWrap( Trigger trigger ){
		this.trigger = trigger;
	}
	
	public String getName(){
		return trigger.getKey().getName();
	}
	
	public String getCronExpression(){
		if( trigger instanceof CronTrigger )
			return ((CronTrigger)trigger).getCronExpression();
		
		return null;
	}
	
	public Date getPreviousFireTime(){
		return trigger.getPreviousFireTime();
	}
	
	public Date getNextFireTime(){
		return trigger.getNextFireTime();
	}
	
	public Date getEndTime(){
		return trigger.getEndTime();
	}

	public String getGroup(){
		return trigger.getKey().getGroup();
	}
	
	public String getDescription(){
		return trigger.getDescription();
	}

	public TriggerState getState() {
		return state;
	}

	public void setState(TriggerState state) {
		this.state = state;
	}

}
