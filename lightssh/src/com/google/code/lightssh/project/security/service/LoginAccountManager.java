package com.google.code.lightssh.project.security.service;

import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Permission;

/**
 * LoginAccount Manager
 * @author YangXiaojin
 *
 */
public interface LoginAccountManager extends BaseManager<LoginAccount>{
	
	/**
	 * 根据名称查询登录账号
	 */
	public LoginAccount get( String name );
	
	/**
	 * 根据电子邮箱查登录帐号
	 */
	public LoginAccount getByEmail(String email);
	
	/**
	 * 根据名称查询登录账号,只查询当前实现（不关联查询）
	 */
	public LoginAccount getLight( String name );
	
	/**
	 * 根据名称查询登录账号，如果关联了Party一并带出
	 */
	public LoginAccount getWithParty( String name );
	
	/**
	 * 初始化系统管理员登录账号
	 */
	public void initLoginAccount( );
	
	/**
	 * 保存登录帐号
	 */
	public void save( LoginAccount account,LoginAccount operator );
	
	/**
	 * 更新密码
	 */
	public void updatePassword( String name,String password,String newPassword );

	/**
	 * 重置密码
	 * @param name 登录帐号
	 * @param newPassword 新密码
	 */
	public void resetPassword( String name,String newPassword );
	
	/**
	 * 查询交易所管理员
	 */
	public List<LoginAccount> listAdmin( );
	
	/**
	 * 查询 拥有某个权限的 登录账户
	 */
	public List<LoginAccount> listByPermission(Permission permission );
	
	/**
	 * 查询 拥有某个权限的 登录账户
	 */
	public List<LoginAccount> listByPermission(String permission );
	
	/**
	 * 根据角色ID查询有效登录帐户
	 * @param id 角色ID
	 * @return 有效登录帐户
	 */
	public List<LoginAccount> listByRole(String id );
	
	/**
	 * 根据ID查询有效登录帐户
	 * @param ids 登录帐户ID
	 */
	public List<LoginAccount> listByIds(Object[] ids );
	
	/**
	 * 启用或禁用CA登录
	 */
	public void toggleCa( LoginAccount account ,Access log );
	/**
	 * 查询会员登录帐户
	 */
	public List<LoginAccount> listByParty( Party party );
	
	/**
	 * 登录失败锁定时间
	 */
	public boolean updateLockTime( Long id );

	/**
	 * 解除登录失败锁定时间
	 */
	public void releaseLockTime( LoginAccount la );
	
	/**
	 * 删除登录帐号
	 */
	public void remove(LoginAccount account,LoginAccount operator,String remark);

}
