package com.google.code.lightssh.project.scheduler.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.scheduler.entity.PlanDetail;


/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("planDetailDao")
public class PlanDetailDaoJpa extends JpaDao<PlanDetail> implements PlanDetailDao{

	private static final long serialVersionUID = 2299825707757948995L;
	
	/**
	 * 查询依赖未完成任务
	 */
	@SuppressWarnings("unchecked")
	public List<PlanDetail> listRelyOnUnsuccessful( String id ){
		if( StringUtils.isEmpty(id) )
			return null;
		
		StringBuffer sb = new StringBuffer(" FROM " + entityClass.getName());
		sb.append(" AS m WHERE m.precondition.id = ? AND m.status != ? ");
		
		Query query = getEntityManager().createQuery(sb.toString());
		addQueryParams(query,new Object[]{id,PlanDetail.Status.SUCCESS});
		
		return query.getResultList();
	}
	
	/**
	 * 更新计划任务明细
	 */
	public int update(String id,PlanDetail.Status originalStatus,PlanDetail.Status newStatus ){
		String hql = " UPDATE PlanDetail AS m SET m.status = '" +newStatus.name()
			+ "' WHERE m.id = '"+id+"' AND m.status = '"+originalStatus.name()+"'" ;
		Query query = getEntityManager().createQuery(hql);
		return query.executeUpdate();
	}
	
	/**
	 * 定时任务调用完成更新状态
	 * 如果异步回执已更新状态为'成功'或'失败'则不进行状态更新
	 */
	public int updateStatusAfterInvoke(String id,PlanDetail.Status newValue ){
		String hql = "UPDATE " + this.entityClass.getName() 
			+ " AS m SET m.status = ? WHERE m.id = ? AND m.status not in (?) " ;
		
		List<Object> params = new ArrayList<Object>( );
		params.add( newValue );
		params.add( id );
		params.add(PlanDetail.Status.SUCCESS );
		//params.add(PlanDetail.Status.FAILURE ); //取消失败
		
		Query query = getEntityManager().createQuery(hql);
		this.addQueryParams(query, params);
		return query.executeUpdate();
	}
	
	/**
	 * 定时任务调用完成更新'开始执行时间'
	 */
	public int updateFireTimeAfterInvoke(String id,Calendar fireTime ){
		String hql = "UPDATE " + this.entityClass.getName() 
		+ " AS m SET m.fireTime = ? WHERE m.id = ? AND m.fireTime IS NULL " ;
		
		List<Object> params = new ArrayList<Object>( );
		params.add( fireTime );
		params.add( id );
		
		Query query = getEntityManager().createQuery(hql);
		this.addQueryParams(query, params);
		return query.executeUpdate();
	}

}
