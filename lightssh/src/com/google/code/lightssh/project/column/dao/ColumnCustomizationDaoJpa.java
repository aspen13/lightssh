package com.google.code.lightssh.project.column.dao;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.column.entity.ColumnCustomization;

/**
 * 
 * @author Aspen
 * @date 2013-10-31
 * 
 */
@Repository("columnCustomizationDao")
public class ColumnCustomizationDaoJpa extends JpaDao<ColumnCustomization> implements ColumnCustomizationDao{

	private static final long serialVersionUID = -5682191579374359728L;

}
