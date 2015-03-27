package com.google.code.lightssh.project.party.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.party.entity.Employee;
import com.google.code.lightssh.project.party.entity.Person;

/**
 * 
 * @author Aspen
 * @date 2013-9-2
 * 
 */
public interface EmployeeManager extends BaseManager<Employee>{
	
	/**
	 * 根据人员ID查询人事信息
	 */
	public Employee get(Person person);
	
	/**
	 * 根据人员ID查询人事信息
	 */
	public Employee getByPerson(String personId);

}
