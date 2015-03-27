package com.google.code.lightssh.project.tree.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * Tree 
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_TREE")
public class Tree extends UUIDModel{

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@Column( name="NAME",unique=true,length=100)
	protected String name;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200)
	protected String description;
	
	/**
	 * 所对应结点
	 */
	@OneToOne( cascade=CascadeType.ALL )
	@JoinColumn( name="NODE_ID",nullable=false )
	protected Node root;
	
	/**
	 * 最大层数
	 */
	@Column( name="MAX_LAYER")
	private Integer maxLayer;
	
	/**
	 * 最多孩子数
	 */
	@Column( name="MAX_CHILDREN")
	private Integer maxChildren;
	
	public void preInsert( ){
		super.preInsert();
		this.root.setId( UUID.randomUUID().toString() );
	}
	
	/**
	 * get tree layer 
	 */
	@Transient
	public int getLayer( ){
		if( root == null ) 
			return 0;
		
		return root.getChildrenLayer( );
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

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public Integer getMaxLayer() {
		return maxLayer;
	}

	public void setMaxLayer(Integer maxLayer) {
		this.maxLayer = maxLayer;
	}

	public Integer getMaxChildren() {
		return maxChildren;
	}

	public void setMaxChildren(Integer maxChildren) {
		this.maxChildren = maxChildren;
	}
	
}
