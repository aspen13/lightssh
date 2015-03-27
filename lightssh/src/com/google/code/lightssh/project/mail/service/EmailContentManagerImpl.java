package com.google.code.lightssh.project.mail.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.dao.SearchCondition;
import com.google.code.lightssh.common.model.Result;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.service.BaseManagerImpl;
import com.google.code.lightssh.project.mail.dao.EmailContentDao;
import com.google.code.lightssh.project.mail.entity.EmailContent;
import com.google.code.lightssh.project.mail.entity.EmailContent.Status;
import com.google.code.lightssh.project.scheduler.entity.JobQueue;

/** 
 * @author YangXiaojin
 * @date 2012-11-13
 * @description：TODO
 */
@Component("emailContentManager")
public class EmailContentManagerImpl extends BaseManagerImpl<EmailContent> implements EmailContentManager{
	
	private static final long serialVersionUID = 7414699452624813838L;
	
	//@Resource(name="jobQueueManager")
	//private JobQueueManager jobQueueManager;
	
	@Resource(name="emailSendManager")
	private EmailSendManager emailSendManager;
	
	@Resource(name="secondaryTaskExecutor")
	private TaskExecutor taskExecutor;
	
	@Resource(name="emailContentDao")
	public void setDao(EmailContentDao dao ){
		super.dao = dao;
	}
	
	public EmailContentDao getDao(){
		return (EmailContentDao)this.dao;
	}
	
	/**
	 * 邮件入队列
	 */
	public void send( String addressee, String subject, String content ){
		EmailContent ec = newTransactionSave(addressee,subject,content);
		//send(ec);
		sendByThreadPool(ec);
	}
	
	/**
	 * 邮件入队列
	 */
	public EmailContent newTransactionSave( String addressee, String subject, String content ){
		EmailContent ec = new EmailContent();
		ec.setType(EmailContent.Type.HTML );
		ec.setStatus( Status.NEW );
		ec.setFailureCount(0);
		
		ec.setAddressee(addressee);
		ec.setSubject(subject);
		ec.setContent(content);
		
		save(ec);
		
		return ec;
		//jobQueueManager.jobInQueue(QUEUE_KEY,ec.getId() );
	}
	
	/**
	 * 发邮件
	 */
	public void sendByThreadPool(final EmailContent ec ){
		taskExecutor.execute( new Runnable() {
            public void run() {
            	emailSendManager.send(ec);
            }
		});
	}
	
	/**
	 * 更新状态
	 */
	public void updateStatus(boolean success,EmailContent ec ){
		EmailContent entity = dao.read(ec);
		if( success ){
			entity.setFinishedTime(Calendar.getInstance());
			entity.setStatus( Status.SUCCESS );
		}else{
			entity.setStatus( Status.FAILURE );
			entity.incFailureCount( );
			entity.setErrMsg(ec.getErrMsg());
		}
		
		entity.setSender(ec.getSender());
		
		dao.update(entity);
	}
	
	/**
	 * 重发邮件
	 */
	public void resend(EmailContent t ){
		EmailContent ec = updateStatus(t,Status.RESEND);
		if( ec != null){
			sendByThreadPool( ec );
			/*
			jobQueueManager.jobInQueue(
					EmailContentManager.QUEUE_KEY,ec.getId(),2);
			*/
		}
	}
	
	/**
	 * 更新状态
	 */
	public EmailContent updateStatus(EmailContent t,Status status){
		EmailContent ec = get(t);
		if( ec != null){
			ec.setStatus( status );
			dao.update(ec);
		}
		
		return ec;
	}
	
	public ListPage<EmailContent> list(ListPage<EmailContent> page,EmailContent t ) {
		SearchCondition sc = new SearchCondition();
		if( t != null ){
			if( t.getType() != null)
				sc.equal("type",t.getType() );
			
			if( t.getStatus() != null )
				sc.equal("status",t.getStatus() );
			
			if( !StringUtils.isEmpty(t.getAddressee()) )
				sc.like("addressee",t.getAddressee() );
				
		}
		
		if(page == null )
			page = new ListPage<EmailContent>();
		
		page.addDescending("createdTime");
		
		return dao.list(page,sc);
	}
	
	/**
	 * 邮件发送成功
	 */
	public void updateStatus(Collection<Result> results ){
		List<EmailContent> list = new ArrayList<EmailContent>();
		
		for( Result result:results ){
			EmailContent ec = get(result.getKey());
			if( ec == null )
				continue;
			
			list.add(ec);
			if( result.isSuccess() ){
				ec.setFinishedTime(Calendar.getInstance());
				ec.setStatus( Status.SUCCESS );
			}else{
				ec.setStatus( Status.FAILURE );
				if( result.getObject() != null && result.getObject() instanceof JobQueue ){
					JobQueue jq = (JobQueue)result.getObject();
					ec.incFailureCount( jq.getFailureCount());
					ec.setErrMsg(jq.getErrMsg());
				}
			}
		}
		
		dao.update(list);
	}
	
}
