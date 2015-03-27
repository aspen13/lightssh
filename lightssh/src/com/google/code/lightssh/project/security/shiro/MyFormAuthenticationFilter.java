package com.google.code.lightssh.project.security.shiro;

import com.google.code.lightssh.common.support.shiro.CaptchaFormAuthenticationFilter;

/**
 * 扩展“验证码”
 * @author YangXiaojin
 *
 */
public class MyFormAuthenticationFilter extends CaptchaFormAuthenticationFilter{
	
	//private static Logger log = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);
	
	/**
	 * 验证码SessionKey
	 */
	protected String getKaptchaSessionKey( ){
		return com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;
	}

}
