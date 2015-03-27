package com.google.code.lightssh.project.party.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.code.lightssh.project.geo.entity.GeographicBoundary;

/**
 * person
 * @author Aspen
 *
 */
@Entity
@Table( name="T_PARTY_PERSON" )
@PrimaryKeyJoinColumn(name="id") 
public class Person extends Party{
	
	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat SEQUENCE_SDF = new SimpleDateFormat("yyMM");

	/**
	 * gender enum
	 */
	public enum Gender{
		MALE("男")			//male
		,FEMALE("女");		//female
		
		private String value;
		
		Gender( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString(){
			return this.value;
		}
	}
	
	@Override
	public String getSequenceKey() {
		return SEQUENCE_SDF.format( new Date() );
	}
	
	public int getSequenceLength() {
		return 3;
	}
	
	public void preInsert( ){
		//do nothing
	}
	
	/**
	 * 婚姻状况
	 */
	public enum MaritalStatus{
		UNKNOWN("未知")			//未知
		,SINGLE("单身")			//单身
		,MARRIED("已婚")			//已婚
		,SEPARATED("离异")		//分居、离婚
		,WIDOWED("丧偶");		//丧偶
		
		private String value;
		
		MaritalStatus( String value ){
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
	 * 教育水平
	 */
	public enum EducationLevel{
		MANDATORY_SCHOOLING("义务教育")		//义务教育
		,HIGH_SCHOOL("高中")					//高中
		,VOCATIONAL_SCHOOL("职业学校")		//职业学校
		,JUNIOR_COLLEGE("专科")				//专科
		,UNDERGRADUATE("本科")				//本科
		,MASTER("研究生")					//研究生
		,PHD("博士")							//博士
		,OTHER("其它");						//其它
		
		private String value;
		
		EducationLevel( String value ){
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
	 * 血型
	 */
	public enum BloodType{
		A("A") 
		,B("B")
		,AB("AB")
		,O("O");
		
		private String value;
		
		BloodType( String value ){
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
	 * first name
	 */
	@Column( name="FIRST_NAME" )
	protected String firstName;
	
	/**
	 * last name
	 */
	@Column( name="LAST_NAME" )
	protected String lastName;
	
	/**
	 * 证件类型
	 */
	@Column( name="CREDENTIALS_TYPE",length=50 )
	protected CredentialsType credentialsType;
	
	/**
	 * 证件号
	 */
	@Column( name="IDENTITY_CARD_NUMBER" )
	protected String identityCardNumber;
	
	/**
	 * 最高学历
	 */
	@Column( name="DEGREE",length=50 )
	protected EducationLevel degree;
	
	/**
	 * 称呼
	 */
	@Column( name="TITLE",length=20 )
	protected String title;
	
	/**
	 * 别名
	 */
	@Column( name="NICKNAME",length=100 )
	protected String nickname;
	
	/**
	 * 性别
	 */
	@Column( name="GENDER",length=20 )
	@Enumerated(value=EnumType.STRING)
	protected Gender gender;
	
	/**
	 * 生日
	 */
	@Column( name="BIRTHDAY",columnDefinition="DATE" )
	@Temporal(TemporalType.DATE)
	protected Date birthday;
	
	/**
	 * 婚姻状况
	 */
	@Column( name="MARITAL_STATUS",length=20 )
	@Enumerated(value=EnumType.STRING)
	protected MaritalStatus maritalStatus;
	
	/**
	 * 所属国家
	 */
	@ManyToOne
	@JoinColumn( name="COUNTRY_ID" )
	protected GeographicBoundary country;
	
	/**
	 * 民族
	 */
	@Column( name="ETHNIC_GROUP",length=50 )
	protected String ethnicGroup;
	
	/**
	 * 政治面貌
	 */
	@Column( name="PARTY_AFFILIATION",length=50 )
	protected String partyAffiliation;
	
	/**
	 * 所属二级地理区域
	 */
	@ManyToOne
	@JoinColumn( name="SECONDARY_GEO_ID" )
	protected GeographicBoundary secondaryGeo;
	
	/**
	 * 所属三级地理区域
	 */
	@ManyToOne
	@JoinColumn( name="THIRD_GEO_ID" )
	protected GeographicBoundary thirdGeo;
	
	/**
	 * 所属四级地理区域
	 */
	@ManyToOne
	@JoinColumn( name="FOURTH_GEO_ID" )
	protected GeographicBoundary fourthGeo;
	
	/**
	 * 血型
	 */
	@Column( name="BLOOD_TYPE",length=10 )
	@Enumerated(value=EnumType.STRING)
	protected BloodType bloodType;
	
	/**
	 * 身高
	 */
	@Column( name="HEIGHT" )
	protected Integer height;
	
	/**
	 * 体重
	 */
	@Column( name="WEIGHT" )
	protected Double weight;
	
	/**
	 * 人事信息
	 */
	@Transient
	protected Employee employee;
	
	/**
	 * 全名
	 */
	public String getFullName( ){
		return lastName + ' ' + firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}

	public CredentialsType getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(CredentialsType credentialsType) {
		this.credentialsType = credentialsType;
	}

	public EducationLevel getDegree() {
		return degree;
	}

	public void setDegree(EducationLevel degree) {
		this.degree = degree;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public GeographicBoundary getCountry() {
		return country;
	}

	public void setCountry(GeographicBoundary country) {
		this.country = country;
	}

	public GeographicBoundary getSecondaryGeo() {
		return secondaryGeo;
	}

	public void setSecondaryGeo(GeographicBoundary secondaryGeo) {
		this.secondaryGeo = secondaryGeo;
	}

	public GeographicBoundary getThirdGeo() {
		return thirdGeo;
	}

	public void setThirdGeo(GeographicBoundary thirdGeo) {
		this.thirdGeo = thirdGeo;
	}

	public GeographicBoundary getFourthGeo() {
		return fourthGeo;
	}

	public void setFourthGeo(GeographicBoundary fourthGeo) {
		this.fourthGeo = fourthGeo;
	}

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public String getPartyAffiliation() {
		return partyAffiliation;
	}

	public void setPartyAffiliation(String partyAffiliation) {
		this.partyAffiliation = partyAffiliation;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Person [id=" + getIdentity() +  ", birthday=" 
				+ birthday + ", firstName=" + firstName
				+ ", gender=" + gender + ", identityCardNumber="
				+ identityCardNumber + ", lastName=" + lastName + ", nickname="
				+ nickname + ", title=" + title + "]";
	}	
	
	public Person clone(){
		 return (Person)super.clone();
	}
	 
}
