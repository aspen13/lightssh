package com.google.code.lightssh.project.scheduler.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.scheduler.entity.JobQueue;

/**
 * JobQueue JPA 实现
 * @author YangXiaojin
 */
@Repository("jobQueueDao")
public class JobQueueDaoJpa extends JpaDao<JobQueue> implements JobQueueDao{

	private static final long serialVersionUID = 1929954400169139057L;
	
	@SuppressWarnings("unchecked")
	public JobQueue get( String typeid,String refid ){
		if( StringUtils.isEmpty(typeid) || StringUtils.isEmpty(refid) )
			return null;
		
		StringBuffer sb = new StringBuffer(" FROM " + 
				this.entityClass.getName() + " AS m ");
		sb.append(" WHERE m.type.id = ? AND m.refId = ? ");
		
		Query query = getEntityManager().createQuery(sb.toString());
		addQueryParams(query,new String[]{typeid,refid});
		List<JobQueue> results = query.getResultList();
		
		return (results == null || results.isEmpty())?null:results.get(0);
	}

}
