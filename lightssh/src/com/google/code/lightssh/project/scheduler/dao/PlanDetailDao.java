package com.google.code.lightssh.project.scheduler.dao;

import java.util.Calendar;
import java.util.List;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail;


/**
 * 
 * @author YangXiaojin
 *
 */
public interface PlanDetailDao extends Dao<PlanDetail>{
	
	/**
	 * 查询依赖未完成任务
	 */
	public List<PlanDetail> listRelyOnUnsuccessful( String id );
	
	/**
	 * 更新计划任务明细
	 */
	public int update(String id,PlanDetail.Status originalStatus,PlanDetail.Status newStatus );
	
	/**
	 * 定时任务调用完成更新状态
	 * 如果异步回执已更新状态为'成功'则不进行状态更新
	 */
	public int updateStatusAfterInvoke(String id,PlanDetail.Status newValue );
	
	/**
	 * 定时任务调用完成更新'开始执行时间'
	 */
	public int updateFireTimeAfterInvoke(String id,Calendar fireTime );

}
