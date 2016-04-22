package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportBankUserMapper;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.MerchantCount;
import com.froad.po.Org;
import com.froad.po.ReportBankUser;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.JSonUtil;
import com.froad.util.OrgUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportBankUser extends AbstractProTask {
	
	private MerchantMapper merchantMapper;
	
	private ReportBankUserMapper reportBankUserMapper;
	
	private OrgUtil orgUtil = null;
	
	public TaskReportBankUser(String name, TaskBean task) {
		super(name, task);
	}

	
	/**
	 * process by chunk
	 * 
	 * @param batchChunk
	 */
	public void processByChunk(BatchChunk batchChunk){
		Date begDate = null;
		Date endDate = null;
		Page<MerchantCount> page = null;
		StringBuffer logMsg = null;
		List<MerchantCount> list = null;
		List<ReportBankUser> reports = null;
		ReportBankUser r = null;
		ReportBankUser find = null;
		
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
		begDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()) : null;
		endDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		list = merchantMapper.selectTotalMerchantByPage(begDate, endDate, page);
		
		reports = new ArrayList<ReportBankUser>();
		for (MerchantCount c : list) {
			r = new ReportBankUser();
			setReportBankUser(c, r);
			if (null == r.getForgCode()){
				find = null;
			} else {
				find = reportBankUserMapper.selectIsExist(r);
			}
			if(Checker.isNotEmpty(find)){
				LogCvt.warn(JSonUtil.toJSonString(find) + " 已存在");
				continue;
			}
			r.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
			reports.add(r);
		}
		if(Checker.isNotEmpty(reports)){
			List<List<ReportBankUser>> totalUsers = CollectionsUtil.splitList(reports, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportBankUser> subList : totalUsers){
				boolean result = reportBankUserMapper.addByBatch(subList);
				LogCvt.info("签约用户表cb_report_bank_user 添加"+(result?"成功":"失败"));
			}
		}
		
	}
	
	public void initialize(){
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		
		reportBankUserMapper = rpSqlSession.getMapper(ReportBankUserMapper.class);
		
		orgUtil = new OrgUtil(sqlSession);
	}
	
	private void setReportBankUser(MerchantCount c, ReportBankUser r){
		Org userOrg = null;
		String clientId = null;
		
		clientId = c.getClientId();
		
		r.setClientId(c.getClientId());
		r.setSignUserName(c.getContractStaff());
		
		userOrg = orgUtil.getOrg(clientId, c.getUserOrgCode());
		if (null == userOrg){
			LogCvt.warn(new StringBuffer("机构码不存在：").append(c.getUserOrgCode()).toString());
			return;
		}
		
		r.setForgCode(userOrg.getProvinceAgency());
		if (orgUtil.getOrg(clientId, userOrg.getProvinceAgency()) != null){
			r.setForgName(orgUtil.getOrg(clientId, userOrg.getProvinceAgency()).getOrgName());
		}
		r.setSorgCode(userOrg.getCityAgency());
		if (orgUtil.getOrg(clientId, userOrg.getCityAgency()) != null){
			r.setSorgName(orgUtil.getOrg(clientId, userOrg.getCityAgency()).getOrgName());
		}
		r.setTorgCode(userOrg.getCountyAgency());
		if (orgUtil.getOrg(clientId, userOrg.getCountyAgency()) != null){
			r.setTorgName(orgUtil.getOrg(clientId, userOrg.getCountyAgency()).getOrgName());
		}
		
		if (userOrg.getOrgLevel().equals(OrgLevelEnum.orgLevel_one.getLevel())){
			r.setForgCode(userOrg.getOrgCode());
			r.setForgName(userOrg.getOrgName());
		} else if (userOrg.getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())){
			r.setSorgCode(userOrg.getOrgCode());
			r.setSorgName(userOrg.getOrgName());
		} else if (userOrg.getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())){
			r.setTorgCode(userOrg.getOrgCode());
			r.setTorgName(userOrg.getOrgName());
		} else if (userOrg.getOrgLevel().equals(OrgLevelEnum.orgLevel_four.getLevel())){
			r.setLorgCode(userOrg.getOrgCode());
			r.setLorgName(userOrg.getOrgName());
		}

	}
	
}
