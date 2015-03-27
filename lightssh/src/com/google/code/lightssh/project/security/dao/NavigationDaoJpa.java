package com.google.code.lightssh.project.security.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.entity.Permission;

/**
 * Navigation Dao Hibernate
 * @author YangXiaojin
 *
 */
@Repository("navigationDao")
public class NavigationDaoJpa extends JpaAnnotationDao<Navigation> implements NavigationDao{

	private static final long serialVersionUID = 2228287462062288592L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Navigation> listTop() {
		String hql = " SELECT n FROM Navigation AS n WHERE n.parent IS NULL ";
		
		Query query = this.getEntityManager().createQuery(hql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Navigation> listMenu() {
		String hql = " SELECT n FROM Navigation AS n WHERE n.parent IS NULL AND n.isMenu = ? ";
		
		Query query = addQueryParams(getEntityManager().createQuery(hql), Boolean.TRUE);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Permission> listPermission(Collection<Navigation> colls) {
		if( colls == null || colls.isEmpty() )
			return null;
		
		StringBuffer hql = new StringBuffer( 
				" SELECT n.permission FROM Navigation AS n WHERE n.id in ( " );
		boolean isFirst = true;
		for( Navigation item:colls ){
			if( item == null || item.getId() == null )
				continue;
			hql.append( (isFirst?"":",") + "'" + item.getId() + "'" );
			isFirst = false;
		}
		hql.append(" ) ");
		
		Query query = this.getEntityManager().createQuery(hql.toString());
		return query.getResultList();
		
		//return super.getJpaTemplate().find(hql.toString());
	}

}
