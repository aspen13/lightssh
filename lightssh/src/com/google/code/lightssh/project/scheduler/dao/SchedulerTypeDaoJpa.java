package com.google.code.lightssh.project.scheduler.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.scheduler.entity.SchedulerType;

/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("schedulerTypeDao")
public class SchedulerTypeDaoJpa extends JpaDao<SchedulerType> implements SchedulerTypeDao{

	private static final long serialVersionUID = 5549907650147730751L;

}
