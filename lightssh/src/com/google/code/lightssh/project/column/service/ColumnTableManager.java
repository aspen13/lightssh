package com.google.code.lightssh.project.column.service;

import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.column.entity.ColumnItem;
import com.google.code.lightssh.project.column.entity.ColumnTable;

/**
 * 
 * @author Aspen
 * @date 2013-10-30
 * 
 */
public interface ColumnTableManager extends BaseManager<ColumnTable>{
	
	/**
	 * 查询表明细
	 * @param colTabId 动态表ID
	 */
	public List<ColumnItem> listItems( String colTabId );

}
