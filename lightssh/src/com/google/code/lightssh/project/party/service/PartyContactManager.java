package com.google.code.lightssh.project.party.service;

import java.util.Collection;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.contact.entity.ContactMechanism;
import com.google.code.lightssh.project.contact.entity.ContactMechanism.ContactMechanismType;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyContact;

/**
 * party contact manager
 * @author YangXiaojin
 *
 */
public interface PartyContactManager extends BaseManager<PartyContact>{
	
	/**
	 * 保存Party联系方式
	 * @param party Party
	 * @param contact 联系方式
	 */
	public void save( Party party,ContactMechanism contact );
	
	/**
	 * 保存Party联系方式
	 * @param party Party
	 * @param contact 联系方式集
	 */
	public void save( Party party,Collection<ContactMechanism> contacts );
	
	/**
	 * 通过ContactMechanism删除PartyContact
	 * @param contact ContactMechanism
	 */
	public void remove( Party party,ContactMechanism contact );
	
	/**
	 * 根据Party和联系方式类型查询联系方式
	 * @param party Party
	 * @param type 联系方式类型
	 */
	public Collection<PartyContact> list( Party party,ContactMechanismType type );
	
	/**
	 * 根据Party查询联系方式
	 * @param party Party
	 */
	public Collection<PartyContact> list( Party party );

}
