package com.google.code.lightssh.project.mail.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.config.SystemConfig;
import com.google.code.lightssh.common.mail.Address;
import com.google.code.lightssh.common.mail.MailAddress;
import com.google.code.lightssh.common.mail.MailContent;
import com.google.code.lightssh.common.mail.sender.MailSender;
import com.google.code.lightssh.common.model.ConnectionConfig;
import com.google.code.lightssh.common.util.TextFormater;
import com.google.code.lightssh.project.mail.MailConfigConstants;
import com.google.code.lightssh.project.mail.entity.EmailContent;
import com.google.code.lightssh.project.param.service.SystemParamManager;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/** 
 * @author YangXiaojin
 * @date 2013-1-17
 * @description：TODO
 */
@Component("emailSendManager")
public class EmailSendManagerImpl implements EmailSendManager{

	private static final long serialVersionUID = 5592731164104327648L;
	
	@Resource(name="mailSender")
	private MailSender mailSender;
	
	/** 
	 * 系统参数 
	 */
	@Resource(name="systemConfig")
	private SystemConfig systemConfig;
	
	@Resource(name="systemParamManager")
	private SystemParamManager systemParamManager;
	
	@Resource(name="emailContentManager")
	private EmailContentManager emailContentManager;
	
	protected ConnectionConfig getEmailConnectionConfig( ){
		ConnectionConfig config = null;
		
		if( systemParamManager != null ){
			config = systemParamManager.getEmailConnectionConfig();
		}
		
		if( systemConfig == null )
			return config;
		
		if( config == null ){
			config = new ConnectionConfig();
			
			config.setHost( this.systemConfig.getProperty( 
					MailConfigConstants.EMAIL_HOST_KEY ) );
			
			config.setPort( this.systemConfig.getProperty( 
					MailConfigConstants.EMAIL_PORT_KEY ) );
			
			config.setUsername( this.systemConfig.getProperty( 
					MailConfigConstants.EMAIL_USERNAME_KEY ) );
			
			config.setPassword( this.systemConfig.getProperty( 
					MailConfigConstants.EMAIL_PASSWORD_KEY ) );
			
			config.setSsl( "true".equalsIgnoreCase(systemConfig.getProperty( 
					MailConfigConstants.EMAIL_SSL_KEY ) ));
		}
		
		return config;
	}

	
	/**
	 * 发邮件
	 */
	public void send(EmailContent ec ){
		ConnectionConfig config = getEmailConnectionConfig();
		
		MailAddress mailAddress = new MailAddress( );
		mailAddress.setFrom(config.getUsername(),config.getUsername());
		
		for( String item:ec.getAddressees())
			mailAddress.addTo(item, "");
		
		if( !StringUtils.isEmpty(ec.getCc()) ){
			for( String item:ec.getCcs())
				mailAddress.addCc(item, "");
		}
		
		boolean success = false;
		try{
			mailSender.sendHtml(config,mailAddress
					,new MailContent(ec.getSubject(),ec.getContent()));
			success = true;
		}catch( Exception e ){
			ec.setErrMsg( TextFormater.format(e.getMessage(),197,true) );
		}
		
		ec.setSender( config.getUsername() );
		
		emailContentManager.updateStatus(success,ec);
	}
	
	/**
	 * 忘记用户名
	 */
	public void forgotUsername(String title, String email ){
		ConnectionConfig config = getEmailConnectionConfig();
		Address to = new Address(email,title);
		Address from = new Address(config.getUsername(),config.getUsername());
		MailAddress mailAddress = new MailAddress( to,from );
		
		StringBuffer content = new StringBuffer();
		content.append("您在系统的登录帐号为：" + title);
		
		mailSender.send(config,mailAddress
				,new MailContent("用户帮助",content.toString()));
	}
	
	/**
	 * 忘记密码
	 */
	public void forgotPassword(String retrieveUrl, LoginAccount account ){
		if( account == null || StringUtils.isEmpty(account.getEmail()) )
			return ;
		
		ConnectionConfig config = getEmailConnectionConfig();
		Address to = new Address(account.getEmail(),account.getLoginName());
		Address from = new Address(config.getUsername(),config.getUsername());
		MailAddress mailAddress = new MailAddress( to,from );
		
		StringBuffer content = new StringBuffer();
		content.append("<html>");
		//content.append("<img src=\"http://www.gstatic.com/codesite/ph/images/defaultlogo.png\"></br>");
		content.append("\n要重设您系统帐户 "+account.getLoginName()+" 的密码，请点击以下链接：" );
		content.append( "<a href=\""+retrieveUrl+"\">点击重设密码</a>" );
		content.append("\n如果点击以上链接没有反应，请将该网址复制并粘贴到新的浏览器窗口中。" );
		content.append("\n"+retrieveUrl);
		content.append("</html>");
		
		mailSender.sendHtml(config,mailAddress
				,new MailContent("用户帮助-找回登录密码",content.toString()));

	}

}
