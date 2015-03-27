package com.google.code.lightssh.project.workflow.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.workflow.entity.ProcessAttribute;

/**
 * 
 * @author Aspen
 * @date 2013-8-30
 * 
 */
public interface ProcessAttributeManager extends BaseManager<ProcessAttribute>{
	
	/**
	 * 通过流程实例Id查询
	 */
	public ProcessAttribute getByProcInstId( String procInstId );
	
	/**
	 * 保存流程属性
	 * @param procDefKey 流程定义Key
	 * @param procInstId 流程实例Id
	 * @param bizKey 业务Key
	 * @param bizName 业务名称
	 * @param userId 用户Id
	 */
	public void save(String procDefKey,String procInstId
			,String bizKey,String bizName,String userId );

}
