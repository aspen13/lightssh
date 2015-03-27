package com.google.code.lightssh.project.contact.web;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.google.code.lightssh.project.contact.entity.ContactMechanism;
import com.google.code.lightssh.project.contact.entity.PostalAddress;
import com.google.code.lightssh.project.contact.entity.TelephoneNumber;

/**
 * 
 * @author YangXiaojin
 *
 */
public class ContactMechanismConverter  extends StrutsTypeConverter {
	private final String CONTACT_MECHANISM = "contactmechanism";
	private final String POSTAL_ADDRESS = "postaladdress";
	private final String TELEPHONE_NUMBER = "telephonenumber";
	
	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map context, String[] values, Class toClass){
		if( values == null || values[0] == null || CONTACT_MECHANISM.equals(values[0]))
			return new ContactMechanism();
		else if( POSTAL_ADDRESS.equals(values[0]) )
			return new PostalAddress();
		else if( TELEPHONE_NUMBER.equals(values[0]) )
			return new TelephoneNumber();
		
		return new ContactMechanism();
	}

	@SuppressWarnings("rawtypes")
	public String convertToString(Map context, Object o) {
		return o==null?null:o.toString();
	}

}

