package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportSignSummaryDetailMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Merchant;
import com.froad.po.MerchantCount;
import com.froad.po.ReportSignSummaryDetail;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.AuditMerchantUtil;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.OrgUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportSignSummaryDetail extends AbstractProTask {
	
	private MerchantMapper merchantMapper;

	private ReportSignSummaryDetailMapper reportSignSummaryDetailMapper;
	
	private OrgUtil orgUtil = null;
	private AuditMerchantUtil merchantUtil = null;
	
	public TaskReportSignSummaryDetail(String name, TaskBean task) {
		super(name, task);
	}


	/**
	 * process by chunk
	 * 
	 * @param batchChunk
	 */
	public void processByChunk(BatchChunk batchChunk){
		List<ReportSignSummaryDetail> details = null;
		Date lastBegDate = null;
		Date lastEndDate = null;
		MerchantCount merchantCount = null;
		ReportSignSummaryDetail detail = null;
		Long incrCount = null;
		Long cancelCount = null;
		Page<MerchantCount> page = null;
		StringBuffer logMsg = null;
		boolean result = false;
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
		
		lastBegDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()) : null;
		lastEndDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		
		page = new Page<MerchantCount>();
		page.setPageNumber(batchChunk.getChunkPage());
		page.setPageSize(batchChunk.getChunkSize());
		
		List<MerchantCount> list = merchantMapper.selectMerchantIdListInfoByPage(null, lastEndDate, page);
		details = new ArrayList<ReportSignSummaryDetail>();
		for(int i=0; i<list.size(); i++){
			merchantCount = list.get(i);
			
			forgName = null;
			sorgName = null;
			torgName = null;
			lorgName = null;
			lorgCode = null;
			
			forgCode = merchantCount.getProOrgCode();
			if(orgUtil.containsKey(merchantCount.getClientId(), forgCode)){
				forgName = orgUtil.getOrg(merchantCount.getClientId(), forgCode).getOrgName();
			}
			sorgCode = merchantCount.getCityOrgCode();
			if(orgUtil.containsKey(merchantCount.getClientId(), sorgCode)){
				sorgName = orgUtil.getOrg(merchantCount.getClientId(), sorgCode).getOrgName();
			}
			torgCode = merchantCount.getCountyOrgCode();
			if(orgUtil.containsKey(merchantCount.getClientId(), torgCode)){
				torgName = orgUtil.getOrg(merchantCount.getClientId(), torgCode).getOrgName();
			}
			if (OrgLevelEnum.orgLevel_four.getLevel().equals(orgUtil.getOrg(merchantCount.getClientId(), merchantCount.getOrgCode()).getOrgLevel())) {
				lorgCode = merchantCount.getOrgCode();
				lorgName = orgUtil.getOrg(merchantCount.getClientId(), lorgCode).getOrgName();
			}
			
			detail = new ReportSignSummaryDetail();
			detail.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
			detail.setClientId(merchantCount.getClientId());
			detail.setForgCode(forgCode);
			detail.setForgName(forgName);
			detail.setSorgCode(sorgCode);
			detail.setSorgName(sorgName);
			detail.setTorgCode(torgCode);
			detail.setTorgName(torgName);
			detail.setLorgCode(lorgCode);
			detail.setLorgName(lorgName);
			detail.setSignUserName(merchantCount.getContractStaff());
			incrCount = 0l;
			cancelCount = 0l;
			merchantList = merchantUtil.getMerchantList(merchantCount.getClientId(), forgCode, sorgCode, torgCode, merchantCount.getContractStaff());
			for(int j=0; j< merchantList.size(); j++){
				merchant = merchantList.get(j);
				if (merchant.getCreateTime().getTime() > lastEndDate.getTime()){
					// 创建时间大于查询时间，不需要处理
					continue;
				}
				boolean isNew = false;
				boolean isCancel = false;
				if(Checker.isNotEmpty(merchant)){
					if(Checker.isNotEmpty(merchant.getAuditTime())){
						isNew =  merchant.getAuditTime().getTime()>=(lastBegDate != null ? lastBegDate.getTime() : 0) 
								&& merchant.getAuditTime().getTime()<=lastEndDate.getTime();
					}else{
						isNew =  merchant.getCreateTime().getTime()>=(lastBegDate != null ? lastBegDate.getTime() : 0) 
								&& merchant.getCreateTime().getTime()<=lastEndDate.getTime();
					}
					isCancel = !merchant.getIsEnable() && StringUtils.equals(merchant.getDisableStatus(), MerchantDisableStatusEnum.unregistered.getCode());
					if(isNew){
						incrCount++;
					}
					if(isCancel){
						cancelCount++;
					}
				}
			}
			detail.setNewTotalMerchant(incrCount);
			detail.setCancelTotalMerchant(cancelCount);
			details.add(detail);
		}
		
		if(Checker.isNotEmpty(details)){
			List<List<ReportSignSummaryDetail>> totalDetailList = CollectionsUtil.splitList(details, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportSignSummaryDetail> subList : totalDetailList){
				result = reportSignSummaryDetailMapper.addByBatch(subList);
				LogCvt.info("签约商户汇总流水表cb_report_sign_summary_detail 数据添加"+(result?"成功":"失败"));
			}
		}
		
	}
	
	@Override
	public void initialize() {
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		
		reportSignSummaryDetailMapper = rpSqlSession.getMapper(ReportSignSummaryDetailMapper.class);
		
		orgUtil = new OrgUtil(sqlSession);
		
		merchantUtil = new AuditMerchantUtil(sqlSession, AuditMerchantUtil.KEY_ORG_CODE_CONTRACT_STAFF);
	}
}
