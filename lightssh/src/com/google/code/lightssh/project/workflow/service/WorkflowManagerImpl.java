package com.google.code.lightssh.project.workflow.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricDetailQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.query.NativeQuery;
import org.activiti.engine.query.Query;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.model.page.ListPage.OrderType;
import com.google.code.lightssh.common.model.page.OrderBy;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.util.constant.WorkflowConstant;
import com.google.code.lightssh.project.workflow.model.ExecutionType;
import com.google.code.lightssh.project.workflow.model.MyProcess;
import com.google.code.lightssh.project.workflow.model.MyTask;

/**
 * 工作流业务实现
 *
 */
@Component("workflowManager")
public class WorkflowManagerImpl implements WorkflowManager{

	private static final long serialVersionUID = -263409561222061490L;
	
	private static Logger log = LoggerFactory.getLogger(WorkflowManagerImpl.class);
	
	private static final String SQL_SELECT_COUNT = "SELECT COUNT(1) ";
	
	private static final String SQL_SELECT_DATA = "SELECT * ";
	
	private static String ORACLE_PAGINATION_SQL = " SELECT * FROM ( SELECT A.*, ROWNUM RN FROM ( #{SQL}) A WHERE ROWNUM <= #{row_end} ) WHERE RN >= #{row_start}";
	
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	
	@Resource(name="taskService")
	private TaskService taskService;
	
	@Resource(name="repositoryService")
	private RepositoryService repositoryService;
	
	@Resource(name="identityService")
	private IdentityService identityService;
	
	@Resource(name="historyService")
	private HistoryService historyService;
	
	@Resource(name="formService")
	private FormService formService;
	
	@Resource(name="managementService")
	private ManagementService managementService;
	
	@Resource(name="taskLogManager")
	private TaskLogManager taskLogManager;
	
	@Resource(name="processAttributeManager")
	private ProcessAttributeManager processAttributeManager;
	
	/**
	 * 本地查询
	 * TODO 考虑其它数据库
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ListPage<?> query(String from_sql,NativeQuery query, ListPage<?> page ){
		if( page == null )
			page = new ListPage();
		
		//TODO 只实现oracle，待完善
		String pagenation_sql = ORACLE_PAGINATION_SQL.replace("#{SQL}"
				,SQL_SELECT_DATA + from_sql + addOrderBy(page));
		
		int count = (int)query.sql( SQL_SELECT_COUNT + from_sql.toString() ).count();
		int start = page.getStart();
		if( page.getStart() > count ){
			start = (page.getAllPage()-1)*page.getSize() 
				+ ((count>page.getSize())?((count%page.getSize())-1):0);
			page.setNumber( page.getAllPage() );
		}
		
		query.parameter("row_start", start );
		query.parameter("row_end", page.getEnd() - 1 );
		
		page.setAllSize( count  );
		page.setList( query.sql( pagenation_sql ).list() );
		
		return page;
	}
	
	protected String addOrderBy( ListPage<?> page ){
		//add order
		StringBuffer orderby = new StringBuffer("");
		List<OrderBy> orderByList = page.listAllOrderBy();
		if( orderByList != null && !orderByList.isEmpty() ){
			for(OrderBy each:orderByList ){
				if( each == null )
					continue;
				orderby.append("".equals(orderby.toString())?" ORDER BY ":" ,");
				orderby.append( " " + each.getProperty() 
					+ (OrderType.ASCENDING.equals( each.getType() )?" ASC ":" DESC ") );
			}
		}
		
		return orderby.toString();
	}
	
	/**
	 * 查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ListPage<?> query(Query query, ListPage<?> page ){
		if( page == null )
			page = new ListPage();
		
		OrderBy orderBy = page.getOrderBy();
		if( orderBy != null ){
			if( ListPage.OrderType.DESCENDING.equals(orderBy.getType()) )
				query.desc();
			else
				query.asc();
		}
		
		int count = (int)query.count();
		page.setAllSize(count);
		int start = page.getStart()-1;
		if( start > count ){
			start = (page.getAllPage()-1)*page.getSize() 
				+ ((count>page.getSize())?((count%page.getSize())-1):0);
			page.setNumber( page.getAllPage() );
		}
		
		page.setList( query.listPage(start,page.getSize()) );
		
		return page;
	}
	
	/**
	 * 查询部署信息
	 */
	@SuppressWarnings("unchecked")
	public ListPage<Deployment> listDeployment( ListPage<Deployment> page ){
		if( page == null )
			page = new ListPage<Deployment>();
		
		DeploymentQuery query = repositoryService.createDeploymentQuery();
		
		OrderBy orderBy = page.getOrderBy();
		if( orderBy != null ){
			if( "id".equals(orderBy.getProperty()) )
				query.orderByDeploymentId();
			else if( "deploymentTime".equals(orderBy.getProperty()) )
				query.orderByDeploymenTime();
			else if( "name".equals(orderBy.getProperty()) )
				query.orderByDeploymentName();
		}
		
		return (ListPage<Deployment>)query(query,page);
	}
	
