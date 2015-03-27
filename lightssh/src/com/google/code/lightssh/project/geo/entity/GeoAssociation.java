package com.google.code.lightssh.project.geo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * GEO 关系
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_GEO_ASSO")
public class GeoAssociation extends UUIDModel{
	
	private static final long serialVersionUID = 1L;
	
	/** GEO关系类型 */
	public enum GeoAssoType {
		ADMINISTRATIVE("行政划分")
		,CUSTOMIZED("自定义划分");
		
		GeoAssoType( String value ){
			this.value = value;
		}
		
		private String value;
		
		public String getValue(){
			return this.value;
		}
		
		public String toString(){
			return this.value;
		}
		
	}

	/**
	 * 地理边界(整体)
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="TO_GEO_ID")
	private GeographicBoundary toGeo;
	
	/**
	 * 地理边界(部分)
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="FROM_GEO_ID")
	private GeographicBoundary fromGeo;
	
	/**
	 * 地理类型
	 */
	@Column( name="GEO_ASSO_TYPE")
	@Enumerated( EnumType.STRING )
	private GeoAssoType type;
	
	/**
	 * 序号
	 */
	@Column( name="SEQUENCE")
	private Integer sequence;
	
	public GeoAssociation(){
		
	}

	public GeoAssociation(GeographicBoundary toGeo, GeographicBoundary fromGeo) {
		super();
		this.toGeo = toGeo;
		this.fromGeo = fromGeo;
	}


	public GeographicBoundary getToGeo() {
		return toGeo;
	}

	public void setToGeo(GeographicBoundary toGeo) {
		this.toGeo = toGeo;
	}

	public GeographicBoundary getFromGeo() {
		return fromGeo;
	}

	public void setFromGeo(GeographicBoundary fromGeo) {
		this.fromGeo = fromGeo;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public GeoAssoType getType() {
		return type;
	}

	public void setType(GeoAssoType type) {
		this.type = type;
	}

}
