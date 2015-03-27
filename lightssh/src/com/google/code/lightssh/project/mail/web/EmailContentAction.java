package com.google.code.lightssh.project.mail.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.project.mail.entity.EmailContent;
import com.google.code.lightssh.project.mail.service.EmailContentManager;
import com.google.code.lightssh.project.web.action.GenericAction;

/** 
 * @author YangXiaojin
 * @date 2012-11-13
 * @description：TODO
 */
@Component( "emailContentAction" )
@Scope("prototype")
public class EmailContentAction extends GenericAction<EmailContent>{

	private static final long serialVersionUID = -6946751719797106883L;
	
	private EmailContent emailContent;
	
	@Resource(name="emailContentManager")
	public void setManager(EmailContentManager emailContentManager ){
		this.manager = emailContentManager;
	}
	
	public EmailContentManager getManager(){
		return (EmailContentManager)this.manager;
	}

	public EmailContent getEmailContent() {
		this.emailContent = super.model;
		return emailContent;
	}

	public void setEmailContent(EmailContent emailContent) {
		this.model = emailContent;
		this.emailContent = emailContent;
	}
	
	/**
	 * 重发邮件
	 */
	public String resend(){
		if( emailContent == null || emailContent.getId() == null )
			return INPUT;
		
		try{
			getManager().resend( emailContent );
			this.saveSuccessMessage("邮件已重发，发送结果请查看详细！");
		}catch( Exception e ){
			this.saveErrorMessage(e.getMessage());
		}
		
		return SUCCESS;
	}

}
