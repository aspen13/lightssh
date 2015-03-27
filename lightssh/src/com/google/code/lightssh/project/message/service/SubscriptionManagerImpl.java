package com.google.code.lightssh.project.message.service;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.message.dao.SubscriptionDao;
import com.google.code.lightssh.project.message.entity.ReceiveType;
import com.google.code.lightssh.project.message.entity.Subscription;

/**
 * 
 * @author Aspen
 * 
 */
@Component("subscriptionManager")
public class SubscriptionManagerImpl extends BaseManagerImpl<Subscription> implements SubscriptionManager{

	private static final long serialVersionUID = 4433581949519273589L;
	
	@Resource(name="subscriptionDao")
	public void setDao(SubscriptionDao dao){
		this.dao = dao;
	}
	
	public SubscriptionDao getDao(){
		return (SubscriptionDao)this.dao;
	}
	
	/**
	 * 通过唯一约束查询消息订阅
	 * @param catalogId 消息类型
	 * @param type 订阅类型
	 * @param subValue 订阅值
	 */
	public Subscription get(String catalogId,ReceiveType type,String subValue ){
		if( StringUtils.isEmpty(catalogId) ||
				type == null || StringUtils.isEmpty(subValue) )
			return null;
		
		ListPage<Subscription> page = new ListPage<Subscription>(1);
		SearchCondition sc = new SearchCondition();
		sc.equal("catalog.id", catalogId )
			.equal("recType", type )
			.equal("recValue", subValue );
		
		page = dao.list(page, sc);
		
		return (page.getList()==null||page.getList().isEmpty())
			?null:page.getList().get(0);
	}
	
	/**
	 * 是否存在
	 */
	public boolean isUnique(Subscription param) {
		if( param == null || param.getCatalog() == null 
				|| StringUtils.isEmpty(param.getCatalog().getId()) 
				|| param.getRecType() == null 
				|| StringUtils.isEmpty(param.getRecValue())  )
			return false;
		
		Subscription exists = get( param.getCatalog().getId()
				,param.getRecType(),param.getRecValue());
	
		return exists == null || exists.getIdentity().equals(param.getIdentity() );
	}
	
	public void save( Subscription t ){
		if(t == null )
			throw new ApplicationException("参数为空！");
		
		if( ReceiveType.ALL.equals(t.getRecType()) )
			t.setRecValue( ReceiveType.ALL.name() );
		
		Subscription db = dao.read(t);
		if( db == null ){
			db = t;
			t.setCreatedTime( Calendar.getInstance() );
		}else{
			db.setCatalog(t.getCatalog());
			db.setPeriod(t.getPeriod());
			db.setRecType(t.getRecType());
			db.setRecValue(t.getRecValue());
			db.setDescription(t.getDescription());
		}
		
		super.save(db);
	}

}
