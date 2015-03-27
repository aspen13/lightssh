package com.google.code.lightssh.project.scheduler.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.common.util.TextFormater;
import com.google.code.lightssh.project.scheduler.dao.PlanDao;
import com.google.code.lightssh.project.scheduler.dao.PlanDetailDao;
import com.google.code.lightssh.project.scheduler.entity.Plan;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail;
import com.google.code.lightssh.project.scheduler.entity.SchedulerType;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail.Status;
import com.google.code.lightssh.project.sequence.service.SequenceManager;

/**
 * 执行计划业务实现
 * @author YangXiaojin
 *
 */
@Component("planManager")
public class PlanManagerImpl extends BaseManagerImpl<Plan> implements PlanManager{

	private static final long serialVersionUID = -6376825556178324639L;
	
	private static Logger log = LoggerFactory.getLogger( PlanManagerImpl.class );
	
	@Resource(name="planDetailDao")
	private PlanDetailDao planDetailDao;
	
	@Resource(name="sequenceManager")
	private SequenceManager sequenceManager;
	
	@Resource(name="jobQueueManager")
	private JobQueueManager jobQueueManager;
	
	@Resource(name="planDao")
	public void setDao(PlanDao dao ){
		this.dao = dao;
	}
	
	public PlanDao getDao(){
		return (PlanDao)this.dao;
	}
	
	public ListPage<Plan> list(ListPage<Plan> page,Plan t ){
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( !StringUtils.isEmpty(t.getId()) )
				sc.like( "id",t.getId() );
			
			if( t.get_pftPeriod() != null ){
				Calendar cal = Calendar.getInstance();
				Date start = t.get_pftPeriod().getStart();
				Date end = t.get_pftPeriod().getEnd();
				
				if( start != null ){
					cal.setTime(start);
					sc.greateThanOrEqual("planFireTime",cal);
				}
				
				if( end != null ){
					Calendar cal_end = Calendar.getInstance();
					cal_end.setTime(end);
					cal_end.add(Calendar.DAY_OF_MONTH, 1);
					cal_end.add(Calendar.SECOND, -1);
					sc.lessThanOrEqual("planFireTime",cal_end);
				}
			}
		}
		
