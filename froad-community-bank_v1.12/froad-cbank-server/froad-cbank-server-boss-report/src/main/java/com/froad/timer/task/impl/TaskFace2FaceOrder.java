/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:TaskFace2FaceOrder.java
 * Package Name:com.froad.timer.task.impl
 * Date:2015年8月14日下午12:27:30
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.rp_mappers.DataSuborderMapper;
import com.froad.enums.DataSuborderStatus;
import com.froad.enums.OrderFlag;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.logback.LogCvt;
import com.froad.logic.OrderLogic;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.po.BatchChunk;
import com.froad.po.DataSuborder;
import com.froad.po.Org;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OrderMongo;
import com.froad.singleton.MerchantSingleton;
import com.froad.singleton.OrgSingleton;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.TaskTimeUtil;

/**
 * ClassName: 把面对面订单放入cb_data_suborder <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月14日 下午12:27:30
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class TaskFace2FaceOrder extends AbstractProTask {
	
	private OrderLogic orderLogic;

	public TaskFace2FaceOrder(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void initialize() {
		orderLogic = new OrderLogicImpl();
		
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
		MongoPage page = orderLogic.queryFace2FaceOrderListByPage(batchChunk.getChunkPage(), batchChunk.getChunkSize());
		
		List<OrderMongo> orderList = new ArrayList<OrderMongo>();
		if (page != null && page.getItems() != null) {
			orderList = (List<OrderMongo>)page.getItems();
		}
		LogCvt.info("TaskFace2Face:" + batchChunk.getChunkPage() + ":" + orderList.size());
		List<DataSuborder> newOrderList = new ArrayList<DataSuborder>();
		for (OrderMongo order : orderList) {
			
			DataSuborder newOrder = convertNewOrder(order, batchChunk.getBatchDate(), begDate, endDate);
			
			newOrderList.add(newOrder);
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
	private DataSuborder convertNewOrder(OrderMongo order, Integer batchDate, Date begDate, Date endDate){
		Date now = new Date();
		DataSuborder newOrder = new DataSuborder();
		newOrder.setReportDate(batchDate);
		newOrder.setReportBegDate(begDate);
		newOrder.setReportEndDate(endDate);
		newOrder.setCreateTime(now);
		newOrder.setOrderFlag(OrderFlag.place.getCode());
		if (OrderStatus.paysuccess.getCode().equals(order.getOrderStatus())) {
			newOrder.setOrderStatus(DataSuborderStatus.paid.getCode());
		} else {
			newOrder.setOrderStatus(DataSuborderStatus.unpaid.getCode());
		}
		newOrder.setOrderType(OrderType.face2face.getCode());
		newOrder.setOrderTime(new Date(order.getCreateTime()));
		newOrder.setClientId(order.getClientId());
		newOrder.setOrgCode1(order.getForgCode());
		Org org1 = OrgSingleton.getOrgByCode(order.getClientId(), order.getForgCode());
		newOrder.setOrgName1(org1 == null ? "" : org1.getOrgName());
		newOrder.setOrgCode2(order.getSorgCode());
		Org org2 = OrgSingleton.getOrgByCode(order.getClientId(), order.getSorgCode());
		newOrder.setOrgName2(org2 == null ? "" : org2.getOrgName());
		newOrder.setOrgCode3(order.getTorgCode());
		Org org3 = OrgSingleton.getOrgByCode(order.getClientId(), order.getTorgCode());
		newOrder.setOrgName3(org3 == null ? "" : org3.getOrgName());
		newOrder.setOrderId(order.getOrderId());
		newOrder.setSubOrderId("");
		newOrder.setMemberCode(order.getMemberCode());
		newOrder.setMemberName(order.getMemberName());
		newOrder.setMerchantId(order.getMerchantId());
		newOrder.setMerchantName(order.getMerchantName());
		newOrder.setMerchantStatus(""); // 默认为空
		// 获取商户信息，然后再取商户分类信息
		String categoryIds = "", categoryNames = "";
		MerchantDetail merchant = MerchantSingleton.getMerchantDetail(order.getMerchantId());
		if (merchant != null) {
			String merchantStatus = MerchantSingleton.getMerchantStatus(merchant.getClientId(), merchant.getId());
			if (merchantStatus == null) {
				LogCvt.warn("merchant status is null. merchantId=" + order.getMerchantId());
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
		
		newOrder.setProductId(order.getProductId());
		
		newOrder.setProductName("");
		newOrder.setProductType("0");
		newOrder.setPrice(order.getTotalPrice());
		newOrder.setQuantity(1);
		newOrder.setVipPrice(0);
		newOrder.setVipQuantity(0);
		newOrder.setDeliveryMoney(0);
		// 暂时不统计运费
		newOrder.setTotalAmount(newOrder.getPrice() * newOrder.getQuantity()  + newOrder.getVipPrice() * newOrder.getVipQuantity());
		return newOrder;
	}
	

}
