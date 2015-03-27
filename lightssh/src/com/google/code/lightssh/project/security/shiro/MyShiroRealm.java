package com.google.code.lightssh.project.security.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.security.service.LoginAccountManager;

/**
 * Shiro Realm 实现
 * @author YangXiaojin
 *
 */
public class MyShiroRealm extends AuthorizingRealm{
	
	private LoginAccountManager accountManager;

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
					if( !each.isEffective() )
						continue;
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

	/**
	 * 认证信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken ) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String accountName = token.getUsername();
		
		if( accountName != null && !"".equals(accountName) ){
			LoginAccount account = accountManager.get( token.getUsername() );
	
			if( account != null ){
				return new SimpleAuthenticationInfo(
						account.getLoginName(),account.getPassword(), getName() );
			}
		}

		return null;
	}
	
}
