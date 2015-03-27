package com.google.code.lightssh.project.column.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.project.column.entity.ColumnTable;
import com.google.code.lightssh.project.column.service.ColumnTableManager;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * 
 * @author Aspen
 * @date 2013-10-30
 * 
 */
@Component( "columnTableAction" )
@Scope("prototype")
public class ColumnTableAction extends GenericAction<ColumnTable>{

	private static final long serialVersionUID = 8196447291429366339L;
	
	private ColumnTable table;
	
	@Resource(name="columnTableManager")
	public void setManager(ColumnTableManager mgr){
		this.manager = mgr;
	}
	
	public ColumnTableManager getManager( ){
		return (ColumnTableManager)this.manager;
	}
	
	public ColumnTable getTable() {
		this.table = this.model;
		return table;
	}

	public void setTable(ColumnTable table) {
		this.table = table;
		this.model = this.table;
	}
	
	/**
	 * 查询列表明细
	 */
	public String listItem(){
		if( table == null || table.getId() == null ){
			this.saveErrorMessage("参数为空！");
			return INPUT;
		}
		
		try{
			request.setAttribute("column_item_list", 
				getManager().listItems( table.getId() ) );
		}catch(Exception e ){
			this.saveErrorMessage("查询异常:"+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}

}
