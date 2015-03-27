package com.google.code.lightssh.project.security.service;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.LoginFailure;

/**
 * 登录失败业务接口
 * @author YangXiaojin
 *
 */
public interface LoginFailureManager extends BaseManager<LoginFailure>{
	
	/**
	 * 保存登录失败记录
	 * @param la 登录帐户
	 * @param loginName 登录名称
	 * @param ip IP地址
	 * @param sessionId SESSION ID
	 */
	public void save(LoginAccount la,String loginName,String ip,String sessionId );
	

}
