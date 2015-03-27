package com.google.code.lightssh.project.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.dao.jpa.JpaDao;
import com.google.code.lightssh.common.model.page.ListPage;
import com.google.code.lightssh.common.util.StringUtil;
import com.google.code.lightssh.project.party.entity.Organization;
import com.google.code.lightssh.project.party.entity.Party;
import com.google.code.lightssh.project.security.entity.LoginAccount;
import com.google.code.lightssh.project.security.entity.Permission;
import com.google.code.lightssh.project.security.entity.Role;
import com.google.code.lightssh.project.util.constant.AuditStatus;

/**
 * LoginAccount Dao Hibernate implement
 * @author YangXiaojin
 *
 */
@Repository("loginAccountDao")
public class LoginAccountDaoJpa extends JpaDao<LoginAccount> 
	implements LoginAccountDao{

	private static final long serialVersionUID = 4617159875674281868L;

	@SuppressWarnings("unchecked")
	@Override
	public LoginAccount get(String loginName) {
		String hql = " SELECT m FROM " + entityClass.getName() + " AS m WHERE m.loginName = ?1 ";
		//List<LoginAccount> results = getJpaTemplate().find(hql, loginName );
		Query query = this.getEntityManager().createQuery(hql);
		this.addQueryParams(query,loginName);
		List<LoginAccount> results = query.getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}
	
	/**
	 * 根据电子邮箱查登录帐号
	 */
	@SuppressWarnings("unchecked")
	public LoginAccount getByEmail(String email) {
		String hql = " SELECT m FROM " + entityClass.getName() + " AS m WHERE m.email = ?1 ";
		//List<LoginAccount> results = getJpaTemplate().find(hql, email );
		
		Query query = this.getEntityManager().createQuery(hql);
		this.addQueryParams(query,email);
		List<LoginAccount> results = query.getResultList();
		
		return (results==null||results.isEmpty())?null:results.get(0);
	}
	
	/**
	 * 组装LoginAccount对象
	 */
	@SuppressWarnings("unused")
	private LoginAccount buildObject( ResultSet rs ){
		if( rs == null )
			return null;
		
		LoginAccount la = new LoginAccount();
		try{
			la.setId( rs.getLong("ID"));
			la.setPartyId(rs.getString("PARTY_ID"));
			la.setLoginName(rs.getString("LOGIN_NAME"));
			la.setPassword( rs.getString("PASSWORD"));
			la.setStatus(AuditStatus.valueOf( rs.getString("STATUS") ));
			la.setPeriod( rs.getDate("PERIOD_START"), rs.getDate("PERIOD_END"));
			Calendar cal = Calendar.getInstance();
			Timestamp lockedTime = rs.getTimestamp("LAST_LOGIN_LOCK_TIME");
			if( lockedTime != null ){
				cal.setTime( lockedTime );
				la.setLastLoginLockTime( cal );
			}
			//la.setUseCa( rs.getBoolean("USE_CA") );
			//la.setType( LoginAccount.LoginAccountType.valueOf( rs.getString("TYPE") ));
		}catch( SQLException e ){
			e.printStackTrace();
		}
		
		return la;
	}
	
	public LoginAccount getWithPartyIdentity(final String loginName){
		return this.get(loginName);
		
		/*
		//这种方式对连接池释放连接有用//TODO
		final String sql_loginaccount = " select * from T_SECURITY_LOGINACCOUNT t where t.login_name = ? ";
		
		LoginAccount result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SessionImpl session = null;
		try{
			session = ((SessionImpl)getEntityManager().getDelegate());
			conn = session.connection();
			ps = conn.prepareStatement(sql_loginaccount);
			ps.setString(1, loginName);
			rs = ps.executeQuery();
			while(rs.next()){
				result = buildObject( rs );
				break;
			}
			
		}catch( Exception e ){
			//ignore
		}finally{
			session.close();
			close( rs,ps,conn);
		}
		return result;
		*/
		
		//Query query = getEntityManager().createNativeQuery(sql_loginaccount);
		//Object result = addQueryParams(query,new Object[]{loginName}).getSingleResult();
		
		/*
		//这种方式无法释放连接池连接//TODO
		return getEntityManager().unwrap( org.hibernate.Session.class).doReturningWork(
			new ReturningWork<LoginAccount>(){
				public LoginAccount execute(Connection conn) throws SQLException { 
					PreparedStatement ps = conn.prepareStatement(sql_loginaccount);
					ps.setString(1, loginName);
					ResultSet rs = ps.executeQuery();
					LoginAccount result = null;
					while(rs.next()){
						result = buildObject( rs );
						break;
					}
					
					close( rs,ps,conn);
					
					return result;
		        }
			}
		); 
		*/
	}

	public ListPage<LoginAccount> list(ListPage<LoginAccount> page,LoginAccount t ){
		if( t == null )
			return list( page ); 
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer( );
		
		hql.append( " FROM " + entityClass.getName() + " AS m " );
		hql.append( " WHERE 1=1 ");
		if( t.getLoginName() != null && t.getLoginName().trim() != null 
				&& !"".equals(t.getLoginName().trim())){
			hql.append( " AND m.loginName like ? " );
			params.add( "%" + t.getLoginName().trim() + "%");
		}
		
		if( t.getType() != null ){
			hql.append( " AND m.type = ? " );
			params.add( t.getType() );
		}
		
		if( t.getStatus() != null ){
			hql.append( " AND m.status = ? " );
			params.add( t.getStatus());
		}
		
		if( t.get_createDatePeriod() != null ){
			Calendar cal = Calendar.getInstance();
			Date start = t.get_createDatePeriod().getStart();
			Date end = t.get_createDatePeriod().getEnd();
			
			if( start != null ){
				hql.append( " AND m.createDate >= ? " );
				params.add( start );
			}
			
			if( end != null ){
				cal.setTime(end);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				cal.add(Calendar.SECOND, -1);
				
				hql.append( " AND m.createDate <= ? " );
				params.add( cal.getTime() );
			}
		}
		
		int subquery_flag = params.size();
		if(t.getParty()!=null){
			String entityClass = Party.class.getName();
			StringBuffer subQuery = new StringBuffer( );
			
			if( StringUtil.clean(t.getParty().getIdentity()) != null ){
				subQuery.append(" AND n.id like ?");
				params.add("%"+StringUtil.clean(t.getParty().getIdentity())+"%");
			}
			
			String name=StringUtil.clean(t.getParty().getName());
			if( name !=null){
				subQuery.append(" AND n.name like ?");
				params.add("%"+name+"%");
			}
			
			/*
			if(t.getParty() instanceof Member ){
				int member_flag = params.size();
				Member member = (Member)t.getParty();
				if( member.getPartyStatus() != null ){
					subQuery.append(" AND n.partyStatus = ? " );
					params.add( member.getPartyStatus() );
				}
				
				if( StringUtil.clean(member.getIdentity()) != null ){
					subQuery.append(" AND n.id = ? " );
					params.add( StringUtil.clean(member.getIdentity()) );
				}
				
				if( params.size() > member_flag )
					entityClass = Member.class.getName();
			}else */
			if( t.getParty() instanceof Organization ){
				int member_flag = params.size();
				//Query
				if( params.size() > member_flag )
					entityClass = Organization.class.getName();
			}
			
			if( params.size() > subquery_flag ){
				String sub_hql = " SELECT n.id FROM " + entityClass + " As n WHERE 1=1 ";
				hql.append( " AND m.party.id in ( " + sub_hql + subQuery.toString() + "  ) ");
			}
		}
		
		return super.query(page, hql.toString(), params.toArray( ) );
	}
	
	public void updateRole( final LoginAccount account ){
		throw new ApplicationException("DAO未实现！");
	}
	
	public ListPage<LoginAccount> listLight(ListPage<LoginAccount> page,LoginAccount t ){
		StringBuffer hql = new StringBuffer();

		List<Object> params = new ArrayList<Object>();
		String select = " SELECT new " + entityClass.getName() + "(m.id,m.loginName) ";
		hql.append(" FROM " + entityClass.getName() + " AS m WHERE 1=1 ");
		if( t != null ){
			if( t.getType() != null ){
				hql.append(" AND m.type = ? " );
				params.add( t.getType() );
			}
		}
		
		return super.query(page,select,hql.toString(),params.toArray() );
	}
	
	@SuppressWarnings("unchecked")
	public List<LoginAccount> listByPermission(final Permission p ){
		if( p == null || p.getIdentity() == null )
			return null;

		String tokens = "select sp.token from T_SECURITY_PERMISSION sp where sp.TOKEN = ? ";
		
		String roles = "select sr.ID from T_SECURITY_ROLE sr "
			+ " left join T_REF_ROLE_PERMISSION ref_rp " 
			+ " on ref_rp.role_id = sr.id where ref_rp.permission_id in " 
			+ "( " + tokens + " ) ";
		
		final String sql = "select distinct sla.* from T_SECURITY_LOGINACCOUNT sla " 
			+ " left join T_REF_LOGINACCOUNT_ROLE ref_lr on sla.id = ref_lr.loginaccount_id " 
			+ " where ref_lr.role_id in( " + roles + " ) order by sla.LOGIN_NAME asc ";
		
		Query query = addQueryParams(getEntityManager().createNativeQuery(
				sql.toString(), super.entityClass ), p.getToken() );
		
		return query.getResultList();
	}
	
	/**
	 * 根据角色ID或角色名称查询用户
	 */
	@SuppressWarnings("unchecked")
	public List<LoginAccount> listByRole(AuditStatus status,final Role r ){
		if( r == null || (StringUtils.isEmpty(r.getIdentity()) 
				&& StringUtils.isEmpty(r.getName()) ))
			return null;
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer roles = new StringBuffer(
				"select sr.ID from T_SECURITY_ROLE sr where 1=1 ");
		
		if( StringUtils.isNotEmpty(r.getIdentity()) ){
			roles.append(" AND sr.ID = ?");
			params.add( r.getIdentity().trim());
		}
		
		if( StringUtils.isNotEmpty(r.getName()) ){
			roles.append(" AND sr.NAME = ?");
			params.add( r.getName().trim());
		}
		
		
		String la_ids = "select distinct rlr.loginaccount_id from "
				+"T_REF_LOGINACCOUNT_ROLE rlr where rlr.ROLE_ID in( "+roles+" )";
		
		StringBuffer sql = new StringBuffer(
				"select * from T_SECURITY_LOGINACCOUNT where 1=1 AND id in( "+la_ids+" )");
		if( status != null ){
			sql.append(" AND STATUS = ? ");
			params.add( status.name() );
		}
		
		Query query = addQueryParams(getEntityManager().createNativeQuery(
				sql.toString(), super.entityClass ), params);
		
		return query.getResultList();
	}
	
	/**
	 * 更新登录锁定时间
	 */
	public int updateLockTime( Long id ,Calendar time){
		if(time == null)
			time = Calendar.getInstance();
		
		String hql = "UPDATE " + this.entityClass.getName() 
			+ " SET lastLoginLockTime = ? WHERE id = ? ";
		
		Query query = getEntityManager().createQuery(hql);
		this.addQueryParams(query,new Object[]{time,id});
		
		return query.executeUpdate();
	}
	
	/**
	 * 清除登录锁定时间
	 */
	public int cleanLockTime( Long id ){
		String hql = "UPDATE " + this.entityClass.getName() 
				+ " SET lastLoginLockTime = NULL WHERE id = ? ";
		
		Query query = getEntityManager().createQuery(hql);
		this.addQueryParams(query,new Object[]{id});
		
		return query.executeUpdate();
	}
	
}
