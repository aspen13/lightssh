package com.google.code.lightssh.project.party.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRelationship;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.PartyRelationship.RelationshipType;

/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("partyRelationshipDao")
public class PartyRelationshipDaoJpa extends JpaAnnotationDao<PartyRelationship> 
implements PartyRelationshipDao{

	private static final long serialVersionUID = -3759568965957492052L;

	@SuppressWarnings("unchecked")
	@Override
	public List<PartyRelationship> list( RelationshipType type ) {
		String hql = " SELECT m FROM " + entityClass.getName()
			+ " AS m WHERE m.type = ? "
			+ "  AND ( m.period.end IS NULL OR m.period.end >= ? )"
			;
	
		//return getJpaTemplate().find(hql,new Object[]{type,new Date()} );
		
		Query query = this.getEntityManager().createQuery(hql);
		this.addQueryParams(query,new Object[]{type,new Date()} );
		
		return query.getResultList();
	}
	
	@Override
	public void removeByFromRole( PartyRole partyRole ){
		//String hql = " DELETE FROM " + entityClass.getName()
		//	+ " AS m WHERE m.from = ? ";
		throw new ApplicationException("DAO未实现！");
		//getJpaTemplate().delete(hql,partyRole);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartyRelationship> list(RelationshipType type, Party from,Party to ) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer( );
		
		hql.append( " FROM " + entityClass.getName() + " AS m " );
		hql.append( " WHERE m.type = ? ");
		params.add( RelationshipType.ORG_ROLLUP );
		
		if( from != null && from.getIdentity() != null ){
			hql.append( " AND m.from.party.id = ? " );
			params.add( from.getIdentity() );
		}
		
		if( to != null && to.getIdentity() != null){
			hql.append( " AND m.to.party.id = ? " );
			params.add( to.getId() );
		}
		
		Query query = this.getEntityManager().createQuery(hql.toString());
		this.addQueryParams(query, params);
		return query.getResultList();
	}

}
