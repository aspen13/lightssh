package com.google.code.lightssh.project.column.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 
 * @author Aspen
 * @date 2013-10-24
 * 
 */
@Entity
@Table(name="T_COLUMN_ITEM")
public class ColumnItem extends UUIDModel{

	private static final long serialVersionUID = -4886005824023896636L;
	
	/**
	 * 定制化列表
	 */
	@ManyToOne
	@JoinColumn(name="COLUMN_TABLE_ID",nullable=false)
	private ColumnTable table;
	
	/**
	 * 名称
	 */
	@Column(name="NAME",length=255,nullable=false)
	private String name;

	/**
	 * 属性标题
	 */
	@Column(name="TITLE",length=20,nullable=false)
	private String title;
	
	/**
	 * 顺序
	 */
	@Column(name="SEQUENCE",nullable=false)
	private Integer sequence;
	
	/**
	 * 是否可见
	 */
	@Column(name="VISIBLE",nullable=false)
	private Boolean visible;
	
	/**
	 * 是否不可变(不能删除)
	 */
	@Column(name="IMMUTABLE",nullable=false)
	private Boolean immutable;

	/**
	 * 备注
	 */
	@Column(name="DESCRIPTION",length=200)
	private String description;
	
	/**
	 * 用于标签显示
	 */
	public String getDisplayTitle() {
		return title;
	} 

	public ColumnTable getTable() {
		return table;
	}

	public void setTable(ColumnTable table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getImmutable() {
		return immutable;
	}

	public void setImmutable(Boolean immutable) {
		this.immutable = immutable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
