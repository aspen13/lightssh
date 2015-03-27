package com.google.code.lightssh.project.party.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.common.model.Period;
import com.google.code.lightssh.project.contact.entity.ContactMechanism;

/**
 * Party 联系方式
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_PARTY_CONTACT")
public class PartyContact extends UUIDModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Party
	 */
	@OneToOne
	@JoinColumn( name="PARTY_ID" )
	private Party party;
	
	/**
	 * 联系机制
	 */
	@OneToOne( cascade=CascadeType.ALL )
	@JoinColumn( name="CONTACT_ID")
	private ContactMechanism contact;
	
	/**
	 * 时间范围
	 */
	@Embedded
	private Period period;
	
	/**
	 * 显示顺序
	 */
	@Column( name="SEQUENCE" )
	private Integer sequence;
	
	public PartyContact( ){}
	
	public PartyContact(Party party,ContactMechanism contact ){
		this.party = party;
		this.contact = contact;
		this.period = new Period(new Date(),null); 
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public ContactMechanism getContact() {
		return contact;
	}

	public void setContact(ContactMechanism contact) {
		this.contact = contact;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
