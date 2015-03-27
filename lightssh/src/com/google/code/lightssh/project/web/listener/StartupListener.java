package com.google.code.lightssh.project.web.listener;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.google.code.lightssh.common.support.shiro.ConfigConstants;
import com.google.code.lightssh.project.config.ProjectConfig;
import com.google.code.lightssh.project.scheduler.service.SchedulerManager;
import com.google.code.lightssh.project.security.service.LoginAccountManager;
import com.google.code.lightssh.project.util.SpringContextHelper;

/**
 * container startup listener
 * @author Yangxiaojin
 */
public class StartupListener extends ContextLoaderListener{
	
	private static Logger log = LoggerFactory.getLogger(StartupListener.class);
	
	public void contextDestroyed(ServletContextEvent sce) {
		super.contextDestroyed(sce); //Spring
	}

	/**
	 * init system resource
	 */
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce); //Spring 
		
		chooseFreemarkLoger();
		
        //ApplicationContext ctx = WebApplicationContextUtils
        //	.getRequiredWebApplicationContext(sce.getServletContext());
        
        SpringContextHelper.init(sce.getServletContext());
        
		log.debug( "系统初始化..." );
		initScheduler( );
		initRootUser( );
		initSystemProperty( );
	}

	/**
	 * 初始系统账户
	 */
	private void initRootUser( ){
        try{
	        LoginAccountManager laMgr = (LoginAccountManager)
	        	SpringContextHelper.getBean("loginAccountManager");
	        laMgr.initLoginAccount();
        }catch( Exception e ){
        	log.error( e.getMessage() );
        }
	}
	
	/**
	 * 初始化定时任务
	 */
	private void initScheduler( ){
		try{
			SchedulerManager schedulerManager = (SchedulerManager)
				SpringContextHelper.getBean("schedulerManager");
			if( schedulerManager == null )
				return;
			
			schedulerManager.initCronTrigger();
        }catch( Exception e ){
        	log.error( e.getMessage() );
        }
		
	}
	
	
	/**
	 * FreeMarker调试日志
	 */
	public void chooseFreemarkLoger( ){
		try{
			//freemarker.log.Logger.selectLoggerLibrary( freemarker.log.Logger.LIBRARY_NONE );
			freemarker.log.Logger.selectLoggerLibrary( freemarker.log.Logger.LIBRARY_SLF4J );
		}catch( Exception e ){
			log.warn("选择Freemarker日志框架出错:" + e.getMessage() );
		}
	}
	
	/**
	 * CAS启动设置keystore
	 */
	public void initSystemProperty( ){
		try{
			ProjectConfig systemConfig = (ProjectConfig)
				SpringContextHelper.getBean("systemConfig");
			
			if( systemConfig != null && "true".equalsIgnoreCase(
	    			systemConfig.getProperty( ConfigConstants.CAS_ENABLED_KEY, "false")) ){
				String keystore = systemConfig.getProperty(ConfigConstants.CAS_SERVER_KEYSTORE_KEY);
				
				log.info("设置系统属性javax.net.ssl.trustStore:[{}]",keystore);
				System.setProperty("javax.net.ssl.trustStore",keystore);
			}
		}catch( Exception e ){
        	log.error( e.getMessage() );
        }
	}
}
