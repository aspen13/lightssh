package com.google.code.lightssh.project.scheduler.service;

import java.io.Serializable;

/** 
 * 定时任务接口
 */
public interface SchedulerService extends Serializable{
	
	/**
	 * 定时任务执行入口
	 */
	public void execute();

}
