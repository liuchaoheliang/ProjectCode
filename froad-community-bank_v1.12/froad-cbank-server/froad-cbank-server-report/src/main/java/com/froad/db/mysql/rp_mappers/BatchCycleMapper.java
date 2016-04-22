package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.BatchCycle;

public interface BatchCycleMapper {
	/**
	 * update batch date
	 * 
	 * @param batchCycle
	 * @return
	 */
	public boolean updateBatchDate(BatchCycle batchCycle);
	
	/**
	 * find all batch cycle records
	 * 
	 * @return
	 */
	public List<BatchCycle> findAll();
	
	/**
	 * find current batch cycle
	 * 
	 * @return
	 */
	public BatchCycle findCurrentCycle();
	
	/**
	 * update batch cycle status
	 * 
	 * @param status
	 */
	public void updateBatchCycleStatus(@Param("status") char status);
}
