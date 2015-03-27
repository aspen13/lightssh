
package com.google.code.lightssh.project.log.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.util.constant.AuditResult;

/**
 * 实体审核
 * @author YangXiaojin
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class EntityAudit extends UUIDModel{
	
	private static final long serialVersionUID = -2654044025458228946L;

	/**
	 * 审核人
	 */
	@ManyToOne
	@JoinColumn(name="USER_ID")
	protected LoginAccount user;
	
	/**
	 * 审核结果
	 */
	@Column( name="RESULT",length=50 )
	@Enumerated(EnumType.STRING)
	protected AuditResult result;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	protected String description;

	public LoginAccount getUser() {
		return user;
	}

	public void setUser(LoginAccount user) {
		this.user = user;
	}

	public AuditResult getResult() {
		return result;
	}

	public void setResult(AuditResult result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
