package com.google.code.lightssh.project.column.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.column.dao.ColumnCustomizationDao;
import com.google.code.lightssh.project.column.entity.ColumnCustomization;

/**
 * 
 * @author Aspen
 * @date 2013-10-31
 * 
 */
@Component("columnCustomizationManager")
public class ColumnCustomizationManagerImpl extends BaseManagerImpl<ColumnCustomization>
	implements ColumnCustomizationManager{

	private static final long serialVersionUID = 8644633266227803256L;
	
	@Resource(name="columnCustomizationDao")
	public void setDao(ColumnCustomizationDao dao){
		this.dao = dao;
	}
	
	public ColumnCustomizationDao getDao(){
		return (ColumnCustomizationDao)this.dao;
	}
	
}
