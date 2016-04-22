package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportUserDetailMapper;
import com.froad.db.mysql.rp_mappers.ReportUserMapper;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.ReportOrder;
import com.froad.po.ReportUser;
import com.froad.po.ReportUserDetail;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.ReportOrderUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportUserDetail extends AbstractProTask {
	
	private ReportUserMapper userMapper;
	private ReportUserDetailMapper userDetailMapper;
	
	public TaskReportUserDetail(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	protected void processByChunk(BatchChunk batchChunk) {
		StringBuffer logMsg = null;
		Page<ReportUser> page = null;
		Date endDate = null;
		List<ReportUser> users = null;
		List<ReportUserDetail> details = null;
		ReportUserDetail detail = null;
		Long lastBegTime = null;
		Long lastEndTime = null;
		Date signTime = null;
		boolean isNew = false;
		List<ReportOrder> reportOrderList = null;
		ReportOrderUtil orderUtil = null;
		
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
		
		page = new Page<ReportUser>();
		page.setPageNumber(batchChunk.getChunkPage());
		page.setPageSize(batchChunk.getChunkSize());
		
		endDate = TaskTimeUtil.convertToDayEnd(batchChunk.getBatchDate());
		lastBegTime = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()).getTime() : 0l;
		lastEndTime = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()).getTime() : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate()).getTime();
		
		// 订单信息
		orderUtil = new ReportOrderUtil(rpSqlSession, batchChunk.getBatchDate(), ReportOrderUtil.KEY_MEMBER_CODE);
		
		users = userMapper.findInfoByPage(endDate, page);
		details = new ArrayList<ReportUserDetail>();
		for(ReportUser user : users){
			detail = new ReportUserDetail();
			detail.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
			detail.setClientId(user.getClientId());
			detail.setBankCardId(user.getBankCardId());
			detail.setForgCode(user.getForgCode());
			detail.setForgName(user.getForgName());
			detail.setSorgCode(user.getSorgCode());
			detail.setSorgName(user.getSorgName());
			detail.setTorgCode(user.getTorgCode());
			detail.setTorgName(user.getTorgName());
			detail.setLorgCode(user.getLorgCode());
			detail.setLorgName(user.getLorgName());
			detail.setUserId(user.getUserId());
			detail.setLoginId(user.getLoginId());
			detail.setUserName(user.getUserName());
			detail.setMobile(user.getMobile());
			detail.setRegTime(user.getRegTime());
			detail.setRegType(user.getRegType());
			detail.setIsVip(user.getIsVip());
			detail.setSignTime(user.getSignTime());
			detail.setValidStatus(user.getValidStatus());
			signTime = user.getSignTime();
			isNew = user.getValidStatus() == 1 && (Checker.isEmpty(signTime) ? false : signTime.getTime() >= lastBegTime && signTime.getTime() <= lastEndTime);
			detail.setIsNew(isNew ? "1" : "0");
			
			reportOrderList = orderUtil.getOrderList(user.getClientId(), user.getUserId());
			if(Checker.isNotEmpty(reportOrderList)){
				detail.setIsChange("1");
				detail.setTotalOrder(Long.valueOf(reportOrderList.size()));
				long totalAmount = 0l;
				for(ReportOrder order : reportOrderList){
					totalAmount += order.getSubOrderAmount();
				}
				detail.setTotalAmount(totalAmount);
			}else{
				if (!isNew){
					//非新增且无交易，不需要添加记录
					continue;
				}
				detail.setIsChange("0");
				detail.setTotalOrder(0l);
				detail.setTotalAmount(0l);
			}
			details.add(detail);
		}
		if(Checker.isNotEmpty(details)){
			List<List<ReportUserDetail>> totalList = CollectionsUtil.splitList(details, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportUserDetail> subList : totalList){
				boolean result = userDetailMapper.addByBatch(subList);
				LogCvt.info("用户统计详情表cb_report_user_detail添加数据"+(result ? "成功" : "失败"));
			}
		}
	}

	@Override
	public void initialize() {
		userMapper = rpSqlSession.getMapper(ReportUserMapper.class);
		userDetailMapper = rpSqlSession.getMapper(ReportUserDetailMapper.class);
	}
}
