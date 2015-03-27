package com.google.code.lightssh.project.scheduler.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.scheduler.entity.JobQueue;

/**
 * 定时任务队列
 */
public interface JobQueueDao extends Dao<JobQueue>{
	
	/**
	 * @param typeid 类型ID
	 * @param refid 关联业务ID
	 */
	public JobQueue get( String typeid,String refid );

}
