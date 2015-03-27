package com.google.code.lightssh.project.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.base.UUIDModel;


/**
 * 导航菜单
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SECURITY_NAVIGATION")
public class Navigation extends UUIDModel{
	
	private static final long serialVersionUID = 8876574038535772527L;

	/**
	 * 顺序
	 */
	@Column( name="SEQUENCE")
	private Integer sequence;
	
	/**
	 * 名称
	 */
	@Column( name="NAME",length=100 )
	private String name;
	
	/**
	 * 是否菜单
	 */
	@Column( name="IS_MENU")
	private Boolean isMenu;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	private String description;
	
	/**
	 * 对应权限
	 */
	@OneToOne
	@JoinColumn( name="PERMISSION_ID" )
	private Permission permission;
	
	/**
	 * parent
	 */
	@ManyToOne
	@JoinColumn( name="PARENT_ID" )
	private Navigation parent;
	
	/**
	 * children
	 */
	@OneToMany( mappedBy="parent" )
	private Set<Navigation> children;
	
	/**
	 * 是否存在，用于过滤导航树
	 */
	@Transient
	private boolean exists;
	
	/**
	 * 是否菜单
	 */
	public boolean isMenu(){
		return Boolean.TRUE.equals(this.isMenu);
	}
	
	/**
	 * 标记存在
	 */
	protected void markExists( ){
		this.exists = true;
		if( parent != null && !parent.exists ) //!parent.exists 为性能优化
			parent.markExists();
	}
	
	/**
	 * 标记存在
	 * 如果当前Permission在集合pers中 
	 */
	protected void markExistsByPermission( Map<String,Permission> pers ){
		if( pers == null || pers.isEmpty() )
			return;
		
		if( permission != null && permission.getIdentity() != null ){
			Permission tmp = pers.get( permission.getIdentity() );
			if( tmp != null && permission.getIdentity().equals(tmp.getIdentity()))
				markExists();
		}
		
		if( this.children != null && !this.children.isEmpty() )
			for( Navigation item:this.children )
				item.markExistsByPermission( pers );
	}
	
	/**
	 * 移除不存在的导航结点
	 */
	protected Navigation trimNotExists( ){
		if( !this.exists )
			return null;
		
		if( this.children != null && !this.children.isEmpty() ){
			Set<Navigation> removed = new HashSet<Navigation>();
			for( Navigation item:this.children)
				if( !item.isExists() )
					removed.add(item);
			
			if( !removed.isEmpty() )
				this.children.removeAll(removed); //移除
			
			if( this.children != null && !this.children.isEmpty() ) //递归
				for( Navigation item:this.children )
					item.trimNotExists();
		}
		
		return this;
	}
	
	/**
	 * 过滤导航树
	 */
	public Navigation trimByPermission( Set<Permission> pers ){
		if( pers == null || pers.isEmpty() )
			return null;
		
		Map<String,Permission> map = new HashMap<String,Permission>();
		for( Permission item:pers )
			map.put(item.getIdentity(),item);
		
		markExistsByPermission( map ); //标记是否存在
		
		return trimNotExists();
	}
	
	/**
	 * sort by sequence
	 * @param colls
	 * @return
	 */
	public static Collection<Navigation> sort( Collection<Navigation> colls ){
		if( colls == null )
			return null;
		
		List<Navigation> list = new ArrayList<Navigation>(colls);
		Collections.sort( list, new Comparator<Navigation>(){
				public int compare(Navigation o1, Navigation o2) {
					if( o1==null || o2==null || 
							o1.getSequence()==null || o2.getSequence()==null )
						return 0;
					else 
						return o1.getSequence()>o2.getSequence()?1:-1;
				}
			}//end new Comparator
		);
		
		return list;
	}
	
	/**
	 * sorted children 
	 */
	public Collection<Navigation> sortedChildren( ){
		return sort( this.children );
	}
	
	/**
	 * add children
	 * @param child
	 */
	public void addChildren( Navigation child ){
		if( child == null )
			return;
		
		if( this.children == null )
			children = new HashSet<Navigation>();
		
		children.add(child);
		child.parent = this;
	}
	
	/**
	 * add children
	 * @param children
	 */
	public void addChildren( Collection<Navigation> children ){
		if( children == null || children.isEmpty() )
			return;
		
		for( Navigation child:children )
			addChildren( child );
	}

	public Navigation getParent() {
		return parent;
	}

	public void setParent(Navigation parent) {
		this.parent = parent;
	}

	public Set<Navigation> getChildren() {
		return children;
	}

	public void setChildren(Set<Navigation> children) {
		this.children = children;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public Boolean getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Boolean isMenu) {
		this.isMenu = isMenu;
	}

}
