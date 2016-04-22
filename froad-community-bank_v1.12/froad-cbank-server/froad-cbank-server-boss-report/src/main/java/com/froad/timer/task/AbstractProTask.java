package com.froad.timer.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.froad.db.mysql.rp_mappers.BatchChunkMapper;
import com.froad.enums.BatchChunkStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.BatchChunkLogic;
import com.froad.logic.impl.BatchChunkLogicImpl;
import com.froad.po.BatchChunk;
import com.froad.po.BatchCycle;
import com.froad.po.BatchJob;
import com.froad.singleton.BatchCycleSingleton;
import com.froad.singleton.BatchJobSingleton;
import com.froad.timer.bean.TaskBean;
import com.froad.util.IPUtil;
import com.froad.util.JSonUtil;

public abstract class AbstractProTask extends AbstractTask {
	
	protected BatchJob batchJob;
	
	protected List<BatchChunk> chunkList;
	
	protected BatchChunkLogic batchChunkLogic;
	
	protected BatchChunkMapper batchChunkMapper;
	
	protected BatchCycle batchCycle;
	
	public AbstractProTask(String name, TaskBean task) {
		super(name, task);
	}
	
	@Override
	public void run() {
		long begtime = System.currentTimeMillis();
		boolean flag = true;
		try {
			begin();
			
			batchJob = BatchJobSingleton.findByJobName(rpSqlSession, name);
			batchCycle = BatchCycleSingleton.getCurrentCycle(rpSqlSession);
			
			initialize();
			
			before();
			
			StringBuffer logMsg = null;
			List<BatchChunk> chunkList = batchChunkMapper.findActiveBatchChunk(name, IPUtil.getLocalIp(), IPUtil.getNodePort());
			
			logMsg = new StringBuffer();
			logMsg.append("Total chunks for this node to process is: ");
			logMsg.append(chunkList == null ? 0 : chunkList.size());
			LogCvt.info(logMsg.toString());
			
			for(BatchChunk batchChunk : chunkList){
				batchChunk.setStartTime(new Date());
				processByChunk(batchChunk);
				after(batchChunk);
				//sqlSession.commit(true);
				rpSqlSession.commit(true);
			}
			
		} catch (Exception e) {
			flag = false;
			exception(e);
		} finally{
			boolean isDone = isDone();
			end();
			LogCvt.info("定时任务["+name+"]结束,耗时:"+(System.currentTimeMillis()-begtime)+"毫秒");
			if(flag && isDone) {
				next();
			}
		}
	}
	
	@Override
	public void begin() {
		super.begin();
		
		batchChunkLogic = new BatchChunkLogicImpl(sqlSession, rpSqlSession);
		
		batchChunkMapper = rpSqlSession.getMapper(BatchChunkMapper.class);
	}
	
	
	protected void before() {
		List<BatchChunk> batchChunkList = null;
		BatchChunk query = null;
		List<BatchChunk> runningChunkList = null;
		
		
		if(isMasterNode){
			if(batchJob != null && batchCycle != null){
				batchChunkList = new ArrayList<BatchChunk>();
				batchChunkList.addAll(batchChunkLogic.generateChunkByJob(batchCycle, batchJob, batchNodeList));
				if(CollectionUtils.isNotEmpty(batchChunkList)){
					batchChunkMapper.addByBatch(batchChunkList);
					rpSqlSession.commit(true);
				}
			}
		}else{
			query = new BatchChunk();
			query.setBatchDate(batchCycle.getBatchDate());
			query.setJobId(batchJob.getJobId());
			runningChunkList = batchChunkMapper.findBatchChunk(query);
			
			while (CollectionUtils.isEmpty(runningChunkList)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					LogCvt.error(e.getMessage(), e);
				}
				rpSqlSession.commit(true);
				runningChunkList = batchChunkMapper.findBatchChunk(query);
			}
		}
	}
	
	
	public void after(BatchChunk batchChunk) {
		batchChunk.setEndTime(new Date());
		batchChunk.setStatus(BatchChunkStatus.success.getCode());
		batchChunkMapper.updateBatchChunk(batchChunk);
	}
	
	
	@Override
	public void task() {}

	
	public abstract void processByChunk(BatchChunk batchChunk);
	
	
	protected boolean isDone(){
		BatchChunk query = new BatchChunk();
		query.setBatchDate(batchCycle.getBatchDate());
		query.setJobName(name);
		query.setStatus(BatchChunkStatus.init.getCode());
		List<BatchChunk> runningChunkList = batchChunkMapper.findBatchChunk(query);
		// while previous job is still running, waiting for the job to complete
		while (runningChunkList != null && runningChunkList.size() > 0) {
			LogCvt.info(JSonUtil.toJSonString(runningChunkList));
			try {
				Thread.sleep(30 * 1000);
			} catch (InterruptedException e) {
				LogCvt.error("Error while sleep for waiting previouse job to complete", e);
			}
			rpSqlSession.commit(true);
			runningChunkList = new ArrayList<BatchChunk>();
			runningChunkList = batchChunkMapper.findBatchChunk(query);
		}
		return true;
	}
	
}
