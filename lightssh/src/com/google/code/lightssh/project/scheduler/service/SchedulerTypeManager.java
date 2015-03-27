package com.google.code.lightssh.project.scheduler.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.scheduler.entity.SchedulerType;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface SchedulerTypeManager extends BaseManager<SchedulerType>{
	
	/**
	 * 安全取类型
	 */
	public SchedulerType safeGet(String id,String name);
	
	/**
	 * 安全取类型
	 */
	public SchedulerType safeGet(String id,String name,String description);
	
}
