package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportSignSummaryMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Merchant;
import com.froad.po.MerchantCount;
import com.froad.po.ReportSignSummary;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.AuditMerchantUtil;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.OrgUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportSignSummary extends AbstractProTask {
	
	private MerchantMapper merchantMapper;
	
	private ReportSignSummaryMapper reportSignSummaryMapper;
	
	private OrgUtil orgUtil = null;
	private AuditMerchantUtil merchantUtil = null;
	
	public TaskReportSignSummary(String name, TaskBean task) {
		super(name, task);
	}


	/**
	 * process by chunk
	 * 
	 * @param batchChunk
	 */
	public void processByChunk(BatchChunk batchChunk){
		Page<MerchantCount> page = null;
		StringBuffer logMsg = null;
		Date queryDate = null;
		MerchantCount c = null;
		ReportSignSummary summary = null;
		String forgCode = null;
		String forgName = null;
		String sorgCode = null;
		String sorgName = null;
		String torgCode = null;
		String torgName = null;
		String lorgCode = null;
		String lorgName = null;
		Merchant merchant = null;
		List<Merchant> merchantList = null;
		
		// log message
		logMsg  = new StringBuffer();
		logMsg.append("Start process batch_id:batchDate:jobName:chunkId ");
		logMsg.append(batchChunk.getBatchId());
		logMsg.append(":");
		logMsg.append(batchChunk.getBatchDate());
		logMsg.append(":");
		logMsg.append(batchChunk.getJobName());
		logMsg.append(":");
		logMsg.append(batchChunk.getChunkId());
		LogCvt.info(logMsg.toString());
		
		page = new Page<MerchantCount>();
		page.setPageNumber(batchChunk.getChunkPage());
		page.setPageSize(batchChunk.getChunkSize());
		
		queryDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		
		List<MerchantCount> list = merchantMapper.selectMerchantIdListInfoByPage(null, queryDate, page);
		
		List<ReportSignSummary> summarys = new ArrayList<ReportSignSummary>();
		for(int i=0; i<list.size(); i++){
			c = list.get(i);
			
			forgName = null;
			sorgName = null;
			torgName = null;
			lorgName = null;
			lorgCode = null;
			
			forgCode = c.getProOrgCode();
			if(orgUtil.containsKey(c.getClientId(), forgCode)){
				forgName = orgUtil.getOrg(c.getClientId(), forgCode).getOrgName();
			}
			sorgCode = c.getCityOrgCode();
			if(orgUtil.containsKey(c.getClientId(), sorgCode)){
				sorgName = orgUtil.getOrg(c.getClientId(), sorgCode).getOrgName();
			}
			torgCode = c.getCountyOrgCode();
			if(orgUtil.containsKey(c.getClientId(), torgCode)){
				torgName = orgUtil.getOrg(c.getClientId(), torgCode).getOrgName();
			}
			if (OrgLevelEnum.orgLevel_four.getLevel().equals(orgUtil.getOrg(c.getClientId(), c.getOrgCode()).getOrgLevel())) {
				lorgCode = c.getOrgCode();
				lorgName = orgUtil.getOrg(c.getClientId(), lorgCode).getOrgName();
			}
			
			
			summary = new ReportSignSummary();
			summary.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
			summary.setClientId(c.getClientId());
			summary.setForgCode(forgCode);
			summary.setForgName(forgName);
			summary.setSorgCode(sorgCode);
			summary.setSorgName(sorgName);
			summary.setTorgCode(torgCode);
			summary.setTorgName(torgName);
			summary.setLorgCode(lorgCode);
			summary.setLorgName(lorgName);
			summary.setSignUserName(c.getContractStaff());
			merchantList = merchantUtil.getMerchantList(c.getClientId(), forgCode, sorgCode, torgCode, c.getContractStaff());
			long totalCount = merchantList.size();
			for(int j=0; j< merchantList.size(); j++){
				merchant = merchantList.get(j);
				if (merchant.getCreateTime().getTime() > queryDate.getTime()){
					// 创建时间大于查询时间，不需要处理
					totalCount--;
					continue;
				}
				boolean isCancel = false;
				if(Checker.isNotEmpty(merchant)){
					isCancel = !merchant.getIsEnable() && StringUtils.equals(merchant.getDisableStatus(), MerchantDisableStatusEnum.unregistered.getCode());
					//商户解约或者审核通过时间大于当前跑批时间
					if (isCancel || (Checker.isNotEmpty(merchant.getAuditTime()) && (merchant.getAuditTime().getTime() > queryDate.getTime()))) {
						totalCount--;
					}
				}
			}
			summary.setTotalMerchants(totalCount);
			summarys.add(summary);
		}
		if(Checker.isNotEmpty(summarys)){
			List<List<ReportSignSummary>> totalSummaryList = CollectionsUtil.splitList(summarys, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportSignSummary> subList : totalSummaryList){
				boolean result = reportSignSummaryMapper.addByBatch(subList);
				LogCvt.info("签约商户汇总表cb_report_sign_summary添加"+(result?"成功":"失败"));
			}
		}
		
	}
	
	@Override
	public void initialize() {
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		
		reportSignSummaryMapper = rpSqlSession.getMapper(ReportSignSummaryMapper.class);
		
		orgUtil = new OrgUtil(sqlSession);
		
		merchantUtil = new AuditMerchantUtil(sqlSession, AuditMerchantUtil.KEY_ORG_CODE_CONTRACT_STAFF);
	}
}
