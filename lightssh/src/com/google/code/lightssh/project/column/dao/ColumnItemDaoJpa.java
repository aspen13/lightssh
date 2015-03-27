package com.google.code.lightssh.project.column.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.column.entity.ColumnItem;

/**
 * ColumnItemDao
 * @author Aspen
 * @date 2014-7-1
 *
 */
@Repository("columnItemDao")
public class ColumnItemDaoJpa extends JpaDao<ColumnItem> implements ColumnItemDao{

	private static final long serialVersionUID = 8290942258302006311L;

}
