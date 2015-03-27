package com.google.code.lightssh.project.contact.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;

/**
 * 联系机制
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_CONTACT_MECHANISM")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name="CLAZZ_TYPE",discriminatorType=DiscriminatorType.STRING,length=50)
@DiscriminatorValue("base")
public class ContactMechanism extends UUIDModel{

	private static final long serialVersionUID = 2168747780776560365L;
	
	/**
	 * 联系方式类型
	 */
	public enum ContactMechanismType{
		POSTAL_ADDRESS("邮政地址")
		,TELEPHONE("电话")
		,FAX("传真")
		,MOBILE("手机")
		,WEB_ADDRESS("网址")
		,EMAIL("邮箱")
		,QQ("QQ")
		,MSN("MSN")
		,OTHER("其它")
		;
		private String value;
		
		ContactMechanismType( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString( ){
			return this.value;
		}
	}
	
	/**
	 * 联系方式目的
	 */
	public enum ContactMechanismPurpose{
		OTHER("其它")
		,MAIN_FAX_NUMBER("传真")
		,HEADQUARTERS_NUMBER("总部电话")
		,SERVICE_NUMBER("客服电话")
		,COMPLAINT_NUMBER("投诉电话")
		,OFFICE_NUMBER("办公电话")
		,HOME_NUMBER("家庭电话")
		,PERSONAL_NUMBER("私人电话")
		,WROK_EMAIL("工作邮箱")
		,PERSONAL_EMAIL("私人邮箱")
		;
		
		private String value;
		
		ContactMechanismPurpose( String value ){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String toString( ){
			return this.value;
		}
	}
	
	/**
	 * 其它类型名
	 */
	@Column( name="OTHER_TYPE_NAME",length=50)
	private String otherTypeName;
	
	/**
	 * 其它类型值
	 */
	@Column( name="OTHER_TYPE_VALUE",length=200 )
	private String otherTypeValue;
	
	/**
	 * 备注
	 */
	@Column( name="DESCRIPTION",length=200)
	private String description;
	
	/**
	 * 联系机制类型
	 */
	@Column( name="TYPE",length=50 )
	@Enumerated(value=EnumType.STRING)
	private ContactMechanismType type;
	
	/**
	 * 联系机制目的
	 */
	@Column( name="PURPOSE",length=50 )
	@Enumerated(value=EnumType.STRING)
	protected ContactMechanismPurpose purpose;
	
	/**
	 * 格式化输出
	 */
	public String format( ){
		switch( this.type ){
			case OTHER:return "("+this.otherTypeName+")" + this.otherTypeValue;
			case EMAIL:
			case QQ:
			case MSN:
			case WEB_ADDRESS:
				return this.otherTypeValue;
			default: return "["+this.type+"]-" + this.description;
		}
	}

	public String getOtherTypeName() {
		return otherTypeName;
	}

	public void setOtherTypeName(String otherTypeName) {
		this.otherTypeName = otherTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ContactMechanismType getType() {
		return type;
	}

	public void setType(ContactMechanismType type) {
		this.type = type;
	}

	public String getOtherTypeValue() {
		return otherTypeValue;
	}

	public void setOtherTypeValue(String otherTypeValue) {
		this.otherTypeValue = otherTypeValue;
	}

	public ContactMechanismPurpose getPurpose() {
		return purpose;
	}

	public void setPurpose(ContactMechanismPurpose purpose) {
		this.purpose = purpose;
	}

}
