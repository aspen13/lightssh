package com.google.code.lightssh.project.workflow.service;

import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.project.util.constant.WorkflowConstant;
import com.google.code.lightssh.project.workflow.model.ExecutionType;

/**
 * 
 * @author Aspen
 * @date 2013-8-28
 * 
 */
@Component("businessHookHandler")
public class BusinessHookHandler implements ExecutionListener{

	private static final long serialVersionUID = -600379525468276973L;
	
	private static Logger log = LoggerFactory.getLogger(BusinessHookHandler.class);
	
	@Resource(name="workflowManager")
	private WorkflowManager workflowManager;
	
	/**
	 * 工作流业务实现Map
	 */
	@Resource(name="workflowBizHookMap")
	private Map<String,BusinessManager> workflowBizHookMap;
	
	/**
	 * 获取业务处理类
	 */
	protected BusinessManager getBizManager( String procDefKey ){
		if( workflowBizHookMap == null ){
			log.warn("工作流业务处理类为空！");
			return null;
		}
		
		BusinessManager mgr = workflowBizHookMap.get(procDefKey);
		if( mgr == null )
			log.warn("工作流[{}]业务处理类未定义！",procDefKey);
		
		return mgr;
	}
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String procDefId = execution.getProcessDefinitionId();
		String procInstId = execution.getProcessInstanceId();
		String bizKey = execution.getProcessBusinessKey();
		
		log.debug("流程挂钩业务处理,流程定义Id[{}],业务关联Key[{}]",procDefId,bizKey);
		
		ProcessDefinition procDef = workflowManager.getProcessDefinition(procDefId);
		if( procDef == null ){
			log.error("工作流定义[{}]不存在！",procDefId);
			return;
		}
		
		String procDefKey = procDef.getKey(); //流程定义Key
		BusinessManager bizMgr = getBizManager( procDefKey );
		
		Object motion = execution.getVariable( 
				WorkflowConstant.TASK_ACTIOIN_VARIABLE_NAME );
		
		log.debug("流程挂钩业务处理,流程实例Id[{}],业务Key[{}],Activity名称[{}],参数[{}]=[{}]",
				new Object[]{procInstId,bizKey,execution.getCurrentActivityName()
						,WorkflowConstant.TASK_ACTIOIN_VARIABLE_NAME,motion});
		
		ExecutionType type = null; 
		if( motion != null )
			type = ExecutionType.valueOf(motion.toString());
		
		if( bizMgr != null )
			bizMgr.process(type,procDefKey,procInstId,bizKey);
	}

}
