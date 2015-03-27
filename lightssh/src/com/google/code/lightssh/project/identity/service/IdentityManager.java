package com.google.code.lightssh.project.identity.service;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.identity.entity.Identity;
import com.google.code.lightssh.project.identity.entity.IdentityType;

/**
 * identity manager
 * 
 * @author YangXiaojin
 *
 */
public interface IdentityManager extends BaseManager<Identity>{
	
	/**
	 * get by value
	 */
	public Identity get(String value);
	
	/**
	 * get by identity type and value
	 * @param type Identity.Type
	 * @param value value
	 * @return Identity
	 */
	public Identity get( IdentityType type, String value );
	
	/**
	 * get class identity
	 */
	public Identity getClassIdentity( Persistence<?> model );
	
	/**
	 * save 
	 */
	public void saveClassIdentity( Persistence<?> model );

}
