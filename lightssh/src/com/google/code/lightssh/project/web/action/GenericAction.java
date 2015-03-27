package com.google.code.lightssh.project.web.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.web.SessionKey;
import com.google.code.lightssh.project.security.entity.LoginAccount;

public class GenericAction<T extends Persistence<?>> extends com.google.code.lightssh.common.web.action.CrudAction<T>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 验证码
	 */
	private String captcha;
	
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	/**
	 * 登录帐户
	 */
	public LoginAccount getLoginAccount( ){
		return (LoginAccount)request.getSession().getAttribute( SessionKey.LOGIN_ACCOUNT );
	}
	
	/**
	 * 登录用户名
	 */
	public String getLoginUser( ){
		LoginAccount la = getLoginAccount();
		return la==null?null:la.getLoginName();
	}
	
	/**
	 * 登录
	 */
	protected void login( LoginAccount account ){
		if( account == null || account.getLoginName() == null
				|| account.getPassword() == null )
			return;
		
		AuthenticationToken token = new UsernamePasswordToken(
				account.getLoginName(),account.getPassword());
		
		SecurityUtils.getSubject().login(token);
		
		//记录Session
		request.getSession().setAttribute(
				SessionKey.LOGIN_ACCOUNT, account);
	}
	
	/**
	 * 缓存参数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void cacheRequestParams( ){
		Map<String,Map> cachedParamMap = (Map<String,Map>)request.getSession()
			.getAttribute( SessionKey.REQUEST_PARAMETERS );
		
		if( cachedParamMap == null )
			cachedParamMap = new HashMap<String,Map>();
		
		Map<String,String[]> oneRequestParams = new HashMap<String,String[]>();
		Enumeration names = request.getParameterNames();
		while( names.hasMoreElements() ){
			String name = names.nextElement().toString();
			oneRequestParams.put(name, request.getParameterValues(name));
		}
			
		cachedParamMap.put(request.getRequestURI(),oneRequestParams);
		request.getSession().setAttribute(SessionKey.REQUEST_PARAMETERS, cachedParamMap);
		
	}
	
	/**
	 * 获取缓存的参数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getCachedParams( ){
		Map<String,Map> cachedParamMap = (Map<String,Map>)request.getSession()
			.getAttribute( SessionKey.REQUEST_PARAMETERS );
		
		if( cachedParamMap == null )
			return null;
		
		//return cachedParamMap.get( ServletActionContext.getRequest().getRequestURI() );
		return cachedParamMap.get( (String)request.getAttribute("struts.request_uri") );
	}
	
	/**
	 * 清除验证码
	 */
	protected void cleanCaptcha( ){
		request.getSession().setAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY,null);
	}

}
