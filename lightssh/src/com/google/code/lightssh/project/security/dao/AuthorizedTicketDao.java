package com.google.code.lightssh.project.security.dao;

import java.util.Calendar;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.security.entity.AuthorizedTicket;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface AuthorizedTicketDao extends Dao<AuthorizedTicket>{
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 */
	public AuthorizedTicket get(String ticket,String token,String url,String sessionId);
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 * 授权令牌可多次授权，并且未失效
	 */
	public AuthorizedTicket get( String token,String url,String sessionId,Calendar validTime );
	
	/**
	 * 作废令牌
	 */
	public boolean invalidTicket(String ticket,String sessionId,Calendar invalidTime );
	
	/**
	 * 作废令牌
	 */
	public boolean invalidTicket(String ticket,String sessionId,Calendar invalidTime,boolean afterAuth );
	
	/**
	 * 增加认证次数
	 */
	public boolean incAuthCount(String ticket,String sessionId);

}
