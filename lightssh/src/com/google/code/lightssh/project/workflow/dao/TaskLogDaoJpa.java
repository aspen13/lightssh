package com.google.code.lightssh.project.workflow.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.workflow.entity.TaskLog;

/**
 * 
 * @author Aspen
 * @date 2013-8-27
 * 
 */
@Repository("taskLogDao")
public class TaskLogDaoJpa extends JpaDao<TaskLog> implements TaskLogDao{

	private static final long serialVersionUID = -5518400578822297298L;

}
