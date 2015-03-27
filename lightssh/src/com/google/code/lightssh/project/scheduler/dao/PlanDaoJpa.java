package com.google.code.lightssh.project.scheduler.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.project.scheduler.entity.Plan;

/**
 * 
 * @author YangXiaojin
 *
 */
@Repository("planDao")
public class PlanDaoJpa extends JpaDao<Plan> implements PlanDao{

	private static final long serialVersionUID = -1286910597664170719L;

	/**
	 * 所有明细执行完成后更新计划任务状态
	 */
	public boolean updateStatusIfAllDetailFinished( String id ){
		StringBuffer sql = new StringBuffer( );
		String subSql = " SELECT count(1) - sum( decode(STATUS,'SUCCESS',1,0)) FROM T_SCHEDULER_PLAN_DETAIL where plan_id = ? ";
		sql.append(" update T_SCHEDULER_PLAN set finished = 1,finish_time=sysdate ");
		sql.append(" where id =  ? AND ( "+ subSql +" ) = 0");
		
		Query query = getEntityManager().createNativeQuery(sql.toString());
		return addQueryParams(query, new Object[]{id,id}).executeUpdate() > 0;
	}
	

}
