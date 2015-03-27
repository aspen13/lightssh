package com.google.code.lightssh.project.party.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.ReflectionUtil;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.log.service.AccessManager;
import com.google.code.lightssh.project.party.dao.PartyDao;
import com.google.code.lightssh.project.party.entity.Organization;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRelationship;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.Person;
import com.google.code.lightssh.project.party.entity.PartyRelationship.RelationshipType;
import com.google.code.lightssh.project.party.entity.PartyRole.RoleType;
import com.google.code.lightssh.project.sequence.service.SequenceManager;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("partyManager")
public class PartyManagerImpl extends BaseManagerImpl<Party> implements PartyManager{
	
	private static final long serialVersionUID = -7697405089934152590L;

	@Resource(name="sequenceManager")
	private SequenceManager sequenceManager;
	
	@Resource(name="accessManager")
	private AccessManager accessManager;
	
	@Resource(name="partyRoleManager")
	private PartyRoleManager partyRoleManager;
	
	@Resource(name="partyRelationshipManager")
	private PartyRelationshipManager partyRelationshipManager;
	
	@Resource(name="partyDao")
	public void setDao( PartyDao dao ){
		super.dao = dao;
	}
	
	public PartyDao getDao(){
		return (PartyDao)super.dao;
	}
	
	public void setSequenceManager(SequenceManager sequenceManager) {
		this.sequenceManager = sequenceManager;
	}
	
	public void setAccessManager(AccessManager accessManager) {
		this.accessManager = accessManager;
	}

	public void setPartyRoleManager(PartyRoleManager partyRoleManager) {
		this.partyRoleManager = partyRoleManager;
	}

	public void setPartyRelationshipManager(
			PartyRelationshipManager partyRelationshipManager) {
		this.partyRelationshipManager = partyRelationshipManager;
	}

	public void remove(Party t, Access access) {
		Class<?> clazz = Person.class;
		if( t instanceof Organization )
			clazz = Organization.class;
		
		Party party = getDao().read(clazz,t);
		if( party != null ){
			dao.delete(party);
			
			if( access != null && isDoLog(party))
				accessManager.log(access, party, null);
		}
		
	}
	
	public void remove(Organization t, Access access) {
		Organization party = this.getOrganizationWithChildren( t );
		if( party == null )
			return;
		
		if( party.getChildren() != null && !party.getChildren().isEmpty() )
			throw new ApplicationException("组织机构("
					+party.getId()+")存在下级组织，不允许删除！");
		
		//dao.delete(party);
		
		if( access != null && isDoLog(party))
			accessManager.log(access, party, null);
		
		RoleType[] internalOrgs = RoleType.internalOrg();
		RoleType[] aboutRollups = new RoleType[internalOrgs.length+2]; 
		for( int i=0;i<internalOrgs.length;i++ )
			aboutRollups[i] = internalOrgs[i];
		aboutRollups[internalOrgs.length] = RoleType.INTERNAL_ORG;
		aboutRollups[internalOrgs.length+1] = RoleType.PARENT_ORG;
		List<PartyRole> partyRoles = partyRoleManager.list(party,aboutRollups);
		
		//TODO partyRelationshipManager.removeByFromRole(partyRole);
		PartyRelationship pr = partyRelationshipManager.getRollupByFromParty( party );
		if( pr != null )
			partyRelationshipManager.remove( pr );
		
		//顺序问题
		for( PartyRole partyRole:partyRoles ){
			partyRoleManager.remove( partyRole );
		}
		
		dao.delete(party);
	}

	@Override
	public void save(Party party, Access access) {
		if( party == null )
			throw new ApplicationException("数据为空，不能进行保存！");
		
		Party original = null;
		boolean inserted = StringUtil.clean( party.getIdentity() ) == null;
		
		if( party instanceof Organization && !isUniqueName( party ) )
			throw new ApplicationException("名称["+party.getName()+"]已经存在！");
		
		if( party instanceof Person ){
			Person p =  ((Person) party);
			if( p.getCountry() != null 
					&& StringUtils.isEmpty(p.getCountry().getIdentity()))
				p.setCountry(null);
			
			if( p.getSecondaryGeo() != null 
					&& StringUtils.isEmpty(p.getSecondaryGeo().getIdentity()) )
				p.setSecondaryGeo(null);
			
			if( p.getThirdGeo() != null 
					&& StringUtils.isEmpty(p.getThirdGeo().getIdentity()) )
				p.setThirdGeo(null);
			
			if( p.getFourthGeo() != null 
					&& StringUtils.isEmpty(p.getFourthGeo().getIdentity()) )
				p.setFourthGeo(null);
		}
		
		if( inserted ){
			party.setId( sequenceManager.nextSequenceNumber( party ) );
			dao.create( party );
		}else{
			Party old = dao.read( party );
			if( old != null ){
				original = old.clone();
				ReflectionUtil.assign(party, old);
				dao.update( old );
			}
		}
		
		if( access != null && isDoLog(party)){
			accessManager.log(access, original, party);
		}
	}

