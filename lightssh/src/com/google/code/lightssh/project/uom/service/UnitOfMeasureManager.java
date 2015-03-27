package com.google.code.lightssh.project.uom.service;

import java.util.List;

import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.project.uom.entity.UnitOfMeasure;
import com.google.code.lightssh.project.uom.entity.UnitOfMeasure.UomType;

/**
 * 
 * @author YangXiaojin
 *
 */
public interface UnitOfMeasureManager extends BaseManager<UnitOfMeasure>{
	
	/**
	 * 根据类型查询计量单位
	 * @param type 计量单位类型
	 */
	public List<UnitOfMeasure> list( UomType type );
	
	/**
	 * 根据类型查询计量单位(活动的)
	 * @param type 计量单位类型
	 */
	public List<UnitOfMeasure> listActivity( UomType type );
	
	/**
	 * 反转改变Active属性
	 */
	public void toggleActive( UnitOfMeasure uom );

}
