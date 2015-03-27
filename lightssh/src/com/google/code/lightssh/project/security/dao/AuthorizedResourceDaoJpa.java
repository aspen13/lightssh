
package com.google.code.lightssh.project.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.security.entity.AuthorizedResource;

/** 
 * @author YangXiaojin
 * @date 2013-3-7
 * @description：TODO
 */
@Repository("authorizedResourceDao")
public class AuthorizedResourceDaoJpa extends JpaDao<AuthorizedResource> implements AuthorizedResourceDao{

	private static final long serialVersionUID = -3606910894341455653L;
	
	/**
	 * 通过URL查询，正则匹配
	 */
	@SuppressWarnings("unchecked")
	public AuthorizedResource getWithRegexp( String url ){
		String sql = "select * from T_SECURITY_AUTH_RES where REGEXP_LIKE(?,url)";
		
		List<AuthorizedResource> results = getEntityManager().createNativeQuery(
				sql, super.entityClass ).setParameter(1,url)
				.setMaxResults(1).getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}

}
