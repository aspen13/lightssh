package com.google.code.lightssh.project.sequence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Repository;

import com.google.code.lightssh.common.dao.jpa.JpaAnnotationDao;
import com.google.code.lightssh.project.sequence.entity.Sequence;

@Repository("sequenceDao")
public class SequenceDaoJpa extends JpaAnnotationDao<Sequence> implements SequenceDao {

	private static final long serialVersionUID = 8323290441670520984L;

	@Override
	public long nextDatabaseSequenceNumber(final String seqName ) {
		final String sql = "select "+seqName+".nextval  from dual";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SessionImpl session = null;
		try{
			session = ((SessionImpl)getEntityManager().getDelegate());
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				return rs.getLong(1);
			}
			
		}catch( Exception e ){
			//ignore
		}finally{
			//session.close();
			close( rs,ps,conn);
		}
		
		return -1;
		
		/*
		Long result = getEntityManager().unwrap( org.hibernate.Session.class).doReturningWork(
				new ReturningWork<Long>(){
					public Long execute(Connection conn) throws SQLException { 
						PreparedStatement ps = conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							return rs.getLong(1);
						}
						return null;
			        }
				}
			); 
		return result==null?0:result;
		*/
	}


}
