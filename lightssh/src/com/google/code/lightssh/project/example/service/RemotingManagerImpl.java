package com.google.code.lightssh.project.example.service;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.party.entity.Person;

/**
 * 远程调用接口示例
 * @author YangXiaojin
 *
 */
@Component("remotingManager")
public class RemotingManagerImpl implements RemotingManager {
	
	/**
	 * for test
	 */
	public String sayHello( String name ){
		if( "exception".equals(name))
			throw new RuntimeException("异常测试");
		
		return "hello," + name + "!";
	}

	@Override
	public ListPage<Person> list(String param) {
		// TODO Auto-generated method stub
		return null;
	}

}
