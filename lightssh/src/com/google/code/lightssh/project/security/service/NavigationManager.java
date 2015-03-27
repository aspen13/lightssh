package com.google.code.lightssh.project.security.service;

import java.util.Collection;
import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.entity.Permission;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface NavigationManager extends BaseManager<Navigation>{
	
	/**
	 * 查询所有
	 */
	public List<Navigation> listAll();
	
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
	public Collection<Permission> listPermission( Collection<Navigation> colls );
	
	/**
	 * 查询组织机构或会员拥有的权限
	 */
	public List<Navigation> listByParty( Party party );

}
