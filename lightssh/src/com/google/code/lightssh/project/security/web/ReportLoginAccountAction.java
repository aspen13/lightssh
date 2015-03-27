package com.google.code.lightssh.project.security.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.report.jr.JasperEngine;
import com.google.code.lightssh.common.web.action.ReportAction;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.service.LoginAccountManager;

@Component( "reportLoginAccountAction" )
@Scope("prototype")
public class ReportLoginAccountAction extends ReportAction<LoginAccount>{
	
	private static final long serialVersionUID = 3862513833381074305L;
	
	private LoginAccountManager manager;
	
	private LoginAccount account;
	
	private ListPage<LoginAccount> page;
	
	@Resource( name="loginAccountManager" )
	public void setManager(LoginAccountManager manager) {
		this.manager = manager;
	}

	public LoginAccount getAccount() {
		return account;
	}

	public void setAccount(LoginAccount account) {
		this.account = account;
	}

	public ListPage<LoginAccount> getPage() {
		return page;
	}

	public void setPage(ListPage<LoginAccount> page) {
		this.page = page;
	}
	
	public ReportLoginAccountAction() {
		super.jasperEngine = new JasperEngine();
	}

	@Override
	public String getTemplateFileName() {
		return "security_login_account";
	}

	@Override
	public List<LoginAccount> getDataSource() {
		if( page == null )
			page = new ListPage<LoginAccount>( );
		
		page.setSize( Integer.MAX_VALUE );
		page = manager.list(page,account);
		return page.getList();
	}

}
