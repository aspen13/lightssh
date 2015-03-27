package com.google.code.lightssh.project.log.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.log.entity.Access;

@Repository("accessDao")
public class AccessDaoJpa extends JpaAnnotationDao<Access> {
	
	private static final long serialVersionUID = -8246977185697977669L;

	public ListPage<Access> list(ListPage<Access> page,Access t ){
		if( t == null )
			return list( page );
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer( );
		
		hql.append( " FROM " + entityClass.getName() + " AS m " );
		hql.append( " WHERE 1=1 ");
		if( t.getType() != null ){
			hql.append( " AND m.type = ? " );
			params.add( t.getType() );
		}
		
		if( StringUtil.clean( t.getIp() ) != null ){
			hql.append( " AND m.ip like ? " );
			params.add( "%" + t.getIp() + "%" );
		}
		
		if( t.get_period() != null ){
			Date start = t.get_period().getStart();
			Date end = t.get_period().getEnd();
			
			if( start != null ){
				hql.append( " AND m.time >= ? " );
				params.add( start );
			}
			
			if( end != null ){
				Calendar cal_end = Calendar.getInstance();
				cal_end.setTime(end);
				cal_end.add(Calendar.DAY_OF_MONTH, 1);
				cal_end.add(Calendar.SECOND, -1);
				hql.append( " AND m.time < ? " );
				params.add( cal_end.getTime() );
			}
		}
		
		return super.query(page, hql.toString(), params.toArray( ) );
	}

}
