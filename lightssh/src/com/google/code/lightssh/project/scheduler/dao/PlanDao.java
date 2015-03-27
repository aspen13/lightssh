package com.google.code.lightssh.project.scheduler.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.scheduler.entity.Plan;


/**
 * 
 * @author YangXiaojin
 *
 */
public interface PlanDao extends Dao<Plan>{

	/**
	 * 所有明细执行完成后更新计划任务状态
	 */
	public boolean updateStatusIfAllDetailFinished( String id );
	
}
