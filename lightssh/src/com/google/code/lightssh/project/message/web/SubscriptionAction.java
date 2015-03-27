package com.google.code.lightssh.project.message.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.message.entity.Subscription;
import com.google.code.lightssh.project.message.service.SubscriptionManager;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * 
 * @author Aspen
 * @date 2013-9-3
 * 
 */
@Component( "subscriptionAction" )
@Scope("prototype")
public class SubscriptionAction extends GenericAction<Subscription>{

	private static final long serialVersionUID = 2395680064090502873L;
	
	private Subscription subscription;
	
	@Resource( name="subscriptionManager" )
	public void setManager(SubscriptionManager subscriptionManager) {
		super.manager = subscriptionManager;
	}
	
	public SubscriptionManager getManager(){
		return (SubscriptionManager)this.manager;
	}

	public Subscription getSubscription() {
		this.subscription = this.model;
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
		super.model = this.subscription;
	}
	
	@JSON(name="unique")
	public boolean isUnique() {
		return unique;
	}
	
	public String unique( ){
		if( subscription != null && ReceiveType.ALL.equals(subscription.getRecType()) )
			subscription.setRecValue( ReceiveType.ALL.name() );
		
		this.unique = this.getManager().isUnique( subscription );
		return SUCCESS;
	}
	
	public String edit( ){
		if( subscription != null && subscription.getRecType() != null 
				&& StringUtils.isNotEmpty( subscription.getRecValue() )
				&& subscription.getCatalog() != null 
				&& StringUtils.isNotEmpty( subscription.getCatalog().getId() )){
			
			setSubscription( getManager().get(subscription.getCatalog().getId()
					,subscription.getRecType(),subscription.getRecValue()));
			return SUCCESS;
		}
		
		return super.edit();
	}
	
	public String save(){
		if( this.subscription == null ){
			this.saveErrorMessage("参数为空！");
			return INPUT;
		}
		
		subscription.setCreator( this.getLoginUser() );
		//subscription.setCreatedTime( Calendar.getInstance() );
		
		try{
			this.getManager().save(subscription);
		}catch( Exception e ){
			this.addActionError("保存消息订阅异常："+e.getMessage());
			return INPUT;
		}
		
		return super.save();
	}

}
