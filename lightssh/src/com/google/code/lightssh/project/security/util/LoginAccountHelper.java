package com.google.code.lightssh.project.security.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.service.LoginAccountManager;
import com.google.code.lightssh.project.util.SpringContextHelper;

public class LoginAccountHelper {
	
	public static final String LOGINACCOUNT_MANAGER_NAME = "loginAccountManager";
	
	/**
	 * 根据Key查询流程定义
	 */
	public static List<LoginAccount> listByIds(String ids ){
		if( StringUtils.isEmpty(ids) )
			return null;
		
		LoginAccountManager bean = (LoginAccountManager)
				SpringContextHelper.getBean(LOGINACCOUNT_MANAGER_NAME);
		
		return bean.listByIds( ids.split(",") );
	}

}
