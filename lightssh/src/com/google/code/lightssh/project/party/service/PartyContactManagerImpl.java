package com.google.code.lightssh.project.party.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.contact.entity.ContactMechanism;
import com.google.code.lightssh.project.contact.entity.ContactMechanism.ContactMechanismType;
import com.google.code.lightssh.project.party.dao.PartyContactDao;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyContact;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("partyContactManager")
public class PartyContactManagerImpl extends BaseManagerImpl<PartyContact>
implements PartyContactManager{
	
	private static final long serialVersionUID = 1405379451063089511L;

	@Resource( name="partyContactDao")
	public void setDao( PartyContactDao dao ){
		this.dao = dao;
	}
	
	public PartyContactDao getDao(){
		return (PartyContactDao)super.dao;
	}

	@Override
	public Collection<PartyContact> list(Party party,
			ContactMechanismType type) {
		return getDao().list(party, type);
	}

	@Override
	public Collection<PartyContact> list(Party party) {
		return getDao().list(party );
	}

	@Override
	public void remove(Party party,ContactMechanism contact) {
		Collection<PartyContact> results = getDao().list(party, contact);
		if( results != null && !results.isEmpty() )
			remove( results );
	}

	@Override
	public void save(Party party, ContactMechanism contact) {
		if( party != null && party.getIdentity() != null && contact != null ){
			if( contact.isInsert() )
				contact.preInsert();
			super.save( new PartyContact(party,contact) );
		}
	}

	@Override
	public void save(Party party, Collection<ContactMechanism> contacts) {
		if( contacts == null || contacts.isEmpty() )
			return;
		
		for( ContactMechanism contact:contacts )
			this.save(party, contact);
	}

}
