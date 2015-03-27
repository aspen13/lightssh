package com.google.code.lightssh.project.tree.entity;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 结点
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_TREE_NODE")
public class Node extends UUIDModel{

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@Column( name="NAME",length=50 )
	protected String name;
		
	/**
	 * 显示顺序
	 */
	@Column( name="SEQUENCE")
	protected Integer sequence;
	
	/**
	 * 父结点
	 */
	@ManyToOne
	@JoinColumn( name="PARENT_ID")
	protected Node parent;
	
	/**
	 * 孩子结点
	 */
	@OneToMany( mappedBy="parent")
	protected Set<Node> children;
	
	public Node(){
		
	}
	
	public Node( String name,String description ){
		this.name = name;
		this.description = description;
	}
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200)
	protected String description;
	
	//-- util methods ----------------------------------------------------------
	
	/**
	 * add child
	 */
	public void addChild( Node node ){
		if( children == null ){
			children = new LinkedHashSet<Node>( );
		}
		
		if( children.add( node ) ){
			node.parent = this;
		}
	}
	
	/**
	 * add children
	 * @param collection
	 */
	public void addChild( Collection<Node> children ){
		if( children == null ) 
			return;
		
		for( Node child: children ){
			addChild( child );
		}
	}
	
	/**
	 * current layer 
	 * @return
	 */
	@Transient
	public int getCurrentLayer( ){
		if( parent == null )
			return 1;
		
		return parent.getCurrentLayer() + 1;
	}
	
	/**
	 * children layer
	 * @return
	 */
	@Transient
	public int getChildrenLayer( ){
		if( children == null || children.isEmpty() )
			return 0;
		
		int result = 0;
		for( Node child: children ){
			int current = child.getChildrenLayer() + 1;
			result = current>result?current:result;
		}
		
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Set<Node> getChildren() {
		return children;
	}

	public void setChildren(Set<Node> children) {
		this.children = children;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
}
