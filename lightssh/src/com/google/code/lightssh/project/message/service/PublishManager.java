package com.google.code.lightssh.project.message.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.message.entity.Publish;
import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 
 * @author Aspen
 * @date 2013-9-5
 * 
 */
public interface PublishManager extends BaseManager<Publish>{
	
	/**
	 * 标记为已读
	 */
	public boolean markToRead( String id ,LoginAccount user);
	
	/**
	 * 删除发布消息
	 */
	public boolean delete( String id ,String msgId,LoginAccount user);
	
	/**
	 * 删除发布消息
	 */
	public int deleteByMessage( String msgId );
	
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
