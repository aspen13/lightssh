
package com.google.code.lightssh.project.util;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.common.util.ReflectionUtil;

/**
 * 对象比较
 * @author YangXiaojin
 *
 */
public class CompareUtil {
	
	public static final String STYLE_NEW = "new"; 
	public static final String STYLE_DELETE = "delete"; 
	public static final String STYLE_MODIFY = "modify"; 
	public static final String STYLE_EQUAL = "equal"; 
	public static final String STYLE_ERROR = "error"; 
	
	/**
	 * 比较值样式
	 */
	public static String style(Object originalBean,Object newBean,String field ){
		if( (originalBean == null && newBean == null) || StringUtils.isEmpty(field) )
			return STYLE_ERROR;
		
		if( originalBean == null )
			return STYLE_NEW;
		
		if( newBean == null )
			return STYLE_DELETE;
		
		Object originalValue = null,newValue = null;
		try {
			//originalValue = BeanUtils.getProperty(originalBean,field);
			//newValue = BeanUtils.getProperty(newBean,field);
			originalValue = ReflectionUtil.reflectGetValue(originalBean,field);
			newValue = ReflectionUtil.reflectGetValue(newBean,field);
		} catch (Exception e) {
			return STYLE_ERROR;
		} 
		
		if( (originalValue instanceof String) 
				&& StringUtils.isEmpty((String)originalValue))
			originalValue = null;
		
		if( (newValue instanceof String) 
				&& StringUtils.isEmpty((String)newValue))
			newValue = null;
		
		if( originalValue == null && newValue == null )
			return STYLE_EQUAL;
		
		if( originalValue != null && newValue != null 
				&& originalValue.equals(newValue) )
			return STYLE_EQUAL;
		
		if( originalValue == null && newValue != null )
			return STYLE_NEW;
		
		if( newValue == null && originalValue != null )
			return STYLE_DELETE;
		
		return STYLE_MODIFY;
	}
	public static String style(Object originalBean,Object newBean,String origfield,String newfield ){
		if( (originalBean == null && newBean == null) || (StringUtils.isEmpty(origfield)&&StringUtils.isEmpty(newfield) ))
			return STYLE_MODIFY;
		
		if( originalBean == null )
			return STYLE_NEW;
		
		if( newBean == null )
			return STYLE_DELETE;
		
		Object originalValue = null,newValue = null;
		try {
			//originalValue = BeanUtils.getProperty(originalBean,field);
			//newValue = BeanUtils.getProperty(newBean,field);
			originalValue = ReflectionUtil.reflectGetValue(originalBean,origfield);
			newValue = ReflectionUtil.reflectGetValue(newBean,newfield);
		} catch (Exception e) {
			return STYLE_MODIFY;
		} 
		
		if( (originalValue instanceof String) 
				&& StringUtils.isEmpty((String)originalValue))
			originalValue = null;
		
		if( (newValue instanceof String) 
				&& StringUtils.isEmpty((String)newValue))
			newValue = null;
		
		if( originalValue == null && newValue == null )
			return STYLE_EQUAL;
		
		if( originalValue != null && newValue != null 
				&& originalValue.equals(newValue) )
			return STYLE_EQUAL;
		
		if( originalValue == null && newValue != null )
			return STYLE_NEW;
		
		if( newValue == null && originalValue != null )
			return STYLE_DELETE;
		
		return STYLE_MODIFY;
	}
}
