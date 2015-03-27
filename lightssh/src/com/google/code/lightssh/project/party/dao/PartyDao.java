package com.google.code.lightssh.project.party.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.party.entity.Party;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface PartyDao extends Dao<Party>{
	
	/**
	 * 查询具体子类
	 */
	public ListPage<Party> list(Class<?> clazz,ListPage<Party> page,Party t );
	
	/**
	 * 查询具体子类
	 */
	public Party read( Class<?> clazz,Party party );

}
