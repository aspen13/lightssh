package com.google.code.lightssh.project.party.web;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.web.action.CrudAction;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.party.entity.Organization;
import com.google.code.lightssh.project.party.entity.PartyContact;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.entity.PartyRole.RoleType;
import com.google.code.lightssh.project.party.service.PartyContactManager;
import com.google.code.lightssh.project.party.service.PartyManager;
import com.google.code.lightssh.project.party.service.PartyRoleManager;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component( "organizationAction" )
@Scope("prototype")
public class OrganizationAction extends CrudAction<Organization>{

	private static final long serialVersionUID = 1L;
	
	@Resource( name="partyRoleManager" )
	private PartyRoleManager partyRoleManager;
	
	@Resource( name="partyManager" )
	private PartyManager partyManager;
	
	@Resource( name="partyContactManager" )
	private PartyContactManager partyContactManager;
	
	private Organization party;
	
	private RoleType party_role_type;
	
	private ListPage<Organization> page;

	public void setPartyRoleManager(PartyRoleManager partyRoleManager) {
		this.partyRoleManager = partyRoleManager;
	}

	public void setPartyContactManager(PartyContactManager partyContactManager) {
		this.partyContactManager = partyContactManager;
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}

	public void setPartyManager(PartyManager partyManager) {
		super.manager = this.manager;
		this.partyManager = partyManager;
	}

	public Organization getParty() {
		return party;
	}

	public void setParty(Organization party) {
		this.party = party;
	}
	
	public RoleType getParty_role_type() {
		return party_role_type;
	}

	public void setParty_role_type(RoleType partyRoleType) {
		party_role_type = partyRoleType;
	}

	public ListPage<Organization> getPage() {
		return page;
	}

	public void setPage(ListPage<Organization> page) {
		this.page = page;
	}
	
	/**
	 * popup
	 */
	public String popup( ){
		Organization root = partyManager.listRollup();
		request.setAttribute("popup_org_rollup", root );
		
		return SUCCESS;
	}
	
	public String list( ){
		party = partyManager.listRollup();
		return SUCCESS;
	}
	
	/**
	 * 查看企业信息
	 */
	public String viewparent( ){
		party = partyManager.getParentOrganization();
		
		if( party != null ){
			//角色类型
			List<PartyRole> types = partyRoleManager.list(
					party, RoleType.internalOrg() );
			if( types != null && !types.isEmpty() )
				this.party_role_type = types.get(0).getType();
			
			Collection<PartyContact> contacts = partyContactManager.list(party);
			request.setAttribute("party_contacts",contacts );
		}
		
		if( "edit".equals(request.getParameter("action")))
			return INPUT;
		
		return SUCCESS;
	}
	
	/**
	 * 初始化设置企业信息
	 * @return
	 */
	public String initparent( ){
		if( party == null ){
			return INPUT;
		}
		
		if( party_role_type == null){
			this.addFieldError("party_role_type", "非法角色类型！");
			return INPUT;
		}
		
		boolean isInsert = party.isInsert();
        Access access = new Access(  );
        access.init(request);
        access.setDescription("编辑企业信息");
        //access.setOperator( SecurityUtil.getPrincipal() );
        
        try{
        	partyManager.save(party,access, new RoleType[]{
        			party_role_type,RoleType.PARENT_ORG});
        }catch( Exception e ){ //other exception
        	if( isInsert )
        		party.postInsertFailure();
            addActionError( e.getMessage() );
            e.printStackTrace();
            return INPUT;
        } 
        
        String hint =  "成功设置企业信息！" ;
        saveSuccessMessage( hint );
		
		return SUCCESS;
	}
	
    public String edit( ){
        if( party != null && party.getIdentity() != null ){
        	party = partyManager.getOrganizationWithParent(party);
        	if( party == null )
            	this.addActionError("找不到(id="+party.getId()+")的相关数据！");
        	
        	RoleType[] allowedSelectTypes = RoleType.internalOrg();
			RoleType[] paramRoleTypes = new RoleType[allowedSelectTypes.length+1];
			paramRoleTypes[allowedSelectTypes.length] = RoleType.PARENT_ORG;
			System.arraycopy( allowedSelectTypes,0, paramRoleTypes, 0,allowedSelectTypes.length);

			List<PartyRole> partyRoles = partyRoleManager.list(party,paramRoleTypes );
        	if( partyRoles != null && !partyRoles.isEmpty() ){
        		for( PartyRole role:partyRoles )
        			if(  RoleType.PARENT_ORG.equals( role.getType() ) )
        				return "settings";
        		this.party_role_type = partyRoles.get(0).getType();
        	}
        }
        
        return SUCCESS;
    }
	
	public String save( ){
		if( party == null )
			return INPUT;
		
        Access access = new Access(  );
        access.init(request);
        access.setDescription("操作组织机构");
        //access.setOperator( SecurityUtil.getPrincipal() );
        
        try{
        	partyManager.save(party,access,party_role_type);
        }catch( Exception e ){ //other exception
            addActionError( e.getMessage() );
            return INPUT;
        } 
        
        String hint =  "保存["+ party.getName() +"]成功！" ;
        saveSuccessMessage( hint );
        String saveAndNext = request.getParameter("saveAndNext");
        if( saveAndNext != null && !"".equals( saveAndNext.trim() ) ){
        	return NEXT;
        }else{        	
        	return SUCCESS;
        }
	}

	
    /**
     * delete
     * @return
     */
     public String delete( ){
    	 if( party == null || StringUtils.isEmpty(party.getIdentity())){
             return INPUT;
         }
        
         Access access = new Access(  );
         access.init(request);
         
         try{
        	 this.getPartyManager().remove( party,access );
        	 saveSuccessMessage( "成功删除数据(id=" + party.getIdentity() + ")！" );
         }catch( Exception e ){ //other exception
             saveErrorMessage( "删除发生异常：" + e.getMessage() );
             return INPUT;
         } 
        
         return SUCCESS;
     }

}
