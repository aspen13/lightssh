package com.google.code.lightssh.project.message.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.message.entity.Message;
import com.google.code.lightssh.project.message.entity.Publish;

/**
 * 
 * @author Aspen
 * 
 */
@Repository("messageDao")
public class MessageDaoJpa extends JpaDao<Message> implements MessageDao{

	private static final long serialVersionUID = 8245137954310955563L;
	
	@Resource(name="publishDao")
	private PublishDao publishDao;
	
	public void save( Message t ){
		if( t == null )
			throw new ApplicationException("参数为空！");
		
		if( t.isInsert() )
			this.create(t);
		else
			this.update(t);
		this.getEntityManager().flush();
		
		//发布消息
		if( Message.Status.PUBLISH.equals(t.getStatus()) ){
			t.setPublishedCount(publishDao.publish(
				t.getRecType(),t.getRecValue(),t.getId()));
		}else{
			t.setPublishedCount(0);
		}
		
		update(t);
	}
	
	public Message readWithLock(String id ){
		String sql = " SELECT * FROM T_MSG_MESSAGE WHERE ID = ? FOR UPDATE ";
		
		return (Message) addQueryParams( getEntityManager().createNativeQuery(
				sql,Message.class),new Object[]{id}).getSingleResult();
 	}
	
	/**
	 * 增加属性
	 */
	public int incProperty(String property,String id ){
		String hql = " UPDATE " + this.entityClass.getName() + " SET " 
				+ property + " = " + property +"+ 1 WHERE id = ? "  ;
		
		return addQueryParams( getEntityManager().createQuery(hql)
				,new Object[]{id}).executeUpdate();
	}
	
	
	public ListPage<Message> list( ListPage<Message> page, Message t ){
		StringBuffer sql = new StringBuffer(" FROM "+this.entityClass.getName() + " AS m WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if( t != null ){
			if( StringUtils.isNotEmpty(t.getTitle())){
				sql.append(" AND m.title like ? ");
				params.add("%"+t.getTitle().trim()+"%");
			}
			
			if( StringUtils.isNotEmpty(t.getContent())){
				sql.append(" AND m.content like ? ");
				params.add("%"+t.getContent().trim()+"%");
			}
			
			if( StringUtils.isNotEmpty(t.getCreator())){
				sql.append(" AND m.creator = ? ");
				params.add( t.getCreator().trim() );
			}
			
			if( t.getReader() != null && t.getReader().getId() != null ){
				sql.append(" AND m.id IN ( ");
				sql.append(" SELECT message.id FROM " + Publish.class.getName() );
				sql.append(" WHERE user.id = ? ) " );
				
				params.add( t.reader.getId() );
			}
			
			if( t.getCatalog() != null ){
				if( StringUtils.isNotEmpty(t.getCatalog().getId()) ){
					sql.append(" AND m.catalog.id = ? ");
					params.add( t.getCatalog().getId().trim() );
				}
			}
		}
		
		return super.query(page,sql.toString(), params.toArray() );
	}
	
	/**
	 * 删除消息
	 */
	public int remove( String id,String user ){
		if( StringUtils.isEmpty(id) )
			return 0;
		
		String hql = " DELETE FROM " + this.entityClass.getName() + " WHERE id = ? ";
		
		List<Object> params = new ArrayList<Object>();
		params.add( id );
		
		if( StringUtils.isNotEmpty( user ) ){
			hql += " AND creator = ? ";
			params.add(user);
		}
		
		return addQueryParams( getEntityManager().createQuery(hql)
				,params).executeUpdate();
	}
	
}
