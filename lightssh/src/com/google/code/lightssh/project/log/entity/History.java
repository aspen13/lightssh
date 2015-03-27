package com.google.code.lightssh.project.log.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.entity.base.BaseModel;
import com.google.code.lightssh.common.util.IoSerialUtil;
import com.google.code.lightssh.common.util.ReflectionUtil;
import com.google.code.lightssh.project.log.entity.Field.FieldType;

/**
 * 对象历史记录
 * @see IoSerialUtil
 * @author Aspen
 */
@Entity
@Table( name="T_LOG_HISTORY" )
public class History extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 创建时间
	 */
	@Column( name="CREATE_DATE")
	private Date createDate;
	
	/**
	 * 原始值
	 */
	@Column( name="ORIGINAL_OBJECT",columnDefinition="BLOB")
	private byte[] originalObject;
	
	/**
	 * 新值
	 */
	@Column( name="NEW_OBJECT",columnDefinition="BLOB")
	private byte[] newObject;
	
	/**
	 * 访问日志
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ACCESS_ID")
	private Access access;
		
	public History() {
		super();
	}
	
	public History(Access access,byte[] originalObject,byte[] newObject) {
		this.access = access;
		this.originalObject = originalObject;
		this.newObject = newObject;		
	}
	
	public History(Access access,Persistence<?> originalModel,Persistence<?> newModel) {
		this(access,IoSerialUtil.serialize(originalModel),IoSerialUtil.serialize(newModel));
	}
	
	public void preInsert( ){
		super.preInsert();
		if( access != null && access.getId()==null)
			access.preInsert();
	}


	/**
	 * 是否创建
	 */
	public boolean isCreated( ){
		return IoSerialUtil.deserialize(originalObject)==null 
			&& IoSerialUtil.deserialize(newObject)!= null;
	}
	
	/**
	 * 是否删除
	 */
	public boolean isDeleted( ){
		return IoSerialUtil.deserialize(originalObject)!=null 
			&& IoSerialUtil.deserialize(newObject)== null;
	}
	
	/**
	 * 是否更新
	 */
	public boolean isUpdated( ){
		return originalObject!=null && newObject!=null;
	}
	
	/**
	 * 用于页面对比显示的对象属性
	 * @return
	 */
	public Collection<Field> toFileds( ){
		Serializable originalObj = IoSerialUtil.deserialize( getOriginalObject() );
		Serializable newObj = IoSerialUtil.deserialize( getNewObject() );
		if( originalObj == null && newObj == null )
			return null;
		
		Map<String,String> opMap = ReflectionUtil.propertiesByGetterAndSetter(originalObj); 
		Map<String,String> npMap = ReflectionUtil.propertiesByGetterAndSetter(newObj);
		
		Map<String,String> allMap = new HashMap<String,String>();
		if( opMap != null )
			allMap.putAll( opMap );
		if( npMap != null )
			allMap.putAll( npMap );
		
		if( allMap.isEmpty() )
			return null;
		
		List<Field> fields = new ArrayList<Field>();
		for( String p:allMap.keySet() ){
			Object ov = null;
			if( originalObj != null && opMap.get(p) != null )
				ov = ReflectionUtil.reflectGetValue( originalObj ,p );
			
			Object nv = null;
			if( newObj != null && npMap.get(p) != null )
				nv = ReflectionUtil.reflectGetValue( newObj ,p );
			
			FieldType type = FieldType.NO_CHANGE;
			if( originalObj != null && newObj != null ){
				if( opMap.get(p) == null )
					type = FieldType.ADD_FIELD;
				if( npMap.get(p) == null )
					type = FieldType.REMOVE_FIELD;
			}
			
			Field field = new Field(type,p,null,ov,nv);
			fields.add( field );
		}
		return fields;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public byte[] getOriginalObject() {
		return originalObject;
	}

	public void setOriginalObject(byte[] originalObject) {
		this.originalObject = originalObject;
	}

	public byte[] getNewObject() {
		return newObject;
	}

	public void setNewObject(byte[] newObject) {
		this.newObject = newObject;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}
	
	
}
