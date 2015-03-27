
package com.google.code.lightssh.project.security.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.project.log.entity.EntityChange;

/**
 * 登录帐户变更
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SECURITY_LOGINACCOUNT_CHANGE" )
public class LoginAccountChange extends EntityChange{

	private static final long serialVersionUID = -5980453254621734070L;
	
	/**
	 * 关联登录帐户
	 */
	@ManyToOne
	@JoinColumn(name="LOGINACCOUNT_ID")
	private LoginAccount loginAccount;

	public LoginAccount getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(LoginAccount loginAccount) {
		this.loginAccount = loginAccount;
	}

}
