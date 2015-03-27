package com.google.code.lightssh.project.party.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.party.entity.Employee;
import com.google.code.lightssh.project.party.entity.Person;

/**
 * 
 * @author Aspen
 * @date 2013-9-2
 * 
 */
public interface EmployeeDao extends Dao<Employee>{
	
	public Employee get(Person p);

}
