package com.google.code.lightssh.project.scheduler.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.CronExpression;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.web.action.BaseAction;
import com.google.code.lightssh.project.scheduler.entity.TriggerWrap;
import com.google.code.lightssh.project.scheduler.service.SchedulerManager;

/**
 * scheduler action
 * @author YangXiaojin
 *
 */
@Component( "schedulerAction" )
@Scope("prototype")
public class SchedulerAction extends BaseAction{

	private static final long serialVersionUID = 6483507951968694317L;
	
	private ListPage<TriggerWrap> page;
	
	private String name;
	
	private String group;
	
	public ListPage<TriggerWrap> getPage() {
		return page;
	}

	public void setPage(ListPage<TriggerWrap> page) {
		this.page = page;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Resource( name="schedulerManager" )
	private SchedulerManager schedulerManager;

	public void setSchedulerManager(SchedulerManager schedulerManager) {
		this.schedulerManager = schedulerManager;
	}
	
	/**
	 * 列出所有定时任务
	 */
	public String listTriggers(){
		if( page == null )
			page = new ListPage<TriggerWrap>();
		
		page.setSize(500);
		List<TriggerWrap> triggers = schedulerManager.listAllTrigger();
		if( triggers != null ){
			page.setAllSize(triggers.size());
			page.setList(triggers);
		}
		return SUCCESS;
	}
	
	/**
	 * 停用/启用 Trigger
	 */
	public String toggle(){
		try{
			schedulerManager.toggleTrigger(name,group);
		}catch( Exception e ){
			this.saveErrorMessage("任务操作失败：" + e.getMessage() );
		}
		
		return SUCCESS;
	}
	
	/**
	 * 刷新定时任务
	 */
	public String refresh(){
		try{
			schedulerManager.refresh(name,group);
		}catch( Exception e ){
			this.saveErrorMessage("刷新定时任务["+name+","+group+"]异常：" + e.getMessage() );
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑触发器
	 */
	public String editTrigger(){
		try{
			request.setAttribute("trigger", schedulerManager.get(group, name) );
		}catch( Exception e ){
			this.saveErrorMessage("获取时钟异常："+e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * 更新触发器
	 */
	public String saveTrigger(){
		String hour = request.getParameter("hour");
		String minute = request.getParameter("minute");
		try{
			CronExpression exp = new CronExpression("0",minute,hour,"?","*","*");
			schedulerManager.updateCronExp(name,group,exp);
			this.saveSuccessMessage("更新时钟成功！");
		}catch( Exception e ){
			this.addActionError("更新时钟异常："+e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}

}
