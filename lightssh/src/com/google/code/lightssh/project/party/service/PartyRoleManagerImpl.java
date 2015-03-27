package com.google.code.lightssh.project.party.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.party.dao.PartyRoleDao;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.PartyRole.RoleType;

/**
 * @author YangXiaojin
 *
 */
@Component("partyRoleManager")
public class PartyRoleManagerImpl extends BaseManagerImpl<PartyRole> implements PartyRoleManager{

	private static final long serialVersionUID = -412450282593132209L;

	@Resource(name="partyRoleDao")
	public void setDao( PartyRoleDao dao ){
		super.dao = dao;
	}
	
	public PartyRoleDao getDao(){
		return (PartyRoleDao)super.dao;
	}
	
	@Override
	public List<PartyRole> list(RoleType type) {
		return getDao().list(type);
	}

	@Override
	public void save(Party party, Collection<RoleType> types) {
		if( party == null || party.getIdentity() == null 
				|| types == null || types.isEmpty())
			throw new IllegalArgumentException(
					"保存PartyRole参数错误，Party和RoleType不能为空。");
		
		List<PartyRole> entities = new ArrayList<PartyRole>( types.size() );
		for( RoleType type:types ){
			entities.add( new PartyRole(party,type));
		}
		
		this.save( entities );
	}

	@Override
	public List<Party> listParty(RoleType type) {
		return getDao().listParty(type);
	}

	@Override
	public List<PartyRole> list(Party party) {
		return getDao().list(party);
	}

	@Override
	public void remove(Party party) {
		 getDao().remove(party);
	}

	@Override
	public List<PartyRole> list(Party party, RoleType[] inTypes) {
		return getDao().list(party, inTypes);
	}

}
