package com.google.code.lightssh.project.contact.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 电话
 * @author YangXiaojin
 *
 */
@Entity
@DiscriminatorValue("TelephoneNumber")
//@Table( name="T_CONTACT_TELEPHONENUMBER")
public class TelephoneNumber extends ContactMechanism{

	private static final long serialVersionUID = 6395136445849961584L;
	
	/**
	 * 国家号
	 */
	@Column(name="COUNTRY_CODE",length=10)
	private String countryCode;
	
	/**
	 * 区号
	 */
	@Column(name="AREA_CODE",length=10)
	private String areaCode;
	
	/**
	 * 号码
	 */
	@Column(name="CONTACT_NUMBER",length=20)
	private String contactNumber;
	
	/**
	 * 附加号(分机)
	 */
	@Column(name="EXT_CODE",length=10)
	private String extCode;
	
	/**
	 * 寻找人
	 */
	@Column(name="ASK_FOR_NAME",length=20)
	private String askForName;
	
	public String format( ){
		switch( getType() ){
			case TELEPHONE:
			case FAX:return (countryCode==null?"":countryCode+"-")
				+ (areaCode==null?"":areaCode+"-") + contactNumber 
				+ (extCode==null?"":"-"+extCode);
			case MOBILE:return (countryCode==null?"":"("+countryCode+")")+ this.contactNumber;
			default: return super.format();
		}
	}
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getExtCode() {
		return extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	public String getAskForName() {
		return askForName;
	}

	public void setAskForName(String askForName) {
		this.askForName = askForName;
	}
}
