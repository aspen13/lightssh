package com.google.code.lightssh.project.sequence.entity;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.google.code.lightssh.common.entity.Persistence;

/**
 * 序号
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_SEQUENCE")
public class Sequence implements Persistence<String>{

	private static final long serialVersionUID = -5602318217016522653L;
	
	/**
	 * Key
	 */
	@Id
	@Column( name="ID")
	private String key;
	
	/**
	 * 最后流水号
	 */
	@Column( name="LAST_NUMBER" )
	private Integer lastNumber;
	
	/**
	 * 版本号
	 */
	@Version
	@Column( name="VERSION" )
	private Integer version;
	
	public Sequence( ){
		
	}
	
	public Sequence( String key ){
		this.key = key;
		this.lastNumber = 0;
	}
	
	/**
	 * 增涨LastNumber
	 */
	public void incLastNumber( ){
		incLastNumber(1);
	}
	
	/**
	 * 增涨LastNumber
	 */
	public void incLastNumber( int step ){
		if( lastNumber == null )
			this.lastNumber = 0;
		step = Math.max(1, step );
		this.lastNumber+=step;
	}
	
	/**
	 * 格式化数据
	 */
	public String getFormatLastNumber( int serialLen ){
		int len = Math.max(1, serialLen );
		String pattern = "";
		for( int i=0;i<len;i++ )
			pattern += "0";
		
		DecimalFormat df = new DecimalFormat( pattern );
		return df.format( getLastNumber());
	}
	
	public void preInsert( ){
		//do nothing
	}
	
	public void postInsertFailure( ){
		//do nothing
	}
	
	public boolean isInsert( ){
		return this.key == null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getLastNumber() {
		return lastNumber;
	}

	public void setLastNumber(Integer lastNumber) {
		this.lastNumber = lastNumber;
	}

	@Override
	public String getIdentity() {
		return this.key;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
