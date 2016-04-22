package com.froad.db.mysql.rp_mappers;

import java.util.List;

import com.froad.po.BatchJob;

public interface BatchJobMapper {
	
	/**
	 * find all batch job records
	 * 
	 * @return
	 */
	public List<BatchJob> findAll();
}
