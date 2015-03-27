package com.google.code.lightssh.project.party.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.party.dao.EmployeeDao;
import com.google.code.lightssh.project.party.entity.Employee;
import com.google.code.lightssh.project.party.entity.Organization;
import com.google.code.lightssh.project.party.entity.Person;

/**
 * 
 * @author Aspen
 * @date 2013-9-2
 * 
 */
@Component("employeeManager")
public class EmployeeManagerImpl extends BaseManagerImpl<Employee> implements EmployeeManager{

	private static final long serialVersionUID = -6304815635978981819L;
	
	@Resource(name="partyManager")
	private PartyManager partyManager;
	
	@Resource(name="employeeDao")
	public void setDao(EmployeeDao dao){
		this.dao = dao;
	}
	
	public EmployeeDao getDao(){
		return (EmployeeDao)this.dao;
	}
	
	/**
	 * 根据人员ID查询人事信息
	 */
	public Employee get(Person person){
		return getDao().get(person);
	}
	
	/**
	 * 根据人员ID查询人事信息
	 */
	public Employee getByPerson(String personId){
		Person p = new Person();
		p.setId( personId);
		
		return getDao().get(p);
	}
	
	/**
	 * 保存人事信息
	 */
	public void save(Employee t){
		if( t.getPerson() == null || t.getPerson().getId() == null )
			throw new ApplicationException("人员信息为空！");
		
		Person p = partyManager.getPerson( t.getPerson() );
		if( p == null )
			throw new ApplicationException("人员信息["+t.getPerson().getId()+"]不存在！");
		
		if( t.getOrganization() == null || StringUtils.isEmpty(t.getOrganization().getId()))
			throw new ApplicationException("部门信息为空！");
		
		Organization org = partyManager.getOrganization(t.getOrganization().getId());
		if( org == null )
			throw new ApplicationException("部门信息["+t.getOrganization().getId()+"]不存在！");
		
		Employee db_e = get(p);
		if( db_e == null ){
			db_e = t;
			dao.create(db_e);
		}else{
			db_e.setOrganization(t.getOrganization());
			db_e.setStatus(t.getStatus());
			db_e.setType( t.getType() );
			db_e.setPosition( t.getPosition() );
			db_e.setWorkplace(t.getWorkplace());
			db_e.setEmploymentDate(t.getEmploymentDate());
			db_e.setDescription(t.getDescription());
			dao.update(db_e);
		}
	}
	
}
