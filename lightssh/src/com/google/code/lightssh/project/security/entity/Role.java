package com.google.code.lightssh.project.security.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.model.Sequenceable;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.util.constant.AuditStatus;

/**
 * 系统角色
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SECURITY_ROLE")
public class Role implements Persistence<String>,Sequenceable,Cloneable{

	private static final long serialVersionUID = -2118356457067244665L;
	
	/**
	 * ID
	 */
	@Id
	@Column(name="ID")
	protected String id;
	
	/**
	 * 只读，用于系统初始化角色
	 */
	@Column( name="READONLY" )
	private Boolean readonly;
	
	/**
	 * 角色名称
	 */
	
	@Column( name="NAME",length=100 )
	private String name;
	
	/**
	 * 状态
	 */
	@Column( name="STATUS",length=50 )
	@Enumerated(EnumType.STRING)
	private AuditStatus status;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200  )
	private String description;
	
	/**
	 * 创建日期
	 */
	@Column(name="CREATED_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	protected Calendar createdTime;
	
	/**
	 * 角色对应权限
	 */
	@ManyToMany( fetch=FetchType.EAGER )
	@JoinTable( name="T_REF_ROLE_PERMISSION", 
			joinColumns=@JoinColumn( name="ROLE_ID"),
			inverseJoinColumns=@JoinColumn( name="PERMISSION_ID"))
	private Set<Permission> permissions;
	
	/**
	 * 状态是否有效
	 */
	public boolean isEffective(){
		return AuditStatus.EFFECTIVE.equals(this.status);
	}
	
	/**
	 * 是否标记删除
	 */
	public boolean isRemoved(){
		return AuditStatus.DELETE.equals(this.status);
	}
	
	public void addPermission( Permission p ){
		if( p == null )
			return;
		
		List<Permission> list = new ArrayList<Permission>( );
		list.add(p);
		addPermission(list);
	}
	
	/**
	 * 添加权限
	 */
	public void addPermission( Collection<Permission> colls ){
		if( this.permissions == null )
			this.permissions = new HashSet<Permission>();
		
		if( colls == null ) 
			return;
		
		for( Permission p:colls )
			this.permissions.add( p );
	}
	
	public void setPermissions( Collection<Permission> colls ){
		this.permissions = new HashSet<Permission>();
		
		if( colls == null ) 
			return;
		
		for( Permission p:colls )
			this.permissions.add( p );
	}
	
	/**
	 * get permission as string array
	 */
	public Collection<String> getPermissionsAsString(){
		if( this.permissions == null || this.permissions.isEmpty() )
			return null;
		
		List<String> result = new ArrayList<String>();
		for( Permission each: this.getPermissions() ){
			result.add( each.getToken() );
		}
		
		return result;
	}
	
	public void preInsert( ){
		if( StringUtils.isEmpty(id) )
			this.id = UUID.randomUUID().toString();
		this.createdTime = Calendar.getInstance();
	}
	
	public Role clone(){
		 try{
			 return (Role)super.clone();
		 }catch( CloneNotSupportedException e ){
			 return null;
		 }
	}
	
	@Override
	public String getSequenceKey() {
		return "SR";
	}

	@Override
	public int getSequenceLength() {
		return 5;
	}

	@Override
	public int getSequenceStep() {
		return 1;
	}
	
	public boolean isInsert( ){
		return StringUtil.clean(this.id) == null;
	}
	
	@Override
	public void postInsertFailure() {
		this.id = null;
	}

	@Override
	public String getIdentity() {
		return this.id;
	}
	
	//-- getters and setters --------------------------------------------------

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
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

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

}
