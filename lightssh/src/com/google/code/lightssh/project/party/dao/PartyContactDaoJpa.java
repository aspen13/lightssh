package com.google.code.lightssh.project.party.dao;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.project.contact.entity.ContactMechanism;
import com.google.code.lightssh.project.contact.entity.ContactMechanism.ContactMechanismType;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyContact;

/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("partyContactDao")
public class PartyContactDaoJpa extends JpaAnnotationDao<PartyContact> implements PartyContactDao{

	private static final long serialVersionUID = 4134413325348834944L;

	@Override
	public Collection<PartyContact> list(Party party,ContactMechanism contact) {
		//String hql = " FROM " + entityClass.getName() 
		//	+ " AS m WHERE m.contact.id = ? "; 

		throw new ApplicationException("删除未实现");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PartyContact> list(Party party,
			ContactMechanismType... types ) {
		StringBuffer hql = new StringBuffer( 
				" SELECT m FROM " + entityClass.getName()
			+ " AS m WHERE m.party = ? " );
		
		if( types != null ){
			if( types.length == 1 ){
				hql.append(" AND m.contact.type = '"+types[0].name()+"' ");
			}else
				for( int i=0;i<types.length;i++ ){
					if( i== 0 )
						hql.append(" AND m.contact.type in ( ");
					hql.append( ((i==0)?"":",") + "'"+ types[i].name() +"'" );
					if( i==types.length-1 )
						hql.append(" )");
				}//end for
		}
		
		//return getJpaTemplate().find(hql.toString(),party);
		
		Query query = getEntityManager().createQuery(hql.toString());
		super.addQueryParams(query, new Object[]{party});
		return query.getResultList();
	}

}
