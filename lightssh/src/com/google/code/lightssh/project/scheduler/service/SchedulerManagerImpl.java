package com.google.code.lightssh.project.scheduler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.model.CronExpression;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.scheduler.entity.JobInterval;
import com.google.code.lightssh.project.scheduler.entity.TriggerWrap;

public class SchedulerManagerImpl implements SchedulerManager{
	
	private static final long serialVersionUID = 544811394387288846L;

	private static Logger log = LoggerFactory.getLogger(SchedulerManagerImpl.class);
	
	private JobIntervalManager jobIntervalManager;
	
	private StdScheduler scheduler;

	public void setJobIntervalManager(JobIntervalManager jobIntervalManager) {
		this.jobIntervalManager = jobIntervalManager;
	}

	public void setQuartzScheduler(
			StdScheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	
	/**
	 * 获取触发器
	 */
	public TriggerWrap get(String group,String name){
		TriggerWrap wrap = null;
		try {
			Trigger trigger = scheduler.getTrigger( TriggerKey.triggerKey(name, group) );
			wrap = new TriggerWrap( trigger );
			wrap.setState(getTriggerState(trigger.getKey()));
		} catch (SchedulerException e) {
			return null;
		}
		
		return wrap;
	}
	
	/**
	 * 更新时钟
	 */
	public void updateCronExp( String name,String group,CronExpression cronExp ){
		Trigger trigger = null;
		try{
			trigger = scheduler.getTrigger( TriggerKey.triggerKey(name, group) );
		}catch(Exception e ){
			throw new ApplicationException(e);
		}
		if( trigger == null )
			throw new ApplicationException("时钟["+group+"]["+name+"]不存在！");
		
		boolean enabled = !TriggerState.PAUSED.equals(getTriggerState(trigger.getKey()));
		JobInterval jobInterval = jobIntervalManager.get( name );
		boolean insert = false;
		if( jobInterval == null ){
			jobInterval = new JobInterval();
			jobInterval.setTriggerName(name);
			insert = true;
		}
		jobInterval.setCronExpression(cronExp.toString());
		jobInterval.setEnabled(enabled);
		if( insert )
			this.jobIntervalManager.create(jobInterval);
		else
			this.jobIntervalManager.update(jobInterval);
		
		freshTrigger(trigger,enabled,cronExp.toString());
	}
	
	/**
	 * 获得系统Trigger
	 */
	private List<Trigger> listTriggers( ){
		List<Trigger> triggers = new ArrayList<Trigger>( );
		
		List<String> groups  = new ArrayList<String>();
		groups.add( Scheduler.DEFAULT_GROUP );
		
		try{
			groups = scheduler.getTriggerGroupNames();
		}catch( Exception e ){}
		
		try{
			for( String group:groups ){
				Set<TriggerKey> keys = scheduler.getTriggerKeys(
						GroupMatcher.triggerGroupContains(group));
				
				for( TriggerKey key:keys )
					triggers.add( scheduler.getTrigger( key ) );
			}
		}catch( Exception e ){
			log.warn("获取系统定时任务异常："+e.getMessage());
		}
		
		return triggers;
	}
	
	/**
	 * 定时器设置
	 */
	private Map<String,JobInterval> getJobIntervalMap( ){
		List<JobInterval> list = jobIntervalManager.listAvailable();
		if( list == null || list.isEmpty() )
			return null;
		
		Map<String,JobInterval> jiMap = new HashMap<String,JobInterval>();
		for( JobInterval ji:list )
			jiMap.put(ji.getTriggerName(), ji);
		
		return jiMap;
	}

	@Override
	public void initCronTrigger() {
		if( scheduler == null || jobIntervalManager == null )
			return;
		
		Map<String,JobInterval> jiMap = getJobIntervalMap();
		if( jiMap == null || jiMap.isEmpty() )
			return;
		
		List<Trigger> triggers = listTriggers( );
		if( triggers == null || triggers.isEmpty() )
			return;
		
		//针对每个Trigger 设置Cron表示达
		for( Trigger trigger:triggers ){
			JobInterval jobInterval = jiMap.get( trigger.getKey().getName() );
			if( trigger instanceof CronTrigger && jobInterval != null ){
				freshTrigger( trigger,jobInterval.isEnabled()
						,jobInterval.getCronExpression() );
			}
		}//end for
	}
	
	/**
	 * 刷新定时任务
	 */
	public void refresh(String name,String group){
		if( scheduler == null )
			return;
		
		try {
			Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(name, group));
			if( trigger != null && trigger instanceof CronTrigger){
				JobInterval jobInterval = this.jobIntervalManager.get( name );
				if( jobInterval != null ){
					CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(
							jobInterval.getCronExpression());
					
					trigger = TriggerBuilder.newTrigger()
						.withIdentity( trigger.getKey() )
						.withDescription(trigger.getDescription())
						.withSchedule(csb).build();
					scheduler.rescheduleJob(trigger.getKey(),trigger);
					if( !jobInterval.isEnabled() )
						scheduler.pauseTrigger(trigger.getKey());
				}
			}
		} catch (SchedulerException e) {
			throw new ApplicationException(e);
		}
		
	}
	
