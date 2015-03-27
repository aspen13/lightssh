package com.google.code.lightssh.project.workflow.service;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.workflow.dao.ProcessAttributeDao;
import com.google.code.lightssh.project.workflow.entity.ProcessAttribute;

/**
 * 
 * @author Aspen
 * @date 2013-8-30
 * 
 */
@Component("processAttributeManager")
public class ProcessAttributeManagerImpl extends BaseManagerImpl<ProcessAttribute> 
	implements ProcessAttributeManager{

	private static final long serialVersionUID = -7628499918081876329L;
	
	@Resource(name="processAttributeDao")
	public void setDao(ProcessAttributeDao dao){
		this.dao = dao;
	}
	
	public ProcessAttributeDao getDao(){
		return (ProcessAttributeDao)this.dao;
	}
	
	/**
	 * 通过流程实例Id查询
	 */
	public ProcessAttribute getByProcInstId( String procInstId ){
		return getDao().getByProcInstId(procInstId);
	}
	
	/**
	 * 保存流程属性
	 * @param procDefKey 流程定义Key
	 * @param procInstId 流程实例Id
	 * @param bizKey 业务Key
	 * @param bizName 业务名称
	 * @param userId 用户Id
	 */
	public void save(String procDefKey,String procInstId
			,String bizKey,String bizName,String userId ){
		
		ProcessAttribute pa = new ProcessAttribute();
		pa.setActProcDefKey(procDefKey);
		pa.setActProcInstId(procInstId);
		pa.setBizKey(bizKey);
		pa.setBizName(bizName);
		pa.setOwner(userId);
		pa.setCreatedTime(Calendar.getInstance());
		
		dao.create(pa);
	}

}
