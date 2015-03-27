package com.google.code.lightssh.project.party.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.party.dao.PartyRelationshipDao;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRelationship;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.PartyRelationship.RelationshipType;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("partyRelationshipManager")
public class PartyRelationshipManagerImpl extends BaseManagerImpl<PartyRelationship> 
implements PartyRelationshipManager{
	
	private static final long serialVersionUID = -3739191774353397479L;
	
	//@Resource(name="partyRoleManager")
	//private PartyRoleManager partyRoleManager;
	
	@Resource(name="partyRelationshipDao")
	public void setDao( PartyRelationshipDao dao ){
		super.dao = dao;
	}
	
	public PartyRelationshipDao getDao(){
		return (PartyRelationshipDao)super.dao;
	}

	@Override
	public void removeByFromRole(PartyRole partyRole) {
		getDao().removeByFromRole(partyRole);
	}

	@Override
	public PartyRelationship getRollupByFromParty(Party from) {
		List<PartyRelationship> results = getDao().list(
				RelationshipType.ORG_ROLLUP, from, null);
		return (results==null||results.isEmpty())?null:results.get(0);
	}

	@Override
	public List<PartyRelationship> listRollupByToParty(Party to) {
		return getDao().list( RelationshipType.ORG_ROLLUP, null, to);
	}

	/**
	 * list by RoleType
	 */
	public List<PartyRelationship> list( RelationshipType type ){
		return getDao().list(type);
	}
}
