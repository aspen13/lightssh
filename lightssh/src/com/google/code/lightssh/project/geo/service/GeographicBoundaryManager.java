package com.google.code.lightssh.project.geo.service;

import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.geo.entity.GeoType;
import com.google.code.lightssh.project.geo.entity.GeographicBoundary;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface GeographicBoundaryManager extends BaseManager<GeographicBoundary>{
	
	/**
	 * 查询地理区域 
	 * @param type 地理区域类型
	 * @param active 是否活动的
	 */
	public List<GeographicBoundary> list(GeoType type,Boolean active);
	
	/**
	 * 根据类型查询GEO
	 * @param type 地理边界类型
	 */
	public List<GeographicBoundary> list( GeoType type );
	
	/**
	 * 根据类型查询GEO(活动的)
	 * @param type 地理边界类型
	 */
	public List<GeographicBoundary> listActivity( GeoType type );
	
	/**
	 * 反转改变Active属性
	 */
	public void toggleActive( GeographicBoundary geo );
	
	/**
	 * 查询Geo关联的下级‘地理区域’
	 */
	public List<GeographicBoundary> listGeoByParent( GeographicBoundary geo );

}
