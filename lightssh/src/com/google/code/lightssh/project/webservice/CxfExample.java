package com.google.code.lightssh.project.webservice;

import javax.jws.WebService;

/**
 * CXF示例
 * @author YangXiaojin
 *
 */
@WebService
public interface CxfExample {

	public String sayHello( String username );
	
	public boolean upload( BigData fu );
	
}
