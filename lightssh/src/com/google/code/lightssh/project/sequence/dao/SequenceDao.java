package com.google.code.lightssh.project.sequence.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.sequence.entity.Sequence;

public interface SequenceDao extends Dao<Sequence>{
	
	/**
	 * 下一个数据库序列号
	 */
	public long nextDatabaseSequenceNumber( String seqName );
	
}
