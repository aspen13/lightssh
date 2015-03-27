package com.google.code.lightssh.project.example.service;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.party.entity.Person;

/**
 * 远程调用接口示例
 * @author YangXiaojin
 *
 */
public interface RemotingManager {
	
	/**
	 * for test
	 */
	public String sayHello( String name );
	
	/**
	 */
	public ListPage<Person> list( String param );

}