	/**
	 * 部署流程
	 */
	public void deploy( String resourceName,InputStream inputStream){
		repositoryService.createDeployment()
			.addInputStream(resourceName, inputStream)
			.enableDuplicateFiltering()
			.name( "lightssh" ).deploy();
	}
	
	/**
	 * 取消部署
	 */
	public void undeploy( String deploymentId ){
		this.repositoryService.deleteDeployment(deploymentId,true);
	}
	
	/**
	 * 查询流程定义
	 */
	@SuppressWarnings("unchecked")
	public ListPage<ProcessDefinition> listProcessDefinition( ListPage<ProcessDefinition> page ){
		if( page == null )
			page = new ListPage<ProcessDefinition>();
		
		ProcessDefinitionQuery query = repositoryService
			.createProcessDefinitionQuery().latestVersion();
		
		OrderBy orderBy = page.getOrderBy();
		if( orderBy != null ){
			if( "name".equals(orderBy.getProperty()) )
				query.orderByProcessDefinitionName();
			else if( "key".equals(orderBy.getProperty()) )
				query.orderByProcessDefinitionKey();
			else if( "id".equals(orderBy.getProperty()) )
				query.orderByProcessDefinitionId();
			else if( "category".equals(orderBy.getProperty()) )
				query.orderByProcessDefinitionCategory();
			else if( "version".equals(orderBy.getProperty()) )
				query.orderByProcessDefinitionVersion();
			else if( "deploymentId".equals(orderBy.getProperty()) )
				query.orderByDeploymentId();
		}
		
		return (ListPage<ProcessDefinition>)query(query,page);
	}
	
	/**
	 * 查询流程定义
	 * @param ID 流程定义Id
	 */
	public ProcessDefinition getProcessDefinition( String id ){
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		
		query.processDefinitionId(id);
		
		return query.singleResult();
	}

	/**
	 * 获取BpmnModel 通过流程定义ID
	 */
	public BpmnModel getBpmnModel(String procDefId ){
		return repositoryService.getBpmnModel(procDefId);
	}
	
	/**
	 * 根据流程实例ID查询活动的Activity ID
	 */
	public List<String> getActiveActivityIds(String procInstId ){
		return this.runtimeService.getActiveActivityIds( procInstId );
	}
	
