
package com.google.code.lightssh.project.security.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.security.entity.Permission;

/** 
 * @author YangXiaojin
 * @date 2013-3-8
 * @description：TODO
 */

public interface PermissionManager extends BaseManager<Permission>{
	
	/**
	 * 通过URL查询
	 */
	public Permission getByUrlWithRegexp( String url );

}
