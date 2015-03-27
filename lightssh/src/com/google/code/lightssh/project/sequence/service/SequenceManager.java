package com.google.code.lightssh.project.sequence.service;

import com.google.code.lightssh.common.model.Sequenceable;
import com.google.code.lightssh.common.service.Manager;
import com.google.code.lightssh.project.sequence.entity.Sequence;

/**
 * Sequence Manager
 * @author YangXiaojin
 *
 */
public interface SequenceManager extends Manager{
	
	/**
	 * 下一个数据库序列号
	 */
	public long nextDatabaseSerialNumber( String seqName );
	
	/**
	 * 下一个数据库序列号
	 */
	public String nextDatabaseSequenceNumber( Sequenceable sa,String seqName );
	
	/**
	 * 下一个序列号码
	 */
	public String nextSequenceNumber( Sequenceable sa );
	
	
	/**
	 * 下一个序列号码
	 */
	public String newTransactionNextSequenceNumber(Sequenceable sa);
	
	/**
	 * 下一个格式化的流水号
	 */
	public String nextSerialNumber(Sequenceable sa );
	
	/**
	 * 下一个序列
	 */
	public Sequence nextSequence(Sequenceable sa );
	
	/**
	 * 下一个序列
	 */
	public Sequence nextSequence(String key ,int step );
	
	/**
	 * 下一个序列
	 */
	public Sequence nextSequence(String key );

}
