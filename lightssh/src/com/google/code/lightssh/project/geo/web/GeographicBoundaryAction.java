package com.google.code.lightssh.project.geo.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.common.web.action.CrudAction;
import com.google.code.lightssh.project.geo.entity.GeoType;
import com.google.code.lightssh.project.geo.entity.GeographicBoundary;
import com.google.code.lightssh.project.geo.service.GeographicBoundaryManager;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("geoAction")
@Scope("prototype")
public class GeographicBoundaryAction extends CrudAction<GeographicBoundary>{

	private static final long serialVersionUID = 8080328735431672890L;
	
	private GeographicBoundary geo;
	
	private List<GeographicBoundary> list;
	
	@Resource(name="geoManager")
	public void setGeoManager( GeographicBoundaryManager geoManager ){
		super.manager = geoManager;
	}
	
	public GeographicBoundaryManager getManager(){
		return (GeographicBoundaryManager)super.manager;
	}

	public GeographicBoundary getGeo() {
		geo = super.model;
		return geo;
	}

	public void setGeo(GeographicBoundary geo) {
		this.geo = geo;
		super.model = this.geo;
	}

	@JSON( name="list")
	public List<GeographicBoundary> getList() {
		return list;
	}

	public void setList(List<GeographicBoundary> list) {
		this.list = list;
	}

	public String toggle( ){
		try{
			getManager().toggleActive(geo);
		}catch( Exception e ){
			this.saveErrorMessage(e.getMessage());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查询国家
	 */
	public String listcountry( ){
		Boolean active = null;
		if(geo != null )
			active = geo.getActive();
		
		list = getManager().list( GeoType.COUNTRY,active );
		
		return SUCCESS;
	}
	
	/**
	 * 查询下级地理区域
	 */
	public String listchildren( ){
		if( geo != null && StringUtil.hasText( geo.getCode() ) ){
			geo.set_includes(GeographicBoundary.parse( 
					request.getParameter("types") )) ;
			
			list = this.getManager().listGeoByParent(geo);
		}
		
		return SUCCESS;
	}
	
	
}
