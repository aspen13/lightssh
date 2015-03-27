
package com.google.code.lightssh.project.security.service;

import java.util.Calendar;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.security.entity.AuthorizedTicket;

/** 
 * @author YangXiaojin
 * @date 2013-3-7
 * @description：TODO
 */

public interface AuthorizedTicketManager extends BaseManager<AuthorizedTicket>{
	
	/**
	 * 授权令牌
	 * @param url 授权URL
	 * @param licensor 授权用户
	 * @param token 授权令牌
	 * @param scope 授权范围
	 * @param sessinid SESSION ID
	 * @param grantor 被授权用户
	 */
	public AuthorizedTicket authTicket(String url,String licensor,String token
			,AuthorizedTicket.Scope scope,String sessinid,String grantor);
	
	/**
	 * 授权令牌
	 * @param url 授权URL
	 * @param licensor 授权用户
	 * @param token 授权令牌
	 * @param scope 授权范围
	 * @param sessinid SESSION ID
	 * @param grantor 被授权用户
	 * @param validTime 失效时间
	 */
	public AuthorizedTicket authTicket(String url,String licensor,String token
			,AuthorizedTicket.Scope scope,String sessinid,String grantor,Calendar validTime);
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 */
	public AuthorizedTicket get(String ticket,String token,String url,String sessionId);
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 * 授权令牌可多次授权，并且未失效
	 */
	public AuthorizedTicket get( String token,String url,String sessionId );
	
	/**
	 * 作废令牌
	 */
	public boolean invalidTicket(String ticket,String sessionId,Calendar invalidTime );
	
	/**
	 * 作废令牌,认证完成后
	 */
	public boolean invalidTicketAfterAuth(String ticket,String sessionId,Calendar invalidTime );
	
	/**
	 * 增加认证次数
	 */
	public boolean incAuthCount(String ticket,String sessionId);

}
