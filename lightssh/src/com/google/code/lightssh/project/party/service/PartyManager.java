package com.google.code.lightssh.project.party.service;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.party.entity.Organization;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.Person;
import com.google.code.lightssh.project.party.entity.PartyRole.RoleType;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface PartyManager extends BaseManager<Party>{
	
	/**
	 * 带条件的分页查询
	 */
	public ListPage<Party> listPerson(ListPage<Party> page,Party party );
	
	/**
	 * 带条件的分页查询
	 */
	public ListPage<Party> listOrganization(ListPage<Party> page,Party party );
	
	/**
	 * 查询 Organization
	 */
	public Organization getOrganization( Party party );
	
	/**
	 * 查询 Organization
	 */
	public Organization getOrganization(String partyid);
	
	/**
	 * 查询 最上级组织
	 */
	public Organization getParentOrganization( );
	
	/**
	 * 查询 Organization
	 */
	public Organization getOrganizationWithParent( Party party );
	
	/**
	 * 查询所有隶属关系
	 */
	public Organization listRollup( );

	/**
	 * 查询 Person
	 */
	public Person getPerson( Party party );
	
	/**
	 * 保存时记录日志
	 */
	public void save( Party party ,Access access );
	
	/**
	 * 保存内部组织
	 * @param party 内部组织
	 * @param types 角色类型
	 * @param access 日志记录
	 */
	public void save( Organization party,Access access,RoleType...types );
	
	/**
	 * 带日志的删除
	 */
	public void remove(Party party, Access access);
	
	/**
	 * 带日志的删除
	 */
	public void remove(Organization party, Access access);
	
	/**
	 * 名称是否唯一
	 */
	public boolean isUniqueName( Party party );

}
