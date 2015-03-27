package com.google.code.lightssh.project.security.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.IoSerialUtil;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.security.entity.RoleAudit;
import com.google.code.lightssh.project.security.entity.RoleChange;
import com.google.code.lightssh.project.security.service.NavigationManager;
import com.google.code.lightssh.project.security.service.RoleAuditManager;
import com.google.code.lightssh.project.security.service.RoleChangeManager;
import com.google.code.lightssh.project.security.service.RoleManager;
import com.google.code.lightssh.project.util.constant.AuditResult;
import com.google.code.lightssh.project.util.constant.AuditStatus;
import com.google.code.lightssh.project.util.constant.WorkflowConstant;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * Role Action
 * @author YangXiaojin
 *
 */
@Component( "roleAction" )
@Scope("prototype")
public class RoleAction extends GenericAction<Role>{

	private static final long serialVersionUID = 1L;
	
	private Role role;
	
	private RoleAudit roleAudit;
	
	private RoleChange roleChange;
	
	private List<Navigation> p_list;
	
	private ListPage<RoleAudit> raPage;
	
	private ListPage<RoleChange> rcPage;
	
	@Resource( name="navigationManager" )
	private NavigationManager navigationManager;
	
	@Resource( name="roleChangeManager" )
	private RoleChangeManager roleChangeManager;
	
	@Resource( name="roleAuditManager" )
	private RoleAuditManager roleAuditManager;
	
	@JSON(name="page")
	public ListPage<Role> getPage( ){
		return super.getPage();
	}
	
	@Resource( name="roleManager" )
	public void setManager( RoleManager roleManager ){
		super.manager = roleManager;
	}
	
	public RoleManager getManager(){
		return (RoleManager)this.manager;
	}

	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}
	
	public ListPage<RoleChange> getRcPage() {
		return rcPage;
	}

	public void setRcPage(ListPage<RoleChange> rcPage) {
		this.rcPage = rcPage;
	}

	public RoleChange getRoleChange() {
		return roleChange;
	}

	public void setRoleChange(RoleChange roleChange) {
		this.roleChange = roleChange;
	}

	/**
	 * 导航
	 */
	public List<Navigation> getNavigation(){
		return navigationManager.listTop();
	}

	public Role getRole() {
		role = super.model;
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
		super.model = role;
	}
	
	public RoleAudit getRoleAudit() {
		return roleAudit;
	}

	public void setRoleAudit(RoleAudit roleAudit) {
		this.roleAudit = roleAudit;
	}

	public List<Navigation> getP_list() {
		return p_list;
	}

	public void setP_list(List<Navigation> pList) {
		p_list = pList;
	}

	public ListPage<RoleAudit> getRaPage() {
		return raPage;
	}

	public void setRaPage(ListPage<RoleAudit> raPage) {
		this.raPage = raPage;
	}
	
	/**
	 * 工作流显示业务数据
	 */
	public String workflowView(){
		String bizKey = request.getParameter( WorkflowConstant.BIZ_KEY_NAME );
		
		//this.setRole( this.getManager().get(bizKey));
		if( StringUtils.isEmpty(bizKey) ){
			this.addActionError("业务参数为空！");
			return SUCCESS;
		}
		
		RoleChange rc = roleChangeManager.get(bizKey);
		if( rc == null ){
			this.addActionError("业务数据["+bizKey+"]不存在！");
			return SUCCESS;
		}
		
		//TODO
		this.setRoleChange(rc);
		todoAudit();
		
		return SUCCESS;
	}

	public String save( ){
		if( role != null ){
			role.setReadonly( Boolean.FALSE );
			role.setStatus(AuditStatus.NEW);
		}
		
		String result = super.save();
		if( request.getParameter("saveAndAuthorize") != null )
			result = "authorize";
		
		return result;
	}
	
    public String delete( ){
        if( role == null || role.getIdentity() == null ){
        	this.saveErrorMessage("参数错误！");
            return INPUT;
        }
       
        try{
        	this.getManager().remove( role,getLoginAccount(),null );
        	saveSuccessMessage( "删除操作已受理，审核通过后生效！" );
        }catch( Exception e ){ 
            saveErrorMessage( "删除发生异常：" + e.getMessage() );
            return INPUT;
        } 
       
        return SUCCESS;
    }

	public String list( ){
		if( page == null )
			page = new ListPage<Role>( );
		page.addAscending("createdTime");
		
		return super.list();
	}
	
	/**
	 * 弹出窗口
	 */
	public String popup( ){
		if ( page == null )
			page = new ListPage<Role>();
		page.addAscending( "createdTime" );
		
		page = manager.list( page , role );
		
		if( "true".equalsIgnoreCase(request.getParameter("multi")) )
			return "multi"; //多选
		
		return SUCCESS;
	}
	
	/**
	 * 保存角色权限
	 */
	public String addPermission( ){
		if( role == null || role.getId() == null )
			return INPUT;
		
		try{
			getManager().save( role,p_list,getLoginAccount());
		}catch( Exception e ){
			saveErrorMessage("角色添加权限异常：" + e.getMessage() );
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 待审核列表
	 */
	public String todoAuditList(){
		if(rcPage == null )
			rcPage = new ListPage<RoleChange>();
		
		rcPage.addDescending("createdTime");
		rcPage = roleChangeManager.listTodoAudit(rcPage,roleChange);
		return SUCCESS;
	}
	
	/**
	 * 待审核
	 */
	public String todoAudit(){
		this.roleChange = this.roleChangeManager.get(roleChange);
		if( roleChange == null ){
			this.saveErrorMessage("参数错误，无法查找到变更信息！");
			return INPUT;
		}
		this.setRole(roleChange.getRole());
		
		byte[] originalObject = roleChange.getOriginalObject();
		if( originalObject != null )
			request.setAttribute("originalRole", IoSerialUtil.deserialize(originalObject));
		byte[] newObject = roleChange.getNewObject();
		if( newObject != null )
			request.setAttribute("newRole", IoSerialUtil.deserialize(newObject));
		
		return this.edit();
	}
	
	/**
	 * 审核
	 */
	public String audit(){
		if( roleAudit == null || roleChange == null ){
			this.saveErrorMessage("审核参数为空！");
			return ERROR;
		}
		
		roleAudit.setRoleChange(roleChange);//关联角色变更
		
		roleAudit.setUser(this.getLoginAccount());
		boolean passed = request.getParameter("passed")!=null;
		roleAudit.setResult(passed?AuditResult.LAST_AUDIT_PASSED:AuditResult.LAST_AUDIT_REJECT);
		
		try{
			this.roleAuditManager.audit(roleAudit,roleChange);
		}catch(Exception e ){
			this.saveErrorMessage("审核操作异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 审核结果
	 */
	public String auditList( ){
		if( raPage == null )
			raPage = new ListPage<RoleAudit>();
		
		raPage.addDescending("createdTime");
		roleAuditManager.list(raPage,roleAudit);
		return SUCCESS;
	}
	
}
