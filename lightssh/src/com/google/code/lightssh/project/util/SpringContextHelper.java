package com.google.code.lightssh.project.util;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 
 * @author YangXiaojin
 * @date 2012-12-11
 */
public class SpringContextHelper implements Serializable{
	
	private static final long serialVersionUID = -8524944880959387704L;
	
	private static Logger log = LoggerFactory.getLogger(SpringContextHelper.class);

	private static ApplicationContext applicationContext;
	
	public static void init( ServletContext sc ) {   
		if( sc == null )
			return;
		
        applicationContext = WebApplicationContextUtils
        	.getRequiredWebApplicationContext(sc);
	}
	
	/**
	 * 获取Bean
	 */
	public static Object getBean(String beanId){
		try{
			return applicationContext.getBean(beanId);
		}catch( BeansException e ){
			log.warn("获取BEAN[{}]异常:{}",beanId,e.getMessage());
		}

		return null;
	}

}
