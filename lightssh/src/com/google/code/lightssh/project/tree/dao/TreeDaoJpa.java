package com.google.code.lightssh.project.tree.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.tree.entity.Tree;

/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("treeDao")
public class TreeDaoJpa extends JpaAnnotationDao<Tree>{
	
	private static final long serialVersionUID = 8024579934424493402L;

	public ListPage<Tree> list(ListPage<Tree> page,Tree t ){
		if( t == null )
			return list( page );
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder( );
		
		hql.append( " FROM " + entityClass.getName() + " AS m " );
		hql.append( " WHERE 1=1 ");
		if( StringUtil.clean( t.getName()) != null ){
			hql.append( " AND m.name = ? " );
			params.add( t.getName().trim() );
		}
		
		if( StringUtil.clean( t.getDescription() ) != null ){
			hql.append( " AND m.description like ? " );
			params.add( "%" + t.getDescription().trim() + "%");
		}
		
		return super.query(page, hql.toString(), params.toArray( ) );
	}
	
}
