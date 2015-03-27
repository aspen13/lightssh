package com.google.code.lightssh.project.workflow.service;

import com.google.code.lightssh.project.workflow.model.ExecutionType;

/**
 * 工作流业务接口
 * @author Aspen
 * @date 2013-8-28
 * 
 */
public interface BusinessManager {
	
	/**
	 * 业务处理
	 * @param type 类型
	 * @param procDefKey 流程定义Key
	 * @param procInstId 流程实例Id
	 * @param bizKey 业务关联Key
	 */
	public void process( ExecutionType type,String procDefKey,String procInstId,String bizKey );

}
