package com.google.code.lightssh.project.party.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.party.entity.Employee;
import com.google.code.lightssh.project.party.entity.Person;

/**
 * 
 * @author Aspen
 * @date 2013-9-2
 * 
 */
@Repository("employeeDao")
public class EmployeeDaoJpa extends JpaDao<Employee> implements EmployeeDao{

	private static final long serialVersionUID = 4817610276856370538L;
	
	@SuppressWarnings("unchecked")
	public Employee get(Person p){
		if( p == null || p.getId() == null )
			return null;
		
		String hql = " FROM " + this.entityClass.getName() + " AS m WHERE m.person.id = ? ";
		
		Query query = addQueryParams(getEntityManager().createQuery(hql,Employee.class),p.getId());
		List<Employee> results = query.setMaxResults(1).getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}

}
