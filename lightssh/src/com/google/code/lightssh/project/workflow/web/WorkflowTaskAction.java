package com.google.code.lightssh.project.workflow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.web.action.GenericAction;
import com.google.code.lightssh.project.workflow.model.MyTask;
import com.google.code.lightssh.project.workflow.service.WorkflowManager;

/**
 * 
 * @author Aspen
 * @date 2013-8-15
 * 
 */
@SuppressWarnings("rawtypes")
@Component( "workflowTaskAction" )
@Scope("prototype")
public class WorkflowTaskAction extends GenericAction{

	private static final long serialVersionUID = 394568619904313580L;
	
	private MyTask task;
	
	private ListPage<Task> taskPage;
	
	private String taskId;

	private Integer count = 0;
	
	private Result result;

	@Resource(name="workflowManager")
	private WorkflowManager workflowManager;
	
	private List<String> users;

	public ListPage<Task> getTaskPage() {
		return taskPage;
	}

	public void setTaskPage(ListPage<Task> taskPage) {
		this.taskPage = taskPage;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public MyTask getTask() {
		return task;
	}

	public void setTask(MyTask task) {
		this.task = task;
	}
	
	@JSON(name="count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	@JSON(name="result")
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	/**
	 * 所有待办任务
	 */
	public String list(){
		taskPage = workflowManager.listTask(task,taskPage);
		return SUCCESS;
	}
	
	/**
	 * 我的待办任务
	 */
	public String myTodoList(){
		if( task == null )
			task = new MyTask();
		task.setAssignee( getLoginUser() ); //登录用户
		
		taskPage = workflowManager.listTask(task,taskPage);
		
		return SUCCESS;
	}
	
	/**
	 * 我的待办任务数
	 */
	public String myTodoCount(){
		if( task == null )
			task = new MyTask();
		task.setAssignee( getLoginUser() ); //登录用户
		
		if( taskPage == null )
			taskPage = new ListPage<Task>();
		taskPage.setSize(0);
		
		try{
			taskPage = workflowManager.listTask(task,taskPage);
			this.count = taskPage.getAllSize();
		}catch( Exception e ){
		}
		
		return SUCCESS;
	}
	
	/**
	 * 我的待签任务
	 */
	public String myAssignList(){
		if( task == null )
			task = new MyTask();
		task.setCandidateUser( getLoginUser() ); //当前登录用户
		
		taskPage = workflowManager.listTask(task,taskPage);
		
		return SUCCESS;
	}
	
	/**
	 * 认领流程
	 */
	public String claim( ){
		if( getLoginUser() == null )
			return LOGIN;
		
		try{
			workflowManager.claim( taskId ,getLoginUser() );
			
			this.saveSuccessMessage("成功签收流程！");
		}catch( Exception e ){
			this.saveErrorMessage( e.getMessage() );
		}
		
		return SUCCESS;
	}
	
	/**
	 * 代办认领人
	 */
	public String proxyClaim( ){
		try{
			String userId = request.getParameter("userId");
			workflowManager.changeAssignee(taskId ,userId);
		}catch( Exception e ){
			this.saveErrorMessage( e.getMessage() );
		}
		return SUCCESS;
	}
	
	/**
	 * 添加会签人
	 */
	public String multiClaim( ){
		try{
			//String userId = request.getParameter("userId");
			List<String> users = new ArrayList<String>(); //TODO
			users.add("Aspen");
			
			workflowManager.addAssignee(taskId ,users);
		}catch( Exception e ){
			this.saveErrorMessage( e.getMessage() );
		}
		
		return SUCCESS;
	}
	
	/**
	 * 预做任务
	 */
	public String prepare( ){
		if( getLoginUser() == null )
			return LOGIN;
		
		if( StringUtils.isEmpty(taskId) ){
			this.saveErrorMessage("任务号为空！");
			return INPUT;
		}
		
		Task task = workflowManager.getTask(taskId);
		if( task == null ){
			this.saveErrorMessage("流程任务["+taskId+"]不存在！");
			return INPUT;
		}
		
		if( !getLoginUser().equals( task.getAssignee() ) ){
			this.saveErrorMessage("当前用户不是任务签收人["
					+task.getAssignee()+"],不允许操作!");
			return INPUT;
		}
		
		request.setAttribute("procInstId",task.getProcessInstanceId() );
		request.setAttribute("task",task );
			
		return SUCCESS;
	}
	
	/**
	 * 渲染表单
	 */
	public String render( ){
		TaskFormData data = (TaskFormData)request.getAttribute("task_form_data");
		if( data == null )
			return ERROR;
		
		List<HistoricDetail> historicTasks  = workflowManager.listHistoricDetail(taskId);
		if( historicTasks != null ){
			request.setAttribute("history_task_form_data",historicTasks);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 提交表单数据
	 */
	public String submit( ){
		TaskFormData data = workflowManager.getTaskFormData(taskId);
		
		if( data != null && data.getFormProperties() != null ){
			Map<String,String> properties = new HashMap<String,String>();
			for(FormProperty item: data.getFormProperties() )
				properties.put(item.getId(),request.getParameter(item.getId()));
			
			//workflowManager.submitFormData(taskId,properties);
		}
		
		return complete();
	}
	
	/**
	 * 查看任务
	 * TODO
	 */
	public String view( ){
		return SUCCESS;
	}
	
	/**
	 * 完成流程
	 */
	public String complete( ){
		if( this.getLoginAccount() == null )
			return LOGIN;
		
		if( task == null || task.getId() == null ){
			this.saveErrorMessage("参数为空！");
			return INPUT;
		}
		
		//TODO 检查提交流程是否与assignee相同
		try{
			workflowManager.complete(task,getLoginUser());
		}catch( Exception e ){
			this.saveErrorMessage( e.getMessage() );
		}
		
		return SUCCESS;
	}
	
	/**
	 * 回退流程
	 */
	public String undo( ){
		if( task == null || StringUtils.isEmpty(task.getProcessInstanceId())){
			this.saveErrorMessage("参数错误:流程实例ID不能为空!");
			return INPUT;
		}
		 
		try{
			this.workflowManager.undoTask(task.getProcessInstanceId(),getLoginUser());
			this.saveSuccessMessage("流程回退成功！");
		}catch( Exception e ){
			this.saveErrorMessage("流程回退异常:"+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 会签
	 */
	public String countersign( ){
		this.result = new Result();
		
		if( StringUtils.isEmpty(taskId)){
			result.setStatus(false);
			result.setMessage("参数错误:任务ID不能为空!");
			return SUCCESS;
		}
		
		if( users == null || users.isEmpty() ){
			result.setStatus(false);
			result.setMessage("参数错误:会签用户不能为空!");
			return SUCCESS;
		}
		
		try{
			this.workflowManager.countersignTask( getLoginUser(),taskId, users.toArray(new String[]{}));
			//this.saveSuccessMessage("流程会签成功！");
			result.setStatus(true);
			result.setMessage("流程会签成功！");
		}catch( Exception e ){
			//this.saveErrorMessage("流程会签异常:"+e.getMessage());
			//return INPUT;
			result.setStatus(false);
			result.setMessage("流程会签异常:"+e.getMessage());
		}
		
		return SUCCESS;
	}
}
