package com.froad.timer.task.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportBankOrgMapper;
import com.froad.db.mysql.rp_mappers.ReportUserMapper;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.ReportBankOrg;
import com.froad.po.ReportUser;
import com.froad.thirdparty.UserSpecFunc;
import com.froad.thirdparty.impl.UserSpecFuncImpl;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.TaskTimeUtil;
import com.pay.user.dto.MemberSignSpecDto;
import com.pay.user.helper.BankOrg;

public class TaskReportUser extends AbstractProTask {

	private ReportBankOrgMapper bankOrgMapper;
	private ReportUserMapper userMapper;
	
	private UserSpecFunc userSpecFunc;
	
	public TaskReportUser(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	protected void processByChunk(BatchChunk batchChunk) {
		Date begDate = null;
		Date endDate = null; 
		Page<ReportBankOrg> page = null;
		StringBuffer logMsg = null;
		List<ReportUser> users = null;
		ReportUser find = null;
		ReportUser dbUser = null;
		ReportUser user = null;
		Map<String, List<MemberSignSpecDto>> signMems = null;
		List<MemberSignSpecDto> signs = null;
		Map<Long, MemberSignSpecDto> userInfoMap = null;
		MemberSignSpecDto sign = null;
		
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
		
		begDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()) : new Date(0l);
		endDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		
		List<ReportBankOrg> bankOrgs = bankOrgMapper.findInfoByPage(page);
		
		try {
			signMems = userSpecFunc.queryMemberByBankId(BankOrg.BANK_AH.getBankID(), begDate, endDate);
		} catch (IOException e) {
			return;
		}
		
		users = new ArrayList<ReportUser>();
		for(ReportBankOrg bankOrg : bankOrgs){
//			signs = (List<MemberSignSpecDto>) userSpecFunc.queryMemberByCardBin(bankOrg.getBankCardId(), BankOrg.BANK_AH.getBankID(), begDate, endDate).getData();
			
			signs = signMems.get(bankOrg.getBankCardId());
			
			userInfoMap = getUserInfoMap(signs);
			if(Checker.isEmpty(userInfoMap)){
				continue;
			}
			
			for(Long memberCode : userInfoMap.keySet()){
				sign = userInfoMap.get(memberCode);
				find = new ReportUser();
				find.setClientId(bankOrg.getClientId());
				find.setBankCardId(bankOrg.getBankCardId());
				find.setForgCode(bankOrg.getForgCode());
				find.setSorgCode(bankOrg.getSorgCode());
				find.setTorgCode(bankOrg.getTorgCode());
				find.setLorgCode(bankOrg.getLorgCode());
				find.setUserId(memberCode);
				dbUser = userMapper.findOneUser(find);
				if(Checker.isNotEmpty(dbUser)){
					dbUser.setSignTime(sign.getSignTime());
					dbUser.setUserName(sign.getCardHostName());
					dbUser.setIsVip(sign.getIsVip());
					dbUser.setValidStatus(sign.getValidStatus());
					userMapper.updateUser(dbUser);
					continue;
				}
				
				user = new ReportUser();
				user.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
				user.setClientId(bankOrg.getClientId());
				user.setBankCardId(bankOrg.getBankCardId());
				user.setForgCode(bankOrg.getForgCode());
				user.setForgName(bankOrg.getForgName());
				user.setSorgCode(bankOrg.getSorgCode());
				user.setSorgName(bankOrg.getSorgName());
				user.setTorgCode(bankOrg.getTorgCode());
				user.setTorgName(bankOrg.getTorgName());
				user.setLorgCode(bankOrg.getLorgCode());
				user.setLorgName(bankOrg.getLorgName());
				user.setUserId(sign.getMemberCode());
				user.setLoginId(sign.getLoginID());
				user.setUserName(sign.getCardHostName());
				user.setMobile(sign.getMobile());
				user.setRegTime(sign.getCreateTime());
				user.setRegType(sign.getCreateChannel());
				user.setIsVip(Checker.isNotEmpty(sign.getIsVip()) ? sign.getIsVip() : false);
				user.setSignTime(sign.getSignTime());
				user.setValidStatus(sign.getValidStatus());
				users.add(user);
			}
			
		}
		
		if(Checker.isNotEmpty(users)){
			List<List<ReportUser>> totalList = CollectionsUtil.splitList(users, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportUser> subList : totalList){
				boolean result = userMapper.addUserByBatch(subList);
				LogCvt.info("用户表cb_report_user添加数据"+(result ? "成功" : "失败"));
			}
		}
		
		// 最后一个chunk作会员排重
		if (batchChunk.getChunkPage() == batchChunk.getTotalChunk()){
			rpSqlSession.commit(true);
			LogCvt.info("清除临时排重表cb_report_user_unique");
			userMapper.truncateUniqueTable();
			LogCvt.info("插入新数据到临时排重表cb_report_user_unique");
			userMapper.insertUniqueUsers();
		}
	}

	
	private Map<Long, MemberSignSpecDto> getUserInfoMap(List<MemberSignSpecDto> signs){
		Map<Long, MemberSignSpecDto> map = new HashMap<Long, MemberSignSpecDto>();
		if(Checker.isEmpty(signs)){
			return map;
		}
		for(MemberSignSpecDto sign : signs){
			if(map.containsKey(sign.getMemberCode())){
				MemberSignSpecDto dto = map.get(sign.getMemberCode());
				if(dto.getSignTime().getTime() < sign.getSignTime().getTime()){
					map.put(sign.getMemberCode(), sign);
				}
			}else{
				map.put(sign.getMemberCode(), sign);
			}
		}
		return map;
	}
	
	
	
	@Override
	public void initialize() {
		bankOrgMapper = rpSqlSession.getMapper(ReportBankOrgMapper.class);
		userMapper = rpSqlSession.getMapper(ReportUserMapper.class);
		
		userSpecFunc = new UserSpecFuncImpl();
	}

}
