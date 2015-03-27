package com.google.code.lightssh.project.security.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.security.entity.RoleChange;

/**
 * 
 * @author YangXiaojin
 *	
 */
@Repository("roleChangeDao")
public class RoleChangeDaoJpa extends JpaDao<RoleChange> implements RoleChangeDao{

	private static final long serialVersionUID = -362943160764362565L;
	
	public ListPage<RoleChange> list(ListPage<RoleChange> page,RoleChange t ){
		SearchCondition sc = new SearchCondition();
		if( t!= null ){
			
		}
		return list(page,sc);
	}

}
