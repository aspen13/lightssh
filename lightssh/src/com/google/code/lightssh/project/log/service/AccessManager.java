package com.google.code.lightssh.project.log.service;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.log.entity.History;

/**
 * Access Manager
 * @author YangXiaojin
 *
 */
public interface AccessManager extends BaseManager<Access>{
	
	/**
	 * 记录实体变动
	 * @param access 操作记录
	 * @param originalModel 原实体
	 * @param newModel 新实体
	 */
	public void log( Access access, Persistence<?> originalModel,Persistence<?> newModel );
	
	/**
	 * 查询历史记录
	 * @param access
	 * @return
	 */
	public History getHistory( Access access );
	
	/**
	 * 写系统日志
	 */
	public void writeSystemLog(Access t);

}
