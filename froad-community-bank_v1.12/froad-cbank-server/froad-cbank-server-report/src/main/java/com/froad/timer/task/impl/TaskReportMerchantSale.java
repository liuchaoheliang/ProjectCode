package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.froad.db.mongo.MerchantDetailMongoService;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportMerchantSaleMapper;
import com.froad.db.mysql.rp_mappers.ReportSalePaymethodMapper;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ReportOrderType;
import com.froad.enums.SubOrderRefundState;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Merchant;
import com.froad.po.Org;
import com.froad.po.ReportMerchantSale;
import com.froad.po.ReportOrder;
import com.froad.po.ReportOrderProduct;
import com.froad.po.ReportOrderRefund;
import com.froad.po.ReportSalePaymethod;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.OrgUtil;
import com.froad.util.ReportOrderProductUtil;
import com.froad.util.ReportOrderRefundUtil;
import com.froad.util.ReportOrderUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportMerchantSale extends AbstractProTask {
	
	private ReportMerchantSaleMapper reportMerchantSaleMapper = null;
	private ReportSalePaymethodMapper salePaymethodMapper = null;
	
	private MerchantMapper merchantMapper = null;
	
	private MerchantDetailMongoService merchantDetailMongoService = null;
	
	private OrgUtil orgUtil = null;
	
	public TaskReportMerchantSale(String name, TaskBean task) {
		super(name, task);
	}
	
	
	/**
	 * process by chunk
	 * 
	 * @param batchChunk
	 */
	public void processByChunk(BatchChunk batchChunk){
		Page<Merchant> page = null;
		Date queryDate = null;
		StringBuffer logMsg = null;
		List<Merchant> merchantList = null;
		Merchant merchant = null;
		List<ReportMerchantSale> saleList = null;
		List<ReportSalePaymethod> salePayMethodList = null;
		ReportOrderUtil orderUtil = null;
		ReportOrderProductUtil productUtil = null;
		ReportOrderRefundUtil refundUtil = null;
		List<ReportOrder> reportOrderList = null;
		List<ReportOrderProduct> reportProductList = null;
		List<ReportOrderRefund> reportRefundList = null;
		String merchantId = null;
		
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
		
		queryDate = batchCycle.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchCycle.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchCycle.getBatchDate());
		merchantList = merchantMapper.findAllByPage(page, queryDate);
		
		if (merchantList == null || merchantList.size() ==0){
			LogCvt.error("merchant record not found");
			return;
		}
		
		// 订单信息
		orderUtil = new ReportOrderUtil(rpSqlSession, batchChunk.getBatchDate(), ReportOrderUtil.KEY_MERCHANT_ID_ORDER_TYPE);
		
		// 商品信息
		productUtil = new ReportOrderProductUtil(rpSqlSession, batchChunk.getBatchDate(), ReportOrderProductUtil.KEY_MERCHANT_ID_PRODUCT_TYPE);
		
		// 退款信息
		refundUtil = new ReportOrderRefundUtil(rpSqlSession, batchChunk.getBatchDate(), ReportOrderRefundUtil.KEY_MERCHANT_ID_ORDER_TYPE);
		
		saleList = new ArrayList<ReportMerchantSale>();
		salePayMethodList = new ArrayList<ReportSalePaymethod>();
		
		for (int i = 0; i < merchantList.size(); i++){
			merchant = merchantList.get(i);
			if (!merchant.getAuditState().equals(ProductAuditState.passAudit.getCode())){
				continue;
			}
			
			if (merchant.getMerchantStatus()){
				merchantId = merchant.getOrgCode();//机构虚拟商户
			} else {
				merchantId = merchant.getMerchantId();//普通商户
			}
			
			// 面对面订单销售额统计
			LogCvt.info(new StringBuffer(this.getClass().getSimpleName())
					.append("-面对面订单销售额统计-").append(merchant.getMerchantId()).toString());
			reportOrderList = orderUtil.getOrderList(merchant.getClientId(), merchantId, ReportOrderType.face2face.getCode());
			reportProductList = productUtil.getProductList(merchant.getClientId(), merchantId, ReportOrderType.face2face.getCode());
			reportRefundList = refundUtil.getRefundList(merchant.getClientId(), merchantId, ReportOrderType.face2face.getCode());
			processIndividualMerchant(merchant, batchChunk.getBatchDate(),
					ReportOrderType.face2face.getCode(), saleList,
					salePayMethodList, reportOrderList, reportProductList,
					reportRefundList);
			
			// 团购订单销售额统计
			LogCvt.info(new StringBuffer(this.getClass().getSimpleName())
			.append("-团购订单销售额统计-").append(merchant.getMerchantId()).toString());
			reportOrderList = orderUtil.getOrderList(merchant.getClientId(), merchantId, ReportOrderType.group.getCode());
			reportProductList = productUtil.getProductList(merchant.getClientId(), merchantId, ReportOrderType.group.getCode());
			reportRefundList = refundUtil.getRefundList(merchant.getClientId(), merchantId, ReportOrderType.group.getCode());
			processIndividualMerchant(merchant, batchChunk.getBatchDate(),
					ReportOrderType.group.getCode(), saleList,
					salePayMethodList, reportOrderList, reportProductList,
					reportRefundList);
			
			// 预售订单销售额统计
			LogCvt.info(new StringBuffer(this.getClass().getSimpleName())
			.append("-预售订单销售额统计-").append(merchant.getMerchantId()).toString());
			reportOrderList = orderUtil.getOrderList(merchant.getClientId(), merchantId, ReportOrderType.presell.getCode());
			reportProductList = productUtil.getProductList(merchant.getClientId(), merchantId, ReportOrderType.presell.getCode());
			reportRefundList = refundUtil.getRefundList(merchant.getClientId(), merchantId, ReportOrderType.presell.getCode());
			processIndividualMerchant(merchant, batchChunk.getBatchDate(),
					ReportOrderType.presell.getCode(), saleList,
					salePayMethodList, reportOrderList, reportProductList,
					reportRefundList);
			
			// 名优特惠订单销售额统计
			LogCvt.info(new StringBuffer(this.getClass().getSimpleName())
			.append("-名优特惠订单销售额统计-").append(merchant.getMerchantId()).toString());
			reportOrderList = orderUtil.getOrderList(merchant.getClientId(), merchantId, ReportOrderType.special.getCode());
			reportProductList = productUtil.getProductList(merchant.getClientId(), merchantId, ReportOrderType.special.getCode());
			reportRefundList = refundUtil.getRefundList(merchant.getClientId(), merchantId, ReportOrderType.special.getCode());
			processIndividualMerchant(merchant, batchChunk.getBatchDate(),
					ReportOrderType.special.getCode(), saleList,
					salePayMethodList, reportOrderList, reportProductList,
					reportRefundList);
// 线上、线下积分兑换不统计			
//			// 线上积分兑换机构商户子订单销售额统计
//			LogCvt.info(new StringBuffer(this.getClass().getSimpleName())
//			.append("-线上积分兑换机构商户子订单销售额统计-").append(merchant.getMerchantId()).toString());
//			processIndividualMerchant(merchant, batchChunk.getBatchDate(), ReportOrderType.onlinePoint.getCode());
//			
//			// 线下积分兑换机构商户子订单销售额统计
//			LogCvt.info(new StringBuffer(this.getClass().getSimpleName())
//			.append("-线下积分兑换机构商户子订单销售额统计-").append(merchant.getMerchantId()).toString());
//			processIndividualMerchant(merchant, batchChunk.getBatchDate(), ReportOrderType.dotGift.getCode());
		}
		
		if (saleList.size() > 0){
			List<List<ReportMerchantSale>> totalSaleList = CollectionsUtil.splitList(saleList, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportMerchantSale> subList : totalSaleList){
				reportMerchantSaleMapper.addByBatch(subList);
			}
		}
		
		if (salePayMethodList.size() > 0){
			List<List<ReportSalePaymethod>> totalPaymethodList = CollectionsUtil.splitList(salePayMethodList, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportSalePaymethod> subList : totalPaymethodList){
				salePaymethodMapper.addByBatch(subList);
			}
		}
		
		logMsg.delete(0, logMsg.length());
		logMsg.append("Complete process batch_id:batchDate:jobName:chunkId ");
		logMsg.append(batchChunk.getBatchId());
		logMsg.append(":");
		logMsg.append(batchChunk.getBatchDate());
		logMsg.append(":");
		logMsg.append(batchChunk.getJobName());
		logMsg.append(":");
		logMsg.append(batchChunk.getChunkId());
		LogCvt.info(logMsg.toString());
	}
	
	/**
	 * 处理单个商户销售额
	 * 
	 * @param merchant
	 * @param batchDate
	 * @param orderType
	 * @param saleList
	 * @param salePayMethodList
	 * @param orderList
	 * @param productList
	 * @param refundList
	 */
	private void processIndividualMerchant(Merchant merchant, int batchDate,
			String orderType, List<ReportMerchantSale> saleList,
			List<ReportSalePaymethod> salePayMethodList,
			List<ReportOrder> orderList, List<ReportOrderProduct> productList,
			List<ReportOrderRefund> refundList) {
		ReportMerchantSale reportMerchantSale = null;
		String merchantId = null;
		String clientId = null;
		long buyTrips = 0;
		long bankPointAmount = 0;
		long cash = 0;
		long fftPointAmount = 0;
		ReportOrder reportOrder = null;
		ReportOrderProduct reportProduct = null;
		ReportOrderRefund reportRefund = null;
		String fOrgCode = null;
		String fOrgName = null;
		String lOrgCode = null;
		String lOrgName = null;
		long refundAmount = 0;
		long saleProductAmount = 0;
		long saleProductNumber = 0;
		String sOrgCode = null;
		String sOrgName = null;
		String tOrgCode = null;
		String tOrgName = null;
		long totalAmount = 0;
		long totalOrder = 0;
		Set <Long> memberCodeSet = null;
		List<Long> cashPayList = null;
		List<Long> froadPayList = null;
		List<Long> bankPayList = null;
		List<Long> froadCashPayList = null;
		List<Long> bankCashPayList = null;
		ReportSalePaymethod cashPaymethod = null;
		ReportSalePaymethod froadPaymethod = null;
		ReportSalePaymethod bankPaymethod = null;
		ReportSalePaymethod froadCashPaymethod = null;
		ReportSalePaymethod bankCashPaymethod = null;
		long tempAmount = 0;
		StringBuffer merchantTypes = null;
		MerchantDetail merchantDetail = null;
		List<TypeInfo> typeInfoList = null;
		TypeInfo typeInfo = null;
		Org org = null;
		
		memberCodeSet = new HashSet<Long>();
		cashPayList = new ArrayList<Long>();
		froadPayList = new ArrayList<Long>();
		bankPayList = new ArrayList<Long>();
		froadCashPayList = new ArrayList<Long>();
		bankCashPayList = new ArrayList<Long>();
		
		clientId = merchant.getClientId();
		if (merchant.getMerchantStatus()){
			merchantId = merchant.getOrgCode();//机构虚拟商户
		} else {
			merchantId = merchant.getMerchantId();//普通商户
		}
		
		if (Checker.isEmpty(orderList) && Checker.isEmpty(refundList)){
//			LogCvt.info(new StringBuffer("该商户当天没有交易、商户号：").append(merchantId).toString());
			return;
		}
		
		// 机构码信息
		if (orderList != null && orderList.size() > 0){
			reportOrder = orderList.get(0);
			fOrgCode = reportOrder.getForgCode();
			fOrgName = reportOrder.getForgName();
			lOrgCode = reportOrder.getLorgCode();
			lOrgName = reportOrder.getLorgName();
			sOrgCode = reportOrder.getSorgCode();
			sOrgName = reportOrder.getSorgName();
			tOrgCode = reportOrder.getTorgCode();
			tOrgName = reportOrder.getTorgName();
		} else {
			org = orgUtil.getOrg(merchant.getClientId(), merchant.getOrgCode());
			
			fOrgCode = org.getProvinceAgency();
			if (orgUtil.getOrg(merchant.getClientId(), fOrgCode) != null){
				fOrgName = orgUtil.getOrg(merchant.getClientId(), fOrgCode).getOrgName();
			}
			sOrgCode = org.getCityAgency();
			if (orgUtil.getOrg(merchant.getClientId(), sOrgCode) != null){
				sOrgName = orgUtil.getOrg(merchant.getClientId(), sOrgCode).getOrgName();
			}
			tOrgCode = org.getCountyAgency();
			if (orgUtil.getOrg(merchant.getClientId(), tOrgCode) != null) {
				tOrgName =  orgUtil.getOrg(merchant.getClientId(), tOrgCode).getOrgName();
			}
			
			if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_one.getLevel())){
				fOrgCode = org.getOrgCode();
				fOrgName = org.getOrgName();
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())){
				sOrgCode = org.getOrgCode();
				sOrgName = org.getOrgName();
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())){
				tOrgCode = org.getOrgCode();
				tOrgName = org.getOrgName();
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_four.getLevel())){
				lOrgCode = org.getOrgCode();
				lOrgName = org.getOrgName();
			}
		}
		
		if (Checker.isEmpty(lOrgCode)){
			lOrgCode = "";
		}
		
		if (Checker.isEmpty(tOrgCode)){
			tOrgCode = "";
		}
		
		// 订单信息统计
		if (orderList != null && orderList.size() > 0){
			for (int i = 0; i < orderList.size(); i++){
				reportOrder = orderList.get(i);
				
				memberCodeSet.add(reportOrder.getMemberCode());
				
				totalAmount += reportOrder.getSubOrderAmount();
				cash += reportOrder.getRealPrice();
				
				// 银行积分金额
				if (reportOrder.getBankPoint() > 0){
					bankPointAmount += reportOrder.getTotalPrice() - reportOrder.getRealPrice();
				}
				
				// 方付通积分金额
				if (reportOrder.getFftPoint() > 0){
					fftPointAmount += reportOrder.getTotalPrice() - reportOrder.getRealPrice();
				}
				
				// 现金支付
				if (reportOrder.getTotalPrice() == reportOrder.getRealPrice()){
					cashPayList.add(reportOrder.getTotalPrice());
				}
				
				// 方付通积分支付
				if (reportOrder.getFftPoint() > 0 && reportOrder.getRealPrice() == 0){
					froadPayList.add(reportOrder.getTotalPrice());
				}
				
				// 银行积分支付
				if (reportOrder.getBankPoint() > 0 && reportOrder.getRealPrice() == 0){
					bankPayList.add(reportOrder.getTotalPrice());
				}
				
				// 方付通积分+现金支付
				if (reportOrder.getFftPoint() > 0 && reportOrder.getRealPrice() > 0){
					froadCashPayList.add(reportOrder.getTotalPrice());
				}
				
				// 银行积分+现金支付
				if (reportOrder.getBankPoint() > 0 && reportOrder.getRealPrice() > 0){
					bankCashPayList.add(reportOrder.getTotalPrice());
				}
			}
			
			totalOrder = orderList.size();
			
			buyTrips = memberCodeSet.size();
		}
		
		// 商品信息统计，只统计支付完成订单
		if (productList != null && productList.size() > 0) {
			for (int i = 0; i < productList.size(); i++){
				reportProduct = productList.get(i);
				
				// 全额退款的不计算
				if (reportProduct.getOrderStatus().equals(OrderStatus.paysuccess.getCode())
						&& !reportProduct.getRefundState().equals(SubOrderRefundState.REFUND_SUCCESS.getCode())) {
					saleProductNumber += reportProduct.getQuantity() + reportProduct.getVipQuantity();
					saleProductAmount += reportProduct.getMoney() * reportProduct.getQuantity()
							+ reportProduct.getVipMoney() * reportProduct.getVipQuantity();
//							+ reportProduct.getDeliveryMoney();
				}
			}
		}
		
		// 退款金额统计
		if (refundList != null && refundList.size() > 0){
			for (int i = 0; i < refundList.size(); i++){
				reportRefund = refundList.get(i);
				
				refundAmount += reportRefund.getRefundAmount();
				if (reportRefund.getRefundPoint() != 0 && reportRefund.getPointRate() != null){
					refundAmount +=  Arith.div(reportRefund.getRefundPoint(), Double.valueOf(reportRefund.getPointRate()));
				}
			}
		}
		
		merchantTypes = new StringBuffer();
		merchantDetail = merchantDetailMongoService.findById(merchantId);
		if (null != merchantDetail){
			typeInfoList = merchantDetail.getTypeInfo();
			if (typeInfoList != null && typeInfoList.size() > 0){
				for (int i = 0; i < typeInfoList.size(); i++){
					typeInfo = typeInfoList.get(i);
					merchantTypes.append(typeInfo.getTypeName());
					if (i < typeInfoList.size() - 1){
						merchantTypes.append(",");
					}
				}
			}
		}

		
		reportMerchantSale = new ReportMerchantSale();
		reportMerchantSale.setBankPointAmount(bankPointAmount);
		reportMerchantSale.setBuyTrips(buyTrips);
		reportMerchantSale.setCash(cash);
		reportMerchantSale.setClientId(clientId);
		reportMerchantSale.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
		reportMerchantSale.setFftPointAmount(fftPointAmount);
		reportMerchantSale.setForgCode(fOrgCode);
		reportMerchantSale.setForgName(fOrgName);
		reportMerchantSale.setLorgCode(lOrgCode);
		reportMerchantSale.setLorgName(lOrgName);
		reportMerchantSale.setMerchantId(merchantId);
		reportMerchantSale.setMerchantName(merchant.getMerchantName());
		reportMerchantSale.setMerchantTypes(merchantTypes.toString());
		reportMerchantSale.setOrgCode(merchant.getOrgCode());
		reportMerchantSale.setRefundAmount(refundAmount);
		reportMerchantSale.setSaleProductAmount(saleProductAmount);
		reportMerchantSale.setSaleProductNumber(saleProductNumber);
		reportMerchantSale.setSorgCode(sOrgCode);
		reportMerchantSale.setSorgName(sOrgName);
		reportMerchantSale.setTorgCode(tOrgCode);
		reportMerchantSale.setTorgName(tOrgName);
		reportMerchantSale.setTotalAmount(totalAmount);
		reportMerchantSale.setTotalOrder(totalOrder);
		reportMerchantSale.setOrderType(orderType);
		saleList.add(reportMerchantSale);
		
		if (cashPayList.size() > 0) {
			cashPaymethod = new ReportSalePaymethod();
			cashPaymethod.setClientId(reportMerchantSale.getClientId());
			cashPaymethod.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
			cashPaymethod.setForgCode(reportMerchantSale.getForgCode());
			cashPaymethod.setForgName(reportMerchantSale.getForgName());
			cashPaymethod.setLorgCode(reportMerchantSale.getLorgCode());
			cashPaymethod.setLorgName(reportMerchantSale.getLorgName());
			cashPaymethod.setMerchantId(reportMerchantSale.getMerchantId());
			cashPaymethod.setMerchantName(reportMerchantSale.getMerchantName());
			cashPaymethod.setOrgCode(reportMerchantSale.getOrgCode());
			cashPaymethod.setPayMethod(PaymentMethod.cash.getCode());
			cashPaymethod.setSorgCode(reportMerchantSale.getSorgCode());
			cashPaymethod.setSorgName(reportMerchantSale.getSorgName());
			cashPaymethod.setTorgCode(reportMerchantSale.getTorgCode());
			cashPaymethod.setTorgName(reportMerchantSale.getTorgName());
			cashPaymethod.setOrderType(orderType);
			tempAmount = 0;
			for (int i = 0; i < cashPayList.size(); i++){
				tempAmount += cashPayList.get(i);
			}
			cashPaymethod.setTotalAmount(tempAmount);
			cashPaymethod.setTotalOrder((long)cashPayList.size());
			salePayMethodList.add(cashPaymethod);
		}
		
		if (froadPayList.size() > 0){
			froadPaymethod = new ReportSalePaymethod();
			froadPaymethod.setClientId(reportMerchantSale.getClientId());
			froadPaymethod.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
			froadPaymethod.setForgCode(reportMerchantSale.getForgCode());
			froadPaymethod.setForgName(reportMerchantSale.getForgName());
			froadPaymethod.setLorgCode(reportMerchantSale.getLorgCode());
			froadPaymethod.setLorgName(reportMerchantSale.getLorgName());
			froadPaymethod.setMerchantId(reportMerchantSale.getMerchantId());
			froadPaymethod.setMerchantName(reportMerchantSale.getMerchantName());
			froadPaymethod.setOrgCode(reportMerchantSale.getOrgCode());
			froadPaymethod.setPayMethod(PaymentMethod.froadPoints.getCode());
			froadPaymethod.setSorgCode(reportMerchantSale.getSorgCode());
			froadPaymethod.setSorgName(reportMerchantSale.getSorgName());
			froadPaymethod.setTorgCode(reportMerchantSale.getTorgCode());
			froadPaymethod.setTorgName(reportMerchantSale.getTorgName());
			froadPaymethod.setOrderType(orderType);
			tempAmount = 0;
			for (int i = 0; i < froadPayList.size(); i++){
				tempAmount += froadPayList.get(i);
			}
			froadPaymethod.setTotalAmount(tempAmount);
			froadPaymethod.setTotalOrder((long)froadPayList.size());
			salePayMethodList.add(froadPaymethod);
		}
		
		if (bankPayList.size() > 0){
			bankPaymethod = new ReportSalePaymethod();
			bankPaymethod.setClientId(reportMerchantSale.getClientId());
			bankPaymethod.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
			bankPaymethod.setForgCode(reportMerchantSale.getForgCode());
			bankPaymethod.setForgName(reportMerchantSale.getForgName());
			bankPaymethod.setLorgCode(reportMerchantSale.getLorgCode());
			bankPaymethod.setLorgName(reportMerchantSale.getLorgName());
			bankPaymethod.setMerchantId(reportMerchantSale.getMerchantId());
			bankPaymethod.setMerchantName(reportMerchantSale.getMerchantName());
			bankPaymethod.setOrgCode(reportMerchantSale.getOrgCode());
			bankPaymethod.setPayMethod(PaymentMethod.bankPoints.getCode());
			bankPaymethod.setSorgCode(reportMerchantSale.getSorgCode());
			bankPaymethod.setSorgName(reportMerchantSale.getSorgName());
			bankPaymethod.setTorgCode(reportMerchantSale.getTorgCode());
			bankPaymethod.setTorgName(reportMerchantSale.getTorgName());
			bankPaymethod.setOrderType(orderType);
			tempAmount = 0;
			for (int i = 0; i < bankPayList.size(); i++){
				tempAmount += bankPayList.get(i);
			}
			bankPaymethod.setTotalAmount(tempAmount);
			bankPaymethod.setTotalOrder((long)bankPayList.size());
			salePayMethodList.add(bankPaymethod);
		}
		
		if (froadCashPayList.size() > 0){
			froadCashPaymethod = new ReportSalePaymethod();
			froadCashPaymethod = new ReportSalePaymethod();
			froadCashPaymethod.setClientId(reportMerchantSale.getClientId());
			froadCashPaymethod.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
			froadCashPaymethod.setForgCode(reportMerchantSale.getForgCode());
			froadCashPaymethod.setForgName(reportMerchantSale.getForgName());
			froadCashPaymethod.setLorgCode(reportMerchantSale.getLorgCode());
			froadCashPaymethod.setLorgName(reportMerchantSale.getLorgName());
			froadCashPaymethod.setMerchantId(reportMerchantSale.getMerchantId());
			froadCashPaymethod.setMerchantName(reportMerchantSale.getMerchantName());
			froadCashPaymethod.setOrgCode(reportMerchantSale.getOrgCode());
			froadCashPaymethod.setPayMethod(PaymentMethod.froadPointsAndCash.getCode());
			froadCashPaymethod.setSorgCode(reportMerchantSale.getSorgCode());
			froadCashPaymethod.setSorgName(reportMerchantSale.getSorgName());
			froadCashPaymethod.setTorgCode(reportMerchantSale.getTorgCode());
			froadCashPaymethod.setTorgName(reportMerchantSale.getTorgName());
			froadCashPaymethod.setOrderType(orderType);
			tempAmount = 0;
			for (int i = 0; i < froadCashPayList.size(); i++){
				tempAmount += froadCashPayList.get(i);
			}
			froadCashPaymethod.setTotalAmount(tempAmount);
			froadCashPaymethod.setTotalOrder((long)froadCashPayList.size());
			salePayMethodList.add(froadCashPaymethod);
		}
		
		if (bankCashPayList.size() > 0){
			bankCashPaymethod = new ReportSalePaymethod();
			bankCashPaymethod = new ReportSalePaymethod();
			bankCashPaymethod = new ReportSalePaymethod();
			bankCashPaymethod.setClientId(reportMerchantSale.getClientId());
			bankCashPaymethod.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
			bankCashPaymethod.setForgCode(reportMerchantSale.getForgCode());
			bankCashPaymethod.setForgName(reportMerchantSale.getForgName());
			bankCashPaymethod.setLorgCode(reportMerchantSale.getLorgCode());
			bankCashPaymethod.setLorgName(reportMerchantSale.getLorgName());
			bankCashPaymethod.setMerchantId(reportMerchantSale.getMerchantId());
			bankCashPaymethod.setMerchantName(reportMerchantSale.getMerchantName());
			bankCashPaymethod.setOrgCode(reportMerchantSale.getOrgCode());
			bankCashPaymethod.setPayMethod(PaymentMethod.bankPointsAndCash.getCode());
			bankCashPaymethod.setSorgCode(reportMerchantSale.getSorgCode());
			bankCashPaymethod.setSorgName(reportMerchantSale.getSorgName());
			bankCashPaymethod.setTorgCode(reportMerchantSale.getTorgCode());
			bankCashPaymethod.setTorgName(reportMerchantSale.getTorgName());
			bankCashPaymethod.setOrderType(orderType);
			tempAmount = 0;
			for (int i = 0; i < bankCashPayList.size(); i++){
				tempAmount += bankCashPayList.get(i);
			}
			bankCashPaymethod.setTotalAmount(tempAmount);
			bankCashPaymethod.setTotalOrder((long)bankCashPayList.size());
			salePayMethodList.add(bankCashPaymethod);
		}
		
	}
	
	@Override
	public void initialize() {
		reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
		salePaymethodMapper = rpSqlSession.getMapper(ReportSalePaymethodMapper.class);
		
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		
		orgUtil = new OrgUtil(sqlSession);
		
		merchantDetailMongoService = new MerchantDetailMongoService();
	}
	
}
