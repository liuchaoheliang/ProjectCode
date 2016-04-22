package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.MerchantDetailMongoService;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.mappers.MerchantTypeMapper;
import com.froad.db.mysql.rp_mappers.ReportSignTypeSummaryMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Merchant;
import com.froad.po.MerchantCount;
import com.froad.po.MerchantType;
import com.froad.po.ReportSignTypeSummary;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.AuditMerchantUtil;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.OrgUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportSignTypeSummary extends AbstractProTask {
	
	private MerchantMapper merchantMapper;
	private MerchantTypeMapper typeMapper;
	
	private ReportSignTypeSummaryMapper reportSignTypeSummaryMapper;
	
	private MerchantDetailMongoService merchantDetailMongoService;
	
	private OrgUtil orgUtil = null;
	private AuditMerchantUtil merchantUtil = null;

	public TaskReportSignTypeSummary(String name, TaskBean task) {
		super(name, task);
	}

	/**
	 * process by chunk
	 * 
	 * @param batchChunk
	 */
	public void processByChunk(BatchChunk batchChunk){
		List<MerchantType> typeInfos = null;
		List<MerchantCount> list = null;
		List<ReportSignTypeSummary> typeSummarys = null;
		MerchantCount c = null;
		Date queryDate = null;
		String forgCode = null;
		String forgName = null;
		String sorgCode = null;
		String sorgName = null;
		String torgCode = null;
		String torgName = null;
		String lorgCode = null;
		String lorgName = null;
		String merchantId = null;
		Merchant merchant = null;
		List<String> merchantIds = null;
		MerchantType type = null;
		Integer count = null;
		ReportSignTypeSummary s = null;
		Page<MerchantCount> page = null;
		StringBuffer logMsg = null;
		boolean result = false;
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
		
		list = merchantMapper.selectMerchantIdListInfoByPage(null, queryDate, page);
		typeSummarys = new ArrayList<ReportSignTypeSummary>();
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
			
			merchantList = merchantUtil.getMerchantList(c.getClientId(), forgCode, sorgCode, torgCode, c.getContractStaff());
			merchantIds = new ArrayList<String>();
			for(int j=0; j< merchantList.size(); j++){
				merchant = merchantList.get(j);
				merchantId = merchant.getMerchantId();
				boolean isCancel = false;
				if(Checker.isNotEmpty(merchant)){
					isCancel = !merchant.getIsEnable() && StringUtils.equals(merchant.getDisableStatus(), MerchantDisableStatusEnum.unregistered.getCode());
					//非解约状态，审核时间小于当前跑批时间
					if(!isCancel && !(Checker.isNotEmpty(merchant.getAuditTime()) && (merchant.getAuditTime().getTime() > queryDate.getTime()))){
						merchantIds.add(merchantId);
					}
				}
			}
			typeInfos = typeMapper.findAllTypeInfo(c.getClientId());
			for(int k=0; k<typeInfos.size(); k++){
				type = typeInfos.get(k);
				count = merchantDetailMongoService.getTypeSummary(merchantIds, type.getType());
				s = new ReportSignTypeSummary();
				s.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
				s.setClientId(c.getClientId());
				s.setForgCode(forgCode);
				s.setForgName(forgName);
				s.setSorgCode(sorgCode);
				s.setSorgName(sorgName);
				s.setTorgCode(torgCode);
				s.setTorgName(torgName);
				s.setLorgCode(lorgCode);
				s.setLorgName(lorgName);
				s.setSignUserName(c.getContractStaff());
				s.setType(type.getType());
				s.setTotalMerchants(count.longValue());
				typeSummarys.add(s);
			}
		}
		
		if(Checker.isNotEmpty(typeSummarys)){
			List<List<ReportSignTypeSummary>> totalList = CollectionsUtil.splitList(typeSummarys, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportSignTypeSummary> subList : totalList){
				result = reportSignTypeSummaryMapper.addByBatch(subList);
				LogCvt.info("签约商户类型汇总表cb_report_sign_type_summary 添加"+(result?"成功":"失败"));
			}
		}
		
	}
	
	@Override
	public void initialize() {
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		typeMapper = sqlSession.getMapper(MerchantTypeMapper.class);
		
		merchantDetailMongoService = new MerchantDetailMongoService();
		
		reportSignTypeSummaryMapper = rpSqlSession.getMapper(ReportSignTypeSummaryMapper.class);
		
		orgUtil = new OrgUtil(sqlSession);
		
		merchantUtil = new AuditMerchantUtil(sqlSession, AuditMerchantUtil.KEY_ORG_CODE_CONTRACT_STAFF);
	}
}
