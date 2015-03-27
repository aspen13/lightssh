package com.google.code.lightssh.project.contact.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 邮政地址
 * @author YangXiaojin
 *
 */
@Entity
@DiscriminatorValue("PostalAddress")
//@Table( name="T_CONTACT_POSTALADDRESS")
public class PostalAddress extends ContactMechanism{
	
	private static final long serialVersionUID = -1701434350793604887L;

	/**
	 * 收件人
	 */
	@Column( name="CONSIGNEE",length=50 )
	private String consignee;
	
	/**
	 * 邮编
	 */
	@Column( name="POSTAL_CODE",length=20 )
	private String postalCode;
	
	/**
	 * 地址
	 */
	@Column( name="ADDRESS",length=200 )
	private String address;
	
	public String format( ){
		return this.address + (postalCode==null?"":"(邮编:"+postalCode+")"); 
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
