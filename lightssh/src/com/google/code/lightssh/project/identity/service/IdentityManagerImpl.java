package com.google.code.lightssh.project.identity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.identity.dao.IdentityDao;
import com.google.code.lightssh.project.identity.entity.Identity;
import com.google.code.lightssh.project.identity.entity.IdentityType;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component( "identityManager" )
public class IdentityManagerImpl extends BaseManagerImpl<Identity> implements IdentityManager{
	
	private static final long serialVersionUID = 7496130215939685277L;
	
	protected IdentityDao dao;
		
	public IdentityManagerImpl() {
		super();
	}

	@Resource(name="identityDao")
	public void setIdentityDao(IdentityDao dao) {
		super.dao = dao;
		this.dao = dao;
	}
	
	public Identity get(String value) {
		return dao.getByValue( value );
	}

	public Identity get(IdentityType type, String value) {
		return dao.get(type, value);
	}

	public Identity getClassIdentity(Persistence<?> model) {
		Identity i = new Identity( model );
		return dao.get( i.getType(),i.getValue() );
	}

	public void saveClassIdentity(Persistence<?> model) {
		Identity i = new Identity( model );
		dao.create(i);
	}
	
}
