/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:TaskReportBossOrgSale.java
 * Package Name:com.froad.timer.task.impl
 * Date:2015年8月12日下午7:08:27
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.froad.db.mysql.mappers.OrgMapper;
import com.froad.db.mysql.rp_mappers.DataSuborderMapper;
import com.froad.db.mysql.rp_mappers.ReportBossOrgSaleMapper;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Org;
import com.froad.po.ReportBossOrgSale;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.TaskTimeUtil;

/**
 * ClassName: 按机构统计销量数据报表 <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月12日 下午7:08:27
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class TaskReportBossOrgSale extends AbstractProTask {
	
	private OrgMapper orgMapper;
	
	private DataSuborderMapper dataSuborderMapper;
	
	private ReportBossOrgSaleMapper reportBossOrgSaleMapper;
	
	public TaskReportBossOrgSale(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void initialize() {
		orgMapper = sqlSession.getMapper(OrgMapper.class);
		
		dataSuborderMapper = rpSqlSession.getMapper(DataSuborderMapper.class);
		reportBossOrgSaleMapper = rpSqlSession.getMapper(ReportBossOrgSaleMapper.class);
	}

	@Override
	public void processByChunk(BatchChunk batchChunk) {
		// log message
		StringBuffer logMsg = null;
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
		
		Date begDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()) : null;
		Date endDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		
		// 获取一级机构
		List<Org> orgList1 = orgMapper.findOrgList(null, OrgLevelEnum.orgLevel_one.getLevel());
		for (int i = 0; orgList1 != null && i < orgList1.size(); i++) {
			Org org1 = orgList1.get(i);
			
			processByOrg1(org1, batchChunk.getBatchDate(), batchChunk.getBatchDate(), begDate, endDate);

			// 如果是周一，还要统计周报数据
			if (DateUtil.isMonday()) {
				LogCvt.debug("统计周报");
				
				// 获取上周的日期
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				Date lastWeekBegDate = calendar.getTime();
				Date lastWeekEndDate = DateUtil.skipDateTime(calendar.getTime(), 6);
				// 以上周一计算当月第几周，作为批次日期
				int weekBatchDate = DateUtil.getWeekOfMonth(lastWeekBegDate);
				
				Date begin = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, DateUtil.formatDateTime("yyyy-MM-dd 00:00:00", lastWeekBegDate));
				Date end = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, DateUtil.formatDateTime("yyyy-MM-dd 23:59:59", lastWeekEndDate));
				processByOrg1(org1, batchChunk.getBatchDate(), weekBatchDate, begin, end);
				
			}
			
		}
		
	}
	
	/**
	 * 根据一级机构处理数据
	 */
	public void processByOrg1(Org org1, Integer sumBatchDate, Integer batchDate, Date begin, Date end){
		// 获取二级机构
		List<Org> orgList2 = orgMapper.findOrgList(org1.getClientId(), OrgLevelEnum.orgLevel_two.getLevel());
		Org org2 = null;
		ReportBossOrgSale curSum = null, totalSum = null;
		List<ReportBossOrgSale> list = new ArrayList<ReportBossOrgSale>();
		for (int i = 0; orgList2 != null && i < orgList2.size(); i++) {
			org2 = orgList2.get(i);
			// 过滤不合法的机构
			if (!org2.getIsEnable()) {
				LogCvt.info("机构不可用, orgCode=" + org2.getOrgCode() + ", isEnable=" + org2.getIsEnable());
				continue;
			}
			
			// 团购
			curSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.group_merchant.getCode(), begin, end);
			totalSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.group_merchant.getCode(), null, end);
			if (curSum == null) { curSum = new ReportBossOrgSale();}
			if (totalSum == null) { totalSum = new ReportBossOrgSale();}
			list.add(initReportBossOrgSale(batchDate, begin, end, org2.getClientId(), OrderType.group_merchant.getCode(), org2.getOrgCode(), org2.getOrgName(), curSum, totalSum));
			
			// 预售
			curSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.presell_org.getCode(), begin, end);
			totalSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.presell_org.getCode(), null, end);
			if (curSum == null) { curSum = new ReportBossOrgSale();}
			if (totalSum == null) { totalSum = new ReportBossOrgSale();}
			list.add(initReportBossOrgSale(batchDate, begin, end, org2.getClientId(), OrderType.presell_org.getCode(), org2.getOrgCode(), org2.getOrgName(), curSum, totalSum));
			
			// 名优特惠
			curSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.special_merchant.getCode(), begin, end);
			totalSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.special_merchant.getCode(), null, end);
			if (curSum == null) { curSum = new ReportBossOrgSale();}
			if (totalSum == null) { totalSum = new ReportBossOrgSale();}
			list.add(initReportBossOrgSale(batchDate, begin, end, org2.getClientId(), OrderType.special_merchant.getCode(), org2.getOrgCode(), org2.getOrgName(), curSum, totalSum));
			
			// 面对面
			curSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.face2face.getCode(), begin, end);
			totalSum = dataSuborderMapper.sumOrgSaleDataByOrg(sumBatchDate, org2.getClientId(), org2.getOrgCode(), OrderType.face2face.getCode(), null, end);
			if (curSum == null) { curSum = new ReportBossOrgSale();}
			if (totalSum == null) { totalSum = new ReportBossOrgSale();}
			list.add(initReportBossOrgSale(batchDate, begin, end, org2.getClientId(), OrderType.face2face.getCode(), org2.getOrgCode(), org2.getOrgName(), curSum, totalSum));
			
		}
		
		if(Checker.isNotEmpty(list)){
			int batchSize = reportBossOrgSaleMapper.addByBatch(list);
			LogCvt.info("机构销量数据表cb_report_boss_org_sale批量添加" + (batchSize > 0 ? "成功" : "失败"));
		}
		
	}
	
	public ReportBossOrgSale initReportBossOrgSale(Integer batchDate, Date begin, Date end, 
			String clientId, String orderType, String orgCode, String orgName,
			ReportBossOrgSale curSum, ReportBossOrgSale totalSum){
		ReportBossOrgSale orgSale = new ReportBossOrgSale();
		orgSale.setReportDate(batchDate);
		orgSale.setReportBegDate(begin);
		orgSale.setReportEndDate(end);
		orgSale.setCreateTime(new Date());
		orgSale.setClientId(clientId);
		orgSale.setOrderType(orderType);
		orgSale.setOrgCode(orgCode);
		orgSale.setOrgName(orgName);
		orgSale.setCurSoldCount(curSum.getTotalSoldCount() == null ? 0 : curSum.getTotalSoldCount());
		orgSale.setCurSoldAmount(curSum.getTotalSoldAmount() == null ? 0 : curSum.getTotalSoldAmount());
		orgSale.setTotalSoldCount(totalSum.getTotalSoldCount() == null ? 0 : totalSum.getTotalSoldCount());
		orgSale.setTotalSoldAmount(totalSum.getTotalSoldAmount() == null ? 0 : totalSum.getTotalSoldAmount());
		return orgSale;
	}

}
