
package com.google.code.lightssh.project.security.web;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.google.code.lightssh.project.security.entity.Role;

/** 
 * @author YangXiaojin
 * @date 2013-2-26
 * @description：值转换
 */

public class RoleConverter extends StrutsTypeConverter{
	
	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map context, String[] values, Class toClass){
		if( values != null ){
			Role role = new Role();
			role.setId(values[0]);
			return role;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public String convertToString(Map context, Object o) {
		return o==null?null:o.toString();
	}

}
