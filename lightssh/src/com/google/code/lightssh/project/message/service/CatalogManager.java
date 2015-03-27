package com.google.code.lightssh.project.message.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.message.entity.Catalog;

/**
 * 
 * @author Aspen
 * 
 */
public interface CatalogManager extends BaseManager<Catalog>{
	
	/**
	 * 默认信息类消息
	 */
	public Catalog getDefaultInfo( );

}
