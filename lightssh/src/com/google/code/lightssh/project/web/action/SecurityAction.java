package com.google.code.lightssh.project.web.action;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.config.SystemConfig;
import com.google.code.lightssh.common.support.shiro.ConfigConstants;
import com.google.code.lightssh.common.web.SessionKey;
import com.google.code.lightssh.common.web.action.BaseAction;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component( "securityAction" )
@Scope("prototype")
public class SecurityAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	/** 系统参数 */
	@Resource(name="systemConfig")
	private SystemConfig systemConfig;
	
	private boolean show;
	
	public String getVersion( ){
		return systemConfig.getProperty("version","trunk");
	}
	
	@JSON(name="show")
	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	/**
	 * 是否显示验证码
	 */
	public boolean isShowCaptcha( ){
		boolean enabled = "true".equalsIgnoreCase(systemConfig.getProperty(
				ConfigConstants.CAPTCHA_ENABLED_KEY,"false"));
		int times = 0;
		try{
			times = Integer.parseInt(systemConfig.getProperty(
				ConfigConstants.CAPTCHA_LOGIN_IGNORE_TIMES_KEY,"0"));
		}catch(Exception e){
			//ignore
		}
		
		Integer failed_count = (Integer) request.getSession().getAttribute(
				SessionKey.LOGIN_FAILURE_COUNT);
		failed_count=(failed_count == null) ? 0 : failed_count;
		
		return enabled && failed_count>=times;
	}
	
	/**
	 * 是否显示验证码
	 */
	public String showCaptcha( ){
		this.show = isShowCaptcha();
		return SUCCESS;
	}
	
	/**
	 * 登出
	 */
	public String logout(){
		try{
			//提示重复登录
			Object obj = request.getSession().getAttribute(SessionKey.EXCEPTION_OBJECT);
			
			SecurityUtils.getSubject().logout();
			
			if( obj != null && obj instanceof Throwable ){
				request.getSession().setAttribute(SessionKey.EXCEPTION_OBJECT, obj);
			}
		}catch( Exception e ){
			//e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 登录
	 */
	public String login(){		
		//如果登录成功(session未失效),再次登录会一直停留在登录页面
		if( SecurityUtils.getSubject().isAuthenticated() ){
			return SUCCESS;
		}else{
			//提示重复登录
			Object obj = request.getSession().getAttribute(SessionKey.EXCEPTION_OBJECT);
			if( obj != null && obj instanceof Throwable ){
				request.getSession().removeAttribute(SessionKey.EXCEPTION_OBJECT);
				request.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, obj);
			}
		}
		
		return INPUT;
	}

}
