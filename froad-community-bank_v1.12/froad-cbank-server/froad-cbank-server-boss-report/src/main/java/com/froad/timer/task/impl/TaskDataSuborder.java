/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:TaskDataSuborder.java
 * Package Name:com.froad.timer.task.impl
 * Date:2015年8月11日下午8:38:30
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.singleton.MerchantSingleton;
import com.froad.singleton.OrgSingleton;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.TaskTimeUtil;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.rp_mappers.DataSuborderMapper;
import com.froad.enums.OrderFlag;
import com.froad.enums.DataSuborderStatus;
import com.froad.enums.OrderStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.OrderLogic;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.po.BatchChunk;
import com.froad.po.DataSuborder;
import com.froad.po.Org;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;

/**
 * ClassName: 把子订单数据按商品来拆分 <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月11日 下午8:38:30
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class TaskDataSuborder extends AbstractProTask {
	
	private OrderLogic orderLogic;
	
	MongoManager mongo = new MongoManager();
	
	/** 商户信息缓存 */
	static Map<String, MerchantDetail> merchantMap;
	/** 虚拟商户标识缓存 */
	static Map<String, String> merchantStatusMap;
	
	public TaskDataSuborder(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void initialize() {
		orderLogic = new OrderLogicImpl();
		merchantMap = new HashMap<String, MerchantDetail>();
		merchantStatusMap = new HashMap<String, String>();
	}

	@SuppressWarnings("unchecked")
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
		MongoPage page = orderLogic.querySubOrderListByPage(batchChunk.getChunkPage(), batchChunk.getChunkSize());
		
		List<SubOrderMongo> orderList = new ArrayList<SubOrderMongo>();
		if (page != null && page.getItems() != null) {
			orderList = (List<SubOrderMongo>)page.getItems();
		}
		
		List<DataSuborder> newOrderList = new ArrayList<DataSuborder>();
		List<ProductMongo> products = null;
		for (SubOrderMongo suborder : orderList) {
			products = suborder.getProducts();
			
			for (ProductMongo product : products) {
				//LogCvt.debug("suborderId=" + suborder.getSubOrderId());
				DataSuborder newOrder = convertNewOrder(suborder, product, batchChunk.getBatchDate(), begDate, endDate);
				
				newOrderList.add(newOrder);
			}
		}
		
		if(Checker.isNotEmpty(newOrderList)){
			DataSuborderMapper dataSuborderMapper = rpSqlSession.getMapper(DataSuborderMapper.class);
			int batchSize = dataSuborderMapper.addByBatch(newOrderList);
			LogCvt.info("子订单数据表cb_data_suborder批量添加" + (batchSize > 0 ? "成功" : "失败"));
		}
		
	}
	
	/**
	 * 转换到新的订单信息
	 */
	private DataSuborder convertNewOrder(SubOrderMongo suborder, ProductMongo product, int reportDate, Date begDate, Date endDate){
		Date now = new Date();
		DataSuborder newOrder = new DataSuborder();
		newOrder.setReportDate(reportDate);
		newOrder.setReportBegDate(begDate);
		newOrder.setReportEndDate(endDate);
		newOrder.setCreateTime(now);
		newOrder.setOrderFlag(OrderFlag.place.getCode());
		if (OrderStatus.paysuccess.getCode().equals(suborder.getOrderStatus())) {
			newOrder.setOrderStatus(DataSuborderStatus.paid.getCode());
		} else {
			newOrder.setOrderStatus(DataSuborderStatus.unpaid.getCode());
		}
		
		newOrder.setOrderType(suborder.getType());
		newOrder.setOrderTime(new Date(suborder.getCreateTime()));
		newOrder.setClientId(suborder.getClientId());
		
		newOrder.setOrgCode1(suborder.getForgCode());
		Org org1 = OrgSingleton.getOrgByCode(suborder.getClientId(), suborder.getForgCode());
		newOrder.setOrgName1(org1 == null ? "" : org1.getOrgName());
		newOrder.setOrgCode2(suborder.getSorgCode());
		Org org2 = OrgSingleton.getOrgByCode(suborder.getClientId(), suborder.getSorgCode());
		newOrder.setOrgName2(org2 == null ? "" : org2.getOrgName());
		newOrder.setOrgCode3(suborder.getTorgCode());
		Org org3 = OrgSingleton.getOrgByCode(suborder.getClientId(), suborder.getTorgCode());
		newOrder.setOrgName3(org3 == null ? "" : org3.getOrgName());
		newOrder.setOrderId(suborder.getOrderId());
		newOrder.setSubOrderId(suborder.getSubOrderId());
		newOrder.setMemberCode(suborder.getMemberCode());
		newOrder.setMemberName(suborder.getMemberName());
		newOrder.setMerchantId(suborder.getMerchantId());
		newOrder.setMerchantName(suborder.getMerchantName());
		newOrder.setMerchantStatus(""); // 默认为空
		// 获取商户信息，然后再取商户分类信息
		String categoryIds = "", categoryNames = "";
		MerchantDetail merchant = MerchantSingleton.getMerchantDetail(suborder.getMerchantId());
		if (merchant != null) {
			String merchantStatus = MerchantSingleton.getMerchantStatus(merchant.getClientId(), merchant.getId());
			if (merchantStatus == null) {
				LogCvt.warn("merchant status is null. merchantId=" + suborder.getMerchantId());
			}
			newOrder.setMerchantStatus(merchantStatus);
			List<CategoryInfo> merchantCategoryList = merchant.getCategoryInfo();
			
			for (int i = 0; merchantCategoryList != null && i < merchantCategoryList.size(); i++) {
				if (i != 0) {
					categoryIds += ",";
					categoryNames += ",";
				} 
				categoryIds += merchantCategoryList.get(i).getCategoryId();
				categoryNames += merchantCategoryList.get(i).getName();
			}
			
		}
		
		newOrder.setMerchantCategoryIds(categoryIds);
		newOrder.setMerchantCategoryNames(categoryNames);
		
		newOrder.setProductId(product.getProductId());
		newOrder.setProductName(product.getProductName());
		newOrder.setProductType(product.getType());
		newOrder.setPrice(product.getMoney() == null ? 0 : product.getMoney());
		newOrder.setQuantity(product.getQuantity() == null ? 0 : product.getQuantity());
		newOrder.setVipPrice(product.getVipMoney() == null ? 0 : product.getVipMoney());
		newOrder.setVipQuantity(product.getVipQuantity() == null ? 0 : product.getVipQuantity());
		newOrder.setDeliveryMoney(product.getDeliveryMoney() == null ? 0 : product.getDeliveryMoney());
		// 暂时不统计运费
		newOrder.setTotalAmount(newOrder.getPrice() * newOrder.getQuantity()  + newOrder.getVipPrice() * newOrder.getVipQuantity());
		return newOrder;
	}
	
}
