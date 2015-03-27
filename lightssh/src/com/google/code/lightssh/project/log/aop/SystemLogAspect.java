package com.google.code.lightssh.project.log.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.lightssh.common.model.Loggable;
import com.google.code.lightssh.common.web.SessionKey;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.log.service.AccessManager;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 系统日志切面
 * @author YangXiaojin
 *
 */
public class SystemLogAspect {
	
	private static Logger log = LoggerFactory.getLogger( "SYSTEM_LOG" );
	
	@Resource(name="accessManager")
	private AccessManager accessManager;
	
	/**
	 * 初始化日志
	 * 如果无法获取到Rquest及Session不作日志记录
	 */
	protected Access initLog(){
		Access access = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		if( request != null ){
			LoginAccount user = (LoginAccount)request.getSession()
			.getAttribute( SessionKey.LOGIN_ACCOUNT );
			access = new Access( );
			access.init(request);
			access.setOperator( user.getLoginName() );
		}
		
		return access;
	}
	
	/**
	 * 初始化日志
	 * 如果无法获取到Rquest及Session不作日志记录
	 */
	protected Access initLog(Loggable loggable){
		Access access = initLog();
		if( access != null )
			access.setDescription(loggable.logMessage());
		
		return access;
	}
	
	/**
	 * 方法执行完成
	 */
	public void doAfter(JoinPoint jp) {  
		if( jp.getArgs() == null || jp.getArgs().length == 0 
				|| !(jp.getArgs()[0] instanceof Loggable) )
			return;
		
		try{
			Access access = initLog((Loggable)jp.getArgs()[0]);
			if( access == null )
				return;
			
			accessManager.writeSystemLog(access);//系统日志
			
			log.info("log Ending method: "  
	                + jp.getTarget().getClass().getName() + "."  
	                + jp.getSignature().getName());  
		}catch( Exception e ){
			
		}
        
    } 

}
