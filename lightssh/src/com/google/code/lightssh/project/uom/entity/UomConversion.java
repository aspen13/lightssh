package com.google.code.lightssh.project.uom.entity;

/**
 * 单位转换
 * @author YangXiaojin
 *
 */
public class UomConversion {
	
	/**
	 * 计量单位
	 */
	private UnitOfMeasure uom;
	
	/**
	 * 计量单位
	 */
	private UnitOfMeasure to;
	
	/**
	 * 转换因子
	 */
	private Double factor;
	
	public double getConversionFactor( ){
		if( factor == null ) 
			return 1d;
		return factor.doubleValue();
	}

	public UnitOfMeasure getUom() {
		return uom;
	}

	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}

	public UnitOfMeasure getTo() {
		return to;
	}

	public void setTo(UnitOfMeasure to) {
		this.to = to;
	}

	public Double getFactor() {
		return factor;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

}
