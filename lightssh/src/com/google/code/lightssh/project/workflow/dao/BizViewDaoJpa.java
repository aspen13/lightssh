package com.google.code.lightssh.project.workflow.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.workflow.entity.BizView;

/**
 * 
 * @author Aspen
 * @date 2013-10-28
 * 
 */
@Repository("bizViewDao")
public class BizViewDaoJpa extends JpaDao<BizView> implements BizViewDao{

	private static final long serialVersionUID = 8225990569333118891L;

}
