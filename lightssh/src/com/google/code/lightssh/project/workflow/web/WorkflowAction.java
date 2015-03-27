package com.google.code.lightssh.project.workflow.web;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;

import org.activiti.engine.repository.Deployment;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.web.action.GenericAction;
import com.google.code.lightssh.project.workflow.service.WorkflowManager;

/**
 * workflow action
 * @author YangXiaojin
 *
 */
@SuppressWarnings("rawtypes")
@Component( "workflowAction" )
@Scope("prototype")
public class WorkflowAction extends GenericAction{

	private static final long serialVersionUID = 1793759911301918645L;
	
	@Resource(name="workflowManager")
	private WorkflowManager workflowManager;
	
	private ListPage<Deployment> deployment_page;
	
	private File upload;
	
	private String uploadContentType;
	
	private String uploadFileName;
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	public ListPage<Deployment> getDeployment_page() {
		return deployment_page;
	}

	public void setDeployment_page(ListPage<Deployment> deploymentPage) {
		deployment_page = deploymentPage;
	}

	/**
	 * 工作流部署
	 */
	public String deploymentList( ){
		deployment_page = workflowManager.listDeployment(deployment_page);
		
		return SUCCESS;
	}
	
	/**
	 * 部署
	 */
	public String deploy( ){
		if( this.isGet() )
			return INPUT;
		
		if( uploadFileName == null || upload == null ){
			this.addActionMessage("上传文件为空！");
			return INPUT;
		}
		
		String fileName = request.getParameter("deployment_file_name");
		if( !StringUtil.hasText(fileName) )
			fileName = uploadFileName;
			
		if( !fileName.endsWith(".bpmn20.xml") )
			fileName = fileName + ".bpmn20.xml";
		
		try{
			this.workflowManager.deploy(fileName,new FileInputStream(upload) );
		}catch(Exception e ){
			this.saveErrorMessage( e.getMessage() );
		}
		
		return SUCCESS;
	}
	
	/**
	 * 删除部署
	 */
	public String undeploy( ){
		try{
			this.workflowManager.undeploy(request.getParameter("deploymentId"));
		}catch(Exception e ){
			this.saveErrorMessage( e.getMessage() );
		}
		
		return SUCCESS;
	}

}
