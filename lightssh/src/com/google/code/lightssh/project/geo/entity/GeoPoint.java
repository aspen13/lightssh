package com.google.code.lightssh.project.geo.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.project.uom.entity.UnitOfMeasure;

/**
 * 三维位置点
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_GEO_POINT")
public class GeoPoint extends UUIDModel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 经度
	 */
	@Column( name="LONGITUDE" )
	private BigDecimal longitude;
	
	/**
	 * 纬度
	 */
	@Column( name="LATITUDE" )
	private BigDecimal latitude;
	
	/**
	 * 海拔
	 */
	@Column( name="ELEVATION" )
	private BigDecimal elevation;
	
	/**
	 * 海拔单位
	 */
	@ManyToOne
	@JoinColumn( name="ELEVATION_UOM_ID" )
	private UnitOfMeasure elevationUom;

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getElevation() {
		return elevation;
	}

	public void setElevation(BigDecimal elevation) {
		this.elevation = elevation;
	}

	public UnitOfMeasure getElevationUom() {
		return elevationUom;
	}

	public void setElevationUom(UnitOfMeasure elevationUom) {
		this.elevationUom = elevationUom;
	}

}
