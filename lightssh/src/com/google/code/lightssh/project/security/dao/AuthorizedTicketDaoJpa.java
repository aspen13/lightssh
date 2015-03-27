
package com.google.code.lightssh.project.security.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.security.entity.AuthorizedTicket;

/** 
 * @author YangXiaojin
 * @date 2013-3-7
 * @description：TODO
 */
@Repository("authorizedTicketDao")
public class AuthorizedTicketDaoJpa extends JpaDao<AuthorizedTicket> implements AuthorizedTicketDao{

	private static final long serialVersionUID = -1503773513753301076L;
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 */
	@SuppressWarnings("unchecked")
	public AuthorizedTicket get(String ticket,String token,String url,String sessionId){
		String hql = " FROM " + this.entityClass.getName() 
			+ " AS m WHERE m.id = ? AND m.token = ? AND m.url = ? AND m.sessionId = ? ";
		
		Query query = getEntityManager().createQuery(hql).setMaxResults(1);
		query = addQueryParams(query,new Object[]{ticket,token,url,sessionId});
		
		List<AuthorizedTicket> results = query.getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}
	
	/**
	 * 根据Ticket,URL,SessionId查询已授权令牌
	 * 授权令牌可多次授权，并且未失效
	 */
	@SuppressWarnings("unchecked")
	public AuthorizedTicket get( String token,String url,String sessionId,Calendar validTime ){
		String hql = " FROM " + this.entityClass.getName() 
			+ " AS m WHERE m.token = ? AND m.url = ? AND m.sessionId = ? " 
			+ " AND m.scope != ? AND ( m.validTime IS NULL OR m.validTime > ? )";
		
		if( validTime == null )
			validTime = Calendar.getInstance();
		
		Query query = getEntityManager().createQuery(hql).setMaxResults(1);
		query = addQueryParams(query,new Object[]{token,url,sessionId
				,AuthorizedTicket.Scope.ONCE, validTime });
		
		List<AuthorizedTicket> results = query.getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}
	
	/**
	 * 作废令牌
	 */
	public boolean invalidTicket(String ticket,String sessionId,Calendar invalidTime ){
		return this.invalidTicket(ticket, sessionId, invalidTime,false);
	}
	
	/**
	 * 作废令牌
	 */
	public boolean invalidTicket(String ticket,String sessionId,Calendar invalidTime,boolean afterAuth ){
		if( invalidTime == null )
			invalidTime = Calendar.getInstance();
		
		String hql = " UPDATE " + this.entityClass.getName() + " AS m SET m.validTime = ? "
			+ (afterAuth?" , m.authCount = m.authCount + 1 ":"")
			+ " WHERE m.id = ? AND m.sessionId = ? ";
		
		List<Object> params = new ArrayList<Object>( );
		params.add( invalidTime );
		params.add( ticket );
		params.add( sessionId );
		
		Query query = getEntityManager().createQuery(hql);
		this.addQueryParams(query, params);
		return query.executeUpdate() > 0 ;
	}
	
	/**
	 * 增加认证次数
	 */
	public boolean incAuthCount(String ticket,String sessionId){
		String hql = " UPDATE " + this.entityClass.getName() 
			+ " AS m SET m.authCount = m.authCount + 1 "
			+ " WHERE m.id = ? AND m.sessionId = ? ";
		
		List<Object> params = new ArrayList<Object>( );
		params.add( ticket );
		params.add( sessionId );
		
		Query query = getEntityManager().createQuery(hql);
		this.addQueryParams(query, params);
		return query.executeUpdate() > 0 ;
	}

}
