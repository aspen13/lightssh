package com.google.code.lightssh.project.message.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.message.entity.Publish;
import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 
 * @author Aspen
 * 
 */
public interface PublishDao extends Dao<Publish>{
	
	/**
	 * 标记为已读
	 */
	public int markToRead( String id,LoginAccount user );
	
	/**
	 * 删除发布消息
	 */
	public int delete( String id ,String msgId,LoginAccount user);
	
	/**
	 * 删除发布消息
	 */
	public int removeByMessage( String msgId );
	
	/**
	 * 发布消息
	 * @param type 类型
	 * @param value 类型值
	 * @param msgId 信息ID
	 */
	public int publish( ReceiveType type,String value,String msgId );
	
	/**
	 * 转发消息
	 * @param type 类型
	 * @param value 类型值
	 * @param msgId 信息ID
	 */
	public int forward( ReceiveType type,String value,String msgId );

}
