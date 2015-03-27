
package com.google.code.lightssh.project.security.service;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.log.entity.EntityChange;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.LoginAccountChange;

/** 
 * @author YangXiaojin
 * @date 2013-2-23
 * @description：TODO
 */

public interface LoginAccountChangeManager extends BaseManager<LoginAccountChange>{
	
	/**
	 * 保存
	 */
	public LoginAccountChange save(LoginAccount user,EntityChange.Type type
			,LoginAccount originalAcc,LoginAccount newAcc,String remark);
	
	/**
	 * 待审核列表
	 */
	public ListPage<LoginAccountChange> listTodoAudit(ListPage<LoginAccountChange> page,LoginAccountChange t);

	/**
	 * 保存状态
	 */
	public void updateStatus(String id,EntityChange.Status originalStatus,EntityChange.Status newStatus);
}
