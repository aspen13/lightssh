package com.google.code.lightssh.project.scheduler.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.scheduler.dao.SchedulerTypeDao;
import com.google.code.lightssh.project.scheduler.entity.SchedulerType;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("schedulerTypeManager")
public class SchedulerTypeManagerImpl extends BaseManagerImpl<SchedulerType> implements SchedulerTypeManager{

	private static final long serialVersionUID = 3512177844901314400L;
	
	@Resource(name="schedulerTypeDao")
	public void setDao(SchedulerTypeDao dao ){
		this.dao = dao;
	}
	
	public SchedulerTypeDao getDao( ){
		return (SchedulerTypeDao)this.dao;
	}
	
	/**
	 * 安全取类型
	 */
	public SchedulerType safeGet(String id,String name){
		return safeGet(id,name,null);
	}

	@Override
	public SchedulerType safeGet(String id, String name, String description) {
		SchedulerType type = dao.read(id);
		if( type == null ){
			type = new SchedulerType(id,name);
			type.setDescription(description);
			dao.create(type);
		}
		
		return type;
	}

}
