package com.google.code.lightssh.project.column.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.column.dao.ColumnItemDao;
import com.google.code.lightssh.project.column.dao.ColumnTableDao;
import com.google.code.lightssh.project.column.entity.ColumnItem;
import com.google.code.lightssh.project.column.entity.ColumnTable;

/**
 * 
 * @author Aspen
 * @date 2013-10-30
 * 
 */
@Component("columnTableManager")
public class ColumnTableManagerImpl extends BaseManagerImpl<ColumnTable> implements ColumnTableManager{

	private static final long serialVersionUID = 1101857819919107633L;
	
	@Resource(name="columnItemDao")
	private ColumnItemDao columnItemDao;
	
	@Resource(name="columnTableDao")
	public void setDao(ColumnTableDao dao){
		this.dao = dao;
	}
	
	public ColumnTableDao getDao( ){
		return (ColumnTableDao)this.dao;
	}
	
	public ListPage<ColumnTable> list( ListPage<ColumnTable> page, ColumnTable t){
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( StringUtils.isNotEmpty(t.getId()) )
				sc.like("id", t.getId().trim() );
			
			if( StringUtils.isNotEmpty(t.getName()) )
				sc.like("name", t.getName().trim() );
		}
		
		return super.list(page, sc);
	}
	
	/**
	 * 查询表明细
	 * @param colTabId 动态表ID
	 */
	public List<ColumnItem> listItems( String colTabId ){
		if( StringUtils.isEmpty(colTabId) )
			return null;
		
		SearchCondition sc = new SearchCondition();
		ListPage<ColumnItem> page = new ListPage<ColumnItem>(Integer.MAX_VALUE);
		sc.equal("table.id", colTabId.trim() );
		page = columnItemDao.list(page,sc);
		
		return page.getList();
	}

}
