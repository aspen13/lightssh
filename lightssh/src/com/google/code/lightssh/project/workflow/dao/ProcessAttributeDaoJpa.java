package com.google.code.lightssh.project.workflow.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.workflow.entity.ProcessAttribute;

/**
 * 
 * @author Aspen
 * @date 2013-8-30
 * 
 */
@Repository("processAttributeDao")
public class ProcessAttributeDaoJpa extends JpaDao<ProcessAttribute> implements ProcessAttributeDao{

	private static final long serialVersionUID = 1723414628065528253L;
	
	/**
	 * 通过流程实例Id查询
	 */
	@SuppressWarnings("unchecked")
	public ProcessAttribute getByProcInstId( String procInstId ){
		String hql = " FROM " + this.entityClass.getName() 
				+ " AS m WHERE m.actProcInstId = ? ";
			
		Query query = getEntityManager().createQuery(hql).setMaxResults(1);
		query = addQueryParams(query,new Object[]{procInstId});
		
		List<ProcessAttribute> results = query.getResultList();
		
		return (results == null || results.isEmpty())?null:results.get(0);
	}

}
