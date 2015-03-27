package com.google.code.lightssh.project.message.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.project.message.entity.Catalog;
import com.google.code.lightssh.project.message.service.CatalogManager;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * 
 * @author Aspen
 * 
 */
@Component( "catalogAction" )
@Scope("prototype")
public class CatalogAction extends GenericAction<Catalog>{

	private static final long serialVersionUID = 5721161918254282795L;
	
	@Resource( name="catalogManager" )
	public void setManager(CatalogManager catalogManager) {
		super.manager = catalogManager;
	}

}
