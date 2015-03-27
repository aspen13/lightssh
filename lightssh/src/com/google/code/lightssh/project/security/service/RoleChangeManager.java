
package com.google.code.lightssh.project.security.service;

import java.util.List;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.log.entity.EntityChange;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.security.entity.RoleChange;

/** 
 * @author YangXiaojin
 * @date 2013-2-17
 * @description：TODO
 */

public interface RoleChangeManager extends BaseManager<RoleChange>{
	
	/**
	 * 保存
	 */
	public RoleChange save(LoginAccount user,EntityChange.Type type
			,Role originalRole,Role newRole,String remark);
	
	/**
	 * 待审核列表
	 */
	public ListPage<RoleChange> listTodoAudit(ListPage<RoleChange> page,RoleChange t );
	
	/**
	 * 更新状态
	 */
	public void updateStatus(String id,EntityChange.Status originalStatus,EntityChange.Status newStatus);

	/**
	 * 查询角色关联的所有变更信息
	 */
	public List<RoleChange> list(Role role );
}
