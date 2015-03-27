
package com.google.code.lightssh.project.security.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.security.entity.LoginAccountAudit;

/** 
 * @author YangXiaojin
 * @date 2013-2-23
 * @description：TODO
 */
@Repository("loginAccountAuditDao")
public class LoginAccountAuditDaoJpa extends JpaDao<LoginAccountAudit> implements LoginAccountAuditDao{

	private static final long serialVersionUID = -5446529968758446353L;

}
