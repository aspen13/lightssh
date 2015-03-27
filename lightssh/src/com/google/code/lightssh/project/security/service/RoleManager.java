package com.google.code.lightssh.project.security.service;

import java.util.Collection;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.entity.Role;

/**
 * role Manager
 * @author YangXiaojin
 *
 */
public interface RoleManager extends BaseManager<Role>{
	
	/**
	 * 角色添加权
	 */
	public void save( Role role, Collection<Navigation> colls,LoginAccount user );
	
	/**
	 * 删除角色
	 */
	public void remove(Role role,LoginAccount operator,String remark);
	
	/**
	 * 初始化系统管理员角色
	 */
	public Role initRole( boolean forceUpdatePermission );

	/**
	 * 待审核列表
	 */
	public ListPage<Role> listTodoAudit(ListPage<Role> page,Role role);
	
}
