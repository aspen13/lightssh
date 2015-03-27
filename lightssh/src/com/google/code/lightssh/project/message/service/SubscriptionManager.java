package com.google.code.lightssh.project.message.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.message.entity.Subscription;

/**
 * 
 * @author Aspen
 * 
 */
public interface SubscriptionManager extends BaseManager<Subscription>{
	
	/**
	 * 通过唯一约束查询消息订阅
	 * @param catalogId 消息类型
	 * @param type 订阅类型
	 * @param subValue 订阅值
	 */
	public Subscription get(String catalogId,ReceiveType type,String subValue );

	/**
	 * 是否存在
	 */
	public boolean isUnique(Subscription param);
	
}
