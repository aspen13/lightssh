package com.google.code.lightssh.project.message.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.message.dao.PublishDao;
import com.google.code.lightssh.project.message.entity.Message;
import com.google.code.lightssh.project.message.entity.Publish;
import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 
 * @author Aspen
 * @date 2013-9-5
 * 
 */
@Component("publishManager")
public class PublishManagerImpl extends BaseManagerImpl<Publish> implements PublishManager{

	private static final long serialVersionUID = -4659284392763441005L;
	
	private static Logger log = LoggerFactory.getLogger(PublishManagerImpl.class);
	
	@Resource(name="messageManager")
	private MessageManager messageManager;
	
	@Resource(name="publishDao")
	public void setDao(PublishDao dao){
		this.dao = dao;
	}
	
	public PublishDao getDao(){
		return (PublishDao)this.dao;
	}
	
	/**
	 * 标记为已读
	 */
	public boolean markToRead( String id ,LoginAccount user){
		if( StringUtils.isEmpty(id) || user == null )
			return false;
		
		return getDao().markToRead(id, user) > 0;
	}
	
	/**
	 * 删除发布消息
	 */
	public boolean delete( String id ,String msgId,LoginAccount user){
		if( StringUtils.isEmpty(id) || user == null )
			return false;
		
		boolean result = getDao().delete(id,msgId, user) > 0;
		if( result ){
			if( messageManager.incDeletedCount(msgId) )
				log.debug("消息[{}]删除数加1!",msgId);
		}
		
		return result;
	}
	
	/**
	 * 删除发布消息
	 */
	public int deleteByMessage( String msgId ){
		return getDao().removeByMessage(msgId);
	}
	
	/**
	 * 发布消息
	 * @param type 类型
	 * @param value 类型值
	 * @param msgId 信息ID
	 */
	public int publish( ReceiveType type,String value,String msgId ){
		return getDao().publish(type, value, msgId);
	}
	
	public ListPage<Publish> list(ListPage<Publish> page ,Publish t){
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( t.getUser() != null && t.getUser().getId() != null ){
				sc.equal("user.id", t.getUser().getId() );
			}
			
			if( t.getPeriod() != null ){
				Calendar cal = Calendar.getInstance();
				Date start = t.getPeriod().getStart();
				Date end = t.getPeriod().getEnd();
				
				if( start != null ){
					cal.setTime(start);
					sc.greateThanOrEqual("createdTime",cal);
				}
				
				if( end != null ){
					Calendar cal_end = Calendar.getInstance();
					cal_end.setTime(end);
					cal_end.add(Calendar.DAY_OF_MONTH, 1);
					cal_end.add(Calendar.SECOND, -1);
					sc.lessThanOrEqual("createdTime",cal_end);
				}
			}
			
			if( t.getRead() != null ){
				if( Boolean.TRUE.equals( t.getRead() ) )
					sc.isNotNull("readTime");
				else
					sc.isNull("readTime");
			}
			
			if( t.getMessage() != null ){
				Message msg = t.getMessage();
				
				if( StringUtils.isNotEmpty( msg.getTitle() ) ){
					sc.like("message.title", msg.getTitle().trim() );
				}
				
				if( StringUtils.isNotEmpty( msg.getCreator() ) ){
					sc.equal("message.creator", msg.getCreator().trim() );
				}
			}
		}
		
		return dao.list(page, sc);
	}
	
	/**
	 * 转发消息
	 * @param type 类型
	 * @param value 类型值
	 * @param msgId 信息ID
	 */
	public int forward( ReceiveType type,String value,String msgId ){
		return getDao().forward(type, value, msgId);
	}

}
