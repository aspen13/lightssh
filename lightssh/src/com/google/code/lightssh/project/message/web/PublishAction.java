package com.google.code.lightssh.project.message.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.message.entity.Publish;
import com.google.code.lightssh.project.message.service.PublishManager;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * 
 * @author Aspen
 * @date 2013-9-5
 * 
 */
@Component( "publishAction" )
@Scope("prototype")
public class PublishAction extends GenericAction<Publish>{

	private static final long serialVersionUID = -3161126116768024164L;

	private Publish publish;
	
	private Result result;
	
	private Integer count = 0;
	
	@Resource(name="publishManager")
	public void setManager(PublishManager manager){
		this.manager = manager;
	}
	
	public PublishManager getManager( ){
		return (PublishManager)this.manager;
	}

	public Publish getPublish() {
		this.model = publish;
		return publish;
	}

	public void setPublish(Publish publish) {
		this.publish = publish;
		this.model = this.publish;
	}
	
	@JSON(name="result")
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	@JSON(name="count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 读消息
	 */
	public String read( ){
		result = new Result();
		if( publish == null ){
			result.setStatus(false);
			result.setMessage("参数错误！");
			
			return SUCCESS;
		}
		
		try{
			boolean read = getManager().markToRead(publish.getId(),getLoginAccount());
			result.setStatus( read );
		}catch( Exception e ){
			result.setMessage("阅读消息异常："+e.getMessage());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 删除消息
	 */
	public String remove( ){
		result = new Result();
		if( publish == null || StringUtils.isEmpty(publish.getId()) 
				|| publish.getMessage() == null ){
			result.setStatus(false);
			result.setMessage("参数错误！");
			
			return SUCCESS;
		}
		
		try{
			boolean flag = getManager().delete(publish.getId()
					,publish.getMessage().getId(),getLoginAccount());
			result.setStatus( flag );
		}catch( Exception e ){
			result.setMessage("删除消息异常："+e.getMessage());
		}
		
		return SUCCESS;
	}

	/**
	 * 我的消息
	 */
	public String myList( ){
		if(publish == null )
			publish = new Publish();
		
		publish.setUser( this.getLoginAccount() );
		
		if( this.page == null )
			page = new ListPage<Publish>( );
		page.addDescending("readTime");
		page.addDescending("createdTime");
		//page.addAscending("message.createdTime");
		
		try{
			getManager().list(page, publish );
		}catch( Exception e ){
			this.addActionError("查询未读消息异常:"+e.getMessage());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 未读消息数
	 */
	public String myUnreadCount( ){
		if( getLoginAccount() == null ){
			return SUCCESS;
		}
		
		if(publish == null )
			publish = new Publish();
		
		publish.setUser(this.getLoginAccount());
		publish.setRead( false );
		
		if( this.page == null )
			page = new ListPage<Publish>( );
		page.setSize(0);
		
		try{
			page = getManager().list(page, publish );
			this.count = page.getAllSize();
		}catch( Exception e ){
		}
		
		return SUCCESS;
	}
	
	/**
	 * 消息转发
	 */
	public String preforward(){
		if( getLoginAccount() == null )
			return LOGIN;
		
		if( publish == null || StringUtils.isEmpty(publish.getId()) ){
			this.saveErrorMessage("参数为空！");
			return INPUT;
		}
		
		setPublish(getManager().get(publish));
		if( publish == null ){
			saveErrorMessage("转发消息不存在");
			return INPUT;
		}
		
		publish.getMessage().setRecType( null );
		publish.getMessage().setRecValue( null );
		
		if( !getLoginAccount().getId().equals( publish.getUser().getId() ) ){
			saveErrorMessage("只能转发自己的消息！");
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 消息转发
	 */
	public String forward(){
		if( getLoginAccount() == null )
			return LOGIN;
		
		if( publish == null || StringUtils.isEmpty(publish.getId()) 
				|| publish.getMessage() == null ){
			this.saveErrorMessage("参数为空！");
			return INPUT;
		}
		
		try{
			int c = getManager().forward(publish.getMessage().getRecType()
					, publish.getMessage().getRecValue()
					, publish.getMessage().getId());
			this.saveSuccessMessage("成功转发消息至["+c+"]位接收人！");
		}catch( Exception e ){
			this.saveErrorMessage("转发消息异常:"+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
}
