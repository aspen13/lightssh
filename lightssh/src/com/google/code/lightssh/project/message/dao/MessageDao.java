package com.google.code.lightssh.project.message.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.message.entity.Message;

/**
 * 
 * @author Aspen
 * 
 */
public interface MessageDao extends Dao<Message>{
	
	public void save( Message t );
	
	/**
	 * 带锁的查询
	 */
	public Message readWithLock(String id );
	
	/**
	 * 增加属性
	 */
	public int incProperty(String property,String id );
	
	/**
	 * 删除消息
	 */
	public int remove( String id,String user );
	
}
