package com.google.code.lightssh.project.security.dao;

import java.util.Collection;
import java.util.List;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.entity.Permission;

public interface NavigationDao extends Dao<Navigation>{
	
	/**
	 * 查询最上层Navigation
	 */
	public List<Navigation> listTop( );
	
	/**
	 * 查询菜单
	 */
	public List<Navigation> listMenu();
	
	/**
	 * 查询导航对应的权限集
	 */
	public Collection<Permission> listPermission(Collection<Navigation> colls);

}
