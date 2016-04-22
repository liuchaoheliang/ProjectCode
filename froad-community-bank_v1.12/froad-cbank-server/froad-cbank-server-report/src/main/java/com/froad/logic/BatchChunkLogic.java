package com.froad.logic;

import java.util.List;

import com.froad.po.BatchChunk;
import com.froad.po.BatchCycle;
import com.froad.po.BatchJob;
import com.froad.po.BatchNode;


public interface BatchChunkLogic {
	
	/**
	 * generate batch chunks
	 * 
	 * @param batchCycleList
	 * @param batchNodeList
	 * @param batchJobList
	 */
	public void generateChunk(List<BatchCycle> batchCycleList, List<BatchNode> batchNodeList, List<BatchJob> batchJobList);
	
	/**
	 * generate batch chunk by job
	 * 
	 * @param batchCycle
	 * @param batchJob
	 * @param batchNodeList
	 * @return
	 */
	public List<BatchChunk> generateChunkByJob(BatchCycle batchCycle, BatchJob batchJob, List<BatchNode> batchNodeList);
}
