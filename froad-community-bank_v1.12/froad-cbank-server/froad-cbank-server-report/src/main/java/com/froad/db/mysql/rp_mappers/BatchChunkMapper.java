package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.BatchChunk;


public interface BatchChunkMapper {
	/**
	 * add batch chunks by batch
	 * 
	 * @param chunkList
	 * @return
	 */
	public boolean addByBatch(@Param("chunks") List<BatchChunk> chunkList);
	
	/**
	 * find active batch chunks by job
	 * 
	 * @param jobName
	 * @param nodeIp
	 * @param nodePort
	 * @return
	 */
	public List<BatchChunk> findActiveBatchChunk(@Param("jobName") String jobName, @Param("nodeIp") String nodeIp,  @Param("nodePort") int nodePort);
	
	/**
	 * find batch chunks
	 * 
	 * @param batchChunk
	 * @return
	 */
	public List<BatchChunk> findBatchChunk(BatchChunk batchChunk);	
	
	/**
	 * update batch chunk
	 * 
	 * @param batchChunk
	 * @return
	 */
	public boolean updateBatchChunk(BatchChunk batchChunk);
	
	
	
	List<Integer> findChunkJobList(@Param("batchDate")Integer batchDate);
	
	
	
	Integer findEndBatchChunk(@Param("batchDate")Integer batchDate);
	
	
}
