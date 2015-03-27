package com.google.code.lightssh.project.scheduler.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.scheduler.entity.JobInterval;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component( "jobIntervalManager" )
public class JobIntervalManagerImpl extends BaseManagerImpl<JobInterval> implements JobIntervalManager{

	private static final long serialVersionUID = 8239840139265721193L;

	@Resource( name="jobIntervalDao" )
	public void setDao(Dao<JobInterval> dao) {
		this.dao = dao;
	}
	
	@Override
	public List<JobInterval> listAvailable() {
		//TODO 
		return super.dao.listAll();
	}

}
