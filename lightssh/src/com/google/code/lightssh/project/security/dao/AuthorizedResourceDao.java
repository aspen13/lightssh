
package com.google.code.lightssh.project.security.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.security.entity.AuthorizedResource;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface AuthorizedResourceDao extends Dao<AuthorizedResource>{
	
	/**
	 * 通过URL查询，正则匹配
	 */
	public AuthorizedResource getWithRegexp( String url );

}
