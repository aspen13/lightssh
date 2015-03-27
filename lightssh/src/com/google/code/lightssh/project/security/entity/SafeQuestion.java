package com.google.code.lightssh.project.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 安全问题及答案
 *
 */
@Entity
@Table(name="T_SECURITY_SAFEQUESTION")
public class SafeQuestion extends UUIDModel{
	
	private static final long serialVersionUID = 8853813263604005323L;

	/**
	 * 所属组织或人员
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="ACCOUNT_ID")
	private LoginAccount account;
	
	/**
	 * 安全问题
	 */
	@Column( name="QUESTION",length=100 )
	private String question;
	
	/**
	 * 安全问题答案
	 */
	@Column( name="ANSWER",length=200 )
	private String safeAnswer;

	public LoginAccount getAccount() {
		return account;
	}

	public void setAccount(LoginAccount account) {
		this.account = account;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSafeAnswer() {
		return safeAnswer;
	}

	public void setSafeAnswer(String safeAnswer) {
		this.safeAnswer = safeAnswer;
	}

}
