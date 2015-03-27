package com.google.code.lightssh.project.column.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.column.entity.ColumnTable;

/**
 * 
 * @author Aspen
 * @date 2013-10-30
 * 
 */
@Repository("columnTableDao")
public class ColumnTableDaoJpa extends JpaDao<ColumnTable> implements ColumnTableDao{

	private static final long serialVersionUID = 5882508116948122376L;

}
