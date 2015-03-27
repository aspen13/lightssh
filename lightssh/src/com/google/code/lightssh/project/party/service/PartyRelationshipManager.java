package com.google.code.lightssh.project.party.service;

import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRelationship;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.PartyRelationship.RelationshipType;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface PartyRelationshipManager extends BaseManager<PartyRelationship>{
	
	/**
	 * list by RoleType
	 */
	public List<PartyRelationship> list( RelationshipType type );
	
	/**
	 * 根据条件查询
	 */
	public PartyRelationship getRollupByFromParty( Party from );
	
	/**
	 * 根据条件查询
	 */
	public List<PartyRelationship> listRollupByToParty( Party to );
	
	/**
	 * delete PartyRelationship by PartyRole
	 */
	public void removeByFromRole( PartyRole partyRole );

}
