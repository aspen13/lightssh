
package com.google.code.lightssh.project.security.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.security.entity.Permission;

/** 
 * @author YangXiaojin
 * @date 2013-3-8
 * @description：TODO
 */

public interface PermissionDao extends Dao<Permission>{
	
	/**
	 * 通过URL查询
	 */
	public Permission getByUrlWithRegexp( String url );

}
