package com.google.code.lightssh.project.column.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 动态列表名
 * @author Aspen
 * @date 2013-10-24
 * 
 */
@Entity
@Table(name="T_COLUMN_TABLE")
public class ColumnTable implements Persistence<String>{

	private static final long serialVersionUID = -3404787294774413773L;

	/**
	 * 主键
	 */
	@Id
	@Column(name="ID")
	private String id;
	
	/**
	 * 名称
	 */
	@Column(name="NAME")
	private String name;
	
	/**
	 * 备注
	 */
	@Column(name="DESCRIPTION")
	private String description;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String getIdentity() {
		return this.id;
	}

}
