package com.google.code.lightssh.project.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 业务数据查询
 * @author Aspen
 * @date 2013-10-28
 * 
 */
@Entity
@Table(name="T_FLOW_BIZ_VIEW")
public class BizView implements Persistence<String>{
	
	private static final long serialVersionUID = 5225952798587436518L;

	/**
	 * 流程定义Key
	 */
	@Id
	@Column(name="ACT_PROC_DEF_KEY",length=255,nullable=false)
	private String actProcDefKey;
	
	/**
	 * 命令空间
	 */
	@Column(name="NAMESPACE",length=255,nullable=false)
	private String namespace;
	
	/**
	 * Action名称
	 */
	@Column(name="ACTION",length=100,nullable=false)
	private String action;
	
	/**
	 * Action参数名称
	 */
	@Column(name="PARAM_NAME",length=50,nullable=false)
	private String paramName;
	
	/**
	 * 描述
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;

	@Override
	public boolean isInsert() {
		return this.actProcDefKey == null;
	}

	@Override
	public void postInsertFailure() {
		this.actProcDefKey = null;
	}

	@Override
	public void preInsert() {
	}

	@Override
	public String getIdentity() {
		return this.actProcDefKey;
	}

	public String getActProcDefKey() {
		return actProcDefKey;
	}

	public void setActProcDefKey(String actProcDefKey) {
		this.actProcDefKey = actProcDefKey;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
