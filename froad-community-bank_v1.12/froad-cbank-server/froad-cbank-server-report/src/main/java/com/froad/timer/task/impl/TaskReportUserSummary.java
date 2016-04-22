package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportBankOrgMapper;
import com.froad.db.mysql.rp_mappers.ReportUserMapper;
import com.froad.db.mysql.rp_mappers.ReportUserSummaryMapper;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.ReportBankOrg;
import com.froad.po.ReportUser;
import com.froad.po.ReportUserSummary;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportUserSummary extends AbstractProTask {
	private ReportBankOrgMapper bankOrgMapper;
	private ReportUserMapper userMapper;
	private ReportUserSummaryMapper userSummaryMapper;
	
	public TaskReportUserSummary(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	protected void processByChunk(BatchChunk batchChunk) {
		StringBuffer logMsg = null;
		Date endDate = null;
		Map<String, Long> userCountMap = null;
		List<ReportBankOrg> bankOrgs = null;
		List<ReportUserSummary> summarys = null;
		ReportUserSummary summary = null;
		Page<ReportBankOrg> page = null;
		
		// log message
		logMsg = new StringBuffer();
		logMsg.append("Start process batch_id:batchDate:jobName:chunkId ");
		logMsg.append(batchChunk.getBatchId());
		logMsg.append(":");
		logMsg.append(batchChunk.getBatchDate());
		logMsg.append(":");
		logMsg.append(batchChunk.getJobName());
		logMsg.append(":");
		logMsg.append(batchChunk.getChunkId());
		LogCvt.info(logMsg.toString());		
		
		page = new Page<ReportBankOrg>();
		page.setPageNumber(batchChunk.getChunkPage());
		page.setPageSize(batchChunk.getChunkSize());
		
		endDate = TaskTimeUtil.convertToDayEnd(batchChunk.getBatchDate());
		
		userCountMap = getUserCount(endDate);
		bankOrgs = bankOrgMapper.findInfoByPage(page);
		summarys = new ArrayList<ReportUserSummary>();
		for(ReportBankOrg bankOrg : bankOrgs){
			summary = new ReportUserSummary();
			summary.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
			summary.setClientId(bankOrg.getClientId());
			summary.setBankCardId(bankOrg.getBankCardId());
			summary.setForgCode(bankOrg.getForgCode());
			summary.setForgName(bankOrg.getForgName());
			summary.setSorgCode(bankOrg.getSorgCode());
			summary.setSorgName(bankOrg.getSorgName());
			summary.setTorgCode(bankOrg.getTorgCode());
			summary.setTorgName(bankOrg.getTorgName());
			summary.setLorgCode(bankOrg.getLorgCode());
			summary.setLorgName(bankOrg.getLorgName());
			summary.setTotalUser(Checker.isNotEmpty(userCountMap.get(bankOrg.getBankCardId())) ? userCountMap.get(bankOrg.getBankCardId()) : 0l);
			summarys.add(summary);
		}
		
		
		if(Checker.isNotEmpty(summarys)){
			List<List<ReportUserSummary>> totalList = CollectionsUtil.splitList(summarys, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportUserSummary> subList : totalList){
				boolean result = userSummaryMapper.addByBatch(subList);
				LogCvt.info("机构用户流水表cb_report_user_summary添加数据"+(result ? "成功" : "失败"));
			}
		}
	}
	
	private Map<String, Long> getUserCount(Date endDate){
		Map<String, Long> map = new HashMap<String, Long>();
		List<ReportUser> users = userMapper.findUserCountByCardBin(endDate);
		for(ReportUser user : users){
			map.put(user.getBankCardId(), user.getTotalUsers());
		}
		return map;
	}
	
	
	@Override
	public void initialize() {
		bankOrgMapper = rpSqlSession.getMapper(ReportBankOrgMapper.class);
		userMapper = rpSqlSession.getMapper(ReportUserMapper.class);
		userSummaryMapper = rpSqlSession.getMapper(ReportUserSummaryMapper.class);
	}

}
