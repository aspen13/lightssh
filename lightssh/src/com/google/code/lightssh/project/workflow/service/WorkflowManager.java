package com.google.code.lightssh.project.workflow.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.Manager;
import com.google.code.lightssh.project.workflow.model.MyProcess;
import com.google.code.lightssh.project.workflow.model.MyTask;

/**
 * 工作流业务接口
 *
 */
public interface WorkflowManager extends Manager{
	
	/**
	 * 查询流程定义
	 */
	public ListPage<ProcessDefinition> listProcessDefinition( ListPage<ProcessDefinition> page );
	
	/**
	 * 查询流程定义
	 * @param Id 流程定义ID
	 */
	public ProcessDefinition getProcessDefinition( String id );
	
	/**
	 * 获取BpmnModel 通过流程定义ID
	 */
	public BpmnModel getBpmnModel(String procDefId );
	
	/**
	 * 根据流程实例ID查询活动的Activity ID
	 */
	public List<String> getActiveActivityIds(String procInstId );
	
	/**
	 * 根据流程实例ID查询结束的Activity ID
	 */
	public List<String> getHighLightedFlows(String procInstId );
	
	/**
	 * 查询流程实例
	 */
	public ListPage<ProcessInstance> listProcessInstance( ListPage<ProcessInstance> page );
	
	/**
	 * 查询流程实例
	 */
	public ProcessInstance getProcessInstance( String id );
	
	/**
	 * 历史流程实例
	 */
	public HistoricProcessInstance getProcessHistory(String procId );
	
	/**
	 * 查询与我相关的流程
	 */
	public ListPage<HistoricProcessInstance> listProcess(
			MyProcess process,ListPage<HistoricProcessInstance> page );
	
	/**
	 * 查询任务
	 */
	public Task getTask( String taskId );
	
	/**
	 * 查询流程中活动的任务
	 */
	public List<Task> listTaskByProcessId( String processId );
	
	/**
	 * 查询任务
	 */
	public ListPage<Task> listTask(MyTask task, ListPage<Task> page );
	
	/**
	 * 查询流程的上一执行任务
	 * @param processInstanceId 流程实例
	 */
	public HistoricTaskInstance getLastTask(String processInstanceId);
	
	/**
	 * 查询流程中活动的任务
	 */
	public ListPage<HistoricTaskInstance> listHistoricTask(
			MyTask task,ListPage<HistoricTaskInstance> page);
	
	/**
	 * 查询任务之前表单数据
	 */
	public List<HistoricDetail> listHistoricDetail( String taskId );
	
	/**
	 * 查询部署信息
	 */
	public ListPage<Deployment> listDeployment( ListPage<Deployment> page );
	
	/**
	 * 部署流程
	 */
	public void deploy( String resourceName,InputStream inputStream);
	
	/**
	 * 取消部署
	 */
	public void undeploy( String deploymentId );
	
	/**
	 * 启动流程
	 */
	public ProcessInstance start( String processKey);
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义key
	 * @param bizKey 业务key
	 * @param bizName 业务名称
	 * @param userId 用户ID
	 */
	public ProcessInstance start( String procDefKey,String bizKey,String bizName
			,String userId,Map<String,Object> variables);
	
	/**
	 * 认领任务
	 */
	public void claim( String taskId,String userId );
	
	/**
	 * 认领并提交当前活动的流程
	 * @param procInstId 流程实例ID
	 * @param operator 操作人
	 */
	public void claimAndComplete(String procInstId,String operator);
	
	/**
	 * 变更认领人
	 */
	public void changeAssignee( String taskId,String userId );
	
	/**
	 * 添加会签人
	 */
	public void addAssignee( String taskId,List<String> userIds );
	
	/**
	 * 完成任务
	 */
	public void complete( String taskId );
	
	/**
	 * 完成任务
	 */
	public void complete( MyTask myTask,String user );
	
	/**
	 * 终止流程
	 */
	public void terminate( String procInstId,String user,String reason );
	
	/**
	 * 流程注释
	 */
	public List<Comment> getProcessInstanceComments(String processInstanceId);
	
	/**
	 * 任务注释
	 */
	public List<Comment> getTaskComments(String taskId);
	
	/**
	 * 任务表单数据
	 */
	public TaskFormData getTaskFormData(String taskId );
	
	/**
	 * 开始事件表单数据
	 */
	public StartFormData getStartFormData(String processDefinitionId );
	
	/**
	 * 表单数据
	 * @param id 流程类型ID 或  任务ID
	 * @return FormData
	 */
	public FormData getFormData(String id );
	
	/**
	 * 提交数据
	 */
	public ProcessInstance submitStartFormData(String processDefinitionId
			,String businessKey,Map<String,String> properties  );
	
	/**
	 * 提交数据
	 */
	public void submitFormData(String taskId,Map<String,String> properties );
	
	/**
	 * 回退到上一步
	 * @param procInstId 流程实例ID
	 */
	public void undoTask( String procInstId,String user);
	
	/**
	 * 会签
	 * @param operator 操作人
	 * @param taskId 任务ID
	 * @param users 会签用户
	 */
	public void countersignTask(String operator,String taskId,String[] users);

}
