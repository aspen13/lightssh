package com.google.code.lightssh.project.security.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.support.shiro.User;
import com.google.code.lightssh.common.support.shiro.UserService;
import com.google.code.lightssh.project.log.service.LoginLogManager;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 
 * @author Aspen
 * @date 2013-3-28
 * 
 */
@Component("userService")
public class UserManagerImpl implements UserService{
	
	@Resource(name="loginAccountManager")
	private LoginAccountManager loginAccountManager;
	
	@Resource(name="loginLogManager")
	private LoginLogManager loginLogManager;
	
	@Resource(name="loginFailureManager")
	private LoginFailureManager loginFailureManager;
	
	@Resource(name="secondaryTaskExecutor")
	private TaskExecutor taskExecutor;
	
	@Override
	public User getUser(String username) {
		//username.matches( "1\\d{10}" ) //mobile
		
		if ( username.matches( ".*@[^@]*\\.[^@]*" ) ) {
			return loginAccountManager.getByEmail( username );
		} else {
			return loginAccountManager.get(username);
		}
	}

	@Override
	public boolean lock(String userId, Calendar time) {
		if( time == null )
			time = Calendar.getInstance();
		
		Long id = -1L;
		try{
			id = Long.valueOf(userId);
		}catch(Exception e ){
			return false;
		}
		
		return this.loginAccountManager.updateLockTime(id);
	}

	@Override
	public void logLoginSuccess(final User user,final Calendar time,final String ip) {
		Date now = new Date();
		if( time != null )
			now = time.getTime();
		
		final Date date = now;
		// process new thread
		taskExecutor.execute( new Runnable() {
            public void run() {
            	loginLogManager.login(date,ip,user.getUserName() );
            }
		});
	}

	@Override
	public void logLoginFailure(User user, Calendar time, String ip,
			String sessionId) {
		
		//TODO LoginFailure取消与LoginAccount的关联
		if( user instanceof LoginAccount ){
			loginFailureManager.save((LoginAccount)user,user.getUserName(),ip,sessionId);
		}
	}

}
