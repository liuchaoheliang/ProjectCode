package com.froad.timer.task.impl;

import java.util.Date;
import java.util.List;

import com.froad.db.mysql.rp_mappers.BatchCycleMapper;
import com.froad.enums.BatchCycleStatus;
import com.froad.logback.LogCvt;
import com.froad.po.BatchCycle;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractPreTask;
import com.froad.util.TaskTimeUtil;


public class TaskReportPreBatch extends AbstractPreTask {

	private BatchCycleMapper batchCycleMapper;
	
	public TaskReportPreBatch(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void task() {
		List<BatchCycle> batchCycleList = null;
		BatchCycle batchCycle = null;
		Integer currentDate = null;
		
		LogCvt.info(new StringBuffer("Current node is master node? ").append(isMasterNode).toString());
		
		if (isMasterNode){
			
			batchCycleList = retrieveBatchCycles();
			
			if (null != batchCycleList && batchCycleList.size() > 0){
				batchCycle = batchCycleList.get(0);
				currentDate = TaskTimeUtil.getCurrentDate(batchCycle.getBatchDate());
				//set new batch dates
				batchCycle.setLastLastBatchDate(batchCycle.getLastBatchDate());
				batchCycle.setLastBatchDate(batchCycle.getBatchDate());
				batchCycle.setBatchDate(currentDate);
				batchCycle.setStartTime(new Date());
				batchCycle.setEndTime(null);
				batchCycle.setStatus(BatchCycleStatus.ready.getCode());
				batchCycleMapper.updateBatchDate(batchCycle);
			} else {
				LogCvt.error("Batch run date record is not defined in cb_report_batch_cycle !");
				updateBatchCycleStatus(BatchCycleStatus.failed.getCode());
			}
		} else {
			// non master node
			batchCycleList = retrieveBatchCycles();
			
			// check batch cycle status every 1 min
			while (!isBatchCycleReady(batchCycleList)){
				try {
					Thread.sleep((2 * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rpSqlSession.commit(true);
				batchCycleList = retrieveBatchCycles();
			}
		}
	}
	
	
	/**
	 * retrieve batch run dates
	 * 
	 * @param batchCycleMapper
	 * 
	 * @return
	 */
	private List<BatchCycle> retrieveBatchCycles(){
		List<BatchCycle> batchCycleList = null;
		
		batchCycleList = batchCycleMapper.findAll();
		
		return batchCycleList;
	}
	
	/**
	 * update batch cycle status
	 * 
	 * @param batchCycleMapper
	 */
	private void updateBatchCycleStatus(char status){
		batchCycleMapper.updateBatchCycleStatus(status);
	}
	
	
	/**
	 * check whether batch cycle is ready or not
	 * 
	 * @param batchCycleList
	 * @return
	 */
	private boolean isBatchCycleReady(List<BatchCycle> batchCycleList){
		boolean isReady = true;
		BatchCycle batchCycle = null;
		
		for (int i = 0; i < batchCycleList.size(); i++){
			batchCycle = batchCycleList.get(i);
			if (batchCycle.getStatus() != BatchCycleStatus.ready.getCode()){
				isReady = false;
				break;
			}
		}
		
		return isReady;
	}

	@Override
	public void initialize() {
		batchCycleMapper = rpSqlSession.getMapper(BatchCycleMapper.class);
	}
}
