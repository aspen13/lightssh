package com.google.code.lightssh.project.security.shiro;

import java.util.Collection;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.code.lightssh.common.config.SystemConfig;
import com.google.code.lightssh.common.support.shiro.ConfigConstants;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.security.service.LoginAccountManager;

/**
 * CAS Realm 实现
 * @author YangXiaojin
 *
 */
public class MyCasRealm extends CasRealm{
	
	private LoginAccountManager accountManager;
	
	private String casServerUrlPrefixKey = ConfigConstants.CAS_SERVER_URL_PREFIX_KEY;
	
	private String casServiceKey = ConfigConstants.CAS_SERVICE_KEY;
	
	/**
	 * 系统参数
	 */
	private SystemConfig systemConfig;

	public void setAccountManager(LoginAccountManager accountManager) {
		this.accountManager = accountManager;
	}
	
	/**
	 * 授权信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String) principals.fromRealm(getName()).iterator().next();
		
		if( username != null ){
			LoginAccount la = accountManager.get( username );
			if( la != null && la.getRoles() != null ){
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				for( Role each:la.getRoles() ){
					if( each.getName() != null )
						info.addRole(each.getName());
					Collection<String> pers = each.getPermissionsAsString();
					if( pers != null )
						info.addStringPermissions( pers );
				}
				
				return info;
			}
		}
		
		return null;
	}
	
    public String getCasServerUrlPrefix() {
        return systemConfig.getProperty( casServerUrlPrefixKey );
    }

    public String getCasService() {
    	return systemConfig.getProperty( casServiceKey );
    }

	public String getCasServerUrlPrefixKey() {
		return casServerUrlPrefixKey;
	}

	public void setCasServerUrlPrefixKey(String casServerUrlPrefixKey) {
		this.casServerUrlPrefixKey = casServerUrlPrefixKey;
	}

	public String getCasServiceKey() {
		return casServiceKey;
	}

	public void setCasServiceKey(String casServiceKey) {
		this.casServiceKey = casServiceKey;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

}
