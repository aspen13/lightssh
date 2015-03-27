package com.google.code.lightssh.project.workflow.util;

import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.project.util.SpringContextHelper;
import com.google.code.lightssh.project.workflow.entity.ProcessAttribute;
import com.google.code.lightssh.project.workflow.service.ProcessAttributeManager;
import com.google.code.lightssh.project.workflow.service.WorkflowManager;

/**
 * 
 * @author Aspen
 * @date 2013-8-15
 * 
 */
public class WorkflowHelper {
	
	public static final String WORKFLOW_MANAGER_NAME = "workflowManager";
	
	public static final String PROC_ATTR_MANAGER_NAME = "processAttributeManager";

	/**
	 * 根据Key查询流程定义
	 */
	public static ProcessDefinition getProcessDefinition(String key ){
		if( StringUtils.isEmpty(key))
			return null;
		
		WorkflowManager bean = (WorkflowManager)
				SpringContextHelper.getBean(WORKFLOW_MANAGER_NAME);
		
		return bean.getProcessDefinition(key);
	}
	
	/**
	 * 根据ID查询流程实例
	 */
	public static HistoricProcessInstance getProcessInstance(String id ){
		if( StringUtils.isEmpty(id))
			return null;
		
		WorkflowManager bean = (WorkflowManager)
				SpringContextHelper.getBean(WORKFLOW_MANAGER_NAME);
		
		return bean.getProcessHistory( id );
	}
	
	/**
	 * 根据ID查询任务
	 */
	public static Task getTask(String id ){
		if( StringUtils.isEmpty(id))
			return null;
		
		WorkflowManager bean = (WorkflowManager)
				SpringContextHelper.getBean(WORKFLOW_MANAGER_NAME);
		
		return bean.getTask( id );
	}
	
	/**
	 * 根据流程实例ID查询任务
	 */
	public static List<Task> listTaskByProcessId(String id ){
		if( StringUtils.isEmpty(id))
			return null;
		
		WorkflowManager bean = (WorkflowManager)
				SpringContextHelper.getBean(WORKFLOW_MANAGER_NAME);
		
		return bean.listTaskByProcessId( id );
	}
	
	/**
	 * 查询流程属性
	 * @param procInstId 流程实例Id
	 */
	public static ProcessAttribute getProcAttr( String procInstId ){
		if( StringUtils.isEmpty(procInstId))
			return null;
		
		ProcessAttributeManager bean = (ProcessAttributeManager)
				SpringContextHelper.getBean(PROC_ATTR_MANAGER_NAME);
		
		return bean.getByProcInstId(procInstId );
		
	}
}
