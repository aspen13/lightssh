
package com.google.code.lightssh.project.security.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.security.entity.LoginAccountChange;

/** 
 * @author YangXiaojin
 * @date 2013-2-23
 * @description：TODO
 */
@Repository("loginAccountChangeDao")
public class LoginAccountChangeDaoJpa extends JpaDao<LoginAccountChange> implements LoginAccountChangeDao{

	private static final long serialVersionUID = 1159868138167363125L;

}
