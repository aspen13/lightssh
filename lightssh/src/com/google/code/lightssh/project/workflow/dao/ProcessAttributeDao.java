package com.google.code.lightssh.project.workflow.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.workflow.entity.ProcessAttribute;

/**
 * 
 * @author Aspen
 * @date 2013-8-30
 * 
 */
public interface ProcessAttributeDao extends Dao<ProcessAttribute>{
	
	/**
	 * 通过流程实例Id查询
	 */
	public ProcessAttribute getByProcInstId( String procInstId );

}
