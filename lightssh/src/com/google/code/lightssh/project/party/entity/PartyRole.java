package com.google.code.lightssh.project.party.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.code.lightssh.common.entity.base.UUIDModel;
import com.google.code.lightssh.common.model.Period;

/**
 * Party Role
 * @author YangXiaojin
 *
 */
@Entity
@Table( name="T_PARTY_ROLE",uniqueConstraints={@UniqueConstraint(columnNames={"PARTY_ID","TYPE"})} )
public class PartyRole extends UUIDModel{
	
	private static final long serialVersionUID = 2352810614613495760L;

	/**
	 * 业务角色
	 */
	public enum RoleType{
		INTERNAL_ORG("内部组织")
		,PARENT_ORG("最上级组织")
		,CORPORATION_GROUP("集团")
		,CORPORATION("公司")
		,SUBSIDIARY("分公司")
		,DIVISION("分支机构")
		,DEPARTMENT("部门")
		,TEAM("小组")
		,OTHER_ORG_UNIT("其它组织单元")
		
		,EMPLOYEE("职员")
		,EMPLOYER("雇主")
		,EMERGENCY_CONTACT("紧急联系人")
		
		,PARENT("父母")
		,COUPLE("夫妻")
		,BROTHERS("兄弟")
		,SISTERS("姐妹")
		,CHILD("孩子")
		;
		
		/**
		 * 内部组织角色
		 */
		public static RoleType[] internalOrg( ){
			return new RoleType[]{CORPORATION_GROUP,CORPORATION
					,SUBSIDIARY,DIVISION,DEPARTMENT,TEAM,OTHER_ORG_UNIT};
		}
		
		/**
		 * 家庭成员角色
		 */
		public static RoleType[] familyMember( ){
			return new RoleType[]{
				PARENT,COUPLE,BROTHERS,SISTERS,CHILD
			};
		}
		
		private String value;
		
		RoleType( String value ){
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
	 * Party
	 */
	@ManyToOne( fetch=FetchType.LAZY,cascade=CascadeType.PERSIST )
	@JoinColumn( name="PARTY_ID")
	private Party party;
	
	/**
	 * 有效期
	 */
	@Embedded
	private Period period;
	
	/**
	 * 业务角色
	 */
	@Column( name="TYPE",length=50 )
	@Enumerated(value=EnumType.STRING)
	private RoleType type;

	public PartyRole( ) {
		super();
	}
	
	public PartyRole(Party party, RoleType type) {
		super();
		this.party = party;
		this.type = type;
		Calendar calendar = Calendar.getInstance();
		this.period = new Period(calendar.getTime(),null); 
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

}
