package com.google.code.lightssh.project.party.web;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.google.code.lightssh.project.party.entity.Organization;
import com.google.code.lightssh.project.party.entity.Person;

/**
 * Party 类型转换 
 * Party 由于是抽象类，因此需要实例化成具体子类
 * @author YangXiaojin
 *
 */
public class PartyConverter extends StrutsTypeConverter {
	//private final String PERSON = "person";
	private final String ORG = "organization";
	
	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map context, String[] values, Class toClass){
		if( values == null || values[0] == null || values[0].equals(ORG))
			return new Organization();
		
		return new Person();
	}

	@SuppressWarnings({ "rawtypes" })
	public String convertToString(Map context, Object o) {
		return o==null?null:o.toString();
	}

}
