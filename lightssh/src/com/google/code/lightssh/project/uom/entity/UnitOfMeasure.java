package com.google.code.lightssh.project.uom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.Persistence;
import com.google.code.lightssh.common.util.StringUtil;

/**
 * 计量单位
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_UOM_BASE" )
@Inheritance( strategy=InheritanceType.JOINED )
public class UnitOfMeasure implements Persistence<String>{

	private static final long serialVersionUID = 1149518388098215682L;

	/**
	 * 单位类型
	 */
	public enum UomType{
		AREA("面积")	
		,CURRENCY("币种")
		,DATA_SPEED("数据传输速度")
		,DATA_SIZE("数据大小")
		,ENERGY("能量")
		,LENGTH("长度")	
		,TEMPERATURE("温度")
		,TIME_OR_FREQUENCY("时间或频率")	//'Time/Frequency'
		,VOLUME_DRY("DRY VOLUME")
		,LIQUID_VOLUME("液体体积")
		,WEIGHT("重量")
		,VOLUME_LIQ("VOLUME LIQ")
		,OTHER("其它");
		
		UomType( String value ){
			this.value = value;
		}
		
		private String value;
		
		public String getValue(){
			return this.value;
		}
		
		public String toString(){
			return this.value;
		}
	}
	
	/**
	 * 系统编号
	 */
	@Id
	@Column( name="CODE",length=50 )
	protected String code;
	
	/**
	 * 国际代码
	 */
	@Column( name="ISO_CODE",length=50 )
	protected String isoCode;	
		
	/**
	 * 缩写
	 */
	@Column( name="ABBREVIATION",length=20 )
	protected String abbreviation;
	
	/**
	 * 经常使用的
	 */
	@Column( name="ACTIVE")
	protected Boolean active;
	
	/**
	 * 描述
	 */
	@Column( name="DESCRIPTION",length=200 )
	protected String description;
	
	/**
	 * 单位类型
	 */
	@Column( name="UOM_TYPE",length=50 )
	@Enumerated(value=EnumType.STRING)
	protected UomType type;
	
	/**
	 * 是否经常使用的
	 */
	public boolean isActive( ){
		return Boolean.TRUE.equals( this.active );
	}
	
	@Override
	public String getIdentity() {
		return this.code;
	}

	@Override
	public boolean isInsert() {
		return StringUtil.clean( this.code ) == null;
	}

	@Override
	public void postInsertFailure() {
		//do nothing
	}

	@Override
	public void preInsert() {
		//do nothing
	}	

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public UomType getType() {
		return type;
	}

	public void setType(UomType type) {
		this.type = type;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
