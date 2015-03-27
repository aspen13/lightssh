
package com.google.code.lightssh.project.security.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.security.entity.AuthorizedResource;

/** 
 * @author YangXiaojin
 * @date 2013-3-7
 * @description：TODO
 */

public interface AuthorizedResourceManager extends BaseManager<AuthorizedResource>{
	
	/**
	 * 通过URL查询，正则匹配
	 */
	public AuthorizedResource getWithRegexp( String url );
	
	/**
	 * 是否可授权
	 */
	public boolean canAuthorized( String url );

}
