package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.enums.FieldMapping;
import com.froad.enums.OrderStatus;
import com.froad.enums.RefundState;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.handler.RefundApprovalHandler;
import com.froad.handler.impl.RefundApprovalHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.RefundLogic;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.support.OrderSupport;
import com.froad.support.RefundSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.util.EmptyChecker;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class RefundLogicImpl implements RefundLogic {
	
	//common项目公共
	private final static CommonLogic commonLogic = new CommonLogicImpl();

	@Override
	public boolean updateApprovalResult(Map<String, Boolean> noticeMap) {
		RefundApprovalHandler handler = null;
		
		handler = new RefundApprovalHandlerImpl();
		
		return handler.updateApprovalResult(noticeMap);
	}

	
	@Override
	public Map<String, Integer> getCanRefundProductList(String clientId, String orderId) {
		OrderSupport orderSupport = null;
		List<SubOrderMongo> subOrderList = null;
		SubOrderMongo subOrder = null;
		OrderMongo order = null;
		List<ProductMongo> productList = null;
		ProductMongo product = null;
		Map<String, Integer> productMap = null;// key = subOrderId + "_" + productId
		Map<String, Integer> invalidProductMap = null;// key = subOrderId + "_" + productId
		StringBuffer key = null;
		StringBuffer logInfo = null;
		
		try {
			//当前子订单购买当前商品的总数量-当前子子订单针对当前产品已经退款的数量
			LogCvt.info("获取大订单下子订单商品可退款数量");
			
			orderSupport = new OrderSupportImpl();
			productMap = new HashMap<String, Integer>();
			invalidProductMap = new HashMap<String, Integer>();
			key = new StringBuffer();
			logInfo = new StringBuffer();
			
			order = orderSupport.getOrderById(clientId, orderId);
			// 若订单支付状态为非完成支付,不允许退款
			if (null == order || !order.getOrderStatus().equals(OrderStatus.paysuccess.getCode())){
				logInfo.delete(0, logInfo.length());
				logInfo.append("订单未支付：").append(orderId);
				LogCvt.info(logInfo.toString());
				return null;
			}
			
			// 面对面支付订单不允许退款
			if (order.getIsQrcode() == 1) {
				logInfo.delete(0, logInfo.length());
				logInfo.append("面对面订单不允许退款：").append(orderId);
				LogCvt.info(logInfo.toString());
				return null;
			}
			
			// 获取已消费、过期商品数量
			invalidProductMap = getInvalidProductMap(orderId);
			
			//获取大订单下子订单集合
			subOrderList = orderSupport.getSubOrderListByOrderId(clientId, orderId);
			
			// 获取退款记录
			getRefundHis(orderId, subOrderList, invalidProductMap);
			
			if(subOrderList != null && subOrderList.size() > 0) {
				for(int i = 0; i < subOrderList.size(); i++) {
					subOrder = subOrderList.get(i);
					
					// 仅团购、预售、名优特惠支持退款
					if (!subOrder.getType().equals(SubOrderType.group_merchant.getCode())
							&& !subOrder.getType().equals(SubOrderType.presell_org.getCode())
							&& !subOrder.getType().equals(SubOrderType.special_merchant.getCode())
							&& !subOrder.getType().equals(SubOrderType.boutique.getCode())) {
						logInfo.delete(0, logInfo.length());
						logInfo.append("仅团购、预售、名优特惠、精品商城商品允许退款：").append(orderId);
						LogCvt.info(logInfo.toString());
						return null;
					}
					
					productList = subOrder.getProducts();
//					if (subOrder.getType().equals(SubOrderType.presell_org.getCode()) && isExceedPresellTime(invalidProductMap,subOrder, productList)) {
//						logInfo.delete(0, logInfo.length());
//						logInfo.append("预售商品仅预售期内允许退款：").append(orderId);
//						LogCvt.info(logInfo.toString());
//						return null;
//					}
					if (subOrder.getType().equals(SubOrderType.presell_org.getCode())) { // 过滤预售已经过期可退款商品数量
						isExceedPresellTime(invalidProductMap, subOrder, productList);
					}
					
					//当前子订单购买商品集合
					for (int j = 0; j < productList.size(); j++){
						product = productList.get(j);
						key.delete(0, key.length());
						key.append(subOrder.getSubOrderId());
						key.append("_");
						key.append(product.getProductId());
						// 可退款商品数量
						if (invalidProductMap.get(key.toString()) == null){
							productMap.put(key.toString(), product.getQuantity() + product.getVipQuantity());
						} else {
							productMap.put(key.toString(),
									product.getQuantity()
									+ product.getVipQuantity()
									- invalidProductMap.get(key.toString()));
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return productMap;
	}
	
	/**
	 * 获取已退款或退款中商品数量
	 * 
	 * @param orderId
	 * @param subOrderList
	 * @param refundProductMap
	 * @return
	 */
	private void getRefundHis(String orderId, List<SubOrderMongo> subOrderList, Map<String, Integer> refundProductMap){
		RefundSupport refundSupport = null;
		List<RefundHistory> refundHisList = null;
		RefundHistory refundHis = null;
		DBObject queryObj = null;
		List<String> stateList = null;
		RefundShoppingInfo shoppingInfo = null;
		List<RefundProduct> productList = null;
		RefundProduct refProduct = null;
		StringBuffer key = null;
		int quantity = 0;
		Map<String, Integer> subOrderProMap = null;
		SubOrderMongo subOrder = null;
		ProductMongo productMongo = null;
		String subOrderId = null;
		boolean isRefunding = false;
		
		refundSupport = new RefundSupportImpl();
		queryObj = new BasicDBObject();
		key = new StringBuffer();
		subOrderProMap = new HashMap<String, Integer>();
		
		for (int i = 0; i < subOrderList.size(); i++){
			subOrder = subOrderList.get(i);
			subOrderId = subOrder.getSubOrderId();
			
			for (int j = 0; j < subOrder.getProducts().size(); j++){
				productMongo = subOrder.getProducts().get(j);
				key.delete(0, key.length());
				key.append(subOrderId);
				key.append("_");
				key.append(productMongo.getProductId());
				quantity = productMongo.getQuantity();
				if (null != productMongo.getVipQuantity()){
					quantity += productMongo.getVipQuantity();
				}
				subOrderProMap.put(key.toString(), quantity);
			}
		}
		
		// 查找已退款或正在退款商品记录
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		stateList = new ArrayList<String>();
		stateList.add(RefundState.REFUND_SUCCESS.getCode());
		stateList.add(RefundState.REFUND_MANUAL_SUCCESS.getCode());
		stateList.add(RefundState.REFUND_PROCESSING.getCode());
		stateList.add(RefundState.REFUND_AUDIT_PASSED.getCode());
		stateList.add(RefundState.REFUND_AUDIT_PROCESSING.getCode());
		stateList.add(RefundState.REFUND_INIT.getCode());
		queryObj.put(FieldMapping.REFUND_STATE.getMongoField(), new BasicDBObject("$in", stateList));
		refundHisList = refundSupport.findListByDBObject(queryObj);
		
		// 按subOrderId + "_" + productId存储商品退款数量
		if (null != refundHisList && refundHisList.size() > 0){
			for (int i = 0; i < refundHisList.size(); i++){
				refundHis = refundHisList.get(i);
				
				if (refundHis.getRefundState().equals(RefundState.REFUND_PROCESSING.getCode())){
					isRefunding = true;
				} else {
					isRefunding = false;
				}
				
				shoppingInfo = refundHis.getShoppingInfo().get(0);
				productList = shoppingInfo.getProducts();
				
				for (int j = 0; j < productList.size(); j++){
					refProduct = productList.get(j);
					key.delete(0, key.length());
					key.append(shoppingInfo.getSubOrderId());
					key.append("_");
					key.append(refProduct.getProductId());
					quantity = refProduct.getQuantity();
					if (!EmptyChecker.isEmpty(refProduct.getVipQuantity())){
						quantity += refProduct.getVipQuantity();
					}
					
					if (isRefunding && null == refundProductMap.get(key.toString())){
						refundProductMap.put(key.toString(), subOrderProMap.get(subOrderId));
					} else if (refundProductMap.get(key.toString()) != null){
						refundProductMap.put(key.toString(), (refundProductMap.get(key.toString()) + quantity));
					} else {
						refundProductMap.put(key.toString(), quantity);
					}
				}
			}
		}
		
	}
	
	/**
	 * 检查预售商品是否超出预售期
	 * 
	 * @param invalidProductMap
	 * @param subOrder
	 * @param productList
	 * @return
	 */
	private boolean isExceedPresellTime(Map<String, Integer> invalidProductMap, SubOrderMongo subOrder, List<ProductMongo> productList){
		boolean isExceeded = false;
		ProductMongo product = null;
		Map<String, String> redisMap = null;
		long curTime = 0l;
		long presellStartTime = 0l;
		long presellEndTime = 0l;
		
		StringBuilder key = new StringBuilder(subOrder.getSubOrderId()).append("_");
//		key.delete(0, key.length());
//		key.append(subOrder.getSubOrderId());
//		key.append("_");
//		key.append(product.getProductId());
		curTime = System.currentTimeMillis();
		for (int i = 0; i < productList.size(); i++){
			product = productList.get(i);
			redisMap = commonLogic.getProductRedis(subOrder.getClientId(), subOrder.getMerchantId(), product.getProductId());
			if (redisMap != null && !redisMap.isEmpty()){
				presellStartTime = Long.valueOf(redisMap.get(FieldMapping.START_TIME.getMongoField()));
				presellEndTime = Long.valueOf(redisMap.get(FieldMapping.END_TIME.getMongoField()));
				
				if ((curTime < presellStartTime) || (curTime > presellEndTime)) {
					key.delete(key.indexOf("_") + 1, key.length());
					key.append(product.getProductId());
					invalidProductMap.put(key.toString(), product.getQuantity() + product.getVipQuantity());
					isExceeded = false;
//					break;
				}
			}
		}
		
		return isExceeded;
	}
	
	/**
	 * 获取已消费、过期商品数量
	 * 
	 * @param orderId
	 * @return
	 */
	private Map<String, Integer> getInvalidProductMap(String orderId){
		Map<String, Integer> proMap = null;
		TicketSupport ticketSupport = null;
		DBObject queryObj = null;
		List<String> statusList = null;
		List<Ticket> ticketList = null;
		Ticket ticket = null;
		StringBuffer key = null;// subOrderId + productId
		
		proMap = new HashMap<String, Integer>();
		ticketSupport = new TicketSupportImpl();
		queryObj = new BasicDBObject();
		statusList = new ArrayList<String>();
		key = new StringBuffer();
		
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		statusList.add(TicketStatus.consumed.getCode());
		statusList.add(TicketStatus.expired.getCode());
		queryObj.put(FieldMapping.STATUS.getMongoField(), new BasicDBObject(QueryOperators.IN, statusList));
		ticketList = ticketSupport.getListByDBObject(queryObj);
		
		if (!EmptyChecker.isEmpty(ticketList)){
			for (int i = 0; i < ticketList.size(); i++){
				ticket = ticketList.get(i);
				key.delete(0, key.length());
				key.append(ticket.getSubOrderId()).append("_").append(ticket.getProductId());
				if (null != proMap.get(key.toString())) {
					proMap.put(key.toString(), proMap.get(key.toString()) + ticket.getQuantity());
				} else {
					proMap.put(key.toString(), ticket.getQuantity());
				}
			}
		}
		
		return proMap;
	}
	
	/**
	 * 获取已退款商品数量
	 * 
	 * @param orderId
	 * @return
	 */
	public Map<String, Integer> getRefundedProduct(String orderId){
		
		
		Map<String, Integer> productMap = null;// key = subOrderId + "_" + productId
		RefundSupport refundSupport = null;
		List<RefundHistory> refundHisList = null;
		RefundHistory refundHis = null;
		DBObject queryObj = null;
		List<String> stateList = null;
		RefundShoppingInfo shoppingInfo = null;
		List<RefundProduct> productList = null;
		RefundProduct product = null;
		StringBuffer key = null;
		int quantity = 0;
		
		productMap = new HashMap<String, Integer>();
		refundSupport = new RefundSupportImpl();
		queryObj = new BasicDBObject();
		key = new StringBuffer();
		
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		stateList = new ArrayList<String>();
		stateList.add(RefundState.REFUND_SUCCESS.getCode());
		stateList.add(RefundState.REFUND_MANUAL_SUCCESS.getCode());
		queryObj.put(FieldMapping.REFUND_STATE.getMongoField(), new BasicDBObject("$in", stateList));
		refundHisList = refundSupport.findListByDBObject(queryObj);
		
		
		// 按subOrderId + "_" + productId存储商品退款数量
		if (null != refundHisList && refundHisList.size() > 0){
			for (int i = 0; i < refundHisList.size(); i++){
				refundHis = refundHisList.get(i);
				
				shoppingInfo = refundHis.getShoppingInfo().get(0);
				productList = shoppingInfo.getProducts();
				
				for (int j = 0; j < productList.size(); j++){
					product = productList.get(j);
					key.delete(0, key.length());
					key.append(shoppingInfo.getSubOrderId());
					key.append("_");
					key.append(product.getProductId());
					quantity = product.getQuantity();
					if (!EmptyChecker.isEmpty(product.getVipQuantity())){
						quantity += product.getVipQuantity();
					}
					
					if (productMap.get(key.toString()) != null){
						productMap.put(key.toString(), (productMap.get(key.toString()) + quantity));
					} else {
						productMap.put(key.toString(), quantity);
					}
				}
			}
		}
		
		return productMap;
	}


	@Override
	public int getRefundDetails(String clientId, String orderId,String productId) {
		DBObject queryObj = new BasicDBObject();
		queryObj.put("shopping_info.products.product_id", productId);
		queryObj.put("order_id", orderId);
		queryObj.put("client_id", clientId);
		
		RefundSupport refundSupport = new RefundSupportImpl();
		List<RefundHistory> refundHistorys = refundSupport.findListByDBObject(queryObj);
		if(refundHistorys == null || refundHistorys.size() == 0){
			return 0;
		}
		
		int count = 0 ;
		for (RefundHistory refundHistory : refundHistorys) {
			if(RefundState.REFUND_AUDIT_PROCESSING.getCode().equals(refundHistory.getRefundState())
			   || RefundState.REFUND_INIT.getCode().equals(refundHistory.getRefundState())
			   || RefundState.REFUND_SUCCESS.getCode().equals(refundHistory.getRefundState())
					){
				count ++ ;
			}
		}
		
		
		return count;
	}
}
