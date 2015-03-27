package com.google.code.lightssh.project.web.action;

import javax.annotation.Resource;

import com.google.code.lightssh.common.config.SystemConfig;
import com.google.code.lightssh.common.report.jr.SingleCaptionEngine;
import com.google.code.lightssh.common.web.action.NonTemplateReportAction;

/**
 * 报表
 * 绕开 Linux Weblogic ServeletContext.getRealPath 为 NULL 问题
 * @author YangXiaojin
 *
 * @param <T>
 */
public abstract class DefaultReportAction<T> extends NonTemplateReportAction<T>{

	private static final long serialVersionUID = 1L;
	
	/** 报表文件路径  */
	public final static String DEFAULT_JRXML_FILE_DIRECTORY = "report.file.path";
	
	@Resource(name="systemConfig")
	protected SystemConfig systemConfig;

	public DefaultReportAction(){
		super.jasperEngine = new SingleCaptionEngine();
	}
	
	/**
	 * 加载报表目录
	 */
	protected void loadReportDir(){
		super.jasperEngine.setDefaultDirectory(
				systemConfig.getProperty( DEFAULT_JRXML_FILE_DIRECTORY ));
	}
	
	public String report( ){
		loadReportDir(); 
		return super.report();
	}

}
