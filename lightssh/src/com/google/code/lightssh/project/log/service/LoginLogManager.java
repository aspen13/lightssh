package com.google.code.lightssh.project.log.service;

import java.util.Date;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.log.entity.LoginLog;

/**
 * 登录日志业务接口
 * @author YangXiaojin
 *
 */
public interface LoginLogManager extends BaseManager<LoginLog>{
	
	/**
	 * 登录日志
	 */
	public void login(Date date,String ip, String username );

}
