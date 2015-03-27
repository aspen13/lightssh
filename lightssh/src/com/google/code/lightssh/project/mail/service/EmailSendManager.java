package com.google.code.lightssh.project.mail.service;

import com.google.code.lightssh.common.service.Manager;
import com.google.code.lightssh.project.mail.entity.EmailContent;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/** 
 * @author YangXiaojin
 * @date 2013-1-17
 * @description：TODO
 */

public interface EmailSendManager extends Manager{
	
	/**
	 * 发邮件
	 */
	public void send(EmailContent ec );
	
	/**
	 * 忘记用户名
	 */
	public void forgotUsername(String title, String email );
	
	/**
	 * 忘记密码
	 */
	public void forgotPassword(String retrieveUrl, LoginAccount account );

}
