
package com.google.code.lightssh.project.security.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.security.dao.PermissionDao;
import com.google.code.lightssh.project.security.entity.Permission;

/** 
 * @author YangXiaojin
 * @date 2013-3-8
 * @description：TODO
 */
@Component("permissionManager")
public class PermissionManagerImpl extends BaseManagerImpl<Permission> implements PermissionManager{

	private static final long serialVersionUID = -5882324320727417313L;
	
	@Resource(name="permissionDao")
	public void setDao(PermissionDao dao){
		this.dao = dao;
	}
	
	public PermissionDao getDao(){
		return (PermissionDao)this.dao;
	}
	
	/**
	 * 通过URL查询
	 */
	public Permission getByUrlWithRegexp( String url ){
		return this.getDao().getByUrlWithRegexp(url);
	}

}
