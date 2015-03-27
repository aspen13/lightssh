package com.google.code.lightssh.project.geo.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.model.Sequenceable;
import com.google.code.lightssh.common.util.StringUtil;

/**
 * 地理边界
 * @author Aspen
 */
@Entity
@Table( name="T_GEO" )
public class GeographicBoundary implements Persistence<String>,Sequenceable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 系统编码
	 */
	@Id
	@Column( name="CODE",length=50 )
	protected String code;
	
	/**
	 * 名称
	 */
	@Column( name="NAME",length=100 )
	protected String name;
	
	/**
	 * 本地名
	 */
	@Column( name="LOCAL_NAME",length=100 )
	protected String localname;
	
	/**
	 * 缩写
	 */
	@Column( name="ABBREVIATION",length=50 )
	protected String abbreviation;
	
	/**
	 * 数字代码
	 */
	@Column( name="NUMERIC_CODE",length=50 )
	protected String numericCode;
	
	/**
	 * 类型
	 */
	@Column( name="TYPE",length=50 )
	@Enumerated( EnumType.STRING )
	protected GeoType type;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	protected String description;
	
	/**
	 * 是否可见
	 */
	@Column( name="ACTIVE" )
	protected Boolean active;
	
	/**
	 * 上级
	 */
	@Transient
	private GeographicBoundary parent;
	
	/**
	 * 下级
	 */
	@Transient
	private List<GeographicBoundary> children;
	
	/**
	 * 显示顺序
	 */
	@Transient
	private int sequence;
	
	/**
	 * 包含的类型
	 */
	@Transient
	private List<GeoType> _includes;
	
	/**
	 * 添加孩子结点
	 */
	public void addChild( GeographicBoundary child ){
		List<GeographicBoundary> list =new ArrayList<GeographicBoundary>();
		list.add( child );
		addChild( list );
	}
	
	/**
	 * 添加孩子结点
	 */
	public void addChild( Collection<GeographicBoundary> children ){
		if( children == null || children.isEmpty() )
			return;
		
		if( this.children == null )
			this.children = new ArrayList<GeographicBoundary>( );
		
		for( GeographicBoundary child:children ){
			child.parent = this;
			this.children.add(child);
		}
	}
	
	/**
	 * 是否活动的
	 */
	public boolean isActive(){
		return Boolean.TRUE.equals( this.active );
	}
	
	/**
	 * 字符转成 GeoType
	 */
	public static List<GeoType> parse(String types ){
		if( types == null )
			return null;
		
		String[] arr = types.split(",");
		List<GeoType> result = new ArrayList<GeoType>(arr.length);
		for( String item:arr ){
			try{
				result.add(GeoType.valueOf(item));
			}catch(Exception e ){}
		}
		
		return result;
	}
	
	@Override
	public String getIdentity() {
		return this.code;
	}

	@Override
	public boolean isInsert() {
		return StringUtil.clean( this.code ) == null;
	}

	@Override
	public void postInsertFailure() {
		//do nothing
	}

	@Override
	public void preInsert() {
		//do nothing
	}	

	@Override
	public String getSequenceKey() {
		return "GEO-" + this.type.name();
	}

	@Override
	public int getSequenceLength() {
		return 3;
	}

	@Override
	public int getSequenceStep() {
		return 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalname() {
		return localname;
	}

	public void setLocalname(String localname) {
		this.localname = localname;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public GeoType getType() {
		return type;
	}

	public void setType(GeoType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public GeographicBoundary getParent() {
		return parent;
	}

	public void setParent(GeographicBoundary parent) {
		this.parent = parent;
	}

	public List<GeographicBoundary> getChildren() {
		return children;
	}

	public void setChildren(List<GeographicBoundary> children) {
		this.children = children;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return this.code;
	}

	public List<GeoType> get_includes() {
		return _includes;
	}

	public void set_includes(List<GeoType> includes) {
		_includes = includes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeographicBoundary other = (GeographicBoundary) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
