
package com.google.code.lightssh.project.security.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.security.dao.AuthorizedResourceDao;
import com.google.code.lightssh.project.security.entity.AuthorizedResource;

/** 
 * @author YangXiaojin
 * @date 2013-3-7
 * @description：TODO
 */
@Component("authorizedResourceManager")
public class AuthorizedResourceManagerImpl extends BaseManagerImpl<AuthorizedResource> implements AuthorizedResourceManager{

	private static final long serialVersionUID = 512181190184461243L;
	
	@Resource(name="authorizedResourceDao")
	public void setDao(AuthorizedResourceDao dao){
		this.dao = dao;
	}
	
	public AuthorizedResourceDao getDao(){
		return (AuthorizedResourceDao)this.dao;
	}
	
	/**
	 * 通过URL查询，正则匹配
	 */
	public AuthorizedResource getWithRegexp( String url ){
		return this.getDao().getWithRegexp(url);
	}
	
	/**
	 * 是否可授权
	 */
	public boolean canAuthorized( String url ){
		AuthorizedResource authRes = getWithRegexp(url);
		
		return authRes != null && authRes.isEnabled();
	}

}
