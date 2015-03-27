package com.google.code.lightssh.project.sequence.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.code.lightssh.common.ApplicationException;
import com.google.code.lightssh.common.model.Sequenceable;
import com.google.code.lightssh.project.sequence.dao.SequenceDao;
import com.google.code.lightssh.project.sequence.entity.Sequence;

/**
 * Sequence Manager implements
 * @author YangXiaojin
 *
 */
@Component( "sequenceManager" )
public class SequenceManagerImpl implements SequenceManager{
	
	private static final long serialVersionUID = 8897157086717995506L;
	
	private SequenceDao dao;

	@Resource( name="sequenceDao" )
	public void setDao(SequenceDao dao) {
		this.dao = dao;
	}
	
	public Sequence nextSequence(String key,int step ){
		if( key == null )
			throw new ApplicationException("获取序列的参数KEY为空！");
		
		boolean inserted = false;
		Sequence seq = dao.readWithLock( key );
		if( seq == null ){
			seq = new Sequence( key );
			inserted =true;
		}
		seq.incLastNumber( step );
		
		if( inserted )
			dao.create( seq );
		else
			dao.update( seq );
		
		return seq;
	}

	@Override
	public Sequence nextSequence(Sequenceable sa) {
		if( sa == null )
			throw new ApplicationException("获取序列的参数为空！");
		
		return nextSequence( sa.getSequenceKey(),sa.getSequenceStep() );
	}

	@Override
	public Sequence nextSequence(String key) {
		return nextSequence(key,1);
	}

	@Override
	public String nextSequenceNumber(Sequenceable sa) {
		return sa.getSequenceKey() + nextSerialNumber( sa );
	}
	
	@Override
	public String newTransactionNextSequenceNumber(Sequenceable sa) {
		return nextSequenceNumber( sa );
	}
	
	public String nextSerialNumber(Sequenceable sa) {
		Sequence seq = nextSequence( sa );
		if( seq == null )
			throw new ApplicationException("获取得的序列为空！");
		
		return seq.getFormatLastNumber( sa.getSequenceLength() );
	}
	
	@Override
	public long nextDatabaseSerialNumber(String seqName) {
		return dao.nextDatabaseSequenceNumber(seqName);
	}

	@Override
	public String nextDatabaseSequenceNumber(Sequenceable sa, String seqName) {
		return sa.getSequenceKey() + StringUtils.leftPad(
				Long.toString(nextDatabaseSerialNumber(seqName)),sa.getSequenceLength(), "0");
	}
	
}
