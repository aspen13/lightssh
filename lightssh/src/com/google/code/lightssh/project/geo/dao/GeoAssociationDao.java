package com.google.code.lightssh.project.geo.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.geo.entity.GeoAssociation;
import com.google.code.lightssh.project.geo.entity.GeographicBoundary;

/**
 * GeoAssociation Dao
 * @author YangXiaojin
 *
 */
public interface GeoAssociationDao extends Dao<GeoAssociation>{
	
	/**
	 * 查询Geo关联的下级‘地理区域’
	 */
	public ListPage<GeographicBoundary> listGeoByParent(ListPage<GeographicBoundary> page,GeographicBoundary geo );

}
