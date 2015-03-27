package com.google.code.lightssh.project.workflow.service;

import java.util.Calendar;
import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.workflow.entity.TaskLog;
import com.google.code.lightssh.project.workflow.model.ExecutionType;

/**
 * 
 * @author Aspen
 * @date 2013-8-27
 * 
 */
public interface TaskLogManager extends BaseManager<TaskLog>{
	
	/**
	 * 保存
	 * @param procInstId 流程实例ID
	 * @param taskInstId 流程任务ID
	 * @param type 操作类型
	 * @param operator 操作员
	 * @param message 备注
	 */
	public void save( String procInstId,String taskInstId
			,ExecutionType type,String operator,String message);
	
	/**
	 * 保存
	 * @param procInstId 流程实例ID
	 * @param taskInstId 流程任务ID
	 * @param type 操作类型
	 * @param operator 操作员
	 * @param message 备注
	 * @param time 时间 
	 */
	public void save( String procInstId,String taskInstId
			,ExecutionType type,String operator,String message,Calendar time );
	
	/**
	 * 查询所有日志，最多100条
	 * @param procInstId 流程实例ID
	 */
	public List<TaskLog> list( String procInstId );
	
	/**
	 * 查询所有日志
	 * @param procInstId 流程实例ID
	 * @param maxSize 最大记录条数
	 */
	public List<TaskLog> list( String procInstId ,int maxSize );

}
