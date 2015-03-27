
package com.google.code.lightssh.project.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.security.entity.Permission;

/** 
 * @author YangXiaojin
 * @date 2013-3-8
 * @description：TODO
 */
@Repository("permissionDao")
public class PermissionDaoJpa extends JpaDao<Permission> implements PermissionDao{

	private static final long serialVersionUID = 7091272209721190638L;
	
	/**
	 * 通过URL查询
	 */
	@SuppressWarnings("unchecked")
	public Permission getByUrlWithRegexp( String url ){
		String sql = "select * from T_SECURITY_PERMISSION where REGEXP_LIKE(?,url)";
		
		List<Permission> results = getEntityManager().createNativeQuery(
				sql, super.entityClass ).setParameter(1,url)
				.setMaxResults(1).getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}

}
