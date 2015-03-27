
package com.google.code.lightssh.project.party.util;

import com.google.code.lightssh.project.party.entity.Employee;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.service.EmployeeManager;
import com.google.code.lightssh.project.party.service.PartyManager;
import com.google.code.lightssh.project.util.SpringContextHelper;

/**
 * 用于页面显示
 */
public class PartyHelper {
	
	public static final String BEAN_NAME_PARTY_MANAGER = "partyManager";
	
	public static final String BEAN_NAME_EMPLOYEE_MANAGER = "employeeManager";
	
	/**
	 * 查询Party
	 */
	public static Party getParty(String code ){
		PartyManager partyManager = (PartyManager)SpringContextHelper.getBean( BEAN_NAME_PARTY_MANAGER );
		if( partyManager != null )
			return partyManager.get(code);
		
		return null;
	}
	
	/**
	 * 查询人事信息
	 */
	public static Employee getEmployee(String party_id ){
		EmployeeManager mgr = (EmployeeManager)SpringContextHelper.getBean( BEAN_NAME_EMPLOYEE_MANAGER );
		if( mgr != null )
			return mgr.getByPerson( party_id );
		
		return null;
	}

}
