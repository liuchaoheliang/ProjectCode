package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.OrderMapper;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.DeliveryType;
import com.froad.enums.FieldMapping;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderStatus;
import com.froad.enums.ProductType;
import com.froad.enums.RefundResource;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderRefundState;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.handler.RefundApprovalHandler;
import com.froad.log.OrderLogs;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrderLogic;
import com.froad.logic.SettlementLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.LogBeanClone;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Org;
import com.froad.po.Product;
import com.froad.po.ProductMonthCount;
import com.froad.po.Store;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.support.OrderSupport;
import com.froad.support.PaymentSupport;
import com.froad.support.RefundSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thirdparty.request.active.ActiveFunc;
import com.froad.thirdparty.request.active.impl.ActiveFuncImpl;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;
import com.froad.util.active.ActiveConst;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class RefundApprovalHandlerImpl implements RefundApprovalHandler {

	/**
	 * 业务监控
	 */
	private static MonitorService monitorService = new MonitorManager();
	
	private ActiveFunc activeFunc = new ActiveFuncImpl(); 
	
	@Override
	public boolean updateApprovalResult(Map<String, Boolean> noticeMap,String... refundId) {
		RefundHistory refundHis = null;
		RefundSupport refundSupport = null;
		OrderLogic orderLogic = null;
		SettlementLogic settlementLogic = null;
		DBObject queryObj = null;
		DBObject updateObj = null;
		List<Store> storeList = null;
		Store store = null;
		RefundProduct refundProduct = null;
		RefundPaymentInfo refundPayment = null;
		boolean isUpdateState = true;
		boolean isAllSuccess = true;
		String refundState = null;
		StringBuffer logMsg = null;
		ResultBean resultBean = null;
		boolean isAllRefunded = false;
		OrderSupport orderSupport = null;
		String subOrderId = null;
		String subOrderRefundState = null;
		SubOrderMongo subOrder = null;
		String paymentId = null;
		boolean isSuccess = false;
		Iterator<String> it = null;
		String merchantId = null;
		CommonLogic commonLogic = null;
		Org org = null;
		OrderMongo order = null;
		
		try {
			
			boolean isRefundCheckFailed = false;
			
			refundSupport = new RefundSupportImpl();
			orderSupport = new OrderSupportImpl();
			orderLogic = new OrderLogicImpl();
			commonLogic = new CommonLogicImpl();
			logMsg = new StringBuffer();
			
			it = noticeMap.keySet().iterator();
			while (it.hasNext()){
				paymentId = it.next();
				if(paymentId == null){
					//此处是代码第一版设计缺陷导致paymentId本来不一定会生成，但是造成此处取paymentId来进行处理，改值已不一定有值
					
					isRefundCheckFailed = true;
					break;
				}
				isSuccess = noticeMap.get(paymentId);
				LogCvt.info(logMsg.append("更新退款流水：").append(paymentId).append(" 状态：").append(isSuccess).toString());
				refundHis = updateRefundPayInfo(refundSupport, paymentId, isSuccess);
				
				//退款失败：通知营销平台
				if(!isSuccess){
					activeFunc.noticeMarketingPlatformRefundFailure(refundHis);
				}
			}
			
			if(isRefundCheckFailed){ //进入该判断是由于最开始退款模块逻辑设计缺陷，现增加补救措施by Zxy20151121
				//进入该逻辑有几个前提点 1 退款一定是失败的，退款没有通过支付模块实际发起过退款，退款的记录已经修改为失败，但是退款的券状态还没有回滚等，此处主要是
				//为了将应该回滚的数据回滚
				
				LogCvt.info("进入退款逻辑设计缺陷修复方案 refundId: " + refundId[0]);
				refundHis = refundSupport.getByRefundId(refundId[0]);

				//券码回滚
				if (!refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.special_merchant.getCode())) {
					LogCvt.info("修复回滚券码数据");
					rollbackTicket(refundHis);
				}
				
				return true;
			}
			order = orderSupport.getOrderById(refundHis.getClientId(), refundHis.getOrderId());
			
			if(1 == order.getIsVipOrder()){//VIP资格开通类型的订单
				if (isAllSuccess) {
					OrderMongo orderUpdate = new OrderMongo();
					orderUpdate.setOrderId(order.getOrderId());
					orderUpdate.setClientId(order.getClientId());
					orderUpdate.setOrderStatus(OrderStatus.sysclosed.getCode());
					new OrderLogicImpl().updateOrderForPay(orderUpdate);
					RedisCommon.updateUserVIPOrderRedis(order.getClientId(),order.getMemberCode(),false);
					refundState = RefundState.REFUND_SUCCESS.getCode();
				} else {
					refundState = RefundState.REFUND_FAILED.getCode();
				}
				
				queryObj = new BasicDBObject();
				queryObj.put(FieldMapping.ID.getMongoField(), refundHis.get_id());
				
				updateObj = new BasicDBObject();
				updateObj.put(FieldMapping.REFUND_STATE.getMongoField(), refundState);
				updateObj.put(FieldMapping.REFUND_TIME.getMongoField(), System.currentTimeMillis());
				
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append(refundHis.get_id()).append(" 更新退款状态：").append(refundState).toString());
				refundHis = refundSupport.findAndModifyByDBObject(queryObj, new BasicDBObject("$set", updateObj));
				return true;
			}
			
			
			subOrderId = refundHis.getShoppingInfo().get(0).getSubOrderId();
			
			// 更新退款状态
			if (isSuccess){
				for (int i = 0; i < refundHis.getPaymentInfo().size(); i++){
					refundPayment = refundHis.getPaymentInfo().get(i);
					if (null == refundPayment.getResultCode()){
						isUpdateState = false;
						break;
					} else if (refundPayment.getResultCode().equals(ResultCode.failed.getCode())) {
						isAllSuccess = false;
					}
				}
			} else {
				isAllSuccess = false;
				for (int i = 0; i < refundHis.getPaymentInfo().size(); i++){
					refundPayment = refundHis.getPaymentInfo().get(i);
					if (null == refundPayment.getResultCode()){
						isUpdateState = false;
						break;
					}
				}
				
				// 更新子订单退款状态
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append(subOrderId).append(" 更新子订单退款状态：").append(subOrderRefundState).toString());
				subOrderRefundState = SubOrderRefundState.REFUND_INIT.getCode();
				orderLogic.updateOrderAfterRefund(refundHis.getClientId(), refundHis.getOrderId(), subOrderId, subOrderRefundState, false);
			}
			
			if (isUpdateState){
				if (isAllSuccess) {
					refundState = RefundState.REFUND_SUCCESS.getCode();
				} else {
					refundState = RefundState.REFUND_FAILED.getCode();
				}
				
				queryObj = new BasicDBObject();
				queryObj.put(FieldMapping.ID.getMongoField(), refundHis.get_id());
				
				updateObj = new BasicDBObject();
				updateObj.put(FieldMapping.REFUND_STATE.getMongoField(), refundState);
				updateObj.put(FieldMapping.REFUND_TIME.getMongoField(), System.currentTimeMillis());
				
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append(refundHis.get_id()).append(" 更新退款状态：").append(refundState).toString());
				refundHis = refundSupport.findAndModifyByDBObject(queryObj, new BasicDBObject("$set", updateObj));
			}
			
			
			
			// 所有退款流水成功，更新库存
			if (isAllSuccess) {
				
				//保存日志 2015-11-10新增
				RefundHistory refundHistory = refundSupport.getByRefundId(refundHis.get_id());
				OrderLogs.refundSuccess(LogBeanClone.cloneRefundLog(refundHistory));
				
				// 更新券状态
				if (refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.group_merchant.getCode())
						|| refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.presell_org.getCode())) {
					updateTicketStatus(subOrderId, TicketStatus.refunded.getCode(), refundHis.getRefundTime(), refundHis.getRefundResource(), refundHis.get_id());
				}
				storeList = new ArrayList<Store>();
				
				for (int i = 0; i < refundHis.getShoppingInfo().get(0).getProducts().size(); i++){
					store = new Store();
					store.setClientId(refundHis.getClientId());
					merchantId = refundHis.getShoppingInfo().get(0).getMerchantId();
					if(!refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.boutique.getCode())){
						//如果不是这几种类型，那么真正的商户ID要从机构表里面去取
						if(!(refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.special_merchant.getCode()) 
								|| refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.group_merchant.getCode()))){
							org = commonLogic.getOrgByOrgCode(merchantId, refundHis.getClientId());
							merchantId = org.getMerchantId();
						}
					}
					
					store.setMerchantId(merchantId);

					refundProduct = refundHis.getShoppingInfo().get(0).getProducts().get(i);
					store.setProductId(refundProduct.getProductId());
					store.setReduceStore(refundProduct.getQuantity() + refundProduct.getVipQuantity());
					storeList.add(store);
				}
				
				LogCvt.info("需要操作的库存数据: " + JSONObject.toJSONString(storeList));
				
				//减少vip购买优惠金额
				List<RefundShoppingInfo> shoppings = refundHis.getShoppingInfo();
				if(shoppings != null && shoppings.size() > 0){
					Integer discount = 0;
					List<RefundProduct> products = null;
					products = shoppings.get(0).getProducts();
					if(products != null && products.size() > 0){
						for (RefundProduct product : products) {
							if(product.getVipQuantity() != null && product.getVipQuantity() > 0){
								int discountSingle = product.getPrice() - product.getVipPrice();
								discount += discountSingle * product.getVipQuantity();
							}
						}
					}
					
					LogCvt.info("计算出需要减去的VIP优惠额" + discount + "计算前的原始数据为: " + JSONObject.toJSONString(products));
					RedisCommon.updateVipDiscount(order.getClientId(),order.getMemberCode(),discount,false);
				}
				logMsg.delete(0, logMsg.length());
				if (order.getIsSeckill() == 1){
					LogCvt.info(logMsg.append(" 更新秒杀商品库存：").append(refundHis.get_id()).toString());
					resultBean = orderLogic.increaseSeckillStore(storeList);
					if (!resultBean.isSuccess()){
						// 退款审核通过回滚库存失败
						logMsg.delete(0, logMsg.length());
						LogCvt.error(logMsg.append("更新秒杀商品库存缓存失败：").append(refundHis.get_id()).toString());
						monitorService.send(MonitorPointEnum.Order_Refund_Rollback_Store_Failed_Count);
					}
					
					resultBean = orderLogic.updateSeckillProductStore(storeList);//更新mysql
					if (!resultBean.isSuccess()){
						// 退款审核通过回滚库存失败
						logMsg.delete(0, logMsg.length());
						LogCvt.error(logMsg.append("更新秒杀商品库存mysql失败：").append(refundHis.get_id()).toString());
						monitorService.send(MonitorPointEnum.Order_Refund_Rollback_Store_Failed_Count);
					}
				} else {
					LogCvt.info(logMsg.append(" 更新普通商品库存：").append(refundHis.get_id()).toString());
					resultBean = orderLogic.increaseStore(storeList);//更新缓存
					if (!resultBean.isSuccess()){
						// 退款审核通过回滚库存失败
						logMsg.delete(0, logMsg.length());
						LogCvt.error(logMsg.append("更新普通商品库存缓存失败：").append(refundHis.get_id()).toString());
						monitorService.send(MonitorPointEnum.Order_Refund_Rollback_Store_Failed_Count);
					}
					
					resultBean = orderLogic.updateProductStore(storeList);//更新mysql
					if (!resultBean.isSuccess()){
						// 退款审核通过回滚库存失败
						logMsg.delete(0, logMsg.length());
						LogCvt.error(logMsg.append("更新普通商品库存mysql失败：").append(refundHis.get_id()).toString());
						monitorService.send(MonitorPointEnum.Order_Refund_Rollback_Store_Failed_Count);
					}
				}
				
				// 更新自提预售商品提货数量
				if (refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.presell_org.getCode())){
					logMsg.delete(0, logMsg.length());
					LogCvt.info(logMsg.append("更新自提预售商品提货数量：").append(refundHis.get_id()).toString());
					updatePresellTokenCount(refundHis);
				}
				
				// 更新商品销售数量，月销售额
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append("更新商品销售数量：").append(refundHis.get_id()).toString());
				// bug 2209, 产品需求变更，退款不更新已售数量
				updateSellCount(orderSupport, refundHis);
				updateMonthSellCount(refundHis);
				
				// 更新个人购买记录(限购数量等)
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append("更新个人购买记录：").append(refundHis.get_id()).toString());
				updateMemberHistory(orderSupport, refundHis, order.getIsSeckill());
				
				// 修改子订单退款退款状态
				subOrder = orderSupport.getSubOrderByClient(refundHis.getClientId(), refundHis.getOrderId(), subOrderId);
				isAllRefunded = isAllRefunded(refundSupport, refundHis.getOrderId(), subOrder);
				if (isAllRefunded) {
					subOrderRefundState = SubOrderRefundState.REFUND_SUCCESS.getCode();
				} else {
					subOrderRefundState = SubOrderRefundState.REFUND_PART.getCode();
				}
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append(subOrderId).append(" 更新子订单退款状态：").append(subOrderRefundState).toString());
				orderLogic.updateOrderAfterRefund(refundHis.getClientId(), refundHis.getOrderId(), subOrderId, subOrderRefundState, false);
				
				// 如所有子订单已退款，关闭大订单
				if (isAllRefunded && isOrderRefunded(orderSupport, refundHis.getClientId(), refundHis.getOrderId(), subOrderId)){
					logMsg.delete(0, logMsg.length());
					LogCvt.info(logMsg.append("所有子订单已退款，关闭大订单：").append(refundHis.getOrderId()).toString());
					subOrderRefundState = SubOrderRefundState.REFUND_SUCCESS.getCode();
					orderLogic.updateOrderAfterRefund(refundHis.getClientId(), refundHis.getOrderId(), null, null, true);
				}
				
				// 更新商户销售额
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append("更新商户销售额：").append(refundHis.get_id()).toString());
				
				if (isAllRefunded) {
					// 子订单全退，更新金额和笔数
					updateMerchantMoney(subOrder, refundHis, 1);
					
					//2015-11-12新增：子订单全退，判断大订单是否参加满减活动，如果参加活动，通知营销平台
					if(ActiveConst.IS_ACTIVE_TRUE.equals(order.getIsActive())){
						activeFunc.noticeMarketingPlatformRefundFinish(refundHis);
					}
				} else {
					// 部分退款，只更新金额，不更新笔数
					updateMerchantMoney(subOrder, refundHis, 0);
				}
			}
			
			// 名优特惠退款，更新结算记录为无效
			if (isSuccess && refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.special_merchant.getCode())) {
				settlementLogic = new SettlementLogicImpl();
				resultBean = settlementLogic.settlementUninvalid(refundHis.getShoppingInfo().get(0).getSubOrderId(), "退款");
				LogCvt.info(resultBean.toString());
			}
			
			// 回滚券列表
			if (!isSuccess && !refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.special_merchant.getCode())) {
				rollbackTicket(refundHis);
			}
		} catch (Exception e){
			LogCvt.error(new StringBuffer("退款审核结果后续处理失败：").append(noticeMap).toString(), e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 更新自提预售商品提货数量
	 * 
	 * @param refundHis
	 * @return
	 */
	private boolean updatePresellTokenCount(RefundHistory refundHis) {
		OrderSupport orderSupport = null;
		SubOrderMongo subOrder = null;
		String subOrderId = null;
		ProductMongo product = null;
		RefundProduct refundProduct = null;
		Map<String, ProductMongo> productMap = null;
		
		try {
			subOrderId = refundHis.getShoppingInfo().get(0).getSubOrderId();
			
			orderSupport = new OrderSupportImpl();
			subOrder = orderSupport.getSubOrderByClient(refundHis.getClientId(), refundHis.getOrderId(), subOrderId);
			
			if (null == subOrder || null == subOrder.getProducts()){
				return false;
			}
			
			productMap = new HashMap<String, ProductMongo>();
			for (int i = 0; i < subOrder.getProducts().size(); i++) {
				product = subOrder.getProducts().get(0);
				// 仅自提商品预售商品需更新自提数量
				if (product.getDeliveryOption().equals(DeliveryType.take.getCode())) {
					productMap.put(product.getProductId(), product);
				}
			} 
			
			for (int j = 0; j < refundHis.getShoppingInfo().get(0).getProducts().size(); j++){
				refundProduct = refundHis.getShoppingInfo().get(0).getProducts().get(j);
				if (productMap.get(refundProduct.getProductId()) != null){
					orderSupport.processPresellOutletTokenCountRedis(
							refundHis.getClientId(), subOrder.getMerchantId(),
							refundProduct.getProductId(),
							refundProduct.getQuantity());
				}
			}
		} catch (Exception e){
			LogCvt.error(new StringBuffer("自提预售商品提货数量更新失败：").append(refundHis.get_id()).toString(), e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 退款更新购买历史
	 * 
	 * @param orderSupport
	 * @param refHis
	 * @param isSecondKill
	 */
	private void updateMemberHistory(OrderSupport orderSupport, RefundHistory refHis, int isSecondKill){
		String clientId = null;
		Long memberCode = null;
		String productId = null;
		Long count = 0L;
		Long vipCount = 0L;
		List<RefundProduct> refProList = null;
		RefundProduct refPro = null;
		
		try {
			clientId = refHis.getClientId();
			memberCode = Long.valueOf(refHis.getMemberCode());
			
			refProList = refHis.getShoppingInfo().get(0).getProducts();
			for (int i = 0; i < refProList.size(); i++){
				refPro = refProList.get(i);
				productId = refPro.getProductId();
				if (null != refPro.getQuantity()){
					count = Long.valueOf(refPro.getQuantity());
				} else {
					count = 0L;
				}
				if (null != refPro.getVipQuantity()){
					vipCount = Long.valueOf(refPro.getVipQuantity());
				} else {
					vipCount = 0L;
				}
				
				if (isSecondKill == 1){
					LogCvt.info(new StringBuffer().append(memberCode).append(" 更新会员秒杀购买记录：").append(productId).toString());
					LogCvt.info(new StringBuffer().append("vipCount:" + vipCount + " count:"+count).toString());
					orderSupport.subtractMemberBuyHistoryForSeckill(clientId, memberCode, productId, count, vipCount);
				} else {
					LogCvt.info(new StringBuffer().append(memberCode).append(" 更新会员普通购买记录：").append(productId).toString());
					LogCvt.info(new StringBuffer().append("vipCount:" + vipCount + " count:"+count).toString());
					
					orderSupport.subtractMemberBuyHistory(clientId, memberCode, productId, count, vipCount);
				}
			}
		} catch (Exception e){
			LogCvt.error(new StringBuffer("退款更新会员购买历史失败：").append(refHis.get_id()).toString(), e);
		}
		
	}

//	bug 2209, 产品需求变更，退款不更新已售数量
	private void updateSellCount(OrderSupport orderSupport, RefundHistory refHis){
		Product product = null;
		ProductMonthCount productMonthCount = null;
		List<RefundProduct> refProList = null;
		RefundProduct refProduct = null;
		String clientId = null;
		String productId = null;
		int quantity = 0;
		int sellMoney = 0;
		String merchantId = null;
		String productType = null;
		Calendar ca = Calendar.getInstance();
		
		clientId = refHis.getClientId();
		merchantId = refHis.getShoppingInfo().get(0).getMerchantId();
		productType = refHis.getShoppingInfo().get(0).getType();
		refProList = refHis.getShoppingInfo().get(0).getProducts();
		
		product = new Product();
        product.setClientId(clientId);
        product.setMerchantId(merchantId);
        product.setType(productType);
        
        productMonthCount = new ProductMonthCount();
        OrderMongo order = orderSupport.getOrderById(refHis.getClientId(),refHis.getOrderId());
        ca.setTime(new Date(order.getPaymentTime()));
        productMonthCount.setYear(Integer.toString(ca.get(Calendar.YEAR)));
        productMonthCount.setMonth(String.format("%02d",ca.get(Calendar.MONTH)+1));
    	productMonthCount.setMerchantId(merchantId);
    	productMonthCount.setClientId(clientId);
        
        for (int i = 0; i < refProList.size(); i++){
        	refProduct = refProList.get(i);
        	productId = refProduct.getProductId();
        	quantity = 0;
        	sellMoney = 0;
        	if (null != refProduct.getQuantity()){
        		quantity = refProduct.getQuantity();
        		sellMoney = Arith.mul(refProduct.getPrice(), refProduct.getQuantity());
        	}
        	if (null != refProduct.getVipQuantity()){
        		quantity += refProduct.getVipQuantity();
        		sellMoney += Arith.mul(refProduct.getVipPrice(), refProduct.getVipQuantity());
        	}
        	product.setProductId(productId);
        	product.setSellCount(quantity*-1);
        	
        	// 团购商品，更新月销售额；其他商品，仅更新商品销售额
        	if (productType.equals(ProductType.group.getCode())){
            	productMonthCount.setProductId(productId);
            	productMonthCount.setSellCount(quantity*-1);
            	productMonthCount.setSellMoney(sellMoney*-1);
            	orderSupport.updateProductSellCount(product, productMonthCount);
        	} else {
        		orderSupport.updateProductSellCount(product, null);
        	}
        }
	}
	
	/**
	 * 更新商品月销售数量、销售金额
	 * 
	 * @param orderSupport
	 * @param refHis
	 */
	private void updateMonthSellCount(RefundHistory refHis){
		ProductMonthCount productMonthCount = null;
		List<RefundProduct> refProList = null;
		RefundProduct refProduct = null;
		String clientId = null;
		String productId = null;
		int quantity = 0;
		int sellMoney = 0;
		String merchantId = null;
		String productType = null;
		Calendar ca = null;
        SqlSession sqlSession = null;
        OrderMapper orderMapper = null;
		
		clientId = refHis.getClientId();
		merchantId = refHis.getShoppingInfo().get(0).getMerchantId();
		productType = refHis.getShoppingInfo().get(0).getType();
		refProList = refHis.getShoppingInfo().get(0).getProducts();
        
        productMonthCount = new ProductMonthCount();
        ca =  Calendar.getInstance();
        productMonthCount.setYear(Integer.toString(ca.get(Calendar.YEAR)));
        productMonthCount.setMonth(String.format("%02d",ca.get(Calendar.MONTH)+1));
    	productMonthCount.setMerchantId(merchantId);
    	productMonthCount.setClientId(clientId);

        try {    
        	sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
        	orderMapper = sqlSession.getMapper(OrderMapper.class);
            
            for (int i = 0; i < refProList.size(); i++){
            	refProduct = refProList.get(i);
            	productId = refProduct.getProductId();
            	quantity = 0;
            	sellMoney = 0;
            	if (null != refProduct.getQuantity()){
            		quantity = refProduct.getQuantity();
            		sellMoney = Arith.mul(refProduct.getPrice(), refProduct.getQuantity());
            	}
            	if (null != refProduct.getVipQuantity()){
            		quantity += refProduct.getVipQuantity();
            		sellMoney += Arith.mul(refProduct.getVipPrice(), refProduct.getVipQuantity());
            	}
            	
            	productMonthCount.setProductId(productId);
            	productMonthCount.setSellCount(quantity*-1);
            	productMonthCount.setSellMoney(sellMoney*-1);
            	
                //仅限团购
                if(ProductType.group.getCode().equals(productType)){
                	orderMapper.updateProductMonthCount(productMonthCount);
                }
            }
            
            sqlSession.commit(true);
        } catch (Exception e) {
            sqlSession.rollback(true);
            LogCvt.error("[严重错误]-mysql-更新商品月度销售统计失败，原因:" + e.getMessage()); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
	}
	
	/**
	 * 回滚券列表
	 * 
	 * @param refundHis
	 */
	private void rollbackTicket(RefundHistory refundHis) {
		TicketSupport ticketSupport = null;
		Ticket ticket = null;
		DBObject queryObj = null;
		DBObject updateObj = null;
		List<Ticket> ticketList = null;
		Ticket updatedTicket = null;
		DBObject rollbackObj = null;

		try {
			ticketSupport = new TicketSupportImpl();

			// 更新原退款券为退款失败
			queryObj = new BasicDBObject();
			updateObj = new BasicDBObject();

			if (refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.group_merchant.getCode())) {
				// 回滚团购提货码
				LogCvt.info(new StringBuffer("退款失败回滚团购码：").append(refundHis.get_id()).toString());
				queryObj.put(FieldMapping.REFUND_ID.getMongoField(), refundHis.get_id());
				String rollbackTicketstatus = null;
				if (StringUtils.equals(refundHis.getRefundResource(), RefundResource.SYSTEM_REFUND.getCode())) { // 系统退款// author vania by 20150902 应架构师 刘学彬 要求团购退款失败 回滚成3-已过期
					rollbackTicketstatus = TicketStatus.expired.getCode(); // 3-已过期
					LogCvt.info("[系统退款]失败, 券状态回滚成: " + rollbackTicketstatus + "-" + TicketStatus.getDescription(rollbackTicketstatus));
				} else { // 用户退款
					rollbackTicketstatus = TicketStatus.sent.getCode(); // 1-已发送
					LogCvt.info("[用户退款]失败, 券状态回滚成: " + rollbackTicketstatus + "-" + TicketStatus.getDescription(rollbackTicketstatus));
				}
//				updateObj = new BasicDBObject(FieldMapping.STATUS.getMongoField(), TicketStatus.sent.getCode()); // 回滚成1-已发送
				updateObj = new BasicDBObject(FieldMapping.STATUS.getMongoField(), rollbackTicketstatus);
				ticketSupport.updateMultiByDBObject(queryObj, updateObj);
			} else if (refundHis.getShoppingInfo().get(0).getType().equals(SubOrderType.presell_org.getCode())) {
				// 预售提货码回滚
				LogCvt.info(new StringBuffer("退款失败回滚预售码：").append(refundHis.get_id()).toString());
				queryObj.put(FieldMapping.REFUND_ID.getMongoField(), refundHis.get_id());
				ticketList = ticketSupport.getListByDBObject(queryObj);
				
				if (null != ticketList && ticketList.size() > 0){
					queryObj = new BasicDBObject();
					updateObj = new BasicDBObject();
					rollbackObj = new BasicDBObject();
					for (int i = 0; i < ticketList.size(); i++){
						ticket = ticketList.get(i);
						queryObj.put(FieldMapping.TICKET_ID.getMongoField(), ticket.getTicketId());
						queryObj.put(FieldMapping.PRODUCT_ID.getMongoField(), ticket.getProductId());
						queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.init.getCode());
						updateObj.put("$inc", new BasicDBObject(FieldMapping.QUANTITY.getMongoField(), ticket.getQuantity()));
						
						updatedTicket = ticketSupport.findAndModifyByDBObject(queryObj, updateObj);
						
						rollbackObj.put(FieldMapping.REFUND_ID.getMongoField(), refundHis.get_id());
						rollbackObj.put(FieldMapping.TICKET_ID.getMongoField(), ticket.getTicketId());
						rollbackObj.put(FieldMapping.PRODUCT_ID.getMongoField(), ticket.getProductId());
						if (null != updatedTicket){
							// 删除退款产生记录
							LogCvt.info(new StringBuffer("退款失败删除退款产生记录：").append(rollbackObj.toString()).toString());
							ticketSupport.removeByDBObject(rollbackObj);
						} else {
							// 全退或剩余部分已消费，直接更新退款生成记录
							LogCvt.info(new StringBuffer("退款失败回滚退款产生记录：").append(rollbackObj.toString()).toString());
							updateObj = new BasicDBObject();
							updateObj.put("$set", new BasicDBObject(FieldMapping.STATUS.getMongoField(), TicketStatus.init.getCode()));
							updateObj.put("$unset", new BasicDBObject(FieldMapping.REFUND_ID.getMongoField(), 1));
							ticketSupport.findAndModifyByDBObject(rollbackObj, updateObj);
						}
					}
				}
			}
		} catch (Exception e){
			// 提货码回滚到有效状态失败
			monitorService.send(MonitorPointEnum.Order_Refund_Rollback_Ticket_Failed_Count);
			LogCvt.error(new StringBuffer("退款回滚提货码状态失败：").append(refundHis.get_id()).toString(), e);
		}
		
	}
	
	/**
	 * 检查是否所有商品已退款
	 * 
	 * @param refundSupport
	 * @param orderId
	 * @param subOrder
	 * @return
	 */
	private boolean isAllRefunded(RefundSupport refundSupport, String orderId, SubOrderMongo subOrder){
		boolean isAllRefunded = true;
		Map<String, Integer> originalMap = null;
		Map<String, Integer> refundedMap = null;
		Iterator<String> iterator = null;
		String productId = null;
		
		originalMap = getOriginalMap(subOrder);
		refundedMap = getRefundedMap(refundSupport, orderId, subOrder.getSubOrderId());
		iterator = originalMap.keySet().iterator();
		while (iterator.hasNext()){
			productId = iterator.next();
			if (!originalMap.get(productId).equals(refundedMap.get(productId))){
				isAllRefunded = false;
				break;
			}
		}
		
		return isAllRefunded;
	}
	
	/**
	 * 获取退款商品数量
	 * 
	 * @param refundSupport
	 * @param OrderId
	 * @param subOrderId
	 * @return
	 */
	private Map<String, Integer> getRefundedMap(RefundSupport refundSupport, String orderId, String subOrderId) {
		Map<String, Integer> prouctMap = null;
		DBObject queryObj = null;
		List<RefundHistory> refundList = null;
		RefundHistory refundHis = null;
		List<String> stateList = null;
		List<RefundProduct> productList = null;
		RefundProduct refundProduct = null;
		String productId = null;
		int quantity = 0;
		
		prouctMap = new HashMap<String, Integer>();
		stateList = new ArrayList<String>();
		queryObj = new BasicDBObject();
		
		queryObj.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		queryObj.put(new StringBuffer(FieldMapping.SHOPPING_INFO.getMongoField()).append(".")
						.append(FieldMapping.SUB_ORDER_ID.getMongoField()).toString(), subOrderId);
		stateList.add(RefundState.REFUND_SUCCESS.getCode());
		stateList.add(RefundState.REFUND_MANUAL_SUCCESS.getCode());
		queryObj.put(FieldMapping.REFUND_STATE.getMongoField(), new BasicDBObject(QueryOperators.IN, stateList));
		refundList = refundSupport.findListByDBObject(queryObj);
		
		prouctMap = new HashMap<String, Integer>();
		if (!EmptyChecker.isEmpty(refundList)) {
			productList = new ArrayList<RefundProduct>();
			for (int i = 0; i < refundList.size(); i++) {
				refundHis = refundList.get(i);
				
				productList = refundHis.getShoppingInfo().get(0).getProducts();
				for (int j = 0; j < productList.size(); j++){
					refundProduct = productList.get(j);
					productId = refundProduct.getProductId();
					quantity = refundProduct.getQuantity();
					if (!EmptyChecker.isEmpty(refundProduct.getVipQuantity())) {
						quantity += refundProduct.getVipQuantity();
					}
					if (null == prouctMap.get(productId)) {
						prouctMap.put(productId, quantity);
					} else {
						prouctMap.put(productId, prouctMap.get(productId) + quantity);
					}
				}
			}
		}
		
		return prouctMap;
	}
	
	/**
	 * 获取原子订单商品数量
	 * 
	 * @param subOrder
	 * @return
	 */
	private Map<String, Integer> getOriginalMap(SubOrderMongo subOrder) {
		Map<String, Integer> productMap = null;
		List<ProductMongo> productList = null;
		ProductMongo product = null;
		String productId = null;
		int quantity = 0;
		
		productMap = new HashMap<String, Integer>();
		
		productList = subOrder.getProducts();
		for (int i = 0; i < productList.size(); i++){
			product = productList.get(i);
			productId = product.getProductId();
			quantity = product.getQuantity();
			if (!EmptyChecker.isEmpty(product.getVipQuantity())){
				quantity += product.getVipQuantity();
			}
			productMap.put(productId, quantity);
		}
		
		return productMap;
	}
	
	/**
	 * 检查大订单下子订单是否全退款
	 * 
	 * @param orderSupport
	 * @param clientId
	 * @param orderId
	 * @param subOrderId
	 * @return
	 */
	private boolean isOrderRefunded(OrderSupport orderSupport, String clientId, String orderId, String subOrderId){
		boolean isOrderRefunded = true;
		List<SubOrderMongo> subOrderList = null;
		SubOrderMongo subOrder = null;
		
		subOrderList = orderSupport.getSubOrderListByOrderId(clientId, orderId);
		if (!EmptyChecker.isEmpty(subOrderList)) {
			for (int i = 0; i < subOrderList.size(); i++){
				subOrder = subOrderList.get(i);
				if (!subOrderId.equals(subOrder.getSubOrderId()) && !subOrder.getRefundState().equals(SubOrderRefundState.REFUND_SUCCESS.getCode())){
					isOrderRefunded = false;
					break;
				}
			}
		}
		
		return isOrderRefunded;
	}
	
	/**
	 * 更新商户销售额
	 * 
	 * @param subOrder
	 * @param refundHis
	 * @param orderCount
	 */
	private void updateMerchantMoney(SubOrderMongo subOrder, RefundHistory refundHis, int orderCount){
		PaymentSupport paySupport = null;
		int money = 0;
		List<RefundProduct> productList = null;
		RefundProduct product = null;
		
		productList = refundHis.getShoppingInfo().get(0).getProducts();
		for (int i = 0; i < productList.size(); i++){
			product = productList.get(i);
			if (!EmptyChecker.isEmpty(product.getVipQuantity()) && EmptyChecker.isEmpty(product.getVipPrice())){
				money += Arith.mul(product.getVipPrice(), product.getVipQuantity());
			}
			money += Arith.mul(product.getPrice(), product.getQuantity());
		}
		
		paySupport = new PaymentSupportImpl();
		paySupport.updateMerchantMonthCountForRefund(subOrder.getClientId(),
				subOrder.getMerchantId(), subOrder.getType(),
				subOrder.getCreateTime(), money, orderCount);
	}
	
	/**
	 * 修改券状态
	 * 
	 * @param subOrderId
	 * @param ticketStatus
	 * @param refundTime
	 * @param refundResource
	 * @param refundId
	 */
	private void updateTicketStatus(String subOrderId, String ticketStatus, Long refundTime, String refundResource, String refundId){
		TicketSupport ticketSupport = null;
		DBObject queryObj = null;
		DBObject updateObj = null;
		
		try {
			ticketSupport = new TicketSupportImpl();
			queryObj = new BasicDBObject();
			updateObj = new BasicDBObject();
			
			queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId);
			if (refundResource != null && refundResource.equals(RefundResource.SYSTEM_REFUND.getCode())){
//				queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.expired.getCode());
				queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.expired_refunding.getCode());
				updateObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.expired_refunded.getCode());
				updateObj.put(FieldMapping.REFUND_ID.getMongoField(), refundId);
			} else {
				queryObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.refunding.getCode());
				updateObj.put(FieldMapping.STATUS.getMongoField(), ticketStatus);
			}
			updateObj.put(FieldMapping.REFUND_TIME.getMongoField(), refundTime);
			ticketSupport.updateMultiByDBObject(queryObj, updateObj);
		} catch (Exception e){
			LogCvt.error(new StringBuffer("退款更新提货码退款状态失败，子订单号：").append(subOrderId).toString(), e);
		}
	}
	
	/**
	 * 更新退款流水
	 * 
	 * @param refundSupport
	 * @param paymentId
	 * @param isSuccess
	 * @return
	 */
	private RefundHistory updateRefundPayInfo(RefundSupport refundSupport, String paymentId, boolean isSuccess){
		RefundHistory refundHis = null;
		DBObject queryObj = null;
		DBObject updateObj = null;
		String paymentResult = null;
		
		// 更新支付流水状态
		queryObj = new BasicDBObject(FieldMapping.PAYMENT_INFO.getMongoField(),
				new BasicDBObject(QueryOperators.ELEM_MATCH,
						new BasicDBObject(FieldMapping.PAYMENT_ID.getMongoField(), paymentId)));
		
		updateObj = new BasicDBObject();
		if (isSuccess) {
			paymentResult = ResultCode.success.getCode();
		} else {
			paymentResult = ResultCode.failed.getCode();
			updateObj.put(
					"$set",
					new BasicDBObject(new StringBuffer(
							FieldMapping.PAYMENT_INFO.getMongoField())
							.append(".$.")
							.append(FieldMapping.RESULT_DESC.getMongoField()).toString(), "退款失败"));
		}
		updateObj.put(
				"$set",
				new BasicDBObject(new StringBuffer(
						FieldMapping.PAYMENT_INFO.getMongoField())
						.append(".$.")
						.append(FieldMapping.RESULT_CODE.getMongoField()).toString(), paymentResult));
		
		LogCvt.info(new StringBuffer("").append(paymentId).append(" 更新退款流水状态：").append(paymentResult).toString());
		refundHis = refundSupport.findAndModifyByDBObject(queryObj, updateObj);
		
		return refundHis;
	}
}
