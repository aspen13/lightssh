package com.google.code.lightssh.project.column.util;

import java.util.ArrayList;
import java.util.List;

import com.google.code.lightssh.common.web.tag.table.model.CustomizeColumn;
import com.google.code.lightssh.common.web.tag.table.model.IColumn;

/**
 * 定制化列
 * @author Aspen
 * @date 2013-10-30
 * 
 */
public class CustomizeColumnHelper {
	
	/**
	 * 获取定制化列
	 * @param tableId 表标识符
	 * @param username 操作用户名
	 */
	public static List<IColumn> getDynamicCols(String tableId,String username){
		List<IColumn> cols = new ArrayList<IColumn>();
		cols.add( new CustomizeColumn("enabled","8") );
		cols.add( new CustomizeColumn("partyAffiliation","2") );
		cols.add( new CustomizeColumn("maritalStatus","1") );
		cols.add( new CustomizeColumn("credentialsType","3") );
		//cols.add( new CustomizeColumn("","") );
		return cols;
	}

}
