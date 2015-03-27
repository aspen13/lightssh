package com.google.code.lightssh.project.party.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 组织
 * @author Aspen
 *
 */
@Entity
@Table( name="T_PARTY_ORGANIZATION" )
@PrimaryKeyJoinColumn(name="id") 
@AttributeOverrides(@AttributeOverride(name="name",column=@Column(unique=true)))
/**
 * hibernate 实现@AttributeOverrides存在问题
 * https://hibernate.onjira.com/browse/HHH-2619
 */
public class Organization extends Party {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 序号
	 */
	@Column( name="SEQUENCE")
	private Integer sequence;
	
	/**
	 * 上级
	 */
	@Transient
	private Organization parent;
	
	/**
	 * 下级
	 */
	@Transient
	private Set<Organization> children;
	
	public void preInsert( ){
		//do nothing
	}
		
	public Organization( String name ) {
		setName(name);
	}
	
	public Organization() {
		super();
	}
	
	@Override
	public String getSequenceKey() {
		return "ORG";
	}
	
	/**
	 * 全名
	 */
	public String getFullName( ){
		return this.getName();
	}
	
	/**
	 * 添加孩子结点
	 */
	public void addChild( Organization child ){
		List<Organization> list =new ArrayList<Organization>();
		list.add( child );
		addChild( list );
	}

	/**
	 * 添加孩子结点
	 */
	public void addChild( Collection<Organization> children ){
		if( children == null || children.isEmpty() )
			return;
		
		if( this.children == null )
			this.children = new HashSet<Organization>( );
		
		for( Organization child:children ){
			this.children.add(child);
			child.parent = this;
		}
	}
	
	/**
	 * 排序
	 */
	public static List<Organization> orderBySequence(List<Organization> list) {
		Collections.sort(list, new Comparator<Organization>() {
			public int compare(Organization o1, Organization o2) {
				int s1 = (o1==null||o1.getSequence()==null?0:o1.getSequence());
				int s2 = (o2==null||o2.getSequence()==null?0:o2.getSequence());
				return s1==s2?0:(s1>s2?1:-1);
			}
		});

		return list;
	}
	
	public List<Organization> getSortedChildren( ){
		if( this.children == null || this.children.isEmpty() )
			return null;
		
		List<Organization> list = new ArrayList<Organization>( this.children );
		orderBySequence( list );
		
		return list;
	}
	
	public Organization clone(){
		 return (Organization)super.clone();
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public Set<Organization> getChildren() {
		return children;
	}

	public void setChildren(Set<Organization> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

}
