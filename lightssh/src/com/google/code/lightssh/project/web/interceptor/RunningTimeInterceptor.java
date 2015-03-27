package com.google.code.lightssh.project.web.interceptor;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.lightssh.common.config.SystemConfig;
import com.google.code.lightssh.common.util.StringUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Action 执行时间拦截器
 * @author YangXiaojin
 *
 */
public class RunningTimeInterceptor extends AbstractInterceptor{
	
	private static final long serialVersionUID = 1L;
	
	private long timingMillis = 0;
	
	public static final String LOG_ACTION_TIMING_LOGGER = "log.action.timing";
	
	public static final String LOG_ACTION_TIMING_MILLIS_KEY = "log.action.timing.millis";
	
	public static final String LOG_ACTION_TIMING_ENABLED_KEY = "log.action.timing.enabled";
	
	private static Logger log = LoggerFactory.getLogger( LOG_ACTION_TIMING_LOGGER );
	
	@Resource( name="systemConfig" )
	private SystemConfig systemConfig;
	
	/**
	 * 是否输出SQL耗时日志
	 */
	protected boolean isTimingLogEnabled( ){
		return systemConfig != null && "true".equals(systemConfig.getProperty( 
				LOG_ACTION_TIMING_ENABLED_KEY ,"false"));
	}
	
    public void init() {
    	if( isTimingLogEnabled() ){
    		String conf = systemConfig.getProperty( LOG_ACTION_TIMING_MILLIS_KEY );
    		try{
    			timingMillis = Long.valueOf( conf );
    		}catch( NumberFormatException e ){
    			log.warn("设置执行时间日志参数[{}]异常：{}",conf,e.getMessage());
    		}
    	}
    }
	
	public String intercept(ActionInvocation invocation) throws Exception{
		long startTime = System.currentTimeMillis(); //开始执行时间
		String result = invocation.invoke();
		long executionTime = System.currentTimeMillis() - startTime; //执行时间
		
		if( isTimingLogEnabled() && timingMillis > 0 ){
	        StringBuffer message = new StringBuffer(100);
	        message.append("执行的Action[");
	        String namespace = invocation.getProxy().getNamespace();
	        if ( StringUtil.clean(namespace) != null ) {
	            message.append(namespace);
	            if( !namespace.equals("/"))
	            	message.append("/");
	        }
	        message.append(invocation.getProxy().getActionName());
	        message.append("!");
	        message.append(invocation.getProxy().getMethod());
	        message.append("] 耗时 ").append(executionTime).append(" 毫秒.");
	        
	        if( executionTime > timingMillis )
	        	log.info( message.toString() );
		}
        
		return result;
	}
	
}
