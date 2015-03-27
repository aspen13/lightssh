package com.google.code.lightssh.project.geo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.geo.dao.GeoAssociationDao;
import com.google.code.lightssh.project.geo.entity.GeoType;
import com.google.code.lightssh.project.geo.entity.GeographicBoundary;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("geoManager")
public class GeographicBoundaryManagerImpl extends BaseManagerImpl<GeographicBoundary>
implements GeographicBoundaryManager{
	
	private static final long serialVersionUID = 1278038793052299840L;
	
	@Resource(name="geoAssociationDao")
	private GeoAssociationDao geoAssociationDao;
	
	@Resource(name="geoDao")
	public void setDao(Dao<GeographicBoundary> dao) {
		this.dao = dao;
	}

	public void setGeoAssociationDao(GeoAssociationDao geoAssociationDao) {
		this.geoAssociationDao = geoAssociationDao;
	}
	
	public List<GeographicBoundary> list(GeoType type,Boolean active) {
		ListPage<GeographicBoundary> page = new ListPage<GeographicBoundary>( Integer.MAX_VALUE );
		GeographicBoundary geo = new GeographicBoundary( );
		geo.setType(type);
		geo.setActive( active );
		page = super.dao.list(page, geo);
		
		return page.getList();
	}

	@Override
	public List<GeographicBoundary> list(GeoType type) {
		return this.list(type,null);
	}

	@Override
	public List<GeographicBoundary> listActivity(GeoType type) {
		return list( type, Boolean.TRUE );
	}

	@Override
	public void toggleActive(GeographicBoundary geo) {
		if( geo == null || geo.getIdentity() == null )
			return;
		
		GeographicBoundary db_geo = this.get( geo );
		if( db_geo != null ){
			db_geo.setActive( !db_geo.isActive() );
			dao.update( db_geo );
		}
	}

	@Override
	public List<GeographicBoundary> listGeoByParent(GeographicBoundary geo) {
		ListPage<GeographicBoundary> page = new ListPage<GeographicBoundary>( Integer.MAX_VALUE );
		page.addAscending("sequence");
		
		return geoAssociationDao.listGeoByParent(page, geo).getList();
	}

}
