package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.froad.db.mongo.MerchantDetailMongoService;
import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.mappers.OutletMapper;
import com.froad.db.mysql.rp_mappers.BatchChunkMapper;
import com.froad.db.mysql.rp_mappers.ReportMerchantOutletMapper;
import com.froad.db.mysql.rp_mappers.ReportSignMerchantMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.MerchantTypeEnum;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.OutletDisableStatusEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.SubOrderType;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Merchant;
import com.froad.po.MerchantOutlet;
import com.froad.po.ReportMerchantOutlet;
import com.froad.po.ReportSignMerchant;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.mongo.TypeInfo;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.DateUtil;
import com.froad.util.OrgUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportSignMerchant extends AbstractProTask {
	
	private MerchantMapper merchantMapper;
	private OutletMapper outletMapper = null;
	
	private ReportSignMerchantMapper reportSignMerchantMapper;
	private ReportMerchantOutletMapper reportMerchantOutletMapper = null;
	
	private OrgUtil orgUtil = null;
	
	/**mongo操作*/
	private MerchantDetailMongoService merchantDetailMongoService = new MerchantDetailMongoService();
    private OrderMongoService orderMongoService = new OrderMongoService();
    private SubOrderMongoService subOrderMongoService = new SubOrderMongoService();
    
	public TaskReportSignMerchant(String name, TaskBean task) {
		super(name, task);
	}

	
	/**
	 * process by chunk
	 * 
	 * @param batchChunk
	 * @param typeInfos
	 */
	public void processByChunk(BatchChunk batchChunk){
		List<ReportSignMerchant> signs = null;
		List<Merchant> merchants = null;
		Map<String, List<OrderMongo>> faceOrderMap = null;
		Map<String, List<SubOrderMongo>> groupOrderMap = null;
		Map<String, List<SubOrderMongo>> specialOrderMap = null;
//		Map<String, List<SubOrderMongo>> orgMerchantOrderMap = null;
		Date lastStartDate = null;
		Date lastEndDate = null;
		MerchantDetail detail = null;
		ReportSignMerchant sm = null;
		Page<Merchant> page = null;
		StringBuffer logMsg = null;
		boolean result = false;
		String orgCode = null;
		String orgName = null;
		String forgCode = null;
		String forgName = null;
		String sorgCode = null;
		String sorgName = null;
		String torgCode = null;
		String torgName = null;
		String lorgCode = null;
		String lorgName = null;
		String userOrgCode = null;
		String userOrgName = null;
		String userForgCode = null;
		String userForgName = null;
		String userSorgCode = null;
		String userSorgName = null;
		String userTorgCode = null;
		String userTorgName = null;
		String userLorgCode = null;
		String userLorgName = null;
		List<List<ReportSignMerchant>> splitSignList = null;
		List<ReportMerchantOutlet> reportOutletList = null;
		List<ReportMerchantOutlet> subReportOutletList = null;
		List<List<ReportMerchantOutlet>> splitReportOutletList = null;
		long lastStartTime = 0;
		long lastEndTime = 0;
		Date reportCreateTime = null;
		
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
		
		page = new Page<Merchant>();
		page.setPageNumber(batchChunk.getChunkPage());
		page.setPageSize(batchChunk.getChunkSize());
		
		lastStartDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()) : null;
		lastEndDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		
		lastStartTime = lastStartDate != null ? lastStartDate.getTime() : 0l;
		lastEndTime = lastEndDate != null ? lastEndDate.getTime() : 0l;
		
		faceOrderMap = getFaceOrderMap(lastStartTime, lastEndTime);
		groupOrderMap = getGroupOrderMap(lastStartTime, lastEndTime);
		specialOrderMap = getSpecialOrderMap(lastStartTime, lastEndTime);
		
		merchants = merchantMapper.findAllByPage(page, lastEndDate);
		signs = new ArrayList<ReportSignMerchant>();
		reportOutletList = new ArrayList<ReportMerchantOutlet>();
		for (Merchant merchant : merchants) {
			if(!ProductAuditState.passAudit.getCode().equals(merchant.getAuditState())){
				continue;
			}
			
			orgName = null;
			forgName = null;
			sorgName = null;
			torgName = null;
			lorgName = null;
			lorgCode = null;
			
			userForgCode = null;
			userForgName = null;
			userSorgCode = null;
			userSorgName = null;
			userTorgCode = null;
			userTorgName = null;
			userLorgCode = null;
			userLorgName = null;
			
			orgCode = merchant.getOrgCode();
			if (orgUtil.containsKey(merchant.getClientId(), orgCode)){
				orgName = orgUtil.getOrg(merchant.getClientId(), orgCode).getOrgName();
			} else {
				if (!merchant.getMerchantStatus()){
					LogCvt.error(new StringBuffer("机构码不存在，orgCode=").append(orgCode).toString());
				}
				continue;
			}
			forgCode = merchant.getProOrgCode();
			if (orgUtil.containsKey(merchant.getClientId(), forgCode)){
				forgName = orgUtil.getOrg(merchant.getClientId(), forgCode).getOrgName();
			}
			sorgCode = merchant.getCityOrgCode();
			if (orgUtil.containsKey(merchant.getClientId(), sorgCode)){
				sorgName = orgUtil.getOrg(merchant.getClientId(), sorgCode).getOrgName();
			}
			torgCode = merchant.getCountyOrgCode();
			if (orgUtil.containsKey(merchant.getClientId(), torgCode)){
				torgName = orgUtil.getOrg(merchant.getClientId(), torgCode).getOrgName();
			}
			
			if (orgUtil.getOrg(merchant.getClientId(), orgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_one.getLevel())){
				forgCode = orgCode;
				forgName = orgName;
			} else if (orgUtil.getOrg(merchant.getClientId(), orgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())) {
				sorgCode = orgCode;
				sorgName = orgName;
			} else if (orgUtil.getOrg(merchant.getClientId(), orgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())) {
				torgCode = orgCode;
				torgName = orgName;
			} else if (orgUtil.getOrg(merchant.getClientId(), orgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_four.getLevel())) {
				lorgCode = orgCode;
				lorgName = orgName;
			}
			
			userOrgCode = merchant.getUserOrgCode();
			if(orgUtil.containsKey(merchant.getClientId(), userOrgCode)){
				userOrgName = orgUtil.getOrg(merchant.getClientId(), userOrgCode).getOrgName();
				
				if (orgUtil.getOrg(merchant.getClientId(), userOrgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_one.getLevel())){
					userForgCode = userOrgCode;
					userForgName = userOrgName;
				} else if (orgUtil.getOrg(merchant.getClientId(), userOrgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())) {
					userForgCode = orgUtil.getOrg(merchant.getClientId(), userOrgCode).getProvinceAgency();
					userForgName = orgUtil.containsKey(merchant.getClientId(), userForgCode) ? orgUtil.getOrg(merchant.getClientId(), userForgCode).getOrgName() : null;
					userSorgCode = userOrgCode;
					userSorgName = userOrgName;
				} else if (orgUtil.getOrg(merchant.getClientId(), userOrgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())) {
					userForgCode = orgUtil.getOrg(merchant.getClientId(), userOrgCode).getProvinceAgency();
					userForgName = orgUtil.containsKey(merchant.getClientId(), userForgCode) ? orgUtil.getOrg(merchant.getClientId(), userForgCode).getOrgName() : null;
					userSorgCode = orgUtil.getOrg(merchant.getClientId(), userOrgCode).getCityAgency();
					userSorgName = orgUtil.containsKey(merchant.getClientId(), userSorgCode) ? orgUtil.getOrg(merchant.getClientId(), userSorgCode).getOrgName() : null;
					userTorgCode = userOrgCode;
					userTorgName = userOrgName;
				} else if (orgUtil.getOrg(merchant.getClientId(), userOrgCode).getOrgLevel().equals(OrgLevelEnum.orgLevel_four.getLevel())) {
					userForgCode = orgUtil.getOrg(merchant.getClientId(), userOrgCode).getProvinceAgency();
					userForgName = orgUtil.containsKey(merchant.getClientId(), userForgCode) ? orgUtil.getOrg(merchant.getClientId(), userForgCode).getOrgName() : null;
					userSorgCode = orgUtil.getOrg(merchant.getClientId(), userOrgCode).getCityAgency();
					userSorgName = orgUtil.containsKey(merchant.getClientId(), userSorgCode) ? orgUtil.getOrg(merchant.getClientId(), userSorgCode).getOrgName() : null;
					userTorgCode = orgUtil.getOrg(merchant.getClientId(), userOrgCode).getCountyAgency();
					userTorgName = orgUtil.containsKey(merchant.getClientId(), userTorgCode) ? orgUtil.getOrg(merchant.getClientId(), userTorgCode).getOrgName() : null;
					userLorgCode = userOrgCode;
					userLorgName = userOrgName;
				}
			} else {
				if (!merchant.getMerchantStatus()){
					LogCvt.error(new StringBuffer("签约用户机构不存在，userOrgCode=").append(userOrgCode).toString());
				}
				continue;
			}
			
			boolean isNew = false;
			boolean isCancel = false;
			if(Checker.isNotEmpty(merchant.getAuditTime())){
				isNew = merchant.getAuditTime().getTime() >= (lastStartDate != null ? lastStartDate.getTime() : 0)
						&& merchant.getAuditTime().getTime() <= lastEndDate.getTime();
				if (isNew) {
					reportCreateTime = DateUtils.addDays(merchant.getAuditTime(), 1);
				} else {
					// 如当天未审核，不统计
					if (merchant.getAuditTime().getTime() > lastEndDate.getTime()){
						continue;
					}
					reportCreateTime = TaskTimeUtil.convertToDay(batchChunk.getBatchDate());
				}
			}else{
				isNew = merchant.getCreateTime().getTime() >= (lastStartDate != null ? lastStartDate.getTime() : 0)
						&& merchant.getCreateTime().getTime() <= lastEndDate.getTime();
				reportCreateTime = TaskTimeUtil.convertToDay(batchChunk.getBatchDate());
			}
					
			isCancel = !merchant.getIsEnable() && StringUtils.equals(merchant.getDisableStatus(), MerchantDisableStatusEnum.unregistered.getCode());
					
			// 普通商户
			if (!merchant.getMerchantStatus()) {
				detail = merchantDetailMongoService.findById(merchant
						.getMerchantId());
				if (Checker.isNotEmpty(detail)) {
					boolean isF2fExists = false;
					if (Checker.isNotEmpty(detail.getTypeInfo())) {
						for (TypeInfo type : detail.getTypeInfo()) {
							sm = new ReportSignMerchant();
							sm.setCreateTime(reportCreateTime);
							sm.setClientId(merchant.getClientId());
							sm.setMerchantId(merchant.getMerchantId());
							sm.setOrgCode(orgCode);
							sm.setOrgName(orgName);
							sm.setForgCode(forgCode);
							sm.setForgName(forgName);
							sm.setSorgCode(sorgCode);
							sm.setSorgName(sorgName);
							sm.setTorgCode(torgCode);
							sm.setTorgName(torgName);
							sm.setLorgCode(lorgCode);
							sm.setLorgName(lorgName);
							sm.setSignUserName(merchant.getContractStaff());
							sm.setUserOrgCode(userOrgCode);
							sm.setUserOrgName(userOrgName);
							sm.setUserForgCode(userForgCode);
							sm.setUserForgName(userForgName);
							sm.setUserSorgCode(userSorgCode);
							sm.setUserSorgName(userSorgName);
							sm.setUserTorgCode(userTorgCode);
							sm.setUserTorgName(userTorgName);
							sm.setUserLorgCode(userLorgCode);
							sm.setUserLorgName(userLorgName);

							// 普通商户有面对面,团购,名优特惠订单
							switch (MerchantTypeEnum.getByType(type.getType())) {
							case face:
								// 昨天的面对面订单
								isF2fExists = true;
								
								// 非新增、非解约和无交易的不统计
								if(!isNew && !isCancel && !faceOrderMap.containsKey(merchant.getMerchantId())){
									continue;
								}
								
								sm.setIsNew(isNew ? 1 : 0);
								sm.setIsCancel(isCancel ? 1 : 0);
								sm.setType(MerchantTypeEnum.face.getType());
								if (!faceOrderMap.containsKey(merchant.getMerchantId())) {
									sm.setIsChange(0);
									sm.setTotalOrders(0l);
									sm.setTotalAmount(0l);
								} else {
									sm.setIsChange(1);
									List<OrderMongo> faceOrders = faceOrderMap.get(merchant.getMerchantId());
									sm.setTotalOrders(Long.valueOf(faceOrders.size()));
									long ftotalAmount = getOrderTotalAmount(faceOrders);
									sm.setTotalAmount(ftotalAmount);
								}
								break;
							case group:
								// 非新增、非解约和无交易的不统计
								if(!isNew && !isCancel && !groupOrderMap.containsKey(merchant.getMerchantId())){
									continue;
								}
								
								sm.setIsNew(isNew ? 1 : 0);
								sm.setIsCancel(isCancel ? 1 : 0);
								setReportSignMerchantByType(sm,	MerchantTypeEnum.group.getType(), merchant.getMerchantId(), groupOrderMap);
								break;
							case special:
								// 非新增、非解约和无交易的不统计
								if(!isNew && !isCancel && !specialOrderMap.containsKey(merchant.getMerchantId())){
									continue;
								}
								
								sm.setIsNew(isNew ? 1 : 0);
								sm.setIsCancel(isCancel ? 1 : 0);
								setReportSignMerchantByType(sm,	MerchantTypeEnum.special.getType(),	merchant.getMerchantId(), specialOrderMap);
								break;
							default:
								continue;
							}
							signs.add(sm);
						}
					}

					// 除直接优惠商户外，其他类型普通商户均可进行面对面支付
					if (!isF2fExists && faceOrderMap.containsKey(merchant.getMerchantId())) {
						sm = new ReportSignMerchant();
						sm.setCreateTime(reportCreateTime);
						sm.setClientId(merchant.getClientId());
						sm.setMerchantId(merchant.getMerchantId());
						sm.setOrgCode(orgCode);
						sm.setOrgName(orgName);
						sm.setForgCode(forgCode);
						sm.setForgName(forgName);
						sm.setSorgCode(sorgCode);
						sm.setSorgName(sorgName);
						sm.setTorgCode(torgCode);
						sm.setTorgName(torgName);
						sm.setLorgCode(lorgCode);
						sm.setLorgName(lorgName);
						sm.setSignUserName(merchant.getContractStaff());
						sm.setUserOrgCode(userOrgCode);
						sm.setUserOrgName(userOrgName);
						sm.setUserForgCode(userForgCode);
						sm.setUserForgName(userForgName);
						sm.setUserSorgCode(userSorgCode);
						sm.setUserSorgName(userSorgName);
						sm.setUserTorgCode(userTorgCode);
						sm.setUserTorgName(userTorgName);
						sm.setUserLorgCode(userLorgCode);
						sm.setUserLorgName(userLorgName);
						sm.setIsNew(0);
						sm.setIsCancel(0);
						sm.setType(MerchantTypeEnum.face.getType());
						sm.setIsChange(1);
						List<OrderMongo> faceOrders = faceOrderMap.get(merchant.getMerchantId());
						sm.setTotalOrders(Long.valueOf(faceOrders.size()));
						sm.setTotalAmount(getOrderTotalAmount(faceOrders));
						signs.add(sm);
					}
				}

				// 生成网点商户信息
				subReportOutletList = generateMerchantOutlet(merchant,
						userOrgCode, userOrgName, userForgCode, userForgName,
						userSorgCode, userSorgName, userTorgCode, userTorgName,
						userLorgCode, userLorgName, lastStartTime, lastEndTime,
						batchChunk.getBatchDate());
				if (Checker.isNotEmpty(subReportOutletList)){
					reportOutletList.addAll(subReportOutletList);
				}
			}
//					else{
					// 银行商户
//						sm = new ReportSignMerchant();
//						sm.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
//						sm.setCuId(user.getId());
//						sm.setMerchantId(merchant.getMerchantId());
//						sm.setIsNew(isNew? 1 : 0);
//						sm.setIsCancel(isCancel? 1 : 0);
//						
//						setReportSignMerchantByType(sm, null, merchant.getOrgCode(), orgMerchantOrderMap);
//						// 银行商户存在面对面订单情况
//						if(faceOrderMap.containsKey(merchant.getOrgCode())){
//							long totalAmount = sm.getTotalAmount();
//							List<OrderMongo> f2fOrders = faceOrderMap.get(merchant.getOrgCode());
//							for(OrderMongo f : f2fOrders){
//								totalAmount += f.getTotalPrice();
//							}
//							sm.setTotalAmount(totalAmount);
//						}
//						signs.add(sm);
//					}
		}
		
		if(Checker.isNotEmpty(signs)){
			splitSignList = CollectionsUtil.splitList(signs, CollectionsUtil.MAX_INSERT_SIZE);
			
			for (List<ReportSignMerchant> signList : splitSignList){
				result = reportSignMerchantMapper.addByBatch(signList);
				LogCvt.info("签约商户统计表cb_report_sign_merchant 数据添加"+(result?"成功":"失败"));
			}
		}
		
		if (Checker.isNotEmpty(reportOutletList)){
			splitReportOutletList = CollectionsUtil.splitList(reportOutletList, CollectionsUtil.MAX_INSERT_SIZE);
			
			for (int i = 0; i < splitReportOutletList.size(); i++){
				subReportOutletList = splitReportOutletList.get(i);
				result = reportMerchantOutletMapper.addByBatch(subReportOutletList);
				LogCvt.info("商户门店信息统计表cb_report_merchant_outlet 数据添加"+(result?"成功":"失败"));
			}
		}
	}
	
	/**
	 * 生成报表商户门店信息
	 * 
	 * @param merchant
	 * @param orgCode
	 * @param orgName
	 * @param forgCode
	 * @param forgName
	 * @param sorgCode
	 * @param sorgName
	 * @param torgCode
	 * @param torgName
	 * @param lorgCode
	 * @param lorgName
	 * @param startTime
	 * @param endTime
	 * @param batchDate
	 * @return
	 */
	private List<ReportMerchantOutlet> generateMerchantOutlet(
			Merchant merchant, String orgCode, String orgName, String forgCode,
			String forgName, String sorgCode, String sorgName, String torgCode,
			String torgName, String lorgCode, String lorgName, long startTime,
			long endTime, int batchDate) {
		List<ReportMerchantOutlet> reportOutletList = null;
		List<ReportMerchantOutlet> tmpReportOutletList = null;
		ReportMerchantOutlet reportOutlet = null;
		ReportMerchantOutlet reportOutletQuery = null;
		List<MerchantOutlet> outletList = null;
		MerchantOutlet merchantOutlet = null;
		MerchantOutlet outletQuery = null;
		String orgAddress = null;
		
		reportOutletList = new ArrayList<ReportMerchantOutlet>();
		
		// 获取网点地址
		outletQuery = new MerchantOutlet();
		outletQuery.setClientId(merchant.getClientId());
		outletQuery.setOutletId(orgUtil.getOrg(merchant.getClientId(), orgCode).getOutletId());
		outletList = outletMapper.findByCondition(outletQuery);
		if (Checker.isEmpty(outletList)){
			LogCvt.warn(new StringBuffer("找不到相关网点信息，org_code=").append(orgCode).toString());
			return reportOutletList;
		}
		orgAddress = outletList.get(0).getAddress();
		
		// 获取商户门店
		outletQuery = new MerchantOutlet();
		outletQuery.setClientId(merchant.getClientId());
		outletQuery.setMerchantId(merchant.getMerchantId());
		outletList = outletMapper.findByCondition(outletQuery);
		if (Checker.isEmpty(outletList)){
			LogCvt.warn(new StringBuffer("找不到相关门店信息，merchant_id=").append(merchant.getMerchantId()).toString());
			return reportOutletList;
		}
		for (int i = 0; i < outletList.size(); i++){
			merchantOutlet = outletList.get(i);
			if (merchantOutlet.getCreateTime().getTime() >= startTime
					&& merchantOutlet.getCreateTime().getTime() <= endTime) {
				// 新增门店
				reportOutlet = new ReportMerchantOutlet();
				reportOutlet.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
				reportOutlet.setClientId(merchant.getClientId());
				reportOutlet.setOrgCode(orgCode);
				reportOutlet.setOrgName(orgName);
				reportOutlet.setOrgAddress(orgAddress);
				reportOutlet.setForgCode(forgCode);
				reportOutlet.setForgName(forgName);
				reportOutlet.setSorgCode(sorgCode);
				reportOutlet.setSorgName(sorgName);
				reportOutlet.setTorgCode(torgCode);
				reportOutlet.setTorgName(torgName);
				reportOutlet.setLorgCode(lorgCode);
				reportOutlet.setLorgName(lorgName);
				reportOutlet.setMerchantId(merchant.getMerchantId());
				reportOutlet.setMerchantName(merchant.getMerchantName());
				reportOutlet.setMerchantOutletId(merchantOutlet.getOutletId());
				reportOutlet.setMerchantOutletName(merchantOutlet.getOutletName());
				reportOutlet.setNewOutletCount(1);
				if (merchantOutlet.getDisableStatus().equals(OutletDisableStatusEnum.normal.getCode())){
					reportOutlet.setCancelOutletCount(0);
				} else {
					reportOutlet.setCancelOutletCount(1);
				}
				reportOutlet.setDisableStatus(merchantOutlet.getDisableStatus());
				reportOutletList.add(reportOutlet);
			} else if (!merchantOutlet.getDisableStatus().equals(OutletDisableStatusEnum.normal.getCode())) {
				// 检查门店是否删除或禁用
				reportOutletQuery = new ReportMerchantOutlet();
				reportOutletQuery.setClientId(merchant.getClientId());
				reportOutletQuery.setMerchantId(merchant.getMerchantId());
				reportOutletQuery.setMerchantOutletId(merchantOutlet.getOutletId());
				tmpReportOutletList = reportMerchantOutletMapper.findByCondition(reportOutletQuery);
				if (Checker.isEmpty(tmpReportOutletList)){
					// 失效门店，如无记录，先增加新增信息
					reportOutlet = new ReportMerchantOutlet();
					reportOutlet.setCreateTime(DateUtil.skipDateTime(merchantOutlet.getCreateTime(), 1));
					reportOutlet.setClientId(merchant.getClientId());
					reportOutlet.setOrgCode(orgCode);
					reportOutlet.setOrgName(orgName);
					reportOutlet.setOrgAddress(orgAddress);
					reportOutlet.setForgCode(forgCode);
					reportOutlet.setForgName(forgName);
					reportOutlet.setSorgCode(sorgCode);
					reportOutlet.setSorgName(sorgName);
					if (Checker.isEmpty(sorgCode)){
						reportOutlet.setSorgCode(forgCode);
						reportOutlet.setSorgName(forgName);
					}
					reportOutlet.setTorgCode(torgCode);
					reportOutlet.setTorgName(torgName);
					reportOutlet.setLorgCode(lorgCode);
					reportOutlet.setLorgName(lorgName);
					reportOutlet.setMerchantId(merchant.getMerchantId());
					reportOutlet.setMerchantName(merchant.getMerchantName());
					reportOutlet.setMerchantOutletId(merchantOutlet.getOutletId());
					reportOutlet.setMerchantOutletName(merchantOutlet.getOutletName());
					reportOutlet.setNewOutletCount(1);
					reportOutlet.setCancelOutletCount(0);
					reportOutlet.setDisableStatus(OutletDisableStatusEnum.normal.getCode());
					reportOutletList.add(reportOutlet);
					
					// 失效门店，失效信息
					reportOutlet = new ReportMerchantOutlet();
					reportOutlet.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
					reportOutlet.setClientId(merchant.getClientId());
					reportOutlet.setOrgCode(orgCode);
					reportOutlet.setOrgName(orgName);
					reportOutlet.setOrgAddress(orgAddress);
					reportOutlet.setForgCode(forgCode);
					reportOutlet.setForgName(forgName);
					reportOutlet.setSorgCode(sorgCode);
					reportOutlet.setSorgName(sorgName);
					if (Checker.isEmpty(sorgCode)){
						reportOutlet.setSorgCode(forgCode);
						reportOutlet.setSorgName(forgName);
					}
					reportOutlet.setTorgCode(torgCode);
					reportOutlet.setTorgName(torgName);
					reportOutlet.setLorgCode(lorgCode);
					reportOutlet.setLorgName(lorgName);
					reportOutlet.setMerchantId(merchant.getMerchantId());
					reportOutlet.setMerchantName(merchant.getMerchantName());
					reportOutlet.setMerchantOutletId(merchantOutlet.getOutletId());
					reportOutlet.setMerchantOutletName(merchantOutlet.getOutletName());
					reportOutlet.setNewOutletCount(0);
					reportOutlet.setCancelOutletCount(1);
					reportOutlet.setDisableStatus(merchantOutlet.getDisableStatus());
					reportOutletList.add(reportOutlet);
				} else if (tmpReportOutletList.size() == 1
						&& tmpReportOutletList.get(0).getDisableStatus().equals(OutletDisableStatusEnum.normal.getCode())) {
					// 失效门店，失效信息
					reportOutlet = new ReportMerchantOutlet();
					reportOutlet.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
					reportOutlet.setClientId(merchant.getClientId());
					reportOutlet.setOrgCode(orgCode);
					reportOutlet.setOrgName(orgName);
					reportOutlet.setOrgAddress(orgAddress);
					reportOutlet.setForgCode(forgCode);
					reportOutlet.setForgName(forgName);
					reportOutlet.setSorgCode(sorgCode);
					reportOutlet.setSorgName(sorgName);
					if (Checker.isEmpty(sorgCode)){
						reportOutlet.setSorgCode(forgCode);
						reportOutlet.setSorgName(forgName);
					}
					reportOutlet.setTorgCode(torgCode);
					reportOutlet.setTorgName(torgName);
					reportOutlet.setLorgCode(lorgCode);
					reportOutlet.setLorgName(lorgName);
					reportOutlet.setMerchantId(merchant.getMerchantId());
					reportOutlet.setMerchantName(merchant.getMerchantName());
					reportOutlet.setMerchantOutletId(merchantOutlet.getOutletId());
					reportOutlet.setMerchantOutletName(merchantOutlet.getOutletName());
					reportOutlet.setNewOutletCount(0);
					reportOutlet.setCancelOutletCount(1);
					reportOutlet.setDisableStatus(merchantOutlet.getDisableStatus());
					reportOutletList.add(reportOutlet);
				} else {
					LogCvt.warn(new StringBuffer(
							"门店已为失效状态，merchant_id=")
							.append(merchant.getMerchantId())
							.append(" outlet_id=")
							.append(merchantOutlet.getOutletId())
							.toString());
				}
			}
		}
		
		return reportOutletList;
	}
	
	@Override
	public void initialize() {
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		outletMapper = sqlSession.getMapper(OutletMapper.class);
		
		reportSignMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
		batchChunkMapper = rpSqlSession.getMapper(BatchChunkMapper.class);
		reportMerchantOutletMapper = rpSqlSession.getMapper(ReportMerchantOutletMapper.class);
		
		orgUtil = new OrgUtil(sqlSession);
	}
	
	/**
	 * 获取面对面订单信息
	 * @Title: getFaceOrderMap 
	 * @Description: key-merchantId, value-orders
	 * @author: froad-huangyihao 2015年6月13日
	 * @modify: froad-huangyihao 2015年6月13日
	 * @return
	 * @throws
	 */
	private Map<String, List<OrderMongo>> getFaceOrderMap(long lastStartTime, long lastEndTime){
		List<OrderMongo> list = null;
		Map<String, List<OrderMongo>> map = null;
		
		list = orderMongoService.findFaceOrder(lastStartTime, lastEndTime);
		map = new HashMap<String, List<OrderMongo>>();
		
		for (OrderMongo o : list) {
			if(Checker.isEmpty(map.get(o.getMerchantId()))){
				List<OrderMongo> orders = new ArrayList<OrderMongo>();
				orders.add(o);
				map.put(o.getMerchantId(), orders);
			}else{
				List<OrderMongo> orders = map.get(o.getMerchantId());
				orders.add(o);
				map.put(o.getMerchantId(), orders);
			}
		}
		return map;
	}
	
	/**
	 * 获取团购订单
	 * @Title: getGroupOrderMap 
	 * @Description: key-merchantId, value-orders
	 * @author: froad-huangyihao 2015年6月13日
	 * @modify: froad-huangyihao 2015年6月13日
	 * @return
	 * @throws
	 */
	private Map<String, List<SubOrderMongo>> getGroupOrderMap(long lastStartTime, long lastEndTime){
		Map<String, List<SubOrderMongo>> map = null;
		List<SubOrderMongo> list = null;
		
		map = new HashMap<String, List<SubOrderMongo>>();
		list = subOrderMongoService.findSubOrderByType(lastStartTime, lastEndTime, SubOrderType.group_merchant.getCode());
		
		for(SubOrderMongo s : list){
			if(Checker.isEmpty(map.get(s.getMerchantId()))){
				List<SubOrderMongo> subs = new ArrayList<SubOrderMongo>();
				subs.add(s);
				map.put(s.getMerchantId(), subs);
			}else{
				List<SubOrderMongo> subs = map.get(s.getMerchantId());
				subs.add(s);
				map.put(s.getMerchantId(), subs);
			}
		}
		return map;
	}
	
	private Map<String, List<SubOrderMongo>> getSpecialOrderMap(long lastStartTime, long lastEndTime){
		Map<String, List<SubOrderMongo>> map = null;
		List<SubOrderMongo> list = null;
		
		map = new HashMap<String, List<SubOrderMongo>>();
		list = subOrderMongoService.findSubOrderByType(lastStartTime, lastEndTime, SubOrderType.special_merchant.getCode());
		
		for(SubOrderMongo s : list){
			if(Checker.isEmpty(map.get(s.getMerchantId()))){
				List<SubOrderMongo> subs = new ArrayList<SubOrderMongo>();
				subs.add(s);
				map.put(s.getMerchantId(), subs);
			}else{
				List<SubOrderMongo> subs = map.get(s.getMerchantId());
				subs.add(s);
				map.put(s.getMerchantId(), subs);
			}
		}
		return map;
	}
	
	
	private long getOrderTotalAmount(List<OrderMongo> orders){
		long totalAmount = 0;
		for (OrderMongo order : orders) {
			totalAmount += order.getTotalPrice();
		}
		return totalAmount;
	}
	
	private long getSubTotalAmount(List<SubOrderMongo> subs){
		long totalAmount = 0;
		for(SubOrderMongo s : subs){
			for (ProductMongo p : s.getProducts()) {
				Integer amount = (p.getQuantity() * p.getMoney()) + (p.getVipQuantity() * p.getVipMoney()) + p.getDeliveryMoney();
				totalAmount += amount;
			}
		}
		return totalAmount;
	}
	

	private void setReportSignMerchantByType(ReportSignMerchant sm, String type, String merchantId, Map<String, List<SubOrderMongo>> subMap){
		List<SubOrderMongo> subs = null;
		sm.setType(ObjectUtils.toString(type, null));
		if (!subMap.containsKey(merchantId)) {
			sm.setIsChange(0);
			sm.setTotalOrders(0l);
			sm.setTotalAmount(0l);
		} else {
			sm.setIsChange(1);
			subs = subMap.get(merchantId);
			sm.setTotalOrders(Long.valueOf(subs.size()));
			long stotalAmount = getSubTotalAmount(subs);
			sm.setTotalAmount(stotalAmount);
		}
	}

}
