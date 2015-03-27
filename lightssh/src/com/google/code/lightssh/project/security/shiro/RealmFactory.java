package com.google.code.lightssh.project.security.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.lightssh.common.config.SystemConfig;
import com.google.code.lightssh.common.support.shiro.ConfigConstants;

/**
 * 
 * @author Aspen
 * @date 2013-4-3
 * 
 */
public class RealmFactory {
	
	private static Logger log = LoggerFactory.getLogger(RealmFactory.class);
	
	public static final String REALM_KEY_JDBC = "jdbc";
	
	public static final String REALM_KEY_CAS = "cas";
	
	/**
	 * 系统参数
	 */
	private SystemConfig systemConfig;
	
	private Map<String,Realm> realms = new HashMap<String,Realm>();

	public void setRealms(Map<String, Realm> realms) {
		this.realms = realms;
	}
	
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
	
	/**
	 * Realm key
	 */
	protected String getRealmKey( ){
		if( systemConfig != null && "true".equals( 
				systemConfig.getProperty( ConfigConstants.CAS_ENABLED_KEY )) )
			return REALM_KEY_CAS;
		
		return REALM_KEY_JDBC;
	}

	public Realm createRealm(){
		String key = getRealmKey();
		Realm realm = realms.get( key );
		if( realm == null ){
			log.error("Shiro Realm[{}]未实现！", key );
		}else{
			log.info("Shiro Realm[{}] 初始化完成！",key);
		}
		return realm;
	}

}
