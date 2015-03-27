
package com.google.code.lightssh.project.scheduler.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.project.scheduler.entity.JobQueue;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail;
import com.google.code.lightssh.project.scheduler.entity.SchedulerType;

/** 
 * @author YangXiaojin
 * @date 2013-1-8
 * @description：批次执行
 */

public abstract class BatchSchedulerService extends AbstractSchedulerService{
	
	private static final long serialVersionUID = -398051282193654224L;
	
	protected PlanManager planManager;
	
	public PlanManager getPlanManager() {
		return planManager;
	}

	@Resource(name="planManager")
	public void setPlanManager(PlanManager planManager) {
		this.planManager = planManager;
	}

	/**
	 * 批次明细执行
	 */
	public abstract Result doBatchBusiness(JobQueue jq,PlanDetail detail,Map<String,Result> mapResults);
	
	/**
	 * 检查依赖
	 */
	protected Result checkRelyOn(JobQueue jq,PlanDetail detail,Map<String,Result> mapResults){
		Result result = new Result(true,jq.getRefId(),jq,null);
		PlanDetail relyOn = detail.getPrecondition();
		if( relyOn != null ){
			String relyOnKey = relyOn.getId();
			//从数据库中取最新记录
			PlanDetail dbRelyOn = planManager.getDetail(relyOnKey);
			if( dbRelyOn != null && dbRelyOn.getStatus() != null 
					&& !dbRelyOn.getStatus().equals( relyOn.getStatus() )){
				relyOn = dbRelyOn;
				detail.setPrecondition( dbRelyOn );
			}
			boolean relyOnFinished = relyOn.isFinished();//已经完成
			boolean exeRelyOnFinished = mapResults.get(relyOnKey)!=null 
				&& mapResults.get(relyOnKey).isSuccess(); //当前批次执行完成
			
			if( !(relyOnFinished || exeRelyOnFinished) ){
				result.setStatus(false);
				result.setMessage("依赖任务["+relyOnKey+"]未完成！");
				return result;
			}
		}
		
		return result;
	}

	@Override
	public Collection<Result> doBusiness(Collection<JobQueue> items){
		List<PlanDetail> details = new ArrayList<PlanDetail>();
		Map<String,JobQueue> mapQueue = new HashMap<String,JobQueue>();
		String planId = null;
		for( JobQueue item:items ){
			PlanDetail detail = planManager.getDetail(item.getRefId());
			if( detail == null )
				throw new ApplicationException("任务队列["
						+getJobTypeKey()+"]关联["+item.getRefId()+"]业务数据不存在！");
			
			planId = detail.getPlan()==null?null:detail.getPlan().getId();
			details.add( detail );
			mapQueue.put(item.getRefId(), item);
		}
		
		planManager.updateFireTime(planId,Calendar.getInstance());
		details = PlanDetail.sort(details); //排序
		
		List<Result> results = new ArrayList<Result>();//返回结果
		Map<String,Result> mapResults = new HashMap<String,Result>();
		for( PlanDetail detail:details ){
			SchedulerType type = detail.getType();
			String key = detail.getId();
			Result result = null;
			if( type == null || StringUtils.isEmpty(type.getId()) ){
				result = new Result(false,key,mapQueue.get(key)
						,"关联业务数据["+key+"]类型为空，无法进行业务处理。");
				mapResults.put(key,result);
				continue;
			}
				
			//批处理
			result = doBatchBusiness(mapQueue.get(key),detail,mapResults);
			
			if( result == null ){
				result = new Result(false,key,mapQueue.get(key),"未实现业务处理！");
			}
			
			mapResults.put(key,result);
		}
		
		//返回结果顺序与参数保持一样
		for( JobQueue item:items ){
			results.add( mapResults.get(item.getRefId() ) );
		}
		
		if( isFinishBatch(planId,mapResults) )
			planManager.updateFinishTime(planId,Calendar.getInstance());
		
		return results;
	}
	
	/**
	 * 批次是否完成
	 * 判断已完成的执行及当前处理结果
	 */
	protected boolean isFinishBatch(String planId,Map<String,Result> mapResults){
		if( mapResults == null || mapResults.isEmpty() )
			return false;
		
		boolean exeSuccess = true;
		for( Result item: mapResults.values())
			exeSuccess = exeSuccess && item.isSuccess();
		
		if( !exeSuccess )
			return false;
		
		List<PlanDetail> details = planManager.listDetail(planId);
		if( details == null || details.isEmpty() )
			return false;
		
		for( PlanDetail detail:details ){
			Result result = mapResults.get( detail.getId() );
			boolean resultSuccess = (result != null) && result.isSuccess(); //执行结果成功
			exeSuccess = exeSuccess && (detail.isFinished()||resultSuccess);
		}
		
		return exeSuccess;
	}

	@Override
	public void postJobExecute(Collection<Result> results) {
		planManager.updateDetailStatus(results);
	}

}
