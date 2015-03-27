package com.google.code.lightssh.project.message.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.common.model.Period;
import com.google.code.lightssh.project.security.entity.LoginAccount;

/**
 * 
 * @author Aspen
 * 
 */
@Entity
@Table(name="T_MSG_PUBLISH")
public class Publish extends UUIDModel{

	private static final long serialVersionUID = 959190640674216218L;
	
	/**
	 * 消息
	 */
	@ManyToOne
	@JoinColumn(name="MESSAGE_ID")
	private Message message;
	
	/**
	 * 用户
	 */
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private LoginAccount user;
	
	/**
	 * 阅读时间
	 */
	@Column(name="READ_TIME",columnDefinition="DATE")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar readTime;
	
	@Transient
	private Period period;
	
	@Transient
	private Boolean read;
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public LoginAccount getUser() {
		return user;
	}

	public void setUser(LoginAccount user) {
		this.user = user;
	}

	public Calendar getReadTime() {
		return readTime;
	}

	public void setReadTime(Calendar readTime) {
		this.readTime = readTime;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

}
