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

import com.froad.db.mysql.mappers.MerchantCategoryMapper;
import com.froad.db.mysql.mappers.OrgMapper;
import com.froad.db.mysql.rp_mappers.DataSuborderMapper;
import com.froad.db.mysql.rp_mappers.ReportBossMerchantCategorySaleMapper;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.MerchantCategory;
import com.froad.po.Org;
import com.froad.po.ReportBossMerchantCategorySale;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.TaskTimeUtil;

/**
 * ClassName: 按商户分类统计销量和占比 <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月12日 下午7:08:27
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class TaskReportBossMerchantCategorySale extends AbstractProTask {
	
	private OrgMapper orgMapper;
	
	private DataSuborderMapper dataSuborderMapper;
	
	private MerchantCategoryMapper merchantCategoryMapper;
	
	private ReportBossMerchantCategorySaleMapper reportBossMerchantCategorySaleMapper;
	
	public TaskReportBossMerchantCategorySale(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void initialize() {
		orgMapper = sqlSession.getMapper(OrgMapper.class);
		merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);
		
		dataSuborderMapper = rpSqlSession.getMapper(DataSuborderMapper.class);
		reportBossMerchantCategorySaleMapper = rpSqlSession.getMapper(ReportBossMerchantCategorySaleMapper.class);
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
	 * 
	 * @param sumBatchDate 统计的批次
	 * @param batchDate 日报或周报的批次
	 */
	public void processByOrg1(Org org1, Integer sumBatchDate, Integer batchDate, Date begin, Date end){
		// 按ClientId获取商户分类
		MerchantCategory mcParam = new MerchantCategory();
		mcParam.setClientId(org1.getClientId());
		mcParam.setIsDelete(false);
		List<MerchantCategory> mcList = merchantCategoryMapper.findMerchantCategory(mcParam);
		
		MerchantCategory mc = null;
		ReportBossMerchantCategorySale mcSale = null;
		List<ReportBossMerchantCategorySale> list = new ArrayList<ReportBossMerchantCategorySale>();
		Long totalCount = 0L, totalAmount = 0L;
		for (int i = 0; mcList != null && i < mcList.size(); i++) {
			mc = mcList.get(i);
			
			mcSale = dataSuborderMapper.sumOrgSaleDataByMerchantCategory(sumBatchDate, mc.getClientId(), mc.getId() + "", end);
			if (mcSale == null) {
				mcSale = new ReportBossMerchantCategorySale();
			}
			
			ReportBossMerchantCategorySale categorySale = new ReportBossMerchantCategorySale();
			categorySale.setReportDate(batchDate);
			categorySale.setReportBegDate(begin);
			categorySale.setReportEndDate(end);
			categorySale.setCreateTime(new Date());
			categorySale.setClientId(mc.getClientId());
			categorySale.setMerchantCategoryId(mc.getId());
			categorySale.setMerchantCategoryName(mc.getName());
			categorySale.setTotalSoldCount(mcSale.getTotalSoldCount() == null ? 0 : mcSale.getTotalSoldCount());
			categorySale.setTotalSoldCountPercent("");
			categorySale.setTotalSoldAmount(mcSale.getTotalSoldAmount() == null ? 0 : mcSale.getTotalSoldAmount());
			categorySale.setTotalSoldAmountPercent("");
			list.add(categorySale);
			
			totalCount += categorySale.getTotalSoldCount();
			totalAmount += categorySale.getTotalSoldAmount();
		}
		
		// 计算占比
		for (int i = 0; list != null && i < list.size(); i++) {
			// 销售数量占比
			double countPer = totalCount == 0 ? 0 : Arith.div(list.get(i).getTotalSoldCount().doubleValue(), totalCount.doubleValue(), 4);
			list.get(i).setTotalSoldCountPercent("" + Arith.mul(countPer, 100d));
			
			// 销售金额占比
			double amountPer = totalAmount == 0 ? 0 : Arith.div(list.get(i).getTotalSoldAmount().doubleValue(), totalAmount.doubleValue(), 4);
			list.get(i).setTotalSoldAmountPercent("" + Arith.mul(amountPer, 100d));
			
		}
		
		if(Checker.isNotEmpty(list)){
			int batchSize = reportBossMerchantCategorySaleMapper.addByBatch(list);
			LogCvt.info("商户分类销售占比数据表cb_report_boss_merchant_category_sale批量添加" + (batchSize > 0 ? "成功" : "失败"));
		}
		
	}

}
