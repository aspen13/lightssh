package com.google.code.lightssh.project.party.dao;

import java.util.Collection;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.contact.entity.ContactMechanism;
import com.google.code.lightssh.project.contact.entity.ContactMechanism.ContactMechanismType;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyContact;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface PartyContactDao extends Dao<PartyContact>{
	
	/**
	 * 根据 Party和Contact 查询
	 */
	public Collection<PartyContact> list(Party party,ContactMechanism contact);
	
	/**
	 * 根据Party和联系方式类型查询联系方式
	 * @param party Party
	 * @param type 联系方式类型
	 */
	public Collection<PartyContact> list( Party party,ContactMechanismType...type );

}
