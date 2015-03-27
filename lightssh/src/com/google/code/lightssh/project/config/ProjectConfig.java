package com.google.code.lightssh.project.config;

import com.google.code.lightssh.common.config.SystemConfig;

/**
 * ftportal 参数配置
 * @author YangXiaojin
 *
 */
public class ProjectConfig extends SystemConfig{
	
	private static final long serialVersionUID = -6615457240004924005L;
	
	/** 系统名称  */
	public static final String PROJECT_NAME = "project";
	
	public ProjectConfig( ){
		super();
	}

	@Override
	public String getProjectName() {
		return PROJECT_NAME;
	}
	
}
