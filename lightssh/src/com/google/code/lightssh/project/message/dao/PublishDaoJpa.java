package com.google.code.lightssh.project.message.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.message.entity.Publish;
import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.util.constant.AuditStatus;

/**
 * 
 * @author Aspen
 * @date 2013-9-5
 * 
 */
@Repository("publishDao")
public class PublishDaoJpa extends JpaDao<Publish> implements PublishDao{

	private static final long serialVersionUID = -2660249120883257504L;
	
	/**
	 * 标记为已读
	 */
	public int markToRead( String id ,LoginAccount user){
		if( StringUtils.isEmpty(id) || user == null || user.getId() == null )
			return 0;
		
		String hql = " UPDATE " + this.entityClass.getName() 
				+ " SET readTime = ? WHERE id = ? AND user.id = ? ";
		
		return addQueryParams( getEntityManager().createQuery(hql)
				,new Object[]{Calendar.getInstance(),id,user.getId()}).executeUpdate();
	}
	
	/**
	 * 删除发布消息
	 */
	public int delete( String id ,String msgId,LoginAccount user){
		if( StringUtils.isEmpty(id) || user == null || user.getId() == null )
			return 0;
		
		String hql = " DELETE FROM " + this.entityClass.getName() 
				+ " WHERE id = ? AND message.id = ? AND user.id = ? ";
		
		return addQueryParams( getEntityManager().createQuery(hql)
				,new Object[]{id,msgId,user.getId()}).executeUpdate();
	}
	
	/**
	 * 删除发布消息
	 */
	public int removeByMessage( String msgId ){
		if( StringUtils.isEmpty(msgId) )
			return 0;
		
		String hql = " DELETE FROM " + this.entityClass.getName() 
				+ " WHERE message.id = ? ";
		
		return addQueryParams( getEntityManager().createQuery(hql)
				,msgId).executeUpdate();
	}
	
	/**
	 * 发布消息
	 * @param type 类型
	 * @param value 类型值
	 * @param msgId 信息ID
	 */
	public int publish( ReceiveType type,String value,String msgId ){
		StringBuffer sql = new StringBuffer(" INSERT INTO T_MSG_PUBLISH(ID,MESSAGE_ID,USER_ID,CREATED_TIME) ");
		
		List<Object> params = new ArrayList<Object>( );
		if( ReceiveType.USER.equals(type) || ReceiveType.ALL.equals(type)
				|| ReceiveType.DEPARTMENT.equals( type )){
			sql.append(" SELECT sys_guid(),'");
			sql.append( msgId );
			sql.append( "',id,sysdate FROM T_SECURITY_LOGINACCOUNT WHERE status = ? ");
			
			params.add( AuditStatus.EFFECTIVE.name() );
			if( ReceiveType.USER.equals(type) ){
				//sql.append(" AND id = ? ");
				//params.add( value ); 
				
				//多个用户
				String[] ids = value.split(",");
				if( ids == null || ids.length == 0 ){
					sql.append(" AND 1 = 2 "); //容错
				}else{
					for(int i=0;i<ids.length;i++ ){
						if( i==0 )
							sql.append(" AND id IN ( " + ids[i] );
						else
							sql.append(" , " + ids[i] );
						
						if( i==ids.length-1 )
							sql.append(" ) ");
					}//end for
				}
			}else if( ReceiveType.DEPARTMENT.equals( type ) ){//部门
				sql.append(" AND party_id IN ( SELECT PERSON_ID FROM T_PARTY_EMPLOYEE WHERE org_id = ? ) ");
				params.add( value );
			}
		}
		
		return addQueryParams(getEntityManager().createNativeQuery(
				sql.toString()),params).executeUpdate();
	}
	
	/**
	 * 转发消息
	 * @param type 类型
	 * @param value 类型值
	 * @param msgId 信息ID
	 */
	public int forward( ReceiveType type,String value,String msgId ){
		StringBuffer sql = new StringBuffer(" MERGE INTO T_MSG_PUBLISH p ");
		sql.append(" USING( ");
		
		List<Object> params = new ArrayList<Object>( );
		if( ReceiveType.USER.equals(type) || ReceiveType.ALL.equals(type)
			|| ReceiveType.DEPARTMENT.equals( type )){
			sql.append(" SELECT sys_guid(),'");
			sql.append( msgId );
			sql.append( "',id,sysdate FROM T_SECURITY_LOGINACCOUNT WHERE status = ? ");
			
			params.add( AuditStatus.EFFECTIVE.name() );
			if( ReceiveType.USER.equals(type) ){
				sql.append(" AND id = ? ");
				params.add( value );
			}else if( ReceiveType.DEPARTMENT.equals( type ) ){//部门
				sql.append(" AND party_id IN ( SELECT PERSON_ID FROM T_PARTY_EMPLOYEE WHERE org_id = ? ) ");
				params.add( value );
			}
		}
		
		sql.append( " ) u ON (u.id = p.user_id AND p.message_id = ? ) " );
		params.add( msgId );
		sql.append( " WHEN NOT MATCHED THEN " );
		sql.append( " INSERT (p.id,p.message_id,p.user_id,p.created_time) " ); 
		sql.append( " 	VALUES(sys_guid(),'"+msgId+"',u.id,sysdate ) " ); 
		
		return addQueryParams(getEntityManager().createNativeQuery(
				sql.toString()),params).executeUpdate();
	}
	
}
