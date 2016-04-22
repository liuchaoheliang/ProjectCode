package com.froad.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.mappers.ProductMapper;
import com.froad.db.mysql.rp_mappers.BatchChunkMapper;
import com.froad.db.mysql.rp_mappers.BatchCycleMapper;
import com.froad.db.mysql.rp_mappers.ReportBankOrgMapper;
import com.froad.db.mysql.rp_mappers.ReportUserMapper;
import com.froad.enums.BatchChunkStatus;
import com.froad.enums.BatchCycleStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.BatchChunkLogic;
import com.froad.po.BatchChunk;
import com.froad.po.BatchCycle;
import com.froad.po.BatchJob;
import com.froad.po.BatchNode;
import com.froad.timer.task.impl.TaskReportBankUser;
import com.froad.timer.task.impl.TaskReportMerchantProduct;
import com.froad.timer.task.impl.TaskReportMerchantSale;
import com.froad.timer.task.impl.TaskReportSignMerchant;
import com.froad.timer.task.impl.TaskReportSignSummary;
import com.froad.timer.task.impl.TaskReportSignSummaryDetail;
import com.froad.timer.task.impl.TaskReportSignTypeSummary;
import com.froad.timer.task.impl.TaskReportUser;
import com.froad.timer.task.impl.TaskReportUserDetail;
import com.froad.timer.task.impl.TaskReportUserSummary;
import com.froad.timer.task.impl.TaskReportUserTrans;
import com.froad.util.TaskTimeUtil;

public class BatchChunkLogicImpl implements BatchChunkLogic {

	private SqlSession sqlSession = null;
	private SqlSession rpSqlSession = null;
	
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
		BatchCycleMapper cycleMapper = null;
		BatchChunkMapper chunkMapper = null;
		
		LogCvt.info(new StringBuffer("Start generating batch chunks: ").append(batchCycleList).toString());
		
		chunkList = new ArrayList<BatchChunk>();
		for (int i = 0; i < batchCycleList.size(); i++){
			batchCycle = batchCycleList.get(i);
			currentDate = TaskTimeUtil.getCurrentDate(batchCycle.getBatchDate());
			
			//set new batch dates
			batchCycle.setLastLastBatchDate(batchCycle.getLastBatchDate());
			batchCycle.setLastBatchDate(batchCycle.getBatchDate());
			batchCycle.setBatchDate(currentDate);
			
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
		
		chunkMapper = rpSqlSession.getMapper(BatchChunkMapper.class);
		cycleMapper = rpSqlSession.getMapper(BatchCycleMapper.class);
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
	public List<BatchChunk> generateChunkByJob(BatchCycle batchCycle, BatchJob batchJob, List<BatchNode> batchNodeList){
		List<BatchChunk> chunkList = null;
		BatchChunk batchChunk = null;
		int noOfChunk = 0;
		Integer totalSize = 0;
		MerchantMapper merchantMapper = null;
		ProductMapper productMapper = null;
		ReportBankOrgMapper reportBankOrgMapper = null;
		ReportUserMapper reportUserMapper = null;
		BigDecimal bigDecimal = null;
		Date startDate = null;
		Date endDate = null;
		
		chunkList = new ArrayList<BatchChunk>();
		
		if (batchJob.getJobName().equals(TaskReportBankUser.class.getSimpleName())) {
			// 银行人员及签约信息统计
			merchantMapper = this.sqlSession.getMapper(MerchantMapper.class);
			// 增量查找，其他为全量查找
			startDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchCycle.getLastBatchDate()) : null;
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = merchantMapper.findRecordCountByDate(startDate, endDate);
		} else if(batchJob.getJobName().equals(TaskReportSignSummary.class.getSimpleName())
				|| batchJob.getJobName().equals(TaskReportSignSummaryDetail.class.getSimpleName())
				|| batchJob.getJobName().equals(TaskReportSignTypeSummary.class.getSimpleName())){
			// 机构签约人总数信息, 机构签约新增解约数信息, 机构签约商户类型总数信息
			merchantMapper = this.sqlSession.getMapper(MerchantMapper.class);
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = merchantMapper.selectMerchantIdListInfoCount(startDate, endDate);
		} else if (batchJob.getJobName().equals(TaskReportMerchantProduct.class.getSimpleName())){
			// 商品统计，全量更新
			productMapper = this.sqlSession.getMapper(ProductMapper.class);
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = productMapper.findRecordCountByDate(endDate);
			
		} else if (batchJob.getJobName().equals(TaskReportUser.class.getSimpleName()) 
				|| batchJob.getJobName().equals(TaskReportUserSummary.class.getSimpleName())){
			reportBankOrgMapper = this.rpSqlSession.getMapper(ReportBankOrgMapper.class);
			totalSize = reportBankOrgMapper.findAllCount();
		} else if (batchJob.getJobName().equals(TaskReportUserDetail.class.getSimpleName()) 
				|| batchJob.getJobName().equals(TaskReportUserTrans.class.getSimpleName())) {
			reportUserMapper = this.rpSqlSession.getMapper(ReportUserMapper.class);
			endDate = TaskTimeUtil.convertToDayEnd(batchCycle.getBatchDate());
			totalSize = reportUserMapper.findAllCount(endDate);
		} else if (batchJob.getJobName().equals(TaskReportMerchantSale.class.getSimpleName())
				|| batchJob.getJobName().equals(TaskReportSignMerchant.class.getSimpleName())) {
			// 商户销量统计, 商户交易信息统计
			merchantMapper = this.sqlSession.getMapper(MerchantMapper.class);
			endDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
			totalSize = merchantMapper.findAllRecordCount(endDate);
		}
		
		LogCvt.info(new StringBuffer(batchJob.getJobName()).append(" total size: ").append(totalSize).toString());
		bigDecimal = new BigDecimal(totalSize);
		noOfChunk = bigDecimal.divide(new BigDecimal(batchJob.getChunkSize()), BigDecimal.ROUND_UP).intValue();
		LogCvt.info(new StringBuffer(batchJob.getJobName()).append(" total chunk: ").append(noOfChunk).toString());
		
		if(noOfChunk == 0){
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
}
