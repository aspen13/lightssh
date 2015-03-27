package com.google.code.lightssh.project.party.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 员工信息
 * @author Aspen
 * @date 2013-9-1
 * 
 */
@Entity
@Table(name="T_PARTY_EMPLOYEE")
public class Employee extends UUIDModel{
	
	private static final long serialVersionUID = 8367554564777019243L;

	/**
	 * 工作性质
	 */
	public enum Type{
		REGULAR("正式")			
		,PROBATION("试用")			
		,INTERN("实习")			
		,TEMPORARY("临时");		
		
		private String value;
		
		Type( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString(){
			return this.value;
		}
	}
	
	/**
	 * 人事状态
	 */
	public enum Status{
		EFFECTIVE("有效")			
		,FIRED("离职");		
		
		private String value;
		
		Status( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString(){
			return this.value;
		}
	}
	
	/**
	 * 人员信息
	 */
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	private Person person;
	
	/**
	 * 组织信息
	 */
	@ManyToOne
	@JoinColumn(name="ORG_ID")
	private Organization organization;
	
	/**
	 * 员工编号
	 */
	@Column(name="CODE",length=20)
	private String code;
	
	/**
	 * 职位
	 */
	@Column(name="POSITION",length=100)
	private String position;
	
	/**
	 * 人事性质
	 */
	@Column(name="TYPE",length=20)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	/**
	 * 人事状态
	 */
	@Column(name="STATUS",length=20)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	/**
	 * 入职时间
	 */
	@Column(name="EMPLOYMENT_DATE")
	private Date employmentDate;
	
	/**
	 * 工作地点
	 */
	@Column(name="WORKPLACE",length=100)
	private String workplace;
	
	/**
	 * 备注
	 */
	@Column(name="DESCRIPTION",length=500)
	private String description;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(Date employmentDate) {
		this.employmentDate = employmentDate;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
