package com.google.code.lightssh.project.security.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.project.log.entity.EntityChange;

/**
 * 角色变更
 * @author YangXiaojin
 *
 */
@Entity
@Table(name="T_SECURITY_ROLE_CHANGE")
public class RoleChange extends EntityChange{

	private static final long serialVersionUID = 4975640723111067418L;
	
	/**
	 * 关联角色
	 */
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
