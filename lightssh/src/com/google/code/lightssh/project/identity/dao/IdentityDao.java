package com.google.code.lightssh.project.identity.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.identity.entity.Identity;
import com.google.code.lightssh.project.identity.entity.IdentityType;

public interface IdentityDao extends Dao<Identity>{
	
	/**
	 * get by value
	 */
	public Identity getByValue( String value );
	
	/**
	 * get by Type and value
	 */
	public Identity get( IdentityType type, String value );

}
