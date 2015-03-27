package com.google.code.lightssh.project.log.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.log.entity.LoginLog;

/**
 * 登录日志业务处理
 * @author YangXiaojin
 *
 */
@Component( "loginLogManager" )
public class LoginLogManagerImpl extends BaseManagerImpl<LoginLog> implements LoginLogManager{
	
	private static final long serialVersionUID = -699487480823847341L;

	@Resource( name="loginLogDao" )
	public void setSystemParamDao( Dao<LoginLog> dao ){
		super.dao = dao;
	}

	public ListPage<LoginLog> list(ListPage<LoginLog> page,LoginLog t ) {
		page.addDescending("createdTime");
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( StringUtil.clean(t.getOperator()) != null )
				sc.like("operator",t.getOperator() );
			
			if( StringUtil.clean(t.getIp()) != null )
				sc.like("ip",t.getIp() );
			
			if( t.get_period() != null ){
				Calendar cal = Calendar.getInstance();
				Date start = t.get_period().getStart();
				Date end = t.get_period().getEnd();
				
				if( start != null ){
					cal.setTime(start);
					sc.greateThanOrEqual("createdTime",cal);
				}
				
				if( end != null ){
					Calendar cal_end = Calendar.getInstance();
					cal_end.setTime(end);
					cal_end.add(Calendar.DAY_OF_MONTH, 1);
					cal_end.add(Calendar.SECOND, -1);
					sc.lessThanOrEqual("createdTime",cal_end);
				}
			}
		}
		return dao.list(page,sc);
	}
	
	public void login(Date date,String ip, String username ) {
		if( username == null )
			return;
		
		LoginLog log = new LoginLog();
		log.setOperator( username );
		log.setIp(ip);
		
		dao.create( log );	
	}
	
}
