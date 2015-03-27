package com.google.code.lightssh.project.party.dao;

import java.util.List;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRelationship;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.PartyRelationship.RelationshipType;

/**
 * PartyRole Dao
 * @author YangXiaojin
 *
 */
public interface PartyRelationshipDao extends Dao<PartyRelationship>{
	
	/**
	 * list by RoleType
	 */
	public List<PartyRelationship> list( RelationshipType type );
	
	/**
	 * delete PartyRelationship by PartyRole
	 */
	public void removeByFromRole( PartyRole partyRole );
	
	/**
	 * 根据条件查询
	 */
	public List<PartyRelationship> list( RelationshipType type,Party from ,Party to );

}
