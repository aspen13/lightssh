package com.google.code.lightssh.project.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.project.party.entity.Party;

/**
 * 所属组织或人员所拥有的角色
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SECURITY_PARTY_ROLE")
public class SecurityPartyRole extends UUIDModel{
	
	private static final long serialVersionUID = 4438201183790188990L;

	/**
	 * 所属组织或人员
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="PARTY_ID")
	private Party party;
	
	/**
	 * 所属组织或人员
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="ROLE_ID")
	private Role role;
	
	/**
	 *描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	private String description;

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
