package com.google.code.lightssh.project.message.service;

import java.util.Calendar;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.message.dao.MessageDao;
import com.google.code.lightssh.project.message.entity.Catalog;
import com.google.code.lightssh.project.message.entity.Message;
import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 
 * @author Aspen
 * 
 */
@Component("messageManager")
public class MessageManagerImpl extends BaseManagerImpl<Message> implements MessageManager{

	private static final long serialVersionUID = -4158212432035375331L;
	
	private static Logger log = LoggerFactory.getLogger(MessageManagerImpl.class);
	
	@Resource(name="catalogManager")
	private CatalogManager catalogManager;
	
	@Resource(name="publishManager")
	private PublishManager publishManager;
	
	/**
	 * 带锁的查询
	 */
	public Message getWithLock(String id ){
		return getDao().readWithLock(id);
	}
	
	/**
	 * 增加删除数
	 */
	public boolean incDeletedCount( String id ){
		getWithLock( id );
		return getDao().incProperty("deletedCount", id) > 0;
	}
	
	@Resource(name="messageDao")
	public void setDao(MessageDao dao){
		this.dao = dao;
	}

	public MessageDao getDao(){
		return (MessageDao)this.dao;
	}
	
	public void save( Message t ){
		if( t == null )
			throw new ApplicationException("参数为空！");
		
		Catalog catalog = catalogManager.getDefaultInfo();
		if( catalog == null )
			throw new ApplicationException("默认信息分类未初始化！");
		
		if(  ReceiveType.ALL.equals(t.getRecType()) ){
			t.setRecValue( ReceiveType.ALL.name() );
			t.setForward( false ); //所有人接收的消息不能转发
		}else{
			t.setForward( catalog.getForward() );
		}
		
		if( t.getStatus() == null )
			t.setStatus(Message.Status.PUBLISH );
		t.setCatalog( catalog );
		t.setCreatedTime( Calendar.getInstance() );
		t.setLinkable(false);
		t.setPublishedCount(0);//TODO
		t.setDeletedCount(0);
		t.setHitCount(0);
		t.setReaderCount(0);
		t.setTodoClean(false);
		
		getDao().save(t);
	}
	
	protected boolean remove(boolean flag, String msgId,LoginAccount account ){
		
		//删除发布的消息
		int pCount = publishManager.deleteByMessage(msgId);
		
		int count = 0;
		if( flag ){
			count = getDao().remove(msgId,
					account==null?null:account.getLoginName() );
		}else{
			count = getDao().update("id",msgId,"status"
					,Message.Status.PUBLISH,Message.Status.DRAFT);
		}
		
		if( count <= 0 )
			throw new ApplicationException("消息更新或删除不成功！");
		
		log.debug("消息[{}][{}],删除发布条数[{}]!",new Object[]{
				flag?"删除":"撤消",count>0?"成功":"失败",pCount});
		
		return count > 0;
	}
	
	/**
	 * 删除消息及明细
	 * @param msgId 消息Id
	 * @param account 用户
	 */
	public boolean remove( String msgId,LoginAccount account ){
		return remove( true,msgId,account );
	}
	
	/**
	 * 撤消消息并删除明细
	 * @param msgId 消息Id
	 * @param account 用户
	 */
	public boolean revoke( String msgId,LoginAccount account ){
		return remove( false,msgId,account );
	}
	
}