	/**
	 * 根据流程实例ID查询结束的Activity ID
	 */
	public List<String> getHighLightedFlows(String procInstId ){
		//流程实例
		HistoricProcessInstance procInst = this.getProcessHistory(procInstId);
		if( procInst == null )
			return null;
		
		//流程定义
		ProcessDefinitionEntity procDef = (ProcessDefinitionEntity)
				((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition( procInst.getProcessDefinitionId() );
		if( procDef == null )
			return null;
		
		List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(procInstId)
				.orderByHistoricActivityInstanceStartTime().asc()
				.list();
		
		Map<String,HistoricActivityInstance> haiMap = new HashMap<String,HistoricActivityInstance>();
		for (HistoricActivityInstance hai : haiList) {
			haiMap.put(hai.getActivityId(),hai);
		}
		
		return getHighLightedFlows(haiMap,procDef.getActivities() );
	}
	
	/**
	 * 获取高亮流程节点
	 */
	protected List<String> getHighLightedFlows(Map<String,HistoricActivityInstance> haiMap,List<ActivityImpl> activities ){
		if( activities == null || activities.isEmpty() 
				|| haiMap == null || haiMap.isEmpty() )
			return null;
		
		List<String> results = new ArrayList<String>();
		
		for(ActivityImpl item:activities){
			Object type = item.getProperty("type");
			if( type.equals("subProcess") ){
				List<String> subs = getHighLightedFlows(haiMap,item.getActivities() );
				if( subs != null && !subs.isEmpty() )
					results.addAll(subs);
			}
			
			String srcId = item.getId();
			if( haiMap.containsKey( srcId ) ){
				List<PvmTransition> pvmTrans = item.getOutgoingTransitions(); //某个节点出来的所有线路
				if( pvmTrans == null || pvmTrans.isEmpty() )
					continue;
				
				for( PvmTransition pvm:pvmTrans){
					String destFlowId = pvm.getDestination().getId();
					if( haiMap.containsKey( destFlowId ) ){
						//判断进入了哪个节点
	                    if ("exclusiveGateway".equals( type )){
	                    	//TODO 待实现
	                    }else{
	                    	results.add( pvm.getId() );
	                    }
					}
				}
			}//end if 
		}
		
		return results;
	}
	
	/**
	 * 查询流程实例
	 */
	@SuppressWarnings("unchecked")
	public ListPage<ProcessInstance> listProcessInstance( ListPage<ProcessInstance> page ){
		if( page == null )
			page = new ListPage<ProcessInstance>();
		
		ProcessInstanceQuery query = runtimeService
			.createProcessInstanceQuery();
		
		OrderBy orderBy = page.getOrderBy();
		if( orderBy != null ){
			if( "businessKey".equals(orderBy.getProperty()) )
				query.orderByProcessDefinitionKey();
			else if( "processDefinitionId".equals(orderBy.getProperty()) )
				query.orderByProcessDefinitionId();
			else if( "processInstanceId".equals(orderBy.getProperty()) )
				query.orderByProcessInstanceId();
		}
		
		return (ListPage<ProcessInstance>)query(query,page);
	}
	
	/**
	 * 查询流程实例
	 */
	public ProcessInstance getProcessInstance( String id ){
		ProcessInstanceQuery query = runtimeService
			.createProcessInstanceQuery();
		
		query.processInstanceId(id);
		
		return query.singleResult();
	}
	
	/**
	 * 历史流程实例
	 */
	public HistoricProcessInstance getProcessHistory(String procId ){
		if( StringUtils.isEmpty(procId) )
			return null;
		
		return historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(procId).singleResult();
	}
	
	/**
	 * 查询与我相关的流程
	 */
	@SuppressWarnings("unchecked")
	public ListPage<HistoricProcessInstance> listProcess(MyProcess process,ListPage<HistoricProcessInstance> page ){
		if( page == null )
			page = new ListPage<HistoricProcessInstance>();
		
		NativeHistoricProcessInstanceQuery query = historyService.createNativeHistoricProcessInstanceQuery();
		StringBuffer sql = new StringBuffer(" FROM " + managementService.getTableName(HistoricProcessInstance.class) + " t where 1=1 ");
		if( process != null ){
			
			//流程实例ID
			if( StringUtils.isNotEmpty(process.getProcessInstanceId()) ){
				sql.append(" AND t.PROC_INST_ID_ = #{proc_inst_id}");
				query.parameter("proc_inst_id", process.getProcessInstanceId().trim());
			}
			
			//流程属性名称
			if( StringUtils.isNotEmpty(process.getProcessAttributeName()) ){
				sql.append(" AND t.PROC_INST_ID_ IN ( ");
				sql.append(" SELECT DISTINCT ACT_PROC_INST_ID FROM T_FLOW_PROCESS_ATTR ");
				sql.append(" WHERE BIZ_NAME LIKE #{proc_attr_name} )");
				query.parameter("proc_attr_name", "%"+process.getProcessAttributeName().trim()+"%");
			}
			
			//流程类型ID
			if( StringUtils.isNotEmpty(process.getProcessDefinitionId()) ){
				sql.append(" AND t.PROC_DEF_ID_ = #{proc_def_id}");
				query.parameter("proc_def_id", process.getProcessDefinitionId().trim());
			}
			
			//流程类型Key
			if( StringUtils.isNotEmpty(process.getProcessDefinitionKey()) ){
				sql.append(" AND t.PROC_DEF_ID_ IN ( ");
				sql.append(" SELECT DISTINCT ID_ FROM ");
				sql.append( managementService.getTableName( ProcessDefinition.class) );
				sql.append(" WHERE KEY_ = #{proc_def_key} )");
				query.parameter("proc_def_key", process.getProcessDefinitionKey().trim());
			}
			
			boolean includeOwner = StringUtils.isNotEmpty(process.getOwner());
			String assignee = process.getAssignee()==null?null:process.getAssignee().trim();
			String owner = process.getOwner()==null?null:process.getOwner().trim();
			
			//与“操作人”相关的流水
			if( StringUtils.isNotEmpty( assignee ) && 
					!assignee.equals( owner ) ){ //优化，与owner相同时，不执行子查询
				sql.append( " AND ( t.PROC_INST_ID_ IN( SELECT DISTINCT PROC_INST_ID_ FROM ");
				sql.append( managementService.getTableName(HistoricTaskInstance.class) );
				sql.append(" WHERE ASSIGNEE_ = #{assignee}");
				
				sql.append( " ) "+(includeOwner?" ":" OR t.START_USER_ID_ = #{owner} " )+")" );
				if(!includeOwner)
					query.parameter("owner", assignee);
				
				query.parameter("assignee", assignee);
			}
			
			//流程创建者
			if( includeOwner ){
				sql.append(" AND t.START_USER_ID_ = #{owner}");
				query.parameter("owner", owner );
			}
			
			//是否完成
			if( process.getFinish() != null ){
				if( process.getFinish() )
					sql.append(" AND t.END_TIME_ IS NOT NULL ");
				else
					sql.append(" AND t.END_TIME_ IS NULL ");
			}
			
			//流程开始时间
			if( process.getStartPeriod() != null ){
				Date start = process.getStartPeriod().getStart();
				Date end = process.getStartPeriod().getEnd();
				
				if( start != null ){
					sql.append(" AND t.START_TIME_ >= #{st_start} ");
					query.parameter("st_start", start);
				}
				
				if( end != null ){
					Calendar cal_end = Calendar.getInstance();
					cal_end.setTime(end);
					cal_end.add(Calendar.DAY_OF_MONTH, 1);
					cal_end.add(Calendar.SECOND, -1);
					
					sql.append(" AND t.START_TIME_ <= #{st_end} ");
					query.parameter("st_end", cal_end.getTime());
				}
			}
		}
		
		if( page.getOrderByList() != null && !page.getOrderByList().isEmpty() ){
			
		}
		
		//page.setAllSize( (int)query.sql( SQL_SELECT_COUNT + sql.toString() ).count() );
		//page.setList( query.sql( SQL_SELECT + sql.toString() ).list() );
		
		return (ListPage<HistoricProcessInstance>) query(sql.toString(),query,page);
	}
	
	/**
	 * 启动流程
	 * @param processKey 流程key
	 */
	public ProcessInstance start( String processKey){
		return runtimeService.startProcessInstanceByKey( processKey );
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义key
	 * @param bizKey 业务key
	 * @param bizName 业务名称
	 * @param userId 用户ID
	 */
	public ProcessInstance start( String procDefKey,String bizKey,String bizName
			,String userId,Map<String,Object> variables){
		
		if( StringUtils.isEmpty(bizKey) )
			throw new ApplicationException("业务关键字(bizKey)为空！");
		
		//用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		if( !StringUtils.isEmpty(userId) )
			identityService.setAuthenticatedUserId(userId);
		
		ProcessInstance procInst = runtimeService.startProcessInstanceByKey(
				procDefKey,bizKey,variables );
		
		if( StringUtils.isNotEmpty(bizName) ){
			processAttributeManager.save(procDefKey,procInst.getId()
					,bizKey, bizName, userId);//TODO保存流程属性
			
			log.debug("保存流程属性：ProcDefKey[{}],BizKey[{}],BizName[{}]"
					,new Object[]{procDefKey,bizKey,bizName});
		}
		
		//保存操作日志
		taskLogManager.save(procInst.getId()
				,procInst.getActivityId(),ExecutionType.SUBMIT,userId,"启动流程");
	    
		return procInst;
	}
	
	/**
	 * 查询任务
	 */
	public Task getTask( String taskId ){
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	/**
	 * 查询流程中活动的任务
	 */
	@SuppressWarnings("unchecked")
	public ListPage<HistoricTaskInstance> listHistoricTask(
			MyTask task,ListPage<HistoricTaskInstance> page){
		if( page == null )
			page = new ListPage<HistoricTaskInstance>();
		
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		if( task != null ){
			//流程实例ID
			if( StringUtils.isNotEmpty(task.getProcessInstanceId()) ){
				query.processInstanceId(task.getProcessInstanceId().trim());
			}
			
			//流程定义ID
			if( StringUtils.isNotEmpty(task.getProcessDefinitionId()) ){
				query.processDefinitionId(task.getProcessDefinitionId().trim());
			}
			
			//签收人
			if( !StringUtils.isEmpty(task.getAssignee()) )
				query.taskAssignee(task.getAssignee());
			
			//任务是否完成
			if( task.getFinish() != null ){
				if( task.getFinish() )
					query.finished();
				else
					query.unfinished();
			}
		} 
		
		return (ListPage<HistoricTaskInstance>) query(query,page);
	}
	
	/**
	 * 查询流程中活动的任务
	 */
	public List<Task> listTaskByProcessId( String processId ){
		TaskQuery query = taskService.createTaskQuery();
		query.active();
		query.processInstanceId(processId);
		
		return query.list();
	}
	
	/**
	 * 当前活动Task
	 * @param processId 流程实例ID
	 */
	protected Task getCurrentTask( String processId ){
		TaskQuery query = taskService.createTaskQuery();
		query.active();
		query.processInstanceId(processId).listPage(0,1);
		
		return query.singleResult();
	}
	
	/**
	 * 查询任务
	 */
	@SuppressWarnings("unchecked")
	public ListPage<Task> listTask(MyTask task, ListPage<Task> page ){
		if( page == null )
			page = new ListPage<Task>();
		
		NativeTaskQuery query = taskService.createNativeTaskQuery();
		StringBuffer sql = new StringBuffer(" FROM " + managementService.getTableName(Task.class) + " t ");
		
		if( task != null ){
			//连接T_ACT_RU_IDENTITYLINK
			if( StringUtils.isNotEmpty(task.getCandidateUser()) ){
				sql.append(" INNER JOIN T_ACT_RU_IDENTITYLINK i on i.TASK_ID_ = t.ID_ ");
				sql.append(" ");
			}
		}
		
		sql.append(" WHERE 1=1 ");
		
		if( task != null ){
			//任务候选者
			if( StringUtils.isNotEmpty(task.getCandidateUser()) ){
				sql.append(" AND t.ASSIGNEE_ IS NULL "); //未签收
				sql.append(" AND i.TYPE_ = 'candidate'");
				sql.append(" AND i.USER_ID_ = #{candidate_user}");
				query.parameter("candidate_user", task.getCandidateUser().trim());
			}
			
			//任务签收人
			if( StringUtils.isNotEmpty(task.getAssignee()) ){
				sql.append(" AND t.ASSIGNEE_ = #{assignee}");
				query.parameter("assignee", task.getAssignee().trim());
			}
			
			//任务拥有者
			if( StringUtils.isNotEmpty(task.getOwner()) ){
				sql.append(" AND t.OWNER_ = #{owner}");
				query.parameter("owner", task.getOwner().trim());
			}
			
			//任务到达时间
			if( task.getStartPeriod() != null ){
				Date start = task.getStartPeriod().getStart();
				Date end = task.getStartPeriod().getEnd();
				
				if( start != null ){
					sql.append(" AND t.CREATE_TIME_ >= #{st_start} ");
					query.parameter("st_start", start);
				}
				
				if( end != null ){
					Calendar cal_end = Calendar.getInstance();
					cal_end.setTime(end);
					cal_end.add(Calendar.DAY_OF_MONTH, 1);
					cal_end.add(Calendar.SECOND, -1);
					
					sql.append(" AND t.CREATE_TIME_ <= #{st_end} ");
					query.parameter("st_end", cal_end.getTime());
				}
			}
			
			//流程
			if( StringUtils.isNotEmpty(task.getProcInstStartUser()) 
					|| task.getProcStartPeriod() != null ){
				sql.append(" AND t.PROC_INST_ID_ IN ( "); //start in
				sql.append(" SELECT PROC_INST_ID_ FROM ");
				sql.append( managementService.getTableName(HistoricProcessInstance.class) );
				sql.append(" WHERE 1=1 "); 
				
				//流程创建人
				if( StringUtils.isNotEmpty(task.getProcInstStartUser() ) ){
					sql.append(" AND START_USER_ID_ = #{proc_inst_owner} ");
					query.parameter("proc_inst_owner", task.getProcInstStartUser() );
				}
				
				//流程开始时间
				if( task.getProcStartPeriod() != null ){
					Date start = task.getProcStartPeriod() .getStart();
					Date end = task.getProcStartPeriod() .getEnd();
					
					if( start != null ){
						sql.append(" AND START_TIME_ >= #{proc_st_start} ");
						query.parameter("proc_st_start", start);
					}
					
					if( end != null ){
						Calendar cal_end = Calendar.getInstance();
						cal_end.setTime(end);
						cal_end.add(Calendar.DAY_OF_MONTH, 1);
						cal_end.add(Calendar.SECOND, -1);
						
						sql.append(" AND START_TIME_ <= #{proc_st_end} ");
						query.parameter("proc_st_end", cal_end.getTime());
					}
				}
				
				sql.append(" ) "); //end in
			}
			
			//流程实例编号
			if( StringUtils.isNotEmpty(task.getProcessInstanceId()) ){
				sql.append(" AND t.PROC_INST_ID_ = #{proc_inst_id}");
				query.parameter("proc_inst_id", task.getProcessInstanceId().trim());
			}
			
			//流程属性名称
			if( StringUtils.isNotEmpty(task.getProcessAttributeName()) ){
				sql.append(" AND t.PROC_INST_ID_ IN ( ");
				sql.append(" SELECT DISTINCT ACT_PROC_INST_ID FROM T_FLOW_PROCESS_ATTR ");
				sql.append(" WHERE BIZ_NAME LIKE #{proc_attr_name} )");
				query.parameter("proc_attr_name", "%"+task.getProcessAttributeName().trim()+"%");
			}
			
			//流程类型Key
			if( StringUtils.isNotEmpty(task.getProcessDefinitionKey()) ){
				sql.append(" AND t.PROC_DEF_ID_ IN ( ");
				sql.append(" SELECT DISTINCT ID_ FROM ");
				sql.append( managementService.getTableName( ProcessDefinition.class) );
				sql.append(" WHERE KEY_ = #{proc_def_key} )");
				query.parameter("proc_def_key", task.getProcessDefinitionKey().trim());
			}
			
		}
		
		return (ListPage<Task>) query(sql.toString(),query,page);
	}
	
	/**
	 * 查询历史任务
	 */
	@SuppressWarnings("unchecked")
	public ListPage<HistoricTaskInstance> listHistoryTask( ListPage<HistoricTaskInstance> page ){
		if( page == null )
			page = new ListPage<HistoricTaskInstance>();
		
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		
		query.finished(); //已完成任务
		
		OrderBy orderBy = page.getOrderBy();
		if( orderBy != null ){
			if( "endTime".equals(orderBy.getProperty()) )
				query.orderByHistoricTaskInstanceEndTime();
			else if( "startTime".equals(orderBy.getProperty()) )
				query.orderByHistoricTaskInstanceStartTime();
		}
		
		return (ListPage<HistoricTaskInstance>)query(query,page);
	}
	
	/**
	 * 查询流程的上一执行任务
	 * @param processInstanceId 流程实例
	 */
	@SuppressWarnings("unchecked")
	public HistoricTaskInstance getLastTask(String processInstanceId){
		ListPage<HistoricTaskInstance> page = new ListPage<HistoricTaskInstance>(1);
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		query.finished(); //已完成任务
		query.processInstanceId(processInstanceId);
		query.orderByHistoricTaskInstanceEndTime().desc();
		
		return ((ListPage<HistoricTaskInstance>)query(query,page)).getFirst();
	}
	
	/**
	 * 查询任务之前表单数据
	 */
	public List<HistoricDetail> listHistoricDetail( String taskId ){
		Task task = this.getTask(taskId);
		if( task == null )
			return null;
		
		List<HistoricTaskInstance> historyTaskList = historyService.createHistoricTaskInstanceQuery()
			.processInstanceId(task.getProcessInstanceId())
			.finished()
			.orderByTaskId().desc()
			.listPage(0,1);
		
		if( historyTaskList == null || historyTaskList.isEmpty() )
			return null;
		HistoricTaskInstance historyTask = historyTaskList.get(0);
		
		HistoricDetailQuery query = historyService.createHistoricDetailQuery().formProperties();
		query.taskId( historyTask.getId() );
		
		return query.list();
	}
	
	/**
	 * 认领流程
	 */
	public void claim( String taskId,String userId ){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if( task == null )
			throw new ApplicationException("任务["+taskId+"]不存在！");
		
		if( StringUtil.hasText( task.getAssignee() ) )
			throw new ApplicationException("任务["+taskId+"]已被用户["+task.getAssignee()+"]认领！");
		
		this.taskLogManager.save(task.getProcessInstanceId(),task.getId()
				,ExecutionType.CLAIM, userId, "用户["+userId+"]签收任务");
		
		taskService.claim(taskId, userId);
	}
	
	/**
	 * 认领并提交当前活动的流程
	 * @param procInstId 流程实例ID
	 * @param operator 操作人
	 */
	public void claimAndComplete(String procInstId,String operator){
		if( StringUtils.isEmpty(operator) )
			throw new ApplicationException("操作人不能为空！");
		
		Task task = this.getCurrentTask( procInstId );
		
		if( task == null )
			throw new ApplicationException("无法获取活动的任务[流程实例ID="+procInstId+"]");
		
		taskService.claim( task.getId(), operator );
		taskService.complete(task.getId());
	}
	
	/**
	 * 变更认领人
	 */
	public void changeAssignee( String taskId,String userId ){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if( task == null )
			throw new ApplicationException("任务["+taskId+"]不存在！");
		
		taskService.setAssignee(taskId, userId);
	}
	
	/**
	 * 添加会签人
	 */
	@Deprecated
	public void addAssignee( String taskId,List<String> userIds ){
		if( userIds == null || userIds.isEmpty() )
			throw new ApplicationException("添加的会签人不能为空！");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if( task == null )
			throw new ApplicationException("任务["+taskId+"]不存在！");
		
		if( StringUtils.isNotEmpty(task.getParentTaskId()) )
			throw new ApplicationException("任务["+task.getName()+"]已是会签任务！");
		
		//taskService.
		
		for( String user:userIds ){
			TaskEntity newTask = (TaskEntity) taskService.newTask(); //TODO 更改ID规则
			
			newTask.setAssignee(user);  
			newTask.setName( task.getName() + "-[会签]");  
			newTask.setProcessDefinitionId(task.getProcessDefinitionId());  
			newTask.setProcessInstanceId(task.getProcessInstanceId() );  
			newTask.setParentTaskId(taskId);  
			newTask.setDescription( task.getName() + "-添加会签人["+user+"]");  
            taskService.saveTask(newTask); 
		}
	}
	
	/**
	 * 完成任务
	 */
	public void complete( String taskId ){
		taskService.complete(taskId);
	}
	
	/**
	 * 完成任务
	 */
	public void complete( MyTask myTask,String user ){
		if( myTask == null || StringUtils.isEmpty(user) )
			throw new ApplicationException("参数为空！");
		
		String taskId = myTask.getId();
		if( StringUtils.isEmpty(taskId) )
			throw new ApplicationException("任务编号不能为空！");
		
		ExecutionType type = myTask.getType();
		if( type == null )
			throw new ApplicationException("任务操作类型不能为空！");
		
		String message = myTask.getMessage();
		if( StringUtils.isEmpty(message)  )
			throw new ApplicationException("流转意见不能为空！");
		
		Task task = taskService.createTaskQuery().taskId(myTask.getId()).singleResult();
		if( task == null )
			throw new ApplicationException("任务["+myTask.getId()+"]不存在！");
		
		//task.getParentTaskId();
		//identityService.setAuthenticatedUserId( user ); 
		
		//保存操作日志
		taskLogManager.save(task.getProcessInstanceId(),task.getId(),type,user, message);
		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put(WorkflowConstant.TASK_ACTIOIN_VARIABLE_NAME,type.name());
		variables.put("passed", ExecutionType.SUBMIT.equals(type)); //TODO
		
		taskService.complete(taskId,variables); //提交
	}
	
	/**
	 * 终止流程
	 */
	public void terminate( String procInstId,String user,String reason ){
		//保存操作日志
		taskLogManager.save(procInstId,procInstId,ExecutionType.TERMINATE,user, reason);
		this.runtimeService.deleteProcessInstance(procInstId, reason);
	}
	
	/**
	 * 流程注释
	 */
	public List<Comment> getProcessInstanceComments(String processInstanceId){
		return taskService.getProcessInstanceComments(processInstanceId);
	}
	
	/**
	 * 任务注释
	 */
	public List<Comment> getTaskComments(String taskId){
		return taskService.getTaskComments(taskId);
	}
	
	/**
	 * 提交数据
	 */
	public ProcessInstance submitStartFormData(String processDefinitionId,String businessKey,Map<String,String> properties  ){
		return formService.submitStartFormData(processDefinitionId,businessKey,properties);
	}
	
	/**
	 * 提交数据
	 */
	public void submitFormData(String taskId,Map<String,String> properties ){
		this.formService.submitTaskFormData(taskId, properties);
	}
	
	/**
	 * 任务表单数据
	 */
	public TaskFormData getTaskFormData(String taskId ){
		return formService.getTaskFormData(taskId);
	}
	
	/**
	 * 开始事件表单数据
	 */
	public StartFormData getStartFormData(String processDefinitionId ){
		return formService.getStartFormData(processDefinitionId);
	}
	
	/**
	 * 表单数据
	 * @param id 流程类型ID 或  任务ID
	 * @return FormData
	 */
	public FormData getFormData(String id ){
		HistoricTaskInstance task = historyService
			.createHistoricTaskInstanceQuery().taskId(id).singleResult();
		if( task != null )
			return getTaskFormData(id);
		return getStartFormData(id);
	}
	
	/** 
     * 流程转向操作 
     *  
     * @param task Task 当前任务
     * @param targetActivityId 目标节点任务ID 
     * @param variables 流程变量 
     */  
    protected void turnTransition(ExecutionType type,String user,Task task
    		,HistoricTaskInstance histTask, Map<String, Object> variables){  
    	
		if( task == null || !(task instanceof TaskEntity))
			throw new ApplicationException("当前任务为空或类型不正确!");
		
        // 当前节点  
        ActivityImpl currActivity = getActivityImpl(task, null);  
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl targetActivity = getActivityImpl(task, histTask.getTaskDefinitionKey());  
        // 设置新流向的目标节点  
        newTransition.setDestination( targetActivity );  
  
        // 执行转向任务  
        taskService.complete(task.getId(), variables);  
        //获取新Task
        Task newTask = getCurrentTask( task.getProcessInstanceId() );
        if( newTask != null ){
        	//新任务从流程定义恢复，原签收人从历史任务中得到
        	taskService.claim(newTask.getId(), histTask.getAssignee() );
        }
        // 删除目标节点新流入  
        targetActivity.getIncomingTransitions().remove(newTransition);  
  
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);  
        
        //保存操作日志
        String message = "流程转向:"+task.getName()+"-->"+histTask.getName();
      	taskLogManager.save(task.getProcessInstanceId(),task.getId(),type,user, message);
    }  
    
    /** 
     * 还原指定活动节点流向 
     *  
     * @param activityImpl 活动节点 
     * @param oriPvmTransitionList 原有节点流向集合 
     */  
    private void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();  
        pvmTransitionList.clear();  
        
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    } 
    
    /**
     * 活动节点
     */
    protected ActivityImpl getActivityImpl(Task task, String activityId ){
    	if( task == null )
    		return null;
    	
    	if( StringUtils.isEmpty(activityId) )
    		activityId = task.getTaskDefinitionKey();
    	
    	//流程定义
		ProcessDefinitionEntity processDef = (ProcessDefinitionEntity)
				((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition( task.getProcessDefinitionId()  );
		if( processDef == null )
			return null;
		
		return ((ProcessDefinitionImpl)processDef).findActivity(activityId);
    }
    
    /** 
     * 清空指定活动节点流向 
     *  
     * @param activityImpl 活动节点 
     * @return 节点流向集合 
     */  
    protected List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>(); 
        
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList)
            oriPvmTransitionList.add(pvmTransition);  
        
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    }  
	
	/**
	 * 回退到上一步
	 * @param procInstId 流程实例ID
	 */
	public void undoTask( String procInstId,String user){
		
		List<Task> activeTasks = taskService.createTaskQuery()
				.processInstanceId(procInstId).active().list();
		if( activeTasks == null || activeTasks.size() != 1 )
			throw new ApplicationException("流程活动任务不存在或存在多个，无法回退!");
		
		Task task = activeTasks.get(0); //当前任务节点
		if( task == null )
			throw new ApplicationException("无法获取当前任务，无法回退!");
		
		List<HistoricTaskInstance> histTaskList = historyService
				.createHistoricTaskInstanceQuery().finished()
				.orderByHistoricTaskInstanceEndTime().desc()
				.listPage(0, 1);
		HistoricTaskInstance histTask = (histTaskList==null
				||histTaskList.isEmpty())?null:histTaskList.get(0);
		
		if( histTask == null )
			throw new ApplicationException("历史任务不存在，无法回退!");
		
		if( histTask.getAssignee() == null || !histTask.getAssignee().equals(user) )
			throw new ApplicationException("其它操作用户已完成了新任务，无法回退!");
		
		//流程转向
		turnTransition(ExecutionType.FALLBACK,user,task,histTask,null);
	}
	
	/**
	 * 会签
	 * @param operator 操作人
	 * @param taskId 任务ID
	 * @param users 会签用户
	 */
	public void countersignTask(String operator,String taskId,String[] users){
		if( StringUtils.isEmpty(taskId) )
			throw new ApplicationException("会签任务ID不能为空!");
		
		if( users == null )
			throw new ApplicationException("会签用户不能为空!");
		
		Task task = getTask(taskId);
		if( task == null )
			throw new ApplicationException("任务ID["+taskId+"]不存在!");
		
		if( StringUtils.isEmpty(operator) ){
			operator = task.getAssignee();
			log.warn("会签操作人为空，使用任务签收人[{}]",task.getAssignee());
		}
		
		StringBuffer message = new StringBuffer("添加会签人[");
		int i = 0;
		
		//对用户不能重复添加会签
		List<Task> subTasks = taskService.getSubTasks(taskId); //子任务
		Map<String,Task> subTaskMap = new HashMap<String,Task>();
		if( subTasks != null && subTasks.size() > 0 ){
			for(Task item: subTasks){
				subTaskMap.put(item.getAssignee(), item);
			}
		}
		
		for( String user:users ){
			if( task.getAssignee().equals(user) )
				throw new ApplicationException("不能添加自己["+user+"]为会签人");
				
			if( subTaskMap.get(user) != null )
				throw new ApplicationException("任务已添加会签人["+user+"]");
			
			TaskEntity subTask = (TaskEntity)taskService.newTask();
			subTask.setParentTaskId(taskId); //上级Task
			subTask.setName( task.getName() + "-["+user+"]会签");
			subTask.setAssignee( user ); //会签用户
			subTask.setProcessDefinitionId( task.getProcessDefinitionId() );
			subTask.setProcessInstanceId( task.getProcessInstanceId() );
			subTask.setDescription("会签"); 
			
			taskService.saveTask(subTask);
			
			message.append((i==0?"":",") +user);
			++i;
		}
		message.append("]");
		
		taskLogManager.save(task.getProcessInstanceId(),task.getId()
				,ExecutionType.SIGN, operator, message.toString());
		
	}
	
}
