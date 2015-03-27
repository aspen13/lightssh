package com.google.code.lightssh.project.log.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.log.entity.History;


@Repository("historyDao")
public class HistoryDaoJpa extends JpaAnnotationDao<History> implements HistoryDao{

	private static final long serialVersionUID = -5605833694019180373L;

	@SuppressWarnings("unchecked")
	@Override
	public History getByAccess(Access access) {
		String hql = " SELECT m FROM " + entityClass.getName()
			+ " AS m WHERE m.access = ? ";
	
		//List<History> results = getJpaTemplate().find(hql,access );
		
		Query query = getEntityManager().createQuery( hql.toString() );
		this.addQueryParams(query, new Object[]{access});
		List<History> results = query.getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}

}
