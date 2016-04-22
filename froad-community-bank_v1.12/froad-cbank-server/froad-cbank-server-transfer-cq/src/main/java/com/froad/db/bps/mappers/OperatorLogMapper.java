package com.froad.db.bps.mappers;

import java.util.List;

import com.froad.db.bps.entity.OperatorLog;

public interface OperatorLogMapper {
	
	public List<OperatorLog> findAll();
}
