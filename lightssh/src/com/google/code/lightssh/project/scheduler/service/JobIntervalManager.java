package com.google.code.lightssh.project.scheduler.service;

import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.scheduler.entity.JobInterval;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface JobIntervalManager extends BaseManager<JobInterval>{
	
	/**
	 * 查询有效的JobInterval
	 */
	public List<JobInterval> listAvailable( );

}
