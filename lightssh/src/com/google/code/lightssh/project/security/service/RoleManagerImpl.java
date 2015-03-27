package com.google.code.lightssh.project.security.service;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.DaoException;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManager;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.log.entity.EntityChange;
import com.google.code.lightssh.project.security.dao.RoleDao;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.entity.Permission;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.security.entity.RoleChange;
import com.google.code.lightssh.project.sequence.service.SequenceManager;
import com.google.code.lightssh.project.util.constant.AuditStatus;
import com.google.code.lightssh.project.workflow.model.WorkflowType;
import com.google.code.lightssh.project.workflow.service.WorkflowManager;

/**
 * Role Manager 实现
 * @author YangXiaojin
 *
 */
@Component("roleManager")
public class RoleManagerImpl extends BaseManagerImpl<Role> implements RoleManager{
	
	private static final long serialVersionUID = 5340905466451969796L;

	/** 系统超级管理员角色  */
	public static final String SUPER_ROLE = "SUPER_ROLE";
	
	@Resource(name="sequenceManager")
	private SequenceManager sequenceManager;
	
	@Resource(name="navigationManager")
	private NavigationManager navigationManager;
	
	@Resource(name="permissionManager")
	private BaseManager<Permission> permissionManager;
	
	@Resource(name="roleChangeManager")
	private RoleChangeManager roleChangeManager;
	
	@Resource(name="workflowManager")
	private WorkflowManager workflowManager;
	
	@Resource(name="roleDao")
	public void setRoleDao( RoleDao roleDao ){
		super.dao = roleDao;
	}
	
	public RoleDao getRoleDao(){
		return (RoleDao)super.dao;
	}
	
	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}
	
	public void setPermissionManager(BaseManager<Permission> permissionManager) {
		this.permissionManager = permissionManager;
	}

	/**
	 * 只针对名称及描述进行修改
	 */
	public void save( Role role ){
		boolean update = !role.isInsert();
		
		/*Role exist = getRoleDao().get( role.getName() );
		if( exist != null && !exist.getIdentity().equals( role.getIdentity()))
			throw new SecurityException( "该角色名称'"+role.getName()+"'已经存在！" );
		*/
		if( update ){
			Role db_role = get( role );
			if( db_role == null )
				throw new DaoException( "该角色已不存在，不能进行修改操作！" );
			
			db_role.setName( role.getName() );
			db_role.setDescription( role.getDescription() );
			//修改审核拒绝的角色进入待审核列表
			if(AuditStatus.AUDIT_REJECT.equals(db_role.getStatus()))
				db_role.setStatus(AuditStatus.NEW);
			dao.update( db_role );
		}else{
			role.setId( sequenceManager.nextSequenceNumber(role) );
			role.setStatus(AuditStatus.NEW);
			dao.create( role );
		}
		
	}

	@Override
	public void save(Role role,Collection<Navigation> colls,LoginAccount user) {
		if( role == null )
			throw new ApplicationException("参数为空！");
		
		Role db_role = get(role);
		Role originalRole = null,newRole = role;
		if( db_role == null ){
			db_role = role;
		}else{
			originalRole = db_role.clone();
			if( !db_role.isEffective() ){
				db_role.setName(role.getName());
				db_role.setDescription(role.getDescription());
			}
		}
		
		//新角色赋值用于序列化
		newRole.setStatus(db_role.getStatus());
		newRole.setCreatedTime(db_role.getCreatedTime());
		newRole.setReadonly(db_role.getReadonly());
		
		//update permission
		Collection<Permission> pers = navigationManager.listPermission(colls);
		newRole.setPermissions(pers);
		if( !db_role.isEffective() ){
			db_role.setPermissions( pers );
		}
		
		if( !db_role.isEffective() )//有效状态不能直接发起变更
			save( db_role );
			
		//变更记录
		String remark = null;
		AuditStatus status = db_role.getStatus();
		EntityChange.Type type = EntityChange.Type.CREATE;
		if( AuditStatus.NEW.equals( status )){
			type = EntityChange.Type.CREATE;
		}else if(AuditStatus.EFFECTIVE.equals(status) ){
			type = EntityChange.Type.UPDATE;
			remark = "角色变更，审核通过后生效！";
		}
		
		RoleChange rc = roleChangeManager.save(user, type, originalRole, newRole,remark);
		
		//启动工作流
		String bizName = "角色变更审核流程_"+db_role.getName()+"_"+user.getLoginName();
		workflowManager.start(WorkflowType.SEC_ROLE.getName(),rc.getId(),bizName,user.getLoginName(),null);
	}
	
	public void remove(Role role,LoginAccount operator,String remark) {
		Role dbRole = dao.read(role.getId());
		if( dbRole == null )
			throw new ApplicationException("角色不存在！");
		
		if( remark == null )
			remark = "删除角色";
		Role newRole = dbRole.clone();
		newRole.setStatus(AuditStatus.DELETE);
		roleChangeManager.save(operator
				,EntityChange.Type.DELETE,dbRole, newRole,remark);
	}

	@Override
	public Role initRole( boolean forceUpdatePermission ) {
		//super role init
		Role role = getRoleDao().read(SUPER_ROLE);
		boolean no_role = false;
		if( role == null ){
			no_role = true;
			role = new Role();
			role.setId(SUPER_ROLE);
			role.setDescription("系统初始化创建超级用户.");
			role.setName("超级管理角色");
			role.setReadonly( Boolean.TRUE );
			role.setStatus(AuditStatus.EFFECTIVE);
		}
		//是否更新角色权限
		
		//list all permission
		ListPage<Permission> page = new ListPage<Permission>(Integer.MAX_VALUE);
		page = permissionManager.list(page);
		if( (no_role || forceUpdatePermission) && 
				page.getList() != null && !page.getList().isEmpty() ){
			role.setPermissions( new HashSet<Permission>( page.getList() ) );
		}
		
		if( no_role )
			create(role);
		else
			update(role );
		
		return role;
	}

	/**
	 * 待审核列表
	 */
	public ListPage<Role> listTodoAudit(ListPage<Role> page,Role role){
		SearchCondition sc = new SearchCondition();
		if( role != null ){
			if( !StringUtils.isEmpty(role.getName()) ){
				sc.like("name", role.getName());
			}
			
		}
		//sc.in("status",Role.Status.NEW.name(),Role.Status.AUDIT_REJECT.name());
		sc.equal("status",AuditStatus.NEW);
		
		return getRoleDao().list(page,sc);
	}


}
