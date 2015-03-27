package com.google.code.lightssh.project.workflow.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.web.action.ImageAction;
import com.google.code.lightssh.project.web.action.GenericAction;
import com.google.code.lightssh.project.workflow.entity.BizView;
import com.google.code.lightssh.project.workflow.entity.TaskLog;
import com.google.code.lightssh.project.workflow.model.MyProcess;
import com.google.code.lightssh.project.workflow.service.BizViewManager;
import com.google.code.lightssh.project.workflow.service.TaskLogManager;
import com.google.code.lightssh.project.workflow.service.WorkflowManager;

/**
 * 
 * @author Aspen
 * @date 2013-8-15
 * 
 */
@SuppressWarnings("rawtypes")
@Component( "workflowProcessAction" )
@Scope("prototype")
public class WorkflowProcessAction extends GenericAction implements ImageAction{

	private static final long serialVersionUID = 7371824285577363653L;
	
	private byte[] imageInBytes;
	
	private String imageContentType;
	
	private MyProcess process;
	
	private ListPage<ProcessDefinition> pd_page;
	
	private ListPage<ProcessInstance> pi_page;
	
	private ListPage<HistoricProcessInstance> hp_page;
	
	@Resource(name="workflowManager")
	private WorkflowManager workflowManager;
	
	@Resource(name="taskLogManager")
	private TaskLogManager taskLogManager;
	
	@Resource(name="bizViewManager")
	private BizViewManager bizViewManager;
	
	public ListPage<ProcessDefinition> getPd_page() {
		return pd_page;
	}

	public void setPd_page(ListPage<ProcessDefinition> pd_page) {
		this.pd_page = pd_page;
	}

	public ListPage<ProcessInstance> getPi_page() {
		return pi_page;
	}

	public void setPi_page(ListPage<ProcessInstance> pi_page) {
		this.pi_page = pi_page;
	}

	public ListPage<HistoricProcessInstance> getHp_page() {
		return hp_page;
	}

	public void setHp_page(ListPage<HistoricProcessInstance> hp_page) {
		this.hp_page = hp_page;
	}

	public MyProcess getProcess() {
		return process;
	}

	public void setProcess(MyProcess process) {
		this.process = process;
	}

	public byte[] getImageInBytes() {
		return imageInBytes;
	}

