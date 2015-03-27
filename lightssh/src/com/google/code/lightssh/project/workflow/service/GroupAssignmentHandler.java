package com.google.code.lightssh.project.workflow.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.service.LoginAccountManager;

/**
 * 
 * @author Aspen
 * @date 2013-8-20
 * 
 */
@Component("groupAssignmentHandler")
public class GroupAssignmentHandler implements TaskListener{

	private static final long serialVersionUID = -6070540081427225201L;
	
	/**
	 * 角色前缀
	 */
	public static final String GROUP_ROLE_PREFIX = "#ROLE{";
	
	/**
	 * 权限前缀
	 */
	public static final String GROUP_PERM_PREFIX = "#PERM{";
	
	/**
	 * 部门前缀
	 */
	public static final String GROUP_DEPT_PREFIX = "#DEPT{";
	
	/**
	 * 后缀
	 */
	public static final String GROUP_SUFFIX = "}";
	
	/**
	 * 当前用户
	 */
	public static final String CURRENT_USER = "@USER";
	
	/**
	 * 当前部门
	 */
	public static final String CURRENT_DEPT = "@DEPT";
	
	private static Logger log = LoggerFactory.getLogger(GroupAssignmentHandler.class);
	
	@Resource(name="loginAccountManager")
	private LoginAccountManager loginAccountManager;
	
	//@Resource(name="workflowManager")
	//private WorkflowManager workflowManager;

	/**
	 * 根据candidateGroups属性，赋值candidateUsers或assignee
	 * candidateGroups属性有如下几种类型：
	 * 	#ROLE{},权限角色
	 * 	#PERM{},权限令牌
	 * 	#DEPT{},部门机构
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		Set<String> users = new HashSet<String>();
		Set<IdentityLink> idLinks = delegateTask.getCandidates(); 
		StringBuffer msg = new StringBuffer();
		
		for(IdentityLink item: idLinks){
			if(this.isMatched(item.getGroupId())){ //只考虑candidateGroups属性
				msg.append( item.getGroupId() + " " );
				Set<String> item_users = listCandidateUser(item.getGroupId());
				if( item_users != null && !item_users.isEmpty() )
					users.addAll(item_users);
			}
		}
		
		if( !users.isEmpty() ){
			delegateTask.addCandidateUsers(users);
			if( users.size() == 1 ) //如果只有一个候选者，直接赋值
				delegateTask.setAssignee((String)users.toArray()[0]);
		}else{
			log.warn("流程[{}]-任务[{}]无法获取到候选用户[{}]",new Object[]{
					delegateTask.getProcessInstanceId(),delegateTask.getName(),msg.toString()} );
		}
	}
	
	/**
	 * 是否匹配
	 */
	protected boolean isGroupMatched( String groupPrefix,String groupSuffix,String value ){
		if( StringUtils.isEmpty(value) )
			return false;
		
		int len = value.length();
		
		return (value.indexOf(groupPrefix)==0 ) 
				&& ((value.lastIndexOf(groupSuffix)+1)==len);
	}
	
	/**
	 * 是否匹配
	 */
	protected boolean isMatched( String value ){
		if( StringUtils.isEmpty(value) )
			return false;
		
		return isGroupMatched(GROUP_ROLE_PREFIX,GROUP_SUFFIX,value)
				|| isGroupMatched(GROUP_PERM_PREFIX,GROUP_SUFFIX,value)
				|| isGroupMatched(GROUP_DEPT_PREFIX,GROUP_SUFFIX,value)
				;
	}
	
	/**
	 * 组值
	 * @param groupPrefix 前缀
	 * @param groupSuffix 后缀
	 */
	protected String plainGroupValue( String groupPrefix,String groupSuffix,String value ){
		return value.substring(groupPrefix.length(),value.length()-GROUP_SUFFIX.length());
	}
	
	/**
	 * 候选用户
	 */
	protected Set<String> listCandidateUser(String group){
		List<LoginAccount> las = listCandidateLoginAccount(group);
		if(las==null || las.isEmpty())
			return null;
		
		Set<String> users = new HashSet<String>();
		for( LoginAccount item:las )
			users.add(item.getLoginName());
		
		return users;
	}
	
	/**
	 * 候选用户
	 */
	protected List<LoginAccount> listCandidateLoginAccount(String group){
		if( !isMatched(group) )
			return null;
		
		if(isGroupMatched(GROUP_ROLE_PREFIX,GROUP_SUFFIX,group) ) //角色
			return listCandidateUserByRole(plainGroupValue(GROUP_ROLE_PREFIX,GROUP_SUFFIX,group));
		else if(isGroupMatched(GROUP_PERM_PREFIX,GROUP_SUFFIX,group) ) //权限
			return listCandidateUserByPermission(plainGroupValue(GROUP_PERM_PREFIX,GROUP_SUFFIX,group));
		
		//else if(isGroupMatched(GROUP_DEPT_PREFIX,GROUP_SUFFIX,group) ) //部门
		//	return listCandidateUserByRole(plainGroupValue(GROUP_DEPT_PREFIX,GROUP_SUFFIX,group));
		
		log.warn("Candidate Group[{}]无对应用户！",group);
		
		return null;
	}
	
	/**
	 * 查询对应角色的登录帐户
	 */
	protected List<LoginAccount> listCandidateUserByRole(String role_id){
		return loginAccountManager.listByRole(role_id);
	}
	
	/**
	 * 查询对应权限的登录帐户
	 */
	protected List<LoginAccount> listCandidateUserByPermission(String perm_id){
		return loginAccountManager.listByPermission(perm_id);
	}

}
