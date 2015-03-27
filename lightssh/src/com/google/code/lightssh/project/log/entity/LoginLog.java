package com.google.code.lightssh.project.log.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.common.model.Period;

/**
 * 登录日志
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_LOG_LOGIN" )
public class LoginLog extends UUIDModel{

	private static final long serialVersionUID = 2239712822433039897L;

	/**
	 * 操作员
	 */
	@Column( name="OPERATOR")
	private String operator;

	/**
	 * ip地址
	 */
	@Column( name="IP")
	private String ip;
		
	/**
	 * description
	 */
	@Column(name="DESCRIPTION")
	private String description;
	
	@Transient
	private Period _period;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Period get_period() {
		return _period;
	}

	public void set_period(Period period) {
		_period = period;
	}

}
