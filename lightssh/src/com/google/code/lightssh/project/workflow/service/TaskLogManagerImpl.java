package com.google.code.lightssh.project.workflow.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.workflow.dao.TaskLogDao;
import com.google.code.lightssh.project.workflow.entity.TaskLog;
import com.google.code.lightssh.project.workflow.model.ExecutionType;

/**
 * 
 * @author Aspen
 * @date 2013-8-27
 * 
 */
@Component("taskLogManager")
public class TaskLogManagerImpl extends BaseManagerImpl<TaskLog> implements TaskLogManager{

	private static final long serialVersionUID = 981597564499558192L;
	
	@Resource(name="taskLogDao")
	public void setDao(TaskLogDao dao ){
		this.dao = dao;
	}
	
	public TaskLogDao getDao(){
		return (TaskLogDao)this.dao;
	}
	
	public ListPage<TaskLog> list(ListPage<TaskLog> page,TaskLog t){
		SearchCondition sc = new SearchCondition();
		
		if( t != null ){
			if( StringUtils.isNotEmpty(t.getActProcInstId()) ){
				sc.equal("actProcInstId", t.getActProcInstId().trim() );
			}
		}
		
		return dao.list(page, sc);
	}
	
	/**
	 * 查询所有日志，最多100条
	 * @param procInstId 流程实例ID
	 */
	public List<TaskLog> list( String procInstId ){
		return list( procInstId,100 );
	}
	
	/**
	 * 查询所有日志
	 * @param procInstId 流程实例ID
	 * @param maxSize 最大记录条数
	 */
	public List<TaskLog> list( String procInstId ,int maxSize ){
		ListPage<TaskLog> page = new ListPage<TaskLog>(maxSize);
		page.addDescending("createdTime");
		
		TaskLog t =new TaskLog();
		t.setActProcInstId(procInstId);
		
		page = this.list(page,t);
		
		return page.getList();
	}
	
	/**
	 * 保存
	 * @param procInstId 流程实例ID
	 * @param taskInstId 流程任务ID
	 * @param type 操作类型
	 * @param operator 操作员
	 * @param message 备注
	 */
	public void save( String procInstId,String taskInstId
			,ExecutionType type,String operator,String message){
		this.save(procInstId, taskInstId, type, operator, message, null);
	}
	
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
			,ExecutionType type,String operator,String message,Calendar time ){
		
		if( StringUtils.isEmpty(procInstId) )
			throw new ApplicationException("流程实例Id不能为空！");
		
		if( StringUtils.isEmpty(taskInstId) )
			throw new ApplicationException("流程任务Id不能为空！");
		
		if( type == null )
			throw new ApplicationException("流程操作类型不能为空！");
		
		if( StringUtils.isEmpty(operator) )
			throw new ApplicationException("操作员不能为空！");
		
		TaskLog log = new TaskLog();
		log.setActProcInstId(procInstId);
		log.setActTaskInstId(taskInstId);
		log.setType(type);
		log.setOperator(operator);
		log.setDescription(message);
		if( time == null )
			time = Calendar.getInstance();
		
		log.setCreatedTime(time);
		
		this.dao.create(log);
	}

}
