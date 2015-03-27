package com.google.code.lightssh.project.scheduler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.model.Nameable;

/** 
 * @author YangXiaojin
 * @date 2012-12-14
 * @description：TODO
 */
@Entity
@Table(name="T_SCHEDULER_TYPE")
public class SchedulerType implements Persistence<String>,Nameable{

	private static final long serialVersionUID = -1852780860102057978L;
	
	/**
	 * id
	 */
	@Id
	@Column(name="ID",length=50)
	protected String id;
	
	/**
	 * 名称
	 */
	@Column(name="NAME",length=100)
	private String name;
	
	/**
	 * 描述
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;

	@Override
	public String getIdentity() {
		return id;
	}

	public SchedulerType() {
		super();
	}

	public SchedulerType(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public boolean isInsert() {
		return StringUtils.isEmpty(id);
	}

	@Override
	public void postInsertFailure() {
		this.id = null;
	}

	@Override
	public void preInsert() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
