package com.google.code.lightssh.project.security.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.common.support.shiro.UrlsSectionService;
import com.google.code.lightssh.project.security.entity.Navigation;
import com.google.code.lightssh.project.security.service.NavigationManager;

/**
 * 
 * @author Aspen
 * @date 2013-4-15
 * 
 */
public class MyUrlsSectionService implements UrlsSectionService{
	
	private NavigationManager navigationManager;

	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}

	@Override
	public Map<String, String> loadUrlsSection() {
		if( navigationManager != null ){
			List<Navigation> menus  = navigationManager.listAll(); //获取所有菜单
			if( menus != null && !menus.isEmpty() ){
				Map<String,String> result = new HashMap<String,String>();
				for( Navigation menu:menus )
					if( menu.getPermission() != null && 
						!StringUtils.isEmpty(menu.getPermission().getUrl())){
						result.put(menu.getPermission().getUrl(),"perms["+menu.getPermission().getToken()+"]");
					}
				return result;
			}
		}
		return null;
	}

}
