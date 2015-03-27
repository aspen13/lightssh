package com.google.code.lightssh.project.log.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.TextFormater;
import com.google.code.lightssh.project.identity.entity.Identity;
import com.google.code.lightssh.project.identity.service.IdentityManager;
import com.google.code.lightssh.project.log.dao.HistoryDao;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.log.entity.AccessType;
import com.google.code.lightssh.project.log.entity.History;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("accessManager")
public class AccessManagerImpl extends BaseManagerImpl<Access> implements AccessManager{
	
	private static final long serialVersionUID = -2943948007204180986L;

	protected Dao<Access> dao;
	
	@Resource(name="historyDao")
	protected HistoryDao historyDao;
	
	@Resource(name="identityManager")
	private IdentityManager identityManager;
	
	public AccessManagerImpl() {
		super();
	}
		
	@Resource(name="accessDao")
	public void setDao(Dao<Access> dao) {
		this.dao = dao;
		super.dao = this.dao;
	}
	
	public void setHistoryDao(HistoryDao historyDao) {
		this.historyDao = historyDao;
	}

	public void setIdentityManager(IdentityManager identityManager) {
		this.identityManager = identityManager;
	}

	public void log(Access access, Persistence<?> originalModel,
			Persistence<?> newModel) {
		if( access == null )
			throw new ApplicationException("access is null!");
		
		if( originalModel != null && newModel != null ){
			access.setType( AccessType.UPDATE );
		}else if( originalModel == null && newModel == null ){
			throw new ApplicationException("original model and new model is null!");
		}else if( originalModel == null ){
			access.setType( AccessType.CREATE );
		}else{
			access.setType( AccessType.DELETE );
		}
		
		Persistence<?> p = originalModel;
		if(p==null) 
			p = newModel;
		Identity classIdentity = identityManager.getClassIdentity( p );
		if( classIdentity == null ){
			classIdentity = new Identity( p );
			this.identityManager.create(classIdentity);
		}
		access.setClassIdentity(classIdentity);
		
		History history = new History(access,originalModel,newModel);
		history.setCreateDate( access.getTime() );		
		//dao.create(access);	
		historyDao.create( history );
	}

	@Override
	public History getHistory(Access access) {
		if( access == null || access.getIdentity() == null )
			return null;
		
		return this.historyDao.getByAccess(access);
	}
	
	/**
	 * 写系统日志
	 */
	public void writeSystemLog(Access t){
		if( t == null )
			return;
		if( t.getDescription() != null )
			t.setDescription(TextFormater.format(t.getDescription(),196,true));
		dao.create(t);
	}

}

