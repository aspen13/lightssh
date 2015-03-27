package com.google.code.lightssh.project.security.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.project.log.entity.EntityAudit;

/**
 * 角色审核
 * @author YangXiaojin
 *
 */
@Entity
@Table(name="T_SECURITY_ROLE_AUDIT")
public class RoleAudit extends EntityAudit{

	private static final long serialVersionUID = -3365668940193726350L;
	
	/**
	 * 角色变更
	 */
	@ManyToOne
	@JoinColumn(name="ROLE_CHANGE_ID")
	private RoleChange roleChange;
	
	public RoleChange getRoleChange() {
		return roleChange;
	}

	public void setRoleChange(RoleChange roleChange) {
		this.roleChange = roleChange;
	}

}
