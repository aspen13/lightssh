package com.google.code.lightssh.project.scheduler.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.scheduler.entity.Plan;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail;
import com.google.code.lightssh.project.scheduler.service.PlanManager;
import com.google.code.lightssh.project.web.action.GenericAction;

/**
 * 计划任务
 */
@Component( "planAction" )
@Scope("prototype")
public class PlanAction extends GenericAction<Plan>{

	private static final long serialVersionUID = 4239036636222865026L;
	
	private PlanManager planManager;
	
	private Plan plan;
	
	private PlanDetail planDetail;
	
	private ListPage<PlanDetail> detailPage;

	@Resource(name="planManager")
	public void setPlanManager(PlanManager planManager) {
		this.planManager = planManager;
		super.manager = this.planManager;
	}

	public Plan getPlan() {
		this.plan = super.model;
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
		super.model=this.plan;
	}
	
	public ListPage<PlanDetail> getDetailPage() {
		return detailPage;
	}

	public void setDetailPage(ListPage<PlanDetail> detailPage) {
		this.detailPage = detailPage;
	}

	public PlanDetail getPlanDetail() {
		return planDetail;
	}

	public void setPlanDetail(PlanDetail planDetail) {
		this.planDetail = planDetail;
	}

	public String list(){
		if( page == null )
			page = new ListPage<Plan>();
		
		page.addDescending("createdTime");
		page.addAscending("id");
		return super.list();
	}
	
	public String listdetail(){
		if( plan == null || plan.getId() == null)
			return INPUT;
		
		List<PlanDetail> details = planManager.listDetail(plan);
		request.setAttribute("list", details);
		return SUCCESS;
	}
	
	/**
	 * 查询明细
	 */
	public String viewdetail(){
		if( planDetail == null || planDetail.getId() == null ){
			if( plan != null && plan.getId() != null )
				return INPUT;
			return ERROR;
		}
		
		String id = planDetail.getId();
		planDetail = planManager.getDetail( id );
		if( planDetail == null ){
			this.saveErrorMessage("执行计划明细["+id+"]不存在！");
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 加入任务队列
	 */
	public String detailInQueue(){
		if( planDetail == null || planDetail.getId()==null){
			this.saveErrorMessage("参数为空！");
			return INPUT;
		}
		
		try{
			this.planManager.detailInQueue(planDetail);
		}catch( Exception e){
			this.saveErrorMessage("加入任务队列异常："+e.getMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}

}
