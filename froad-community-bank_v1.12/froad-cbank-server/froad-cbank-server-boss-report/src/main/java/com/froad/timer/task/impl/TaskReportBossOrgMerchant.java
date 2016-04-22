/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:TaskReportBossOrgMerchant.java
 * Package Name:com.froad.timer.task.impl
 * Date:2015年8月12日下午7:09:04
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.mappers.OrgMapper;
import com.froad.db.mysql.rp_mappers.ReportBossOrgMerchantMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Merchant;
import com.froad.po.Org;
import com.froad.po.ReportBossOrgMerchant;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.singleton.MerchantSingleton;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.MongoTableName;
import com.froad.util.TaskTimeUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName: TaskReportBossOrgMerchant <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月12日 下午7:09:04
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class TaskReportBossOrgMerchant extends AbstractProTask {
	
	/** 团购(本地商圈) */
	static final String MERCHANT_TYPE_GROUP = "0";
	/** 直接优惠(本地商圈) */
	static final String MERCHANT_TYPE_ZHIJIE = "1";
	
	/** 名优特惠 */
	static final String MERCHANT_TYPE_PREFERENT = "2";
	
	private MongoManager mongo;

	private OrgMapper orgMapper;
	
	private ReportBossOrgMerchantMapper reportBossOrgMerchantMapper;
	
	
	public TaskReportBossOrgMerchant(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void initialize() {
		mongo = new MongoManager();
		
		orgMapper = sqlSession.getMapper(OrgMapper.class);
		
		reportBossOrgMerchantMapper = rpSqlSession.getMapper(ReportBossOrgMerchantMapper.class);
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
			
			processByOrg1(org1, batchChunk.getBatchDate(), begDate, endDate);

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
				processByOrg1(org1, weekBatchDate, begin, end);
				
			}
			
		}
		
	}
	
	/**
	 * 根据一级机构处理数据
	 */
	@SuppressWarnings("unchecked")
	public void processByOrg1(Org org1, Integer batchDate, Date begin, Date end){
		int pageSize = 1000;
		MongoPage page = findMerchantListByPage(org1.getClientId(), 1, pageSize);
		if (page == null || page.getItems() == null) {
			LogCvt.warn("pageNo=1, Mongo未查询到MerchantDetail数据。");
			return;
		}
		
		// 初始化数据
		Map<String, ReportBossOrgMerchant> orgMerchantMap = new HashMap<String, ReportBossOrgMerchant>();
		// 获取二级机构
		List<Org> orgList2 = orgMapper.findOrgList(org1.getClientId(), OrgLevelEnum.orgLevel_two.getLevel());
		Org org2 = null;
		for (int i = 0; orgList2 != null && i < orgList2.size(); i++) {
			org2 = orgList2.get(i);
			ReportBossOrgMerchant orgMerchant = new ReportBossOrgMerchant();
			orgMerchant.setReportDate(batchDate);
			orgMerchant.setReportBegDate(begin);
			orgMerchant.setReportEndDate(end);
			orgMerchant.setCreateTime(new Date());
			orgMerchant.setClientId(org2.getClientId());
			orgMerchant.setOrgCode(org2.getOrgCode());
			orgMerchant.setOrgName(org2.getOrgName());
			orgMerchant.setAddnewGroupCount(0);
			orgMerchant.setTotalGroupCount(0);
			orgMerchant.setAddnewPreferentCount(0);
			orgMerchant.setTotalPreferentCount(0);
			orgMerchantMap.put(orgMerchant.getClientId() + "_" + orgMerchant.getOrgCode(), orgMerchant);
		}
		
		Integer totalCount = page.getTotalCount();
		int totalPages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		List<MerchantDetail> merchantList = null;
		MerchantDetail merchantDetail = null;
		Merchant merchant = null;
		for (int p = 1; p <= totalPages; p++) {
			page = findMerchantListByPage(org1.getClientId(), p, pageSize);
			if (page == null || page.getItems() == null) {
				LogCvt.warn("pageNo=" + p + ", Mongo未查询到MerchantDetail数据。");
				continue;
			}
			
			// 遍历Mongo中的商户
			merchantList = (List<MerchantDetail>)page.getItems();
			for (int i = 0; i < merchantList.size(); i++) {
				merchantDetail = merchantList.get(i);
				merchant = MerchantSingleton.getMerchant(merchantDetail.getId());
				if (merchant == null) {
					LogCvt.warn("merchantId=" + merchantDetail.getId() + ", Mysql中未查询到Merchant数据.");
					continue;
				}
				
				String key = merchant.getClientId() + "_" + merchant.getCityOrgCode();
				
				if (!merchant.getIsEnable()) {
					LogCvt.warn("merchantId=" + merchantDetail.getId() + ", 商户已被禁用.");
					continue;
				}
				
				if (!merchant.getDisableStatus().equals(MerchantDisableStatusEnum.normal.getCode())) {
					LogCvt.warn("merchantId=" + merchantDetail.getId() + ", 商户状态不正常.");
					continue;
				}
				
				if (merchant.getAuditTime() == null || !merchant.getAuditState().equals("1")) {
					LogCvt.warn("merchantId=" + merchantDetail.getId() + ", 商户未被审核过.");
					continue;
				}
				
				ReportBossOrgMerchant orgMerchantTemp = orgMerchantMap.get(key);
				if (orgMerchantTemp == null) {
					LogCvt.warn("key=" + key + ", Map中未查询到Merchant数据.");
					continue;
				}
				
				int flag = checkType(merchantDetail.getTypeInfo());
				if (flag < 0) {
					LogCvt.warn("flag=" + flag + ", merchantId=" + merchantDetail.getId() + "未属于任何分类.");
					continue;
				}
				
				// 如果在当前的统计截止时间内
				if (merchant.getAuditTime().getTime() > end.getTime()) {
					LogCvt.warn("merchantId=" + merchantDetail.getId() + "不在统计截止时间内.");
					continue;
				}
				
				// 如果在当前的统计周期内
				if (begin.getTime() <= merchant.getAuditTime().getTime()) {
					
					if (flag == 0 || flag == 1) {
						
						orgMerchantTemp.setAddnewGroupCount(orgMerchantTemp.getAddnewGroupCount() + 1);
						
					}
					
					if (flag == 0 || flag == 2) {
						
						orgMerchantTemp.setAddnewPreferentCount(orgMerchantTemp.getAddnewPreferentCount() + 1);
						
					}
					
				}
				
				if (flag == 0 || flag == 1) {
					
					orgMerchantTemp.setTotalGroupCount(orgMerchantTemp.getTotalGroupCount() + 1);
					
				}
				
				if (flag == 0 || flag == 2) {
					
					orgMerchantTemp.setTotalPreferentCount(orgMerchantTemp.getTotalPreferentCount() + 1);
				}
				
				orgMerchantMap.put(key, orgMerchantTemp);
				
			}
		}
		
		// 转换成List，再遍历一遍是因为想保持排序不变
		List<ReportBossOrgMerchant> list = new ArrayList<ReportBossOrgMerchant>();
		for (int i = 0; orgList2 != null && i < orgList2.size(); i++) {
			org2 = orgList2.get(i);
			
			list.add(orgMerchantMap.get(org2.getClientId() + "_" + org2.getOrgCode()));
		}
		
		if(Checker.isNotEmpty(list)){
			int batchSize = reportBossOrgMerchantMapper.addByBatch(list);
			LogCvt.info("机构商户数据表cb_report_boss_org_merchant批量添加" + (batchSize > 0 ? "成功" : "失败"));
		}
		
	}
	
	/**
	 * 检查所属分类
	 */
	public int checkType(List<TypeInfo> types){
		// 小于0都不是，0-三种都包含，1-团购或直接优惠，2-名优特惠
		if (types == null) {
			return -1;
		}
		
		boolean groupAndZhijie = false, preferent = false;
		for (TypeInfo type : types) {
			if (MERCHANT_TYPE_GROUP.equals(type.getType())
					|| MERCHANT_TYPE_ZHIJIE.equals(type.getType())) {
				
				groupAndZhijie = true;
				
			} 
			
			if (MERCHANT_TYPE_PREFERENT.equals(type.getType())) {
				preferent = true;
			}
		}
		
		if (groupAndZhijie && preferent) {
			return 0;
		} else if (groupAndZhijie) {
			return 1;
		} else if (preferent) {
			return 2;
		}
		
		return -1;
	}
	
	/**
	 * 根据clientId查询商户
	 */
	public MongoPage findMerchantListByPage(String clientId, int pageNo, int pageSize){
		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);
		MongoPage page = new MongoPage(pageNo, pageSize);
		page = mongo.findByPage(page, where, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
		return page;
	}
}