		return super.list(page, sc);
	}
	
	/**
	 * 计划明细
	 */
	public PlanDetail getDetail( String id ){
		if( StringUtils.isEmpty(id) )
			return null;
		
		return this.planDetailDao.read(id);
	}
	
	/**
	 * 计划明细
	 */
	public PlanDetail getDetailWithLock( String id ){
		if( StringUtils.isEmpty(id) )
			return null;
		
		return this.planDetailDao.readWithLock(id);
	}
	
	/**
	 * 更新明细状态
	 */
	public void updateDetailStatus(String detailId,Status status ){
		PlanDetail detail = getDetail(detailId);
		if( detail == null )
			throw new ApplicationException("执行计划明细["+detailId+"]对应数据不存在！");
		
		detail.setStatus(status);
		
		this.planDetailDao.update(detail);
	}
	
	/**
	 * 未成功记录
	 */
	@Deprecated
	protected int getUnsuccessfulCount(String planId,String planDetailId ){
		ListPage<PlanDetail> page = new ListPage<PlanDetail>(0);
		SearchCondition sc = new SearchCondition();
		sc.equal("plan.id", planId);
		sc.notEqual("id", planDetailId);
		sc.notEqual("status", Status.SUCCESS );
		
		page = planDetailDao.list(page, sc);
		
		return page.getAllSize();
	}
	
	public void updateStatus(String detailId ){
		PlanDetail detail = getDetail(detailId);
		if( detail != null && detail.getPlan() != null && 
				getDao().updateStatusIfAllDetailFinished( detail.getPlan().getId() ))
			log.info("计划任务在明细[{}]完成执行后，进行状态更新！",detail);
	}
	
	/**
	 * 更新明细状态
	 */
	public void updateDetailStatus(String detailId,boolean success,String errMsg){
		PlanDetail detail = getDetailWithLock(detailId);
		if( detail == null )
			throw new ApplicationException("执行计划明细["+detailId+"]对应数据不存在！");
		
		PlanDetail.Status oldStatus = detail.getStatus();
		if( PlanDetail.Status.WAITING_FOR_REPLY.equals(detail.getStatus()) 
				|| PlanDetail.Status.NEW.equals(detail.getStatus())
				|| PlanDetail.Status.FAILURE.equals(detail.getStatus())
				|| PlanDetail.Status.EXCEPTION.equals(detail.getStatus()) ){
			
			detail.setStatus(success?PlanDetail.Status.SUCCESS:PlanDetail.Status.FAILURE);
			detail.setFinishTime( Calendar.getInstance() );
			if( StringUtils.isEmpty(errMsg))
				detail.setDescription("回调结果["+(success?"成功":"失败")+"]");
			else
				detail.setDescription( TextFormater.format(errMsg,197,true) );
				
			this.planDetailDao.update(detail);
			
			log.info("执行计划明细["+detailId+"]状态["
					+oldStatus+"]更新为["+detail.getStatus()+"]！");
			
			//TODO 并发可能导致 计划任务状态 不能成功更新
			/*
			if( success ){
				String planId = detail.getPlan().getId();
				int count = getUnsuccessfulCount( planId,detail.getId());
				if( count == 0 ){
					updateFinishTime( planId,Calendar.getInstance());
					log.info("执行计划明细[{}]执行完成，整个任务完成！",detail.getId());
				}
			}
			*/
			
			//TODO 可能导致并发问题
			//执行依赖任务
			/*
			List<PlanDetail> replyOnList = planDetailDao.listRelyOnUnsuccessful(detailId);
			if( replyOnList == null || replyOnList.isEmpty() )
				return;
			
			//TODO 线程池处理
			for( PlanDetail item:replyOnList ){
				if( item.getPlan()==null || item.getPlan().getType() == null ){
					log.warn("上级任务执行[{}]依赖任务[{}]无法调用执行，类型为空！",detailId,item.getId());
					continue;
				}else if( success ){
					log.info("回调任务[{}]执行成功,执行依赖任务[{}]。",detailId,item.getId());
					jobQueueManager.jobInQueue(item.getPlan().getType().getId(),item.getId(),1);
				}else{
					item.setStatus(Status.FAILURE);
					item.setDescription("依赖任务["+detailId+"]执行返回失败！");
					
					planDetailDao.update(item);
				}
			}*/
			
		}else{
			log.warn("执行计划明细["+detailId
					+"]状态已改变为["+detail.getStatus()+"]不能进行更新！");
		}
		
	}
	
	public void executeRelyOnPlanDetail(boolean success,String detailId ){
		//执行依赖任务
		List<PlanDetail> replyOnList = planDetailDao.listRelyOnUnsuccessful(detailId);
		if( replyOnList == null || replyOnList.isEmpty() )
			return;
		
		//TODO 线程池处理
		for( PlanDetail item:replyOnList ){
			if( item.getPlan()==null || item.getPlan().getType() == null ){
				log.warn("上级任务执行[{}]依赖任务[{}]无法调用执行，类型为空！",detailId,item.getId());
				continue;
			}else if( success ){
				log.info("回调任务[{}]执行成功,执行依赖任务[{}]。",detailId,item.getId());
				jobQueueManager.jobInQueue(item.getPlan().getType().getId()
						,item.getId(),1,item.getPlan().getPlanFireTime());
			}else{
				item.setStatus(Status.FAILURE);
				item.setDescription("依赖任务["+detailId+"]执行返回失败！");
				
				planDetailDao.update(item);
			}
		}
	}
	
	/**
	 * 执行计划明细
	 */
	public List<PlanDetail> listDetail(String planId){
		if( StringUtils.isEmpty(planId) )
			return null;
		
		ListPage<PlanDetail> page = new ListPage<PlanDetail>(Integer.MAX_VALUE);
		SearchCondition sc = new SearchCondition();
		sc.equal("plan.id", planId );
		
		page.addAscending("sequence");
		page = this.planDetailDao.list(page, sc);
		
		return page.getList();
	}
	
	/**
	 * 执行计划明细
	 */
	public List<PlanDetail> listDetail(Plan plan){
		return listDetail(plan.getId());
	}
	
	/**
	 * 查询执行计划明细
	 */
	public List<PlanDetail> listDetail( Object[] ids ){
		ListPage<PlanDetail> page = new ListPage<PlanDetail>(Integer.MAX_VALUE);
		SearchCondition sc = new SearchCondition();
		sc.in("id",ids);
		
		page.addAscending("sequence");
		this.planDetailDao.list(page,sc);
		
		return page.getList();
	}
	
	/**
	 * 取创建时间最新记录
	 */
	public Plan getLast(SchedulerType type){
		if( type == null || type.getId() == null )
			return null;
		
		return getLastByType( type.getId() );
	}
	
	/**
	 * 取创建时间最新记录
	 */
	public Plan getLastByType(String type){
		if(  StringUtils.isEmpty(type) )
			return null;
		
		ListPage<Plan> page = new ListPage<Plan>(1);
		SearchCondition sc = new SearchCondition();
		sc.equal("type.id", type );
		
		page.addDescending("createdTime");
		
		return dao.list(page, sc).getFirst();
	}
	
	/**
	 * 保存执行计划
	 */
	public void save( Plan plan,List<PlanDetail> details ){
		this.save(plan, details, true );
	}
	
	/**
	 * 保存执行计划
	 */
	public void save( Plan plan,List<PlanDetail> details,boolean putAllInQueue ){
		if( plan == null || details == null )
			throw new ApplicationException("参数为空！");
		
		plan.setId( sequenceManager.nextSequenceNumber(plan)
				.replaceFirst(Plan.SEQUENCE_KEY,"") );
		dao.create(plan);
		
		List<String> refIds = new ArrayList<String>();
		for( PlanDetail item:details ){
			item.setId( plan.getId() + "-" + item.getSequence() );
			item.setPlan(plan);
			
			//putAllInQueue=true,所有任务进入执行队行
			//putAllInQueue=false,只有没有依赖的任务才进入执行队列
			if( putAllInQueue || !item.isRelyOn() )
				refIds.add(item.getId());
		}
		planDetailDao.create(details);
		
		jobQueueManager.jobInQueue(plan.getType().getId()
				,1,refIds.toArray(new String[0]),plan.getPlanFireTime());
	}
	
	/**
	 * 明细入任务队列
	 */
	public void detailInQueue(PlanDetail pd){
		PlanDetail detail = this.getDetail( pd.getId() );
		if( detail == null )
			throw new ApplicationException("明细数据["+pd.getId()+"]不存在！");
		
		if( detail.getPlan() == null || detail.getPlan().getType() == null )
			throw new ApplicationException("明细数据["+pd.getId()+"]不完整！");
		
		jobQueueManager.jobInQueue(
				detail.getPlan().getType().getId(),detail.getId(),1,detail.getPlan().getPlanFireTime());
	}
	
	/**
	 * 更新执行时间
	 */
	public void updateFireTime(String id,Calendar fireTime){
		if( StringUtils.isEmpty(id) )
			return;
		
		if( fireTime == null )
			fireTime = Calendar.getInstance();
		
		Plan plan = dao.read(id);
		if( plan != null ){
			plan.setFireTime(fireTime);
			dao.update(plan);
		}
	}
	
	/**
	 * 更新完成时间
	 */
	public void updateFinishTime(String id,Calendar finishTime){
		if( StringUtils.isEmpty(id) )
			return;
		
		if( finishTime == null )
			finishTime = Calendar.getInstance();
		
		Plan plan = dao.read(id);
		if( plan != null ){
			if( plan.getFinishTime() == null )
				plan.setFinishTime(finishTime);
			plan.setFinished(Boolean.TRUE);
			
			dao.update(plan);
		}
	}
	
	/**
	 * 查询依赖未完成任务
	 */
	public List<PlanDetail> listRelyOnUnsuccessful( String id ){
		return this.planDetailDao.listRelyOnUnsuccessful(id);
	}
	
	/**
	 * 更新数据状态
	 */
	public void updateDetailStatus(Collection<Result> results){
		if( results == null || results.isEmpty() )
			return;
		
		String anyTaskId = null;
		for( Result result:results ){
			PlanDetail detail = planDetailDao.readWithLock( result.getKey() );
			if( detail != null ){
				if( detail.getFireTime() == null )
					detail.setFireTime(Calendar.getInstance());
				
				PlanDetail.Status newStatus = PlanDetail.Status.WAITING_FOR_REPLY;
				
				if( result.isInvokeSuccess()){
					newStatus = detail.isSynTask()?PlanDetail.Status
							.WAITING_FOR_REPLY:PlanDetail.Status.SUCCESS;
					detail.setFinishTime( Calendar.getInstance() );
				}else{
					newStatus = result.isException()
						?PlanDetail.Status.EXCEPTION:PlanDetail.Status.FAILURE;
					detail.setErrMsg( TextFormater.format(result.getMessage(),497,true) );
					detail.incFailureCount( );
				}
				
				//detail.setStatus(newStatus);
				//planDetailDao.update(detail);
				
				planDetailDao.updateFireTimeAfterInvoke(detail.getId(),detail.getFireTime());
				
				//int i = planDetailDao.update(detail.getId(),PlanDetail.Status.NEW, newStatus);
				//int i =planDetailDao.update("id",detail.getId(),"status",detail.getStatus(), newStatus);
				
				int i = planDetailDao.updateStatusAfterInvoke(detail.getId(),newStatus);
				
				log.info("计划任务[{}]状态从["+detail.getStatus()+"]更新为["
						+newStatus+"]-[{}]",detail.getId(),i>0?"成功":"失败");
				
				anyTaskId = detail.getId();
			}
		}//end for
		
		//如果计划任务明细执行完成，确认完成标志更新成功
		updateStatus( anyTaskId );
	}

}
