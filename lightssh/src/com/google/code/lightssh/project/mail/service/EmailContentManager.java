package com.google.code.lightssh.project.mail.service;

import java.util.Collection;

import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.mail.entity.EmailContent;

/** 
 * @author YangXiaojin
 * @date 2012-11-13
 * @description：TODO
 */

public interface EmailContentManager extends BaseManager<EmailContent>{
	
	public static final String QUEUE_KEY = "EMAIL_SEND_SERVICE";
	
	/**
	 * 邮件入队列
	 */
	public void send( String addressee, String subject, String content );
	
	/**
	 * 重发邮件
	 */
	public void resend(EmailContent t );
	
	/**
	 * 更新状态
	 */
	public void updateStatus(boolean success,EmailContent ec );
	
	/**
	 * 邮件发送成功
	 */
	public void updateStatus(Collection<Result> results );

}
