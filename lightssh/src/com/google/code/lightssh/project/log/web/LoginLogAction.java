package com.google.code.lightssh.project.log.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.web.action.CrudAction;
import com.google.code.lightssh.project.log.entity.LoginLog;
import com.google.code.lightssh.project.log.service.LoginLogManager;

/**
 * LoginLog Action
 * @author YangXiaojin
 *
 */
@Component( "loginLogAction" )
@Scope("prototype")
public class LoginLogAction extends CrudAction<LoginLog>{

	private static final long serialVersionUID = 1L;
	
	private LoginLog loginLog;
	
	@Resource( name="loginLogManager" )
	public void setManager( LoginLogManager loginManager ){
		super.manager = loginManager;
	}

	public LoginLog getLoginLog() {
		this.loginLog = super.model;
		return loginLog;
	}

	public void setLoginLog(LoginLog loginLog) {
		this.loginLog = loginLog;
		super.model = this.loginLog;
	}

}
