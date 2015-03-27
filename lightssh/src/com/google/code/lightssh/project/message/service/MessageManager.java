package com.google.code.lightssh.project.message.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.message.entity.Message;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 
 * @author Aspen
 * 
 */
public interface MessageManager extends BaseManager<Message>{
	
	/**
	 * 带锁的查询
	 */
	public Message getWithLock(String id );
	
	/**
	 * 增加删除数
	 */
	public boolean incDeletedCount( String id );
	
	/**
	 * 删除消息及明细
	 * @param msgId 消息Id
	 * @param account 用户
	 */
	public boolean remove( String msgId,LoginAccount account );
	
	/**
	 * 撤消消息并删除明细
	 * @param msgId 消息Id
	 * @param account 用户
	 */
	public boolean revoke( String msgId,LoginAccount account );

}
