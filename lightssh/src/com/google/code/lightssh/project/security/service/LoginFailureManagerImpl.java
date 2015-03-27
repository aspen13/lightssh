package com.google.code.lightssh.project.security.service;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.LoginFailure;

/**
 * 登录失败业务接口
 * @author YangXiaojin
 *
 */
@Component("loginFailureManager")
public class LoginFailureManagerImpl extends BaseManagerImpl<LoginFailure> 
	implements LoginFailureManager{
	
	private static final long serialVersionUID = -1576213225564275382L;

	@Resource(name="loginFailureDao")
	public void setDao(Dao<LoginFailure> dao ){
		super.dao = dao;
	}
	
	public ListPage<LoginFailure> list(ListPage<LoginFailure> page,LoginFailure t ) {
		if( page == null )
			page = new ListPage<LoginFailure>( );
		
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( !StringUtils.isEmpty(t.getSessionId()) ){
				sc.equal("sessionId",t.getSessionId().trim());
			}
		}
		
		return dao.list(page,sc);
	}
	
	/**
	 * 保存登录失败记录
	 * @param la 登录帐户
	 * @param loginName 登录名称
	 * @param ip IP地址
	 * @param sessionId SESSION ID
	 */
	public void save(LoginAccount la,String loginName,String ip,String sessionId ){
		if( la== null )
			return ;
		
		ListPage<LoginFailure> page = new ListPage<LoginFailure>(1);
		LoginFailure sameUser = new LoginFailure();
		sameUser.setSessionId(sessionId);
		page = list(page,sameUser);
		
		LoginFailure old = null;
		if( page.getList() != null && page.getList().size() > 0 ){
			old = page.getList().get(0);
			old.setLastUpdateTime(Calendar.getInstance());
			old.incFailureCount();
			dao.update(old);
		}else{
			LoginFailure t = new LoginFailure();
			t.setAccount(la);
			t.setLoginName(loginName);
			t.setIp(ip);
			t.setSessionId(sessionId);
			t.incFailureCount();
			dao.create(t);
		}
	}

}
