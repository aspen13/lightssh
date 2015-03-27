package com.google.code.lightssh.project.party.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.common.model.Period;

/**
 * party relationship
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_PARTY_RELATIONSHIP" )
public class PartyRelationship extends UUIDModel{
	
	private static final long serialVersionUID = -4883228034923116706L;
	
	/**
	 * Party
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="FROM_PARTY_ROLE_ID")
	private PartyRole from;
	
	/**
	 * Party
	 */
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="TO_PARTY_ROLE_ID")
	private PartyRole to;
	
	/**
	 * 有效期
	 */
	@Embedded
	private Period period;
	
	/**
	 * 关系类型
	 */
	@Column( name="TYPE",length=50 )
	@Enumerated(value=EnumType.STRING)
	private RelationshipType type;
	
	public PartyRelationship( ){
	}

	public PartyRelationship(RelationshipType type
			,PartyRole from, PartyRole to ){
		super();
		this.from = from;
		this.to = to;
		this.type = type;
		
		Calendar calendar = Calendar.getInstance();
		this.period = new Period(calendar.getTime(),null); 
	}

	public PartyRole getFrom() {
		return from;
	}

	public void setFrom(PartyRole from) {
		this.from = from;
	}

	public PartyRole getTo() {
		return to;
	}

	public void setTo(PartyRole to) {
		this.to = to;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public RelationshipType getType() {
		return type;
	}

	public void setType(RelationshipType type) {
		this.type = type;
	}

	/**
	 * 关系种类
	 */
	public enum RelationshipType{
		ORG_ROLLUP("隶属关系")
		,SUPPLIER("供应关系")
		,CUSTOMER("客户关系")
		,PARTNERSHIP("合作关系")
		,EMPLOYMENT("雇佣关系")
		,AGENT("代理关系")
		,DISTRIBUTION_CHANNEL("分销渠道关系")
		,EMERGENCY_CONTACT("紧急联系人关系")
		,FAMILY("家庭成员关系")
		;
		private String value;
		
		RelationshipType( String value ){
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public String toString( ){
			return this.value;
		}
	}

}
