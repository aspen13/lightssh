package com.google.code.lightssh.project.security.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.security.entity.RoleAudit;

/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("roleAuditDao")
public class RoleAuditDaoJpa extends JpaDao<RoleAudit> implements RoleAuditDao{

	private static final long serialVersionUID = 705818927619837107L;

}
