package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.impl.ReportMongoManager;
import com.froad.db.mysql.rp_mappers.BatchChunkMapper;
import com.froad.db.mysql.rp_mappers.BatchCycleMapper;
import com.froad.db.mysql.rp_mappers.DataSuborderMapper;
import com.froad.db.mysql.rp_mappers.ReportBossMerchantCategorySaleMapper;
import com.froad.db.mysql.rp_mappers.ReportBossOrgMerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportBossOrgSaleMapper;
//import com.froad.db.mysql.rp_mappers.ReportBankOrgMapper;
//import com.froad.db.mysql.rp_mappers.ReportBankUserMapper;
//import com.froad.db.mysql.rp_mappers.ReportMerchantProductMapper;
//import com.froad.db.mysql.rp_mappers.ReportUserMapper;
import com.froad.enums.BatchChunkStatus;
import com.froad.enums.BatchCycleStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.BatchChunkLogic;
import com.froad.po.BatchChunk;
import com.froad.po.BatchCycle;
import com.froad.po.BatchJob;
import com.froad.po.BatchNode;
import com.froad.timer.task.impl.TaskDataSuborder;
import com.froad.timer.task.impl.TaskFace2FaceOrder;
import com.froad.timer.task.impl.TaskReportBossMerchantCategorySale;
import com.froad.timer.task.impl.TaskReportBossOrgMerchant;
import com.froad.timer.task.impl.TaskReportBossOrgSale;
import com.froad.timer.task.impl.TaskReportTempExport;
import com.froad.util.MongoTableName;
//import com.froad.timer.task.impl.TaskReportBankUser;
//import com.froad.timer.task.impl.TaskReportFaceMerchantProduct;
//import com.froad.timer.task.impl.TaskReportMerchantProduct;
//import com.froad.timer.task.impl.TaskReportMerchantSale;
//import com.froad.timer.task.impl.TaskReportSignMerchant;
//import com.froad.timer.task.impl.TaskReportSignSummary;
//import com.froad.timer.task.impl.TaskReportSignSummaryDetail;
//import com.froad.timer.task.impl.TaskReportSignTypeSummary;
//import com.froad.timer.task.impl.TaskReportUser;
//import com.froad.timer.task.impl.TaskReportUserDetail;
//import com.froad.timer.task.impl.TaskReportUserTrans;
import com.froad.util.TaskTimeUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BatchChunkLogicImpl implements BatchChunkLogic {

	private SqlSession sqlSession = null;
	private SqlSession rpSqlSession = null;
	
	ReportMongoManager mongo = new ReportMongoManager();
	
	/**
	 * constructor
	 * 
	 * @param sqlSession
	 * @param rpSqlSession
	 */
	public BatchChunkLogicImpl(SqlSession sqlSession, SqlSession rpSqlSession) {
		this.sqlSession = sqlSession;
		this.rpSqlSession = rpSqlSession;
	}

	@Override
	public void generateChunk(List<BatchCycle> batchCycleList,
			List<BatchNode> batchNodeList, List<BatchJob> batchJobList) {
		BatchCycle batchCycle = null;
		List<BatchChunk> chunkList = null;
		List<BatchChunk> cycleChunkList = null;
		Integer currentDate = null;
		BatchCycleMapper cycleMapper = rpSqlSession.getMapper(BatchCycleMapper.class);
		BatchChunkMapper chunkMapper = rpSqlSession.getMapper(BatchChunkMapper.class);
		
		LogCvt.info(new StringBuffer("Start generating batch chunks: ").append(batchCycleList).toString());
		
		chunkList = new ArrayList<BatchChunk>();
		for (int i = 0; i < batchCycleList.size(); i++){
			batchCycle = batchCycleList.get(i);
			currentDate = TaskTimeUtil.getCurrentDate(batchCycle.getBatchDate());
			
			//set new batch dates
			batchCycle.setLastLastBatchDate(batchCycle.getLastBatchDate());
			batchCycle.setLastBatchDate(batchCycle.getBatchDate());
			batchCycle.setBatchDate(currentDate);
			
			clearHistoryData(batchCycle);
			
			LogCvt.info(new StringBuffer("Start generating batch chunks for batch cycle: ").append(batchCycle.toString()).toString());
			cycleChunkList = generateChunkByCycle(batchCycle, batchNodeList, batchJobList);
			if (cycleChunkList.size() > 0){
				chunkList.addAll(cycleChunkList);
			}
			LogCvt.info(new StringBuffer("Batch chunks generated for batch cycle: ").append(batchCycle.toString()).toString());
			
			batchCycle.setStartTime(new Date());
			batchCycle.setEndTime(null);
			batchCycle.setStatus(BatchCycleStatus.in_progress.getCode());
		}
		
		
		chunkMapper.addByBatch(chunkList);
		cycleMapper.updateBatchDate(batchCycle);
		
		LogCvt.info(new StringBuffer("Generated batch chunk sieze: ").append(chunkList.size()).toString());
	}

	/**
	 * generate batch chunk by batch cycle
	 * 
	 * @param batchCycle
	 * @param batchNodeList
	 * @param batchJobList
	 */
	private List<BatchChunk> generateChunkByCycle(BatchCycle batchCycle, List<BatchNode> batchNodeList, List<BatchJob> batchJobList){
		List<BatchChunk> cycleChunkList = null;
		List<BatchChunk> jobChunkList = null;
		
		cycleChunkList = new ArrayList<BatchChunk>();
		for (int i = 0; i < batchJobList.size(); i++){
			LogCvt.info(new StringBuffer("Start generating batch chunks for batch job: ").append(batchJobList.get(i).toString()).toString());
			
			jobChunkList = generateChunkByJob(batchCycle, batchJobList.get(i), batchNodeList);
			if (jobChunkList.size() > 0){
				cycleChunkList.addAll(jobChunkList);
			}
			
			LogCvt.info(new StringBuffer("Batch chunks generated for batch job: ").append(batchJobList.get(i).toString()).toString());
		}
		
		return cycleChunkList;
	}
	
	/**
	 * generate batch chunk by job
	 * 
	 * @param batchCycle
	 * @param batchJob
	 * @param batchNodeList
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<BatchChunk> generateChunkByJob(BatchCycle batchCycle, BatchJob batchJob, List<BatchNode> batchNodeList){
		List<BatchChunk> chunkList = null;
		BatchChunk batchChunk = null;
		int noOfChunk = 0;
		Integer totalSize = 0;
		//BigDecimal bigDecimal = null;
		Date startDate = null;
		Date endDate = null;
		
		chunkList = new ArrayList<BatchChunk>();
		
		if (batchJob.getJobName().equals(TaskDataSuborder.class.getSimpleName())) {
			// 按商品拆分订单
			
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = getSubOrderCount();
			
		}  else if (batchJob.getJobName().equals(TaskFace2FaceOrder.class.getSimpleName())) {
			// 拆分面对面订单
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = getFace2FaceOrderCount();
			
		}  else if (batchJob.getJobName().equals(TaskReportBossOrgSale.class.getSimpleName())) {
			// 按机构统计销量
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = 1;
			
		}  else if (batchJob.getJobName().equals(TaskReportBossMerchantCategorySale.class.getSimpleName())) {
			// 按商户分类统计销量占比
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = 1;
			
		}  else if (batchJob.getJobName().equals(TaskReportBossOrgMerchant.class.getSimpleName())) {
			// 按机构统计商户数量
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = 1;
			
		}  else if (batchJob.getJobName().equals(TaskReportTempExport.class.getSimpleName())) {
			// 生成报表
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = 1;
			
		}  else {
			
		}
		
		LogCvt.info(new StringBuffer(batchJob.getJobName()).append(" total size: ").append(totalSize).toString());
		//bigDecimal = new BigDecimal(totalSize);
		noOfChunk = totalSize % batchJob.getChunkSize() == 0 ? (totalSize / batchJob.getChunkSize()) : (totalSize / batchJob.getChunkSize() + 1);
		LogCvt.info(new StringBuffer(batchJob.getJobName()).append(" total chunk: ").append(noOfChunk).toString());
		
		if(noOfChunk == 0 && totalSize == 0){
			batchChunk = new BatchChunk();
			batchChunk.setChunkId(1);
			batchChunk.setBatchDate(batchCycle.getBatchDate());
			batchChunk.setLastBatchDate(batchCycle.getLastBatchDate());
			batchChunk.setBatchId(batchCycle.getBatchId());
			batchChunk.setNodeId(batchNodeList.get(0).getNodeId());// assign node id
			batchChunk.setJobId(batchJob.getJobId());
			batchChunk.setJobName(batchJob.getJobName());
			batchChunk.setInputFile(batchJob.getInputFile());
			batchChunk.setChunkPage(1);
			batchChunk.setChunkSize(batchJob.getChunkSize());
			batchChunk.setTotalChunk(0);
			batchChunk.setTotalSize(0);
			batchChunk.setStartTime(new Date());
			batchChunk.setEndTime(new Date());
			batchChunk.setStatus(BatchChunkStatus.success.getCode());
			LogCvt.info(new StringBuffer("Batch chunk generated: ").append(batchChunk.toString()).toString());
			chunkList.add(batchChunk);
		}else if (noOfChunk == 0 && totalSize > 0) {
			batchChunk = new BatchChunk();
			batchChunk.setChunkId(1);
			batchChunk.setBatchDate(batchCycle.getBatchDate());
			batchChunk.setLastBatchDate(batchCycle.getLastBatchDate());
			batchChunk.setBatchId(batchCycle.getBatchId());
			batchChunk.setNodeId(batchNodeList.get(0).getNodeId());// assign node id
			batchChunk.setJobId(batchJob.getJobId());
			batchChunk.setJobName(batchJob.getJobName());
			batchChunk.setInputFile(batchJob.getInputFile());
			batchChunk.setChunkPage(1);
			batchChunk.setChunkSize(batchJob.getChunkSize());
			batchChunk.setTotalChunk(1);
			batchChunk.setTotalSize(totalSize);
			batchChunk.setStartTime(new Date());
			batchChunk.setEndTime(new Date());
			batchChunk.setStatus(BatchChunkStatus.init.getCode());
			LogCvt.info(new StringBuffer("Batch chunk generated: ").append(batchChunk.toString()).toString());
			chunkList.add(batchChunk);
		}else{
			// generate batch chunks
			for (int i = 0; i < noOfChunk; i++){
				batchChunk = new BatchChunk();
				batchChunk.setChunkId(i+1);
				batchChunk.setBatchDate(batchCycle.getBatchDate());
				batchChunk.setLastBatchDate(batchCycle.getLastBatchDate());
				batchChunk.setBatchId(batchCycle.getBatchId());
				batchChunk.setNodeId(batchNodeList.get(i % batchNodeList.size()).getNodeId());// assign node id
				batchChunk.setJobId(batchJob.getJobId());
				batchChunk.setJobName(batchJob.getJobName());
				batchChunk.setInputFile(batchJob.getInputFile());
				batchChunk.setChunkPage(i + 1);
				batchChunk.setChunkSize(batchJob.getChunkSize());
				batchChunk.setTotalChunk(noOfChunk);
				batchChunk.setTotalSize(totalSize);
				batchChunk.setStartTime(null);
				batchChunk.setEndTime(null);
				batchChunk.setStatus(BatchChunkStatus.init.getCode());
				LogCvt.info(new StringBuffer("Batch chunk generated: ").append(batchChunk.toString()).toString());
				chunkList.add(batchChunk);
			}
		}
		
		return chunkList;
	}
	
	/**
	 * 获取所有子订单数量
	 */
	public int getSubOrderCount(){
		return mongo.getCount(new BasicDBObject(), MongoTableName.CB_SUBORDER_PRODUCT);
	}
	
	/**
	 * 获取所有子订单数量
	 */
	public int getFace2FaceOrderCount(){
		DBObject where = new BasicDBObject();
		where.put("is_qrcode", 1);
		return mongo.getCount(where, MongoTableName.CB_ORDER);
	}
	
	/**
	 * 清除当期历史数据
	 */
	public void clearHistoryData(BatchCycle batchCycle){
		// 清除BatchChunk
		BatchChunkMapper chunkMapper = rpSqlSession.getMapper(BatchChunkMapper.class);
		chunkMapper.deleteHistoryData(batchCycle.getBatchDate());
		
		// 清除DataSuborder
		DataSuborderMapper dataSuborderMapper = rpSqlSession.getMapper(DataSuborderMapper.class);
		dataSuborderMapper.deleteHistoryData(batchCycle.getBatchDate());
		
		// 清除ReportBossOrgSale
		ReportBossOrgSaleMapper reportBossOrgSaleMapper = rpSqlSession.getMapper(ReportBossOrgSaleMapper.class);
		reportBossOrgSaleMapper.deleteHistoryData(batchCycle.getBatchDate());
		
		// 清除ReportBossMerchantCategorySale
		ReportBossMerchantCategorySaleMapper reportBossMerchantCategorySaleMapper = rpSqlSession.getMapper(ReportBossMerchantCategorySaleMapper.class);
		reportBossMerchantCategorySaleMapper.deleteHistoryData(batchCycle.getBatchDate());
		
		// 清除ReportBossOrgMerchant
		ReportBossOrgMerchantMapper reportBossOrgMerchantMapper = rpSqlSession.getMapper(ReportBossOrgMerchantMapper.class);
		reportBossOrgMerchantMapper.deleteHistoryData(batchCycle.getBatchDate());
		
		rpSqlSession.commit(true);
	}
}
