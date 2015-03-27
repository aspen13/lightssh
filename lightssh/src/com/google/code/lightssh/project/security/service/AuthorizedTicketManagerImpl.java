
package com.google.code.lightssh.project.security.service;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.security.dao.AuthorizedTicketDao;
import com.google.code.lightssh.project.security.entity.AuthorizedTicket;

/** 
 * @author YangXiaojin
 * @date 2013-3-7
 * @description：TODO
 */
@Component("authorizedTicketManager")
public class AuthorizedTicketManagerImpl extends BaseManagerImpl<AuthorizedTicket> implements AuthorizedTicketManager{

	private static final long serialVersionUID = 6559777614885449522L;
	
	@Resource(name="authorizedTicketDao")
	public void setDao(AuthorizedTicketDao dao){
		this.dao = dao;
	}
	
	public AuthorizedTicketDao getDao(){
		return (AuthorizedTicketDao)this.dao;
	}
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 */
	public AuthorizedTicket get(String ticket,String token,String url,String sessionId){
		return getDao().get(ticket,token, url, sessionId);
	}
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 * 授权令牌可多次授权，并且未失效
	 */
	public AuthorizedTicket get( String token,String url,String sessionId ){
		return getDao().get(token, url, sessionId,Calendar.getInstance() );
	}
	
	/**
	 * 作废令牌
	 */
	public boolean invalidTicket(String ticket,String sessionId,Calendar invalidTime ){
		return getDao().invalidTicket(ticket, sessionId, invalidTime);
	}
	
	/**
	 * 作废令牌,认证完成后
	 */
	public boolean invalidTicketAfterAuth(String ticket,String sessionId,Calendar invalidTime ){
		return getDao().invalidTicket(ticket, sessionId, invalidTime,true);
	}
	
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
			,AuthorizedTicket.Scope scope,String sessinid,String grantor){
		return this.authTicket(url, licensor, token, scope, sessinid, grantor,null);
	}
	
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
			,AuthorizedTicket.Scope scope,String sessinid
			,String grantor,Calendar validTime){
		return this.authTicket(url, licensor, token, scope, sessinid, grantor,null, validTime);
	}
	
	/**
	 * 授权令牌
	 * @param url 授权URL
	 * @param licensor 授权用户
	 * @param token 授权令牌
	 * @param scope 授权范围
	 * @param sessinid SESSION ID
	 * @param grantor 被授权用户
	 * @param maxCount 最大执行次数
	 * @param validTime 失效时间
	 */
	public AuthorizedTicket authTicket(String url,String licensor,String token
			,AuthorizedTicket.Scope scope,String sessinid
			,String grantor,Long maxCount,Calendar validTime){
		if( StringUtils.isEmpty(url) )
			throw new ApplicationException("授权URL为空！");
		
		if( StringUtils.isEmpty(licensor) || StringUtils.isEmpty(token)
				|| StringUtils.isEmpty(grantor) || StringUtils.isEmpty(sessinid) )
			throw new ApplicationException("授权参数错误！");
		
		if( scope == null )
			scope = AuthorizedTicket.Scope.ONCE;
		
		AuthorizedTicket ticket = new AuthorizedTicket();
		ticket.setUrl(url);
		ticket.setLicensor(licensor);
		ticket.setToken(token);
		ticket.setScope(scope);
		ticket.setSessionId(sessinid);
		ticket.setGrantor(grantor);
		ticket.setMaxCount(maxCount);
		ticket.setAuthCount(0L);
		ticket.setValidTime(validTime);
		
		if( AuthorizedTicket.Scope.ONCE.equals(scope) )
			ticket.setMaxCount(1L);
		
		this.save(ticket);
		
		return ticket;
	}
	
	/**
	 * 增加认证次数
	 */
	public boolean incAuthCount(String ticket,String sessionId){
		return getDao().incAuthCount(ticket, sessionId);
	}

}
