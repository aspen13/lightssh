package com.google.code.lightssh.project.uom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 币种
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_UOM_CURRENCY" )
public class Currency extends UnitOfMeasure{

	private static final long serialVersionUID = 3703439118241646789L;

	/**
	 * 符号
	 */
	@Column( name="SYMBOL",length=50 )
	private String symbol;
	
	/**
	 * 符号显示在右边
	 */
	@Column( name="SYMBOL_AT_RIGHT")
	private Boolean symbolAtRight;
	
	/**
	 * 精度
	 */
	@Column( name="PRECISION_")
	private Integer precision;
	
	/**
	 * 符号是否显示在右边
	 */
	public boolean isSymbolAtRight(){
		return Boolean.TRUE.equals(this.symbolAtRight);
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Boolean getSymbolAtRight() {
		return symbolAtRight;
	}

	public void setSymbolAtRight(Boolean symbolAtRight) {
		this.symbolAtRight = symbolAtRight;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

}
