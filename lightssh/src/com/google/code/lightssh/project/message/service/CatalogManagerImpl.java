package com.google.code.lightssh.project.message.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.message.dao.CatalogDao;
import com.google.code.lightssh.project.message.entity.Catalog;

/**
 * 
 * @author Aspen
 * 
 */
@Component("catalogManager")
public class CatalogManagerImpl extends BaseManagerImpl<Catalog> implements CatalogManager{

	private static final long serialVersionUID = -3845245077602418484L;
	
	@Resource(name="catalogDao")
	public void setDao(CatalogDao dao){
		this.dao = dao;
	}

	public CatalogDao getDao( ){
		return (CatalogDao)this.dao;
	}
	
	/**
	 * 默认信息类消息
	 */
	public Catalog getDefaultInfo( ){
		return get( Catalog.DEFAULT_INFO_ID );
	}
	
}
