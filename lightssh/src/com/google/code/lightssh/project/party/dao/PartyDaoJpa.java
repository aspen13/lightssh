package com.google.code.lightssh.project.party.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.ReflectionUtil;
import com.google.code.lightssh.project.party.entity.Employee;
import com.google.code.lightssh.project.party.entity.Organization;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.Person;

@Repository("partyDao")
public class PartyDaoJpa extends JpaAnnotationDao<Party> implements PartyDao{
	
	private static final long serialVersionUID = -2223709423588592533L;

	public ListPage<Party> list(ListPage<Party> page,Party t ){
		return this.list( super.entityClass, page, t );
	}
	
	public ListPage<Party> list(Class<?> clazz,ListPage<Party> page,Party t ){
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer( );
		hql.append( " FROM " + clazz.getName() + " AS m " );
		hql.append( " WHERE 1=1 ");
		
		if( t != null ){
			if( !StringUtils.isEmpty(t.getId()) ){
				hql.append( " AND m.id = ? " );
				params.add( t.getId().trim() );
			}
			
			if( !StringUtils.isEmpty(t.getName())){
				hql.append( " AND m.name like ? " );
				params.add( "%" + t.getName().trim() + "%");
			}
			
			if( t instanceof Person ){
				Person p = (Person)t;
				if( StringUtils.isNotEmpty(p.getIdentityCardNumber()) ){
					hql.append( " AND m.identityCardNumber like ? " );
					params.add( "%" + p.getIdentityCardNumber().trim() + "%");
				}
				
				if( p.getCredentialsType() != null ){
					hql.append( " AND m.credentialsType = ? " );
					params.add( p.getCredentialsType() );
				}
				
				//人事信息
				if(p.getEmployee() != null ){
					Employee employee = p.getEmployee();
					
					StringBuffer hql_emp = new StringBuffer(" SELECT e.person.id FROM " );
					hql_emp.append( Employee.class.getName() );
					hql_emp.append(" AS e WHERE 1=1 ");
					
					if( employee.getOrganization() != null 
							&& StringUtils.isNotEmpty(employee.getOrganization().getId())){
						hql_emp.append( " AND e.organization.id = ? " );
						params.add( employee.getOrganization().getId().trim() );
					}
					
					if( employee.getStatus() != null ){
						hql_emp.append( " AND e.status = ? " );
						params.add( employee.getStatus() );
					}
					
					if( employee.getType() != null ){
						hql_emp.append( " AND e.type = ? " );
						params.add( employee.getType() );
					}
					
					if( hql_emp.toString().indexOf("AND") >0 )
						hql.append( " AND m.id IN ( " + hql_emp +") " );
				}
			}
			
		}
		
		return super.query(page, hql.toString(), params.toArray( ) );
	}
	
	public ListPage<Party> list(Class<?> clazz,ListPage<Party> page, Party t,Collection<String> properties ){
		if( t == null || page == null || properties == null)
			return null;
		
		HashSet<String> set = new HashSet<String>( properties );
		if( set.isEmpty() )
			return null;
		
		List<Object> params = new ArrayList<Object>( set.size() );
		StringBuffer sb = new StringBuffer( " FROM " + clazz.getName() + " AS m WHERE 1 = 1 " );
		for( String property:set ){
			Object value = ReflectionUtil.reflectGetValue(t, property);
			sb.append( " and m." + property + " = ? ");
			params.add( value );
		}
		
		return this.query(page, sb.toString(), params.toArray());
	}
	
	public ListPage<Party> list(ListPage<Party> page, Party t,Collection<String> properties ){
		Class<?> clazz = Organization.class;
		if( t instanceof Person )
			clazz = Person.class;
		return super.list(clazz, page, t, properties);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Party read(Class clazz, Party party) {
		if( party == null )
			return null;
		
		return this.getEntityManager().find(clazz,party.getIdentity());
	}

}
