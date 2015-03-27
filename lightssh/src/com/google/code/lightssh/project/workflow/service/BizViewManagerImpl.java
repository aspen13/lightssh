package com.google.code.lightssh.project.workflow.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.workflow.dao.BizViewDao;
import com.google.code.lightssh.project.workflow.entity.BizView;

/**
 * 
 * @author Aspen
 * @date 2013-10-28
 * 
 */
@Component("bizViewManager")
public class BizViewManagerImpl extends BaseManagerImpl<BizView> implements BizViewManager{

	private static final long serialVersionUID = 3287787826084879629L;
	
	@Resource(name="bizViewDao")
	public void setDao(BizViewDao dao){
		this.dao = dao;
	}
	
	public BizViewDao getDao( ){
		return (BizViewDao)this.dao;
	}

}
