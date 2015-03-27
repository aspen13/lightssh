package com.google.code.lightssh.project.scheduler.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.project.scheduler.entity.JobQueue;

/**
 * 抽象任务业务执行类
 * @author YangXiaojin
 * 
 * TODO 共享业务数据?
 */
public abstract class AbstractSchedulerService implements SchedulerService{

	private static final long serialVersionUID = -9096702523848816901L;
	
	private static final int DEFAULT_LOT_EXECUTE_COUNT = 50;
	
	private static final Logger log = LoggerFactory.getLogger(AbstractSchedulerService.class);
	
	private JobQueueManager jobQueueManager;
	
	@Resource(name="jobQueueManager")
	public void setJobQueueManager(JobQueueManager jobQueueManager) {
		this.jobQueueManager = jobQueueManager;
	}

	/**
	 * 待处理条数
	 */
	protected int getHandleCount(){
		String job_type_id = getJobTypeKey( );
		if( StringUtils.isEmpty(job_type_id) )
			return 0;
		
		return this.jobQueueManager.getJobQueueSize(job_type_id);
	}
	
	/**
	 * 是否单笔执行
	 */
	protected boolean isSingleExecute(){
		return true;
	}
	
	/**
	 * 第批处理条数
	 */
	protected int getLotExecuteCount(){
		return DEFAULT_LOT_EXECUTE_COUNT;
	}
	
	/**
	 * 任务主键
	 */
	public abstract String getJobTypeKey( );
	
	/**
	 * 业务处理
	 */
	public abstract Collection<Result> doBusiness( Collection<JobQueue> items );
	
	/**
	 * 任务队列处理
	 */
	public abstract void postJobExecute(Collection<Result> results);
	
	@Override
	public void execute() {
		try{
			int allSize = getHandleCount();
			if( allSize <= 0 )
				return;
			
			int successCount = 0; //发送成功数目
			ListPage<JobQueue> page = new ListPage<JobQueue>(0);
			page.setAllSize(allSize);
			page.setSize( getLotExecuteCount() );
			
			for( int i=0;i<page.getAllPage();i++ ){
				page.setNumber(i+1);
				page = jobQueueManager.list(page,getJobTypeKey());
				
				if( page == null )
					break;
				
				if( page.getList() == null || page.getList().isEmpty() )
					continue;
				
				Collection<Result> results = executeJobQueue(page.getList());
				for( Result item: results)
					if( item.isSuccess() )
						successCount++;
			}
			
			log.debug("任务队列待处理数目[{}],成功完成数目[{}]!",allSize,successCount);
		}catch( Exception e ){
			log.error( "定时任务异常：",e );
		}
	}
	
	/**
	 * 执行任务队列数据
	 */
	protected Collection<Result> executeJobQueue( Collection<JobQueue> items ){
		Collection<Result> results = null;
		if( items == null || items.isEmpty() ){
			results = new ArrayList<Result>();
			results.add( new Result(false,"参数为空！") );
			return results;
		}
		
		try{
			results = doBusiness(items);//业务处理

			if( results == null )
				throw new ApplicationException("返回结果为空！");
			//Map<String,JobQueue> map = new HashMap<String,JobQueue>();
			
			for(Result result:results ){
				JobQueue item = (JobQueue)result.getObject();
				if( item == null ){//TODO
					throw new ApplicationException("返回结果Result[key="
							+result.getKey()+"]关联Object为空！");
				}
				
				if( !result.isSuccess() && item != null ){
					item.incFailureCount();
					item.setStatus(JobQueue.Status.FAILURE );
					item.setErrMsg(result.getMessage());
				}
			}
		}catch( Exception e ){
			results = new ArrayList<Result>();
			for(JobQueue item:items){
				results.add(new Result(false,item.getRefId(),item,e.getMessage()));
				
				item.incFailureCount();
				item.setStatus( JobQueue.Status.FAILURE );
				
				String errMsg = e.getMessage()+(e.getCause()==null?"":e.getCause().getMessage());
				item.setErrMsg( errMsg.length()>199?errMsg.substring(0,199):errMsg );
				
				log.error("任务队列[id="+item.getIdentity()+",type="
						+item.getType().getName()+",ref_id="
						+item.getRefId()+"]执行异常：",e);
			}
			
		}
		
		List<JobQueue> outQueues = new ArrayList<JobQueue>();//成功队列
		List<JobQueue> updateQueues = new ArrayList<JobQueue>();//更新队列
		for(Result item:results ){
			JobQueue jobQueue = (JobQueue)item.getObject();
			if( item.isSuccess() || jobQueue.isDeletable() )
				outQueues.add(jobQueue);
			else
				updateQueues.add(jobQueue);
		}
		
		//成功或执行达到最大调用次数，从队列中删除
		if( !outQueues.isEmpty() )
			jobQueueManager.remove( outQueues );
		
		if( !updateQueues.isEmpty() )
			jobQueueManager.update( updateQueues );
		
		if( !results.isEmpty() )
			postJobExecute(results);
		
		return results;
	}
	
}
