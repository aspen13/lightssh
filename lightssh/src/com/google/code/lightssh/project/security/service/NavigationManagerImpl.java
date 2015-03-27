package com.google.code.lightssh.project.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.party.entity.PartyRole;
import com.google.code.lightssh.project.party.service.PartyRoleManager;
import com.google.code.lightssh.project.security.dao.NavigationDao;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.entity.Permission;
import com.google.code.lightssh.project.security.entity.Role;

/**
 * 
 * @author YangXiaojin
 *
 */
@Component("navigationManager")
public class NavigationManagerImpl extends BaseManagerImpl<Navigation> implements NavigationManager{

	private static final long serialVersionUID = -1156261316022370821L;

	/**xxxx赋予角色*/
	public static final String TEAM_ROLE = "TEAM_ROLE";
	
	private static Map<PartyRole.RoleType,String> roleMapTo = new HashMap<PartyRole.RoleType,String>();
	static{
		roleMapTo.put(PartyRole.RoleType.TEAM, TEAM_ROLE ); //TEAM会员
		//... ...
	}
	
	@Resource( name="navigationDao" )
	private NavigationDao navigationDao;
	
	@Resource(name="roleManager")
	private RoleManager roleManager;
	
	@Resource(name="partyRoleManager")
	private PartyRoleManager partyRoleManager;
	
	@Override
	public List<Navigation> listTop() {
		return navigationDao.listTop();
	}
	
	public void setNavigationDao(NavigationDao navigationDao) {
		this.navigationDao = navigationDao;
	}

	@Override
	public Collection<Permission> listPermission(Collection<Navigation> colls) {
		if( colls == null || colls.isEmpty() )
			return null;
		
		return this.navigationDao.listPermission(colls);
	}
	
	/**
	 * 根据业务规则取系统定义好的角色
	 */
	protected Set<String> listRolesByRule( List<PartyRole> partyRoles ){
		if( partyRoles == null || partyRoles.isEmpty() )
			return null;
		
		Set<String> results = new HashSet<String>();
		for( PartyRole item:partyRoles ){
			results.add( roleMapTo.get( item.getType() ) );
		}
		
		return results;
	}
	
	/**
	 * 查询角色对应的所有“权限资源”
	 */
	protected Set<Permission> listPermissionByRole( Collection<String> role_ids ){
		if( role_ids == null || role_ids.isEmpty() )
			return null;
		
		List<Role> list = new ArrayList<Role>();
		for( String item:role_ids ){
			Role role = roleManager.get(item);
			if( role != null )
				list.add( role );
		}
		
		if( list.isEmpty() )
			return null;
		
		Set<Permission> results = new HashSet<Permission>();
		for( Role item:list )
			results.addAll( item.getPermissions() );
		
		return results;
	}
	
	/**
	 * 根据会员权限集过滤导航树
	 */
	protected List<Navigation> listFilteringNavigation( Set<Permission> pers ){
		if( pers == null || pers.isEmpty() )
			return null;
		
		List<Navigation> top =this.navigationDao.listTop();//获取最高级菜单
		if( top == null || top.isEmpty() )
			return top;
		
		List<Navigation> results = new ArrayList<Navigation>( );
		for( Navigation item:top ){
			Navigation tmp = item.trimByPermission( pers );
			if( tmp != null )
				results.add(tmp);
		}
		
		return results;
	}
	
	/**
	 * 查询组织机构或会员拥有的权限
	 */
	public List<Navigation> listByParty( Party party ){
		if( party == null || party.getIdentity() == null )
			return null;
		
		List<PartyRole> partyRoles = partyRoleManager.list(party); //查询会员拥有的业务角色
		Set<String> role_ids = listRolesByRule( partyRoles ); //获得会员业务角色对应的权限角色ID
		Set<Permission> partyAllPers = listPermissionByRole( role_ids ); //获得会员拥有的所有权限资源
		
		return listFilteringNavigation( partyAllPers );
	}
	
	/**
	 * 查询所有
	 */
	public List<Navigation> listAll() {
		return this.navigationDao.listAll();
	}

	@Override
	public List<Navigation> listMenu() {
		return this.navigationDao.listMenu();
	}

}
