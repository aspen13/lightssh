package com.google.code.lightssh.project.scheduler.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.scheduler.entity.Plan;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail;
import com.google.code.lightssh.project.scheduler.entity.SchedulerType;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail.Status;

/**
 * 计划任务
 * @author YangXiaojin
 *
 */
public interface PlanManager extends BaseManager<Plan>{
	
	/**
	 * 查询执行计划明细
	 */
	public List<PlanDetail> listDetail( Object[] ids );
	
	/**
	 * 计划明细
	 */
	public PlanDetail getDetail( String id );
	
	/**
	 * 所有明细执行完成后更新计划任务状态
	 */
	public void updateStatus( String detailId );
	
	/**
	 * 更新明细状态
	 */
	public void updateDetailStatus(String detailId,Status status );
	
	/**
	 * 更新明细状态
	 */
	public void updateDetailStatus(String detailId,boolean success,String errMsg );
	
	/**
	 * 执行依赖任务
	 */
	public void executeRelyOnPlanDetail(boolean success,String detailId );
	
	/**
	 * 执行计划明细
	 */
	public List<PlanDetail> listDetail(String planId);
	
	/**
	 * 执行计划明细
	 */
	public List<PlanDetail> listDetail(Plan plan);
	
	/**
	 * 取创建时间最新记录
	 */
	public Plan getLastByType(String type);
	
	/**
	 * 取创建时间最新记录
	 */
	public Plan getLast(SchedulerType type);
	
	/**
	 * 保存执行计划
	 */
	public void save( Plan plan,List<PlanDetail> details );
	
	/**
	 * 保存执行计划
	 */
	public void save( Plan plan,List<PlanDetail> details,boolean putAllInQueue );
	
	/**
	 * 明细入任务队列
	 */
	public void detailInQueue(PlanDetail pd);
	
	/**
	 * 更新执行时间
	 */
	public void updateFireTime(String id,Calendar fireTime);
	
	/**
	 * 更新完成时间
	 */
	public void updateFinishTime(String id,Calendar finishTime);
	
	/**
	 * 查询依赖未完成任务
	 */
	public List<PlanDetail> listRelyOnUnsuccessful( String id );
	
	/**
	 * 更新数据状态
	 */
	public void updateDetailStatus(Collection<Result> results);

}