	public void setImageInBytes(byte[] imageInBytes) {
		this.imageInBytes = imageInBytes;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	/**
	 * 流程定义查询
	 */
	public String processDefinitionList( ){
		pd_page = workflowManager.listProcessDefinition(pd_page);
		return SUCCESS;
	}

	/**
	 * 流程实例查询
	 */
	public String processInstanceList( ){
		pi_page = workflowManager.listProcessInstance(pi_page);
		return SUCCESS;
	}
	
	/**
	 * 流程查询
	 */
	public String list( ){
		if( process == null ){
			process = new MyProcess();
			//process.setFinish( false );
		}
		
		if( hp_page == null )
			hp_page = new ListPage<HistoricProcessInstance>();
		
		if( hp_page.getOrderByList() == null )
			hp_page.addDescending("START_TIME_");
		
		hp_page = workflowManager.listProcess(process,hp_page);
		return SUCCESS;
	}
	
	/**
	 * 我的流程
	 */
	public String myProcess(){
		if( this.getLoginAccount() == null )
			return LOGIN;
		
		if( process == null ){
			process = new MyProcess();
			process.setFinish( false );
		}
		
		if( hp_page == null )
			hp_page = new ListPage<HistoricProcessInstance>();
		
		if( hp_page.getOrderByList() == null )
			hp_page.addDescending("START_TIME_");
		
		process.setAssignee(this.getLoginUser());
		hp_page = workflowManager.listProcess(process,hp_page);
		return SUCCESS; 
	}
	
	/**
	 * 流程查询
	 */
	public String view( ){
		if( process == null || process.getProcessInstanceId() == null )
			return INPUT;
		
		request.setAttribute("procInstId",process.getProcessInstanceId());
		
		return SUCCESS;
	}
	
	/**
	 * 显示图片
	 */
	public String viewProc(){
		HistoricProcessInstance proc = workflowManager.getProcessHistory(
				process.getProcessInstanceId());
		if( proc == null ){
			return INPUT;
		}
		
		request.setAttribute("process", proc );
		
		return SUCCESS;
	}
	
	/**
	 * 显示图片
	 */
	public String viewImage(){
		if( process == null || StringUtils.isEmpty(process.getProcessInstanceId()) )
			this.addActionError("参数为空！");
		else{
			HistoricProcessInstance hpi = workflowManager.getProcessHistory(
					process.getProcessInstanceId() );
			request.setAttribute("processHistory", hpi);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 流程图-流程定义
	 */
	public String procDefImage(){
		if( process == null || process.getProcessDefinitionId() ==null ){
			this.saveErrorMessage("参数错误！");
			return INPUT;
		}
		
		BpmnModel bpmnModel = workflowManager.getBpmnModel(
				process.getProcessDefinitionId() );
		if( bpmnModel == null ){
			this.saveErrorMessage("流程定义ID["+
					process.getProcessDefinitionId()+"]对应BpmnModel数据不存在！");
			return INPUT;
		}
		
		try {
			this.imageInBytes = IOUtils.toByteArray( 
					ProcessDiagramGenerator.generatePngDiagram(bpmnModel) );
			this.imageContentType = "image/png"; //PNG
		} catch (IOException e) {
			this.saveErrorMessage("数据转换异常："+e.getMessage() );
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 流程图-活动节点
	 */
	public String procActiveImage(){
		if( process == null || process.getProcessInstanceId() ==null ){
			this.saveErrorMessage("参数错误！");
			return INPUT;
		}
		
		String procInstId = process.getProcessInstanceId();
		
		ProcessInstance procIntance = workflowManager.getProcessInstance(
				process.getProcessInstanceId() );
		if( procIntance == null ){
			this.saveErrorMessage("流程实例["+process.getProcessInstanceId()+"]不存在！");
			return INPUT;
		}
		
		BpmnModel bpmnModel = workflowManager.getBpmnModel( procIntance.getProcessDefinitionId() );
		if( bpmnModel == null ){
			this.saveErrorMessage("流程定义ID["+ 
					process.getProcessDefinitionId()+"]对应BpmnModel数据不存在！");
			return INPUT;
		}
		
		boolean showFlow = "true".equals(request.getParameter("showFlow"));
		
		List<String> highLightedFlows = null;
		InputStream is = null;
		
		try {
			if( showFlow )
				highLightedFlows = workflowManager.getHighLightedFlows( procInstId );
			if( highLightedFlows != null && !highLightedFlows.isEmpty() ){
				is = ProcessDiagramGenerator.generateDiagram(bpmnModel,"png",
						workflowManager.getActiveActivityIds( procInstId )
						,highLightedFlows);
			}else{
				is = ProcessDiagramGenerator.generateDiagram(bpmnModel,"png",
						workflowManager.getActiveActivityIds( procInstId ));
			}
			
			this.imageInBytes = IOUtils.toByteArray( is );
			this.imageContentType = "image/png"; //PNG
		} catch (IOException e) {
			this.saveErrorMessage("数据转换异常："+e.getMessage() );
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 流程实例操作日志
	 */
	public String viewTasklog(){
		if( process == null || process.getProcessInstanceId() == null )
			return INPUT;
		
		List<TaskLog> logs = taskLogManager.list( process.getProcessInstanceId(),100 );
		request.setAttribute("tasklogOfProc", logs );
		
		return SUCCESS;
	}
	
	/**
	 * 显示业务数据
	 */
	public String viewBizData(){
		if( process == null || process.getProcessInstanceId() == null ){
			this.addActionError("流程实例参数为空！");
			return INPUT;
		}
		
		String procInstId = process.getProcessInstanceId();
		HistoricProcessInstance hisProc = workflowManager.getProcessHistory( procInstId );
		if( hisProc == null ){
			this.addActionError("流程["+procInstId+"]数据不存在！");
			return INPUT;
		}
		
		String bizKey = hisProc.getBusinessKey();
		if( StringUtils.isEmpty(bizKey) ){
			this.addActionError("流程["+procInstId+"]无业务数据关联！");
			return INPUT;
		}
		
		ProcessDefinition procDef = workflowManager.getProcessDefinition(
				hisProc.getProcessDefinitionId());
		if( procDef == null ){
			this.addActionError("流程定义["+hisProc.getProcessDefinitionId()+"]数据不存在！");
			return INPUT;
		}
		
		request.setAttribute("bizKey", bizKey);
		BizView bizView = bizViewManager.get( procDef.getKey() );
		if( bizView == null ){
			this.addActionError("流程定义["+procDef.getKey()+"]数据展示未配置！");
			return INPUT;
		}
		request.setAttribute("bizView", bizView);
		
		return SUCCESS;
	}
	
	/**
	 * 终止流程
	 */
	public String terminate( ){
		if( process == null || StringUtils.isEmpty(
				process.getProcessInstanceId() )){
			this.saveErrorMessage("流程实例Id为空!");
			return INPUT;
		}
		
		try{
			workflowManager.terminate(process.getProcessInstanceId(),this.getLoginUser(), null);
		}catch( Exception e ){
			this.saveErrorMessage("终止流程异常:"+e.getMessage());
			return INPUT;
		}
			
		return SUCCESS;
	}
}
