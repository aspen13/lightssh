package com.google.code.lightssh.project.security.dao;

import java.util.Calendar;
import java.util.List;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Permission;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.util.constant.AuditStatus;

/**
 * LoginAccount Dao
 * @author YangXiaojin
 *
 */
public interface LoginAccountDao extends Dao<LoginAccount>{
	
	/**
	 * get by login name
	 */
	public LoginAccount get( String loginName );
	

	/**
	 * 根据电子邮箱查登录帐号
	 */
	public LoginAccount getByEmail(String email);
	
	/**
	 * 更新角色
	 */
	public void updateRole( LoginAccount account );

	/**
	 * 分页查询LoginAccount id 和登录名
	 */
	public ListPage<LoginAccount> listLight(ListPage<LoginAccount> page,LoginAccount t );
	
	/**
	 * 根据权限查询登录账户
	 */
	public List<LoginAccount> listByPermission(Permission p );
	
	/**
	 * 根据角色ID或角色名称查询用户
	 */
	public List<LoginAccount> listByRole(AuditStatus status,final Role r );
	
	/**
	 * 查询带Party ID
	 */
	public LoginAccount getWithPartyIdentity( String loginName);
	
	/**
	 * 更新登录锁定时间
	 */
	public int updateLockTime( Long userId,Calendar time);
	
	/**
	 * 清除登录锁定时间
	 */
	public int cleanLockTime( Long id );
	
}
