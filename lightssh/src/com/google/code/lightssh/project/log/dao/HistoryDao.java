package com.google.code.lightssh.project.log.dao;

import com.google.code.lightssh.common.dao.Dao;
import com.google.code.lightssh.project.log.entity.Access;
import com.google.code.lightssh.project.log.entity.History;

public interface HistoryDao extends Dao<History>{
	
	public History getByAccess( Access access );

}
