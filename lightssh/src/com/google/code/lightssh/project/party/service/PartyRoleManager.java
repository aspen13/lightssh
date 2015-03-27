package com.google.code.lightssh.project.party.service;

import java.util.Collection;
import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.PartyRole.RoleType;

/**
 * PartyRole Manager
 * @author YangXiaojin
 *
 */
public interface PartyRoleManager extends BaseManager<PartyRole>{
	
	/**
	 * list by RoleType
	 */
	public List<PartyRole> list( RoleType type );
	
	/**
	 * list by Party
	 */
	public List<PartyRole> list( Party party );
	
	/**
	 * list by Party
	 */
	public List<PartyRole> list( Party party ,RoleType[] inTypes );
	
	/**
	 * list Party by RoleType
	 */
	public List<Party> listParty( RoleType type );
	
	/**
	 * save 
	 */
	public void save( Party party,Collection<RoleType> types );
	
	/**
	 * delete PartyRole by party
	 */
	public void remove( Party party );

}
