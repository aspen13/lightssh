package com.google.code.lightssh.project.message.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.project.message.entity.Message;
import com.google.code.lightssh.project.message.service.MessageManager;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * 
 * @author Aspen
 * 
 */
@Component( "messageAction" )
@Scope("prototype")
public class MessageAction extends GenericAction<Message>{

	private static final long serialVersionUID = 3443312151707430333L;
	
	private Message message;
	
	
	@Resource(name="messageManager")
	public void setManager(MessageManager mgr){
		this.manager = mgr;
	}
	
	public MessageManager getManager(){
		return (MessageManager)this.manager;
	}

	public Message getMessage() {
		this.message = this.model;
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
		this.model = this.message;
	}
	
	public String save( ){
		if( message == null )
			message = new Message();
		
		message.setCreator( this.getLoginUser() );
		
		return super.save();
	}
	
	/**
	 * 发件箱
	 */
	public String outbox(){
		if( message == null )
			message = new Message();
		
		message.setCreator( getLoginUser() );
		try{
			page = getManager().list(page, message);
		}catch(Exception e ){
			this.saveErrorMessage("查询消息发件箱异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 管理员删除消息
	 */
	public String remove(){
		if( message == null || StringUtils.isEmpty(message.getId()) ){
			this.saveErrorMessage("参数错误!");
			return INPUT;
		}
		
		try{
			getManager().remove(message.getId(),null);
			this.saveSuccessMessage("成功删除消息！");
		}catch(Exception e ){
			this.saveErrorMessage("删除消息异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 管理员删除消息
	 */
	public String myRemove(){
		LoginAccount user = getLoginAccount();
		if( user == null )
			return LOGIN;
		
		if( message == null || StringUtils.isEmpty(message.getId()) ){
			this.saveErrorMessage("参数错误!");
			return INPUT;
		}
		
		try{
			getManager().remove(message.getId(), user );
			this.saveSuccessMessage("成功删除消息！");
		}catch(Exception e ){
			this.saveErrorMessage("删除消息异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
}