	/**
	 * 刷新触发器
	 */
	private void freshTrigger(Trigger trigger,boolean run,String cronExp ){
		if( scheduler == null )
			return;
		
		try{
			if( run ){ //重设置trigger
				CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(cronExp);
				
				trigger = TriggerBuilder.newTrigger()
					.withIdentity( trigger.getKey() )
					.withDescription(trigger.getDescription())
					.withSchedule(csb).build();
				scheduler.rescheduleJob(trigger.getKey(),trigger);
			}else{//停用
				scheduler.pauseTrigger(trigger.getKey());
			}
		}catch( Exception e ){
			//e.printStackTrace();
		}
	}

	@Override
	public void changeCronTrigger(String triggerName, String cronExpression) {
		if( triggerName == null || cronExpression == null )
			return;
		
		JobInterval jobInterval = jobIntervalManager.get( triggerName );
		if( jobInterval == null )
			return;
		jobInterval.setCronExpression(cronExpression);
		jobIntervalManager.save(jobInterval);
		
		List<Trigger> triggers = listTriggers( );
		if( triggers == null || triggers.isEmpty() )
			return;
		
		/*
		for( Trigger trigger:triggers ){
			if( triggerName.equals( trigger.getName() ) && trigger instanceof CronTrigger ){
				try{
					((CronTrigger)trigger).setCronExpression( jobInterval.getCronExpression() );
					scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
				}catch( Exception e ){
					//e.printStackTrace();
				}
				break;
			}
		}//end for
		*/
	}
	
	/**
	 * 定时器状态
	 */
	protected TriggerState getTriggerState( TriggerKey key ){
		try {
			return scheduler.getTriggerState( key );
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		return TriggerState.NONE;
	}

	@Override
	public List<TriggerWrap> listAllTrigger() {
		List<TriggerWrap> result = null;
		List<Trigger> list = listTriggers();
		
		if( list != null && !list.isEmpty() ){
			result = new ArrayList<TriggerWrap>();
			
			//Map<String,JobInterval> jiMap = getJobIntervalMap();
			
			for(Trigger trigger:list ){
				TriggerWrap wrap = new TriggerWrap( trigger );
				wrap.setState(getTriggerState(trigger.getKey()));
				
				result.add(wrap);
			}
		}
		return result;
	}
	
	/**
	 * 启动或暂停定时器
	 */
	public void toggleTrigger(String triggerName ){
		toggleTrigger(triggerName,Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 启动或暂停定时器
	 */
	public void toggleTrigger(String triggerName,String group ){
		if( StringUtil.clean(triggerName)==null )
			return;
		
		String cronExpression = null,description;
		JobInterval jobInterval = jobIntervalManager.get( triggerName );
		if( jobInterval != null ){
			jobInterval.setEnabled( Boolean.valueOf( !jobInterval.isEnabled() ) );
			cronExpression = StringUtil.clean(jobInterval.getCronExpression());
		}
		
		Trigger target = null;
		try {
			target = scheduler.getTrigger(TriggerKey.triggerKey(triggerName,group));
		} catch (SchedulerException e) {
			log.warn("获取定时任务[{}]异常：{}",triggerName,e.getMessage());
			throw new ApplicationException( e );
		}
		
		if( target == null )
			throw new ApplicationException( "找不到相关定时任务["+triggerName+"]" );
			
		description = target.getDescription();
		boolean enabled = false;
		
		try {
			TriggerState state = scheduler.getTriggerState( target.getKey() );
			
			if( target instanceof CronTrigger){ 
				cronExpression = ((CronTrigger)target).getCronExpression();
			}
			
			if( TriggerState.PAUSED.equals(state) ){
				scheduler.resumeTrigger( target.getKey() );//启动定时器
				enabled = true;
			}else if( TriggerState.NORMAL.equals(state) ){
				scheduler.pauseTrigger(target.getKey());//暂停定时器
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		if( jobInterval == null ){
			jobInterval = new JobInterval();
			jobInterval.setCronExpression(cronExpression);
			jobInterval.setTriggerName(triggerName);
			jobInterval.setDescription(description);
		}

		jobInterval.setEnabled( enabled );
		
		jobIntervalManager.save(jobInterval);
	}
	
}