	/**
	 * 是否做日志记录
	 */
	protected boolean isDoLog( Party party ){
		//TODO 参数设置是否进行日志记录
		return true;
	}

	@Override
	public ListPage<Party> listOrganization(ListPage<Party> page,Party party) {
		return getDao().list( Organization.class, page, party);
	}

	@Override
	public ListPage<Party> listPerson(ListPage<Party> page, Party party) {
		return getDao().list( Person.class, page, party);
	}
	
	@Override
	public boolean isUniqueName(Party party) {
		if( party == null )
			return true;
		
		return this.isUniqueProperty("name",party);
	}

	@Override
	public Organization getParentOrganization() {
		List<Party> list = partyRoleManager.listParty( RoleType.PARENT_ORG );
		if (list==null||list.isEmpty())
			return null;
		
		Organization result = null;
		for( Party party:list )
			if( party instanceof Organization ){
				result = (Organization)party;
				break;
			}
		
		return result;
	}
	
	/**
	 * 允许的内部组织角色
	 * @return
	 */
	private Set<RoleType> getAllowedRoleTypeOfInternalOrg( ){
		Set<RoleType> interalOrgSet = new HashSet<RoleType>( );
		CollectionUtils.addAll(interalOrgSet, RoleType.internalOrg());
		interalOrgSet.add( RoleType.PARENT_ORG );
		interalOrgSet.add( RoleType.INTERNAL_ORG );
		return interalOrgSet;
	}

	@Override
	public void save(Organization party, Access access,RoleType...types) {
		if( party == null )
			throw new IllegalArgumentException("内部组织为空！");
			
		if( types == null )
			throw new IllegalArgumentException("内部组织("+party.getName()+")角色类型为空！");
		
		if( party.getParent() != null && StringUtil.clean(party.getParent().getIdentity()) != null 
				&& party.getParent().getIdentity().equals(party.getIdentity()) )
			throw new IllegalArgumentException("上级组织不能选自己！");
		
		Set<RoleType> allowedTypes = getAllowedRoleTypeOfInternalOrg();
		for( RoleType type:types )
			if( !allowedTypes.contains(type))
				throw new IllegalArgumentException("非法组织角色["+type+"]！");
		
		Set<RoleType> newRoleTypes = new HashSet<RoleType>( );
		CollectionUtils.addAll(newRoleTypes, types );
		newRoleTypes.add( RoleType.INTERNAL_ORG );
		
		boolean isInsert = party.isInsert();
		this.save(party, access);
		
		Set<RoleType> interalOrgSet = new HashSet<RoleType>( );
		CollectionUtils.addAll(interalOrgSet, RoleType.internalOrg());
		
		if( isInsert ){ //新增数据
			PartyRole fromRole = null ; //下级隶属关系
			List<PartyRole> partyRoles = new ArrayList<PartyRole>( newRoleTypes.size() );
			for( RoleType item:newRoleTypes ){
				PartyRole newRole = new PartyRole(party,item);
				partyRoles.add( newRole );
				
				if( interalOrgSet.contains( item ) )
					fromRole = newRole;
			}
			//partyRoleManager.save(party, newRoleTypes );
			partyRoleManager.save( partyRoles );
			
			if( party.getParent() == null || party.getParent().getId() == null )
				return;
			
			PartyRole toRole = null; //上级隶属关系
			List<PartyRole> toRoles = partyRoleManager.list( party.getParent() );
			for( PartyRole role:toRoles ){
				if( RoleType.PARENT_ORG.equals( role.getType() ) ){
					toRole = role;
					break;
				}
					
				if( interalOrgSet.contains( role.getType() ) )
					toRole = role;
			}
			
			if( fromRole != null && toRole != null ){
				PartyRelationship pr = new PartyRelationship( 
						RelationshipType.ORG_ROLLUP,fromRole,toRole );
				partyRelationshipManager.save( pr );
			}
		}else{ //修改数据
			RoleType type = types[0];
			for( RoleType item:types ){
				if( !RoleType.PARENT_ORG.equals(item))
					type = item;
			}
				
			PartyRole newFromRole = null;
			List<PartyRole> savedPartyRoles = partyRoleManager.list(
        			party, RoleType.internalOrg() );
			if( savedPartyRoles != null && !savedPartyRoles.isEmpty()){
				//savedPartyRoles.size()>1 数据有问题
				newFromRole = savedPartyRoles.get(0);
				savedPartyRoles.get(0).setType( type );
			}else{
				//用于数据容错，正常情况不会执行
				newFromRole = new PartyRole(party,type);
				savedPartyRoles.add( newFromRole );
			}
			partyRoleManager.save(savedPartyRoles);
			
			if( party.getParent() != null ){
				PartyRole newToRole = null;
				RoleType[] allowedSelectTypes = RoleType.internalOrg();
				RoleType[] paramRoleTypes = new RoleType[allowedSelectTypes.length+1];
				paramRoleTypes[allowedSelectTypes.length] = RoleType.PARENT_ORG;
				System.arraycopy( allowedSelectTypes,0, paramRoleTypes, 0,allowedSelectTypes.length);
				List<PartyRole> newToRoles = partyRoleManager.list(
	        			party.getParent(), paramRoleTypes );
				if( newToRoles != null && !newToRoles.isEmpty() ){
					newToRole = newToRoles.get(0);
					for( PartyRole role:newToRoles )
						if( RoleType.PARENT_ORG.equals(role.getType()) )
							newToRole = role;
				}else{
					throw new ApplicationException("("+ party.getParent().getName()+")缺失["
							+RoleType.internalOrg()+"]中的一种角色！");
				}
				PartyRelationship pr = partyRelationshipManager.getRollupByFromParty( party );
				if( pr != null ){
					pr.setTo( newToRole );
					//pr.setFrom(newFromRole);
				}else{
					pr = new PartyRelationship(RelationshipType.ORG_ROLLUP
							,newFromRole,newToRole );
				}
				partyRelationshipManager.save( pr );
			}
		}
	}

