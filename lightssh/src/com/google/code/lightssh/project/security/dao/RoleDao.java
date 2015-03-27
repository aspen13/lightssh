package com.google.code.lightssh.project.security.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.security.entity.Role;

/**
 * Role Dao
 * @author YangXiaojin
 *
 */
public interface RoleDao extends Dao<Role>{
	
	/**
	 * get by role name
	 */
	public Role get( String name );

}
