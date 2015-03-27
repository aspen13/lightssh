package com.google.code.lightssh.project.param.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.ConnectionConfig;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.mail.MailConfigConstants;
import com.google.code.lightssh.project.param.entity.SystemParam;

/**
 * 系统参数业务实现
 * @author YangXiaojin
 *
 */
@Component( "systemParamManager" )
public class SystemParamManagerImpl extends BaseManagerImpl<SystemParam> implements SystemParamManager{
	
	private static final long serialVersionUID = 3614332539755325943L;
	
	private static Logger log = LoggerFactory.getLogger(SystemParamManagerImpl.class);
	
	@Resource( name="systemParamDao" )
	public void setSystemParamDao( Dao<SystemParam> dao ){
		super.dao = dao;
	}
	
	public void save( SystemParam param ){
		if( param == null )
			return;
		
		if( param.isInsert() && StringUtil.clean( param.getGroup()) == null )
			param.setGroup( SystemParam.DEFAULT_GROUP_NAME );
		
		param.setLastUpdateTime( new Date() );
		
		log.info( "修改系统参数>" + param.toString() );
		
		super.save(param);
	}

	@Override
	public SystemParam getByName(String name) {
		ListPage<SystemParam> page = new ListPage<SystemParam>(1);
		SearchCondition sc = new SearchCondition();
		sc.equal("name", name );
		page = dao.list(page, sc);
		
		return (page.getList()==null||page.getList().isEmpty())
			?null:page.getList().get(0);
	}
	
	@Override
	public SystemParam getByGroupAndName(String group, String name) {
		ListPage<SystemParam> page = new ListPage<SystemParam>(1);
		SearchCondition sc = new SearchCondition();
		sc.equal("name", name );
		sc.equal("group", group );
		page = dao.list(page, sc);
		
		return (page.getList()==null||page.getList().isEmpty())
			?null:page.getList().get(0);
	}

	@Override
	public List<SystemParam> listByGroup(String group) {
		ListPage<SystemParam> page = new ListPage<SystemParam>( );
		SearchCondition sc = new SearchCondition();
		sc.equal("group", group );
		page = dao.list(page, sc);
		
		return page.getList();
	}

	@Override
	public boolean isUniqueGroupAndName(SystemParam param) {
		if( param == null || param.getName() == null )
			return false;
		
		String group = StringUtil.clean(param.getGroup())==null
			?SystemParam.DEFAULT_GROUP_NAME:param.getGroup();
		
		ListPage<SystemParam> page = new ListPage<SystemParam>(1);
		SearchCondition sc = new SearchCondition();
		sc.equal("group", group ).equal("name",param.getName() );
		page = dao.list(page, sc);
		
		SystemParam exists = (page.getList()==null||page.getList().isEmpty())
			?null:page.getList().get(0);
	
		return exists == null || exists.getIdentity().equals(param.getIdentity() );
	}
	
	public ListPage<SystemParam> list(ListPage<SystemParam> page,SystemParam t ) {
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( t.getName() != null )
				sc.like("name",t.getName() );
			
			if( t.getGroup() != null )
				sc.like("group",t.getGroup() );
			
			if( t.getValue() != null )
				sc.like("value",t.getValue() );
		}
		return dao.list(page,sc);
	}
	
	/**
	 * 取参数值
	 */
	protected String getParamValue( Map<String,SystemParam> paramMap,String name ){
		if( name == null || paramMap == null || paramMap.isEmpty() )
			return null;
		
		SystemParam param = paramMap.get(name);
		if( param == null )
			return null;
		
		return param.getValue();
	}
	
	/**
	 * 邮件参数
	 */
	public ConnectionConfig getEmailConnectionConfig( ){
		List<SystemParam> params = listByGroup( 
				MailConfigConstants.PARAM_GROUP_EMAIL );
		
		if( params == null || params.isEmpty() )
			return null;
		
		Map<String,SystemParam> paramMap = new HashMap<String,SystemParam>();
		for( SystemParam item:params )
			paramMap.put(item.getName(), item);
		
		ConnectionConfig config = new ConnectionConfig();
		
		config.setHost( getParamValue(
				paramMap,MailConfigConstants.EMAIL_HOST_KEY));
		
		config.setPort( getParamValue(
				paramMap,MailConfigConstants.EMAIL_PORT_KEY ) );
		
		config.setUsername(getParamValue(
				paramMap,MailConfigConstants.EMAIL_USERNAME_KEY ) );
		
		config.setPassword(getParamValue(
				paramMap,MailConfigConstants.EMAIL_PASSWORD_KEY ) );
		
		config.setSsl( "true".equalsIgnoreCase(getParamValue(
				paramMap,MailConfigConstants.EMAIL_SSL_KEY ) ));
		
		return config;
	}

}
