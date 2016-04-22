package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.db.mysql.rp_mappers.BatchChunkMapper;
import com.froad.db.mysql.rp_mappers.BatchCycleMapper;
import com.froad.enums.BatchCycleStatus;
import com.froad.logback.LogCvt;
import com.froad.po.BatchCycle;
import com.froad.po.BatchJob;
import com.froad.singleton.BatchCycleSingleton;
import com.froad.singleton.BatchJobSingleton;
import com.froad.singleton.BatchNodeSingleton;
//import com.froad.singleton.MerchantTypeSingleton;
//import com.froad.singleton.OrgSingleton;
//import com.froad.singleton.ProductCategorySingleton;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractPreTask;

public class TaskBatchMonitor extends AbstractPreTask {

	private BatchChunkMapper batchChunkMapper;
	private BatchCycleMapper batchCycleMapper;
	
	public TaskBatchMonitor(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void task() {
		if(isMasterNode){
			BatchCycle batchCycle = BatchCycleSingleton.getCurrentCycle(rpSqlSession);;
			
			List<BatchJob> jobList = new ArrayList<BatchJob>(BatchJobSingleton.getInstance(rpSqlSession).values());
			
			List<Date> endChunks = batchChunkMapper.findEndBatchChunk(batchCycle.getBatchDate());
			
			while (endChunks.size() != jobList.size()) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					LogCvt.error(e.getMessage(), e);
				}
				rpSqlSession.commit(true);
				endChunks = batchChunkMapper.findEndBatchChunk(batchCycle.getBatchDate());
			}
			
			BatchCycle updateCycle = new BatchCycle();
			updateCycle.setBatchId(batchCycle.getBatchId());
			updateCycle.setEndTime(endChunks.get(0));
			updateCycle.setStatus(BatchCycleStatus.init.getCode());
			batchCycleMapper.updateBatchDate(updateCycle);
			
			destroy();
		}
	}

	@Override
	public void initialize() {
		batchChunkMapper = rpSqlSession.getMapper(BatchChunkMapper.class);
		batchCycleMapper = rpSqlSession.getMapper(BatchCycleMapper.class);
	}

	
	private void destroy(){
		BatchCycleSingleton.destroyInstance();
		BatchJobSingleton.destroyInstance();
		BatchNodeSingleton.destroyInstance();
		//MerchantTypeSingleton.destroyInstance();
		//OrgSingleton.destroyInstance();
		//ProductCategorySingleton.destroyInstance();
	}
}
