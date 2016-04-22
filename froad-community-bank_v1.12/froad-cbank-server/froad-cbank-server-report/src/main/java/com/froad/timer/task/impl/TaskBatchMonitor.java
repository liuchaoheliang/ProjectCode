package com.froad.timer.task.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.db.mysql.rp_mappers.BatchChunkMapper;
import com.froad.db.mysql.rp_mappers.BatchCycleMapper;
import com.froad.db.mysql.rp_mappers.BatchJobMapper;
import com.froad.enums.BatchCycleStatus;
import com.froad.logback.LogCvt;
import com.froad.po.BatchCycle;
import com.froad.po.BatchJob;
import com.froad.singleton.BatchCycleSingleton;
import com.froad.singleton.BatchJobSingleton;
import com.froad.singleton.BatchNodeSingleton;
import com.froad.singleton.MerchantSingleton;
import com.froad.singleton.MerchantTypeSingleton;
import com.froad.singleton.OrgSingleton;
import com.froad.singleton.ProductCategorySingleton;
import com.froad.timer.ReportTaskNextRun;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractPreTask;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.MongoUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisUtil;
import com.froad.util.TaskTimeUtil;

public class TaskBatchMonitor extends AbstractPreTask {

	private BatchChunkMapper batchChunkMapper;
	private BatchCycleMapper batchCycleMapper;
	private BatchJobMapper batchJobMapper;
	
	public TaskBatchMonitor(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void task() {
		Integer currentDate = null;
		String str = null;
		BatchCycle batchCycle = null;
		BatchCycle updateCycle = null;
		List<BatchJob> jobList = null;
		List<Integer> chunkJobList = null;
		Integer endChunks = null;
		
		batchCycle = batchCycleMapper.findAll().get(0);
		
		jobList = batchJobMapper.findAll();
		
		chunkJobList = batchChunkMapper.findChunkJobList(batchCycle.getBatchDate());
		while (chunkJobList.size() != jobList.size()) {
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				LogCvt.error(e.getMessage(), e);
			}
			rpSqlSession.commit(true);
			chunkJobList = batchChunkMapper.findChunkJobList(batchCycle.getBatchDate());
		}
		
		endChunks = batchChunkMapper.findEndBatchChunk(batchCycle.getBatchDate());
		while (endChunks > 0) {
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				LogCvt.error(e.getMessage(), e);
			}
			rpSqlSession.commit(true);
			endChunks = batchChunkMapper.findEndBatchChunk(batchCycle.getBatchDate());
		}
		
		LogCvt.info(new StringBuffer("跑批完成：batch_date=").append(batchCycle.getBatchDate()).toString());
		
		str = DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, new Date());
		currentDate = Integer.parseInt(str);
		
		if(isMasterNode){
			updateCycle = new BatchCycle();
			updateCycle.setBatchId(batchCycle.getBatchId());
			updateCycle.setEndTime(TaskTimeUtil.convertToDay(batchCycle.getBatchDate()));
			updateCycle.setStatus(BatchCycleStatus.init.getCode());
			batchCycleMapper.updateBatchDate(updateCycle);
		} else {
			// 从节点需待主节点更新batch cycle后进行
			updateCycle = batchCycleMapper.findAll().get(0);
			while (ObjectUtils.equals(updateCycle.getEndTime(), batchCycle.getEndTime())) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					LogCvt.error(e.getMessage(), e);
				}
				rpSqlSession.commit(true);
				updateCycle = batchCycleMapper.findAll().get(0);
			}
		}
		
		// destroy batch cycle singleton
		BatchCycleSingleton.destroyInstance();
		rpSqlSession.commit(true);
		
		if (batchCycle.getBatchDate() < currentDate){
			LogCvt.info("开始处理下一个批： batch_date=" + updateCycle.getBatchDate());
			ReportTaskNextRun nextTask = new ReportTaskNextRun();
			nextTask.run();
		}
		
		// destroy singletons
		destroy();
		
		if (isMasterNode){
			// clear cache
			clearCache();
		}
	}

	@Override
	protected void initialize() {
		batchChunkMapper = rpSqlSession.getMapper(BatchChunkMapper.class);
		batchCycleMapper = rpSqlSession.getMapper(BatchCycleMapper.class);
		batchJobMapper = rpSqlSession.getMapper(BatchJobMapper.class);
	}

	/**
	 * destroy singletons
	 */
	private void destroy(){
		BatchCycleSingleton.destroyInstance();
		BatchJobSingleton.destroyInstance();
		BatchNodeSingleton.destroyInstance();
		MerchantTypeSingleton.destroyInstance();
		OrgSingleton.destroyInstance();
		ProductCategorySingleton.destroyInstance();
		MerchantSingleton.destroyInstance();
	}
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		clearCache();
	}
	
	/**
	 * clear cache
	 */
	private static void clearCache(){
		RedisUtil redisUtil = null;
		MongoUtil mongoUtil = null;
		Map<String, String> keyHash = null;
		Iterator<String> redisKeyIt = null;
		String redisKey = null;
		Iterator<String> collectionIt = null;
		String collection = null;
		
		try {
			redisUtil = new RedisUtil();
			mongoUtil = new MongoUtil();
			
			keyHash = redisUtil.hgetAll(RedisUtil.MONGO_COLLECTION_KEYS);
			
			if (Checker.isNotEmpty(keyHash)){
				redisKeyIt = keyHash.keySet().iterator();
				collectionIt = keyHash.values().iterator();
				
				// 清除redis-mongo对应关系
				redisUtil.del(RedisUtil.MONGO_COLLECTION_KEYS);
				
				// 清除redis缓存
				while (redisKeyIt.hasNext()){
					redisKey = redisKeyIt.next();
					redisUtil.del(redisKey);
					LogCvt.info(new StringBuffer(redisKey).append(" redis缓存已清除").toString());
				}
				
				// 清除mongo临时集合
				while (collectionIt.hasNext()){
					collection = collectionIt.next();
					mongoUtil.dropCollection(collection);
					LogCvt.info(new StringBuffer(collection).append(" mongo集合已清除").toString());
				}
			}
		} catch (Exception e){
			LogCvt.error("清除缓存失败", e);
		}
	}
}
