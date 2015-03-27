package com.google.code.lightssh.project.identity.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.project.identity.entity.Identity;
import com.google.code.lightssh.project.identity.entity.IdentityType;

@Repository("identityDao")
public class IdentityDaoJPA extends JpaAnnotationDao<Identity> implements IdentityDao{
	
	private static final long serialVersionUID = -1898829074151936219L;

	public IdentityDaoJPA() {
		super();
	}

	@SuppressWarnings("unchecked")
	public Identity getByValue(String value) {
		String hql = " SELECT m FROM " + entityClass.getName()
			+ " AS m WHERE m.value = ? ";
		
		//List<Identity> results = getJpaTemplate().find(hql,value );
		
		Query query = getEntityManager().createQuery( hql.toString() );
		this.addQueryParams(query, new Object[]{value});
		List<Identity> results = query.getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}

	@SuppressWarnings("unchecked")
	public Identity get(IdentityType type, String value) {
		String hql = " SELECT m FROM " + entityClass.getName()
			+ " AS m WHERE m.value = ? AND m.type = ? ";
		
		//List<Identity> results = getJpaTemplate().find(
		//		hql, new Object[]{value,type} );
		
		Query query = getEntityManager().createQuery( hql.toString() );
		this.addQueryParams(query,new Object[]{value,type});
		List<Identity> results = query.getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}

}