	@Override
	public Organization getOrganization(Party party) {
		return (Organization)getDao().read(Organization.class, party);
	}
	
	@Override
	public Organization getOrganization(String partyid) {
		if( partyid == null )
			return null;
		
		Organization org = new Organization();
		org.setId( partyid );
		
		return getOrganization( org );
	}

	@Override
	public Person getPerson(Party party) {
		return (Person)getDao().read(Person.class, party);
	}

	@Override
	public Organization getOrganizationWithParent(Party party) {
		Organization org = this.getOrganization(party);
		if( org != null ){
			PartyRelationship relationship = partyRelationshipManager
				.getRollupByFromParty(org);
			if( relationship != null && relationship.getTo() != null 
					&& relationship.getTo() != null ){

				Organization parent = this.getOrganization(relationship.getTo().getParty() );
				//&& relationship.getTo().getParty() instanceof Organization
				org.setParent( parent );
			}
		}
		
		return org;
	}
	
	public Organization getOrganizationWithChildren(Party party){
		Organization org = this.getOrganization(party);
		if( org != null ){
			List<PartyRelationship> relationships = partyRelationshipManager
				.listRollupByToParty(org);
			if( relationships != null && !relationships.isEmpty() )
				for( PartyRelationship item:relationships ){
					if( item.getTo() != null && 
							item.getFrom().getParty() instanceof Organization)
						org.addChild((Organization)item.getFrom().getParty() );
				}//end for
		}
		
		return org;
	}
	
	@Override
	public Organization listRollup( ) {
		List<PartyRelationship> list = partyRelationshipManager.list( RelationshipType.ORG_ROLLUP );
		if( list == null || list.isEmpty() ){
			List<Party> parties = partyRoleManager.listParty( RoleType.PARENT_ORG );
			if( parties == null || parties.isEmpty() )
				return null;
			
			for( Party party:parties)
				if( party instanceof Organization )
					return (Organization)party;
			
			return null;
		}
		
		Organization root = null;
		Set<Party> set = new HashSet<Party>( );
		for( PartyRelationship pr:list ){
			Organization to = getOrganization(pr.getTo().getParty());
			Organization from = getOrganization(pr.getFrom().getParty());
			if( to !=null && from != null ){
				pr.getTo().setParty(to);
				pr.getFrom().setParty(from);
				set.add( to );
				set.add( from );
				if( RoleType.PARENT_ORG.equals( pr.getTo().getType() ) ){
					//if( root != null )
					//	throw new ApplicationException("数据异常，存在多个'最上级组织'！");
					root = to;
				}
			}
		}
		
		if( root != null ){
			for( PartyRelationship pr:list ){
				Party to = pr.getTo().getParty();
				Party from = pr.getFrom().getParty();
				if( set.contains( to ) ) //构造树
					((Organization)to).addChild( (Organization)from );
			}
		}
		
		return root;
	}

}
