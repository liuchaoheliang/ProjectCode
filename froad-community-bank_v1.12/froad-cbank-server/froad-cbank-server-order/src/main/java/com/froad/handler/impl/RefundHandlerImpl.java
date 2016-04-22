package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.RefundOrderBean;
import com.froad.common.beans.RefundProductBean;
import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.RefundTempBean;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.DeliveryType;
import com.froad.enums.FieldMapping;
import com.froad.enums.ModuleID;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderStatus;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ProductType;
import com.froad.enums.RefundResource;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderRefundState;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.handler.RefundApprovalHandler;
import com.froad.handler.RefundHandler;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrderLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.impl.payment.PaymentCoreLogicImpl;
import com.froad.logic.payment.PaymentCoreLogic;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.support.OrderSupport;
import com.froad.support.RefundSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.support.impl.payment.AWIPSThirdpartyImpl;
import com.froad.thirdparty.request.active.ActiveFunc;
import com.froad.thirdparty.request.active.impl.ActiveFuncImpl;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.refund.RefundInfoVo;
import com.froad.thrift.vo.refund.RefundProductVo;
import com.froad.thrift.vo.refund.RefundRequestVo;
import com.froad.thrift.vo.refund.RefundResponseVo;
import com.froad.thrift.vo.refund.RefundStateRequestVo;
import com.froad.thrift.vo.refund.RefundStateResponseVo;
import com.froad.thrift.vo.refund.RefundTicketVo;
import com.froad.thrift.vo.refund.RefundTicketsRequestVo;
import com.froad.thrift.vo.refund.RefundTicketsResponseVo;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.SimpleID;
import com.froad.util.active.ActiveConst;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.TimeHelper;
import com.froad.util.payment.TimeHelper.TimeType;
import com.froad.util.refund.BaseSubassembly;
import com.froad.util.refund.ProxyRefundType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class RefundHandlerImpl implements RefundHandler {
	
	public static SimpleID simpleId = new SimpleID (ModuleID.refund);
	
	//common项目公共
	private final static CommonLogic commonLogic = new CommonLogicImpl();
	private OrderSupport orderSupport = new OrderSupportImpl();
	
	/**
	 * 业务监控
	 */
	private static MonitorService monitorService = new MonitorManager();
	
	private ActiveFunc activeFunc = new ActiveFuncImpl(); 
	
	@Override
	public RefundStateResponseVo updateRefundState(RefundStateRequestVo req) {
		RefundStateResponseVo response = null;
		ResultVo resultVo = null;
		RefundSupport refundSupport = null;
		RefundShoppingInfo shoppingInfo = null;
		RefundHistory refund = null;
		
		response = new RefundStateResponseVo();
		refundSupport = new RefundSupportImpl();
		//查询当前退款信息
		RefundHistory refundHis = refundSupport.getByRefundId(req.getRefundId());
		resultVo = new ResultVo();
		if(refundHis == null) {
			resultVo.setResultCode(ResultCode.refund_order_not_exists.getCode());
			resultVo.setResultDesc(ResultCode.refund_order_not_exists.getMsg());
			response.setResultVo(resultVo);
			return response;
		}
		shoppingInfo =  refundHis.getShoppingInfo().get(0);
		if(StringUtils.equals(SubOrderType.presell_org.getCode(), shoppingInfo.getType())) {
			//预售退款
			refund = refundSupport.updateRefundState(req.getRefundId(), RefundState.REFUND_FAILED.getCode(),
					req.getRefundState(), SubOrderType.presell_org.getCode());
		} else if(StringUtils.equals(SubOrderType.group_merchant.getCode(), shoppingInfo.getType())) {
			//团购退款
			refund = refundSupport.updateRefundState(req.getRefundId(), RefundState.REFUND_FAILED.getCode(),
					req.getRefundState(), SubOrderType.group_merchant.getCode());
		} else if(StringUtils.equals(SubOrderType.special_merchant.getCode(), shoppingInfo.getType())) {
			//名优特惠退款
			refund = refundSupport.updateRefundState(req.getRefundId(), RefundState.REFUND_AUDIT_PROCESSING.getCode(),
					req.getRefundState(), SubOrderType.special_merchant.getCode());
		}
		if(refund == null) {
			resultVo.setResultCode(ResultCode.refund_order_cannot_modifystate.getCode());
			resultVo.setResultDesc(ResultCode.refund_order_cannot_modifystate.getMsg());
		} else {
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
		}
		response.setResultVo(resultVo);
		return response;
	}
	
	@Override
	public RefundResponseVo refund(String refundResource, RefundRequestVo refundRequestVo) {
		String subOrderId = null;
		OrderMongo order = null;
		SubOrderMongo subOrder = null;
		OrderSupport orderSupport = null;
		List<RefundHistory> refundHisList = null;
		RefundSupport refundSupport = null;
		String refundId = null;
		RefundOrderBean refundOrderBean = null;
		RefundResponseVo responseVo = null;
		RefundInfoVo refundInfoVo = null;
		StringBuffer logMsg = null;
		boolean isSysRaised = false;
		
		responseVo = new RefundResponseVo();
		refundInfoVo = new RefundInfoVo();
		refundOrderBean = new RefundOrderBean();
		logMsg = new StringBuffer();
		
		refundSupport = new RefundSupportImpl();
		orderSupport = new OrderSupportImpl();
		
		if (refundResource.equals(RefundResource.USER_REFUND.getCode())) {
			LogCvt.info(logMsg.append("用户退款申请：").append(JSonUtil.toJSonString(refundRequestVo)).toString());
			// 退款金额实时查询
			if (null != refundRequestVo.getOption() && refundRequestVo.getOption().equals("1")){
				refundOrderBean.setQueryOnly(true);
			} else {
				monitorService.send(MonitorPointEnum.Order_Refund_Request_Count);//用户申请退款次数
			}
		} else {
			isSysRaised = true;
			LogCvt.info(logMsg.append("系统退款申请：").append(JSonUtil.toJSonString(refundRequestVo)).toString());
		}
		
		subOrderId = refundRequestVo.getSubOrderId();
		if (refundOrderBean.isQueryOnly()){
			refundId = "";
		} else {
			refundId = simpleId.nextId();
		}
		refundOrderBean.setRefundId(refundId);
		refundOrderBean.setRefundResource(refundResource);
		refundOrderBean.setClientId(refundRequestVo.getClientId());
		
		/**
		 * 查找子订单cb_suborder_product(client_id+order_id+sub_order_id)
		 */
		logMsg.delete(0, logMsg.length());
		LogCvt.info(logMsg.append("查找子订单信息：").append(refundRequestVo.getSubOrderId()).toString());
		subOrder = retrieveSubOrder(orderSupport, refundRequestVo, refundOrderBean);
		
		/**
		 * 退款商品与原订单商品不匹配
		 */
		if (null == subOrder) {
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("查找不到子订单信息：").append(refundRequestVo.getSubOrderId()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_product_not_match, responseVo);
			return responseVo;
		}
		
		if (isSysRaised && !subOrder.getType().equals(SubOrderType.group_merchant.getCode())) {
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("仅团购提货码支持过期自动退款：").append(refundRequestVo.getSubOrderId()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_only_group_expire_refund, responseVo);
			return responseVo;
		}
		
		/**
		 * 20150225 精品不允许部分退款
		 */
		if(StringUtils.equals(SubOrderType.boutique.getCode(), subOrder.getType())){
			//子订单中的商品集合
			List<ProductMongo> products = subOrder.getProducts();
			
			//退款申请中的商品集合
			List<RefundProductVo> refundProducts = refundRequestVo.getProductList();
			
			if(products.size() !=  refundProducts.size()){
				ResultVo resultVo = new ResultVo();
				resultVo.setResultCode("4021");
				resultVo.setResultDesc("精品商城商品不允许部分退款");
				responseVo.setResultVo(resultVo);
				
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append("精品商城商品不允许部分退款，subOrderId=").append(refundRequestVo.getSubOrderId()).toString());
				return responseVo;
			}
			
			//将退款申请中的商品封装成map
			Map<String,RefundProductVo> refundProductsMap = new HashMap<String, RefundProductVo>();
			for(RefundProductVo refundproduct : refundProducts){
				refundProductsMap.put(refundproduct.getProductId(), refundproduct);
			}

			boolean flag = true;
			//遍历子订单中的商品
			for(ProductMongo product : products){
				if(!refundProductsMap.containsKey(product.getProductId())){
					flag = false;
					break;
				}
				
				//子订单中商品总数量
				int quantity = (product.getQuantity() != null ? product.getQuantity() : 0)
									+ (product.getVipQuantity() != null ? product.getVipQuantity() : 0);
				
				if(refundProductsMap.get(product.getProductId()).getQuantity() != quantity){
					flag = false;
					break;
				}
			}
			if(!flag){
				ResultVo resultVo = new ResultVo();
				resultVo.setResultCode("4021");
				resultVo.setResultDesc("精品商城商品不允许部分退款");
				responseVo.setResultVo(resultVo);
				
				logMsg.delete(0, logMsg.length());
				LogCvt.info(logMsg.append("精品商城商品不允许部分退款，subOrderId=").append(refundRequestVo.getSubOrderId()).toString());
				return responseVo;
			}
		}
		
		/**
		 * 预售商品不在预售期内，不允许退款
		 */
		if (subOrder.getType().equals(SubOrderType.presell_org.getCode()) && isExceedPresellTime(subOrder, refundRequestVo)) {
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("预售商品不在预售期内：").append(refundRequestVo.getSubOrderId()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_exceed_presell_period, responseVo);
			return responseVo;
		}
		
		/**
		 * 查找订单cb_order(client_id+order_id)
		 * a) 若原订单支付状态为非完成支付,不允许退款
		 * b) 面对面支付订单不允许退款
		 * c) 存储total_price,real_price,fft_points,bank_points,point_rate,memberCode到entityMap中
		 */
		logMsg.delete(0, logMsg.length());
		LogCvt.info(logMsg.append("查找大订单信息：").append(refundOrderBean.getOrderId()).toString());
		orderSupport = new OrderSupportImpl();
		order = retrieveOrder(orderSupport, subOrder.getClientId(), refundOrderBean);
		
		// a) 若原订单支付状态为非完成支付,不允许退款
		if (null == order || !order.getOrderStatus().equals(OrderStatus.paysuccess.getCode())){
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("订单为非支付完成状态：").append(order.getOrderStatus()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_order_imcomplete, responseVo);
			return responseVo;
		}
		
		// b) 面对面支付订单不允许退款
		if (order.getIsQrcode() == 1){
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("面对面支付订单不允许退款：").append(refundOrderBean.getOrderId()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_face2face_payment, responseVo);
			return responseVo;
		}
		
		/**
		 * 获取已退款商品信息
		 */
		logMsg.delete(0, logMsg.length());
		LogCvt.info(logMsg.append("查找订单已退款记录：").append(refundOrderBean.getOrderId()).toString());
		refundHisList = refundSupport.findRefundHistoryList(refundOrderBean.getOrderId());
		
		/**
		 * 1) 不允许用户在同一个子订单里面同时发起两个退款，除非前一个退款已经成功完成.
		 * 2) 存储refundedQuantity到entityMap中
		 */
		if (isRefundExists(subOrderId, refundHisList, refundOrderBean)){
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("子订单存在未完成退款记录：").append(refundOrderBean.getOrderId()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_request_exists, responseVo);
			return responseVo;
		}
		
		/**
		 * 1) 生成初始状态(待处理)退款记录
		 * 2) 存储productId,productName,imageUrl,quantity,reason到entityMap中
		 */
		logMsg.delete(0, logMsg.length());
		
		//构造退款持久化对象
		RefundHistory refundHis = generateRefundHistory(refundRequestVo, refundOrderBean, subOrder);
		
		if (!refundOrderBean.isQueryOnly()){
			
			//满减活动需要先计算优惠信息,并设置到refundHis进行持久化
			if(ActiveConst.IS_ACTIVE_TRUE.equals(order.getIsActive())){
				ResultBean rb = activeFunc.noticeMarketingPlatformRefund(refundHis,true);
				ResultVo resultVo = new ResultVo();
				if(!ResultCode.success.getCode().equals(rb.getCode())){
					resultVo.setResultCode(ResultCode.refund_query_active_error.getCode());
					resultVo.setResultDesc(ResultCode.refund_query_active_error.getMsg() + "_" + rb.getMsg());
					responseVo.setResultVo(resultVo);
					return responseVo;
				}
			}
			
			LogCvt.info(logMsg.append("生成待处理退款记录：").append(refundOrderBean.getRefundId()).toString());
			refundSupport.insertRefundHistory(refundHis);
		}
		
		refundInfoVo.setRefundId(refundId);
		refundInfoVo.setOrderId(refundOrderBean.getOrderId());
		refundInfoVo.setRequestTime(new Date().toString());
		responseVo.setRefundInfo(refundInfoVo);
		
		/**
		 * 1) 如商品为已配送状态,不允许退款
		 */
		if (isDelivered(subOrder)) {
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("如商品为已配送状态,不允许退款：").append(subOrder.getSubOrderId()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_product_deliveried, responseVo);
			return responseVo;
		}
		
		/**
		 * 检查已退商品数量是否超出原订单数量
		 */
		if (!verifyRefundQuantity(refundOrderBean)){
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("商品数量是否超出原订单数量：").append(subOrder.getSubOrderId()).toString());
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.refund_quantity_exceed, responseVo);
			return responseVo;
		}
		
		/**
		 * 更新券表cb_ticket为已退款
		 * a) 存储actualRefundQuantity到entityMap中
		 */
		logMsg.delete(0, logMsg.length());
		LogCvt.info(logMsg.append("更新相关提货码信息：").append(refundId).toString());
//		if (!isSysRaised && !updateTicket(refundId, subOrder, refundOrderBean, responseVo)){
		if (!updateTicket(refundId, subOrder, refundOrderBean, responseVo)){
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.failed, responseVo);
			return responseVo;
		}
		
		/**
		 * 退款，先退现金，再退积分
		 *  a) 实际退款金额、数量(因券过期而导致只有部分商品退货)
		 *  b) 运费退款(全部商品退款后才退运费) 商品预售表 cb_product_presell
		 *  c) 赠送积分退还(全部商品退款后才退运费)
		 */
		logMsg.delete(0, logMsg.length());
		LogCvt.info(logMsg.append("开始退款支付：").append(refundId).toString());
		doRefundPayment(refundOrderBean, responseVo,order);
		if (responseVo.getResultVo() != null){
			updateRefundResponse(refundSupport, refundOrderBean, ResultCode.failed, responseVo);
			return responseVo;
		}
		
		/**
		 * 更新子订单退款状态
		 */
		if (!refundOrderBean.isQueryOnly()){
			updateSubOrderStatus(subOrder);
		}
		
		/**
		 * 更新状态为退款中
		 */
		if (!refundOrderBean.isQueryOnly()){
			logMsg.delete(0, logMsg.length());
			LogCvt.info(logMsg.append("更新状态为退款中：").append(refundId).toString());
		}
		updateRefundResponse(refundSupport, refundOrderBean, ResultCode.success, responseVo);
		
		//退款完成后，如果订单参加活动，通知营销平台
		if(ActiveConst.IS_ACTIVE_TRUE.equals(order.getIsActive()) && !refundOrderBean.isQueryOnly()){
			//设置退款流水信息，供通知活动模块使用
			refundHis.setPaymentInfo(refundOrderBean.getPayInfoList());
			ResultBean rb = activeFunc.noticeMarketingPlatformRefund(refundHis,false);
			
			if(!ResultCode.success.getCode().equals(rb.getCode())){
				LogCvt.info("退款成功之后通知活动模块失败，code=" + rb.getCode() +",MSG="+ rb.getMsg());
			}
		}
				
		return responseVo;
	}

	/**
	 * 获取退款商品列表
	 * 
	 * @param refundSupport
	 * @param refundRequestVo
	 * @param refundOrderBean
	 * @return
	 */
	private RefundHistory generateRefundHistory(RefundRequestVo refundRequestVo,RefundOrderBean refundOrderBean,SubOrderMongo subOrder){
		RefundHistory refundHis = null;
		List<RefundShoppingInfo> shoppingInfoList = null;
		RefundShoppingInfo shoppingItem = null;
		List<RefundProduct> refundProductList = null;
		RefundProduct refundProduct = null;
		String productId = null;
		String productName = null;
		String imageUrl = null;
		
		refundHis = new RefundHistory();
		refundHis.set_id(refundOrderBean.getRefundId());
		refundHis.setClientId(refundRequestVo.getClientId());
		refundHis.setOrderId(refundOrderBean.getOrderId());
		refundHis.setRefundState(RefundState.REFUND_INIT.getCode());
		refundHis.setReason(refundRequestVo.getReason());
		refundHis.setRefundResource(refundOrderBean.getRefundResource());
		refundHis.setMemberCode(String.valueOf(refundOrderBean.getMemberCode()));
		
		refundOrderBean.setReason(refundHis.getReason());
		
		shoppingInfoList = new ArrayList<RefundShoppingInfo>();
		shoppingItem = new RefundShoppingInfo();
		refundProductList = new ArrayList<RefundProduct>();
		for (int i = 0; i < refundRequestVo.getProductList().size(); i++){
			productId = refundRequestVo.getProductList().get(i).getProductId();
			RefundProductBean refundTemp = refundOrderBean.getProductMap().get(productId);
			productName = refundTemp.getProductName();
			imageUrl = refundTemp.getImage();
			
			refundProduct = new RefundProduct();
			refundProduct.setProductId(productId);
			refundProduct.setProductName(productName);
			refundProduct.setImageUrl(imageUrl);
			
			refundProduct.setRefundTotalCash(refundTemp.getRefundTotalPrice());
			
			refundProduct.setPrice(refundTemp.getPrice());
			refundProduct.setVipPrice(refundTemp.getVipPrice());
			
			List<ProductMongo> ps = subOrder.getProducts();
			int quantityTotal = 0;
			for (ProductMongo p : ps) {
				if(p.getProductId().equals(productId)){
					quantityTotal = p.getQuantity();
					break;
				}
			}
			
			
			if(quantityTotal > refundTemp.getRefundedQuantity()){
				//普通商品还未退完
				if(refundTemp.getRequestQuantity() + refundTemp.getRefundedQuantity() > quantityTotal){
					//一部分退VIP 一部分退普通
					int refundCommon = quantityTotal - refundTemp.getRefundedQuantity();
					refundProduct.setQuantity(refundCommon);
					refundProduct.setVipQuantity(refundTemp.getRequestQuantity() - refundCommon);
				}else{
					refundProduct.setVipQuantity(0);
					refundProduct.setQuantity(refundTemp.getRequestQuantity());
				}
			}else{
				//只能退VIP
				refundProduct.setVipQuantity(refundTemp.getRequestQuantity());
				refundProduct.setQuantity(0);
			}
			refundProductList.add(refundProduct);
		}
		shoppingItem.setProducts(refundProductList);
		shoppingItem.setSubOrderId(refundRequestVo.getSubOrderId());
		shoppingItem.setMerchantId(refundOrderBean.getMerchantId());
		shoppingItem.setMerchantName(refundOrderBean.getMerchantName());
		shoppingItem.setType(refundOrderBean.getSubOrderType());
		shoppingInfoList.add(shoppingItem);
		
		refundHis.setShoppingInfo(shoppingInfoList);
		refundOrderBean.setShoppingInfoList(shoppingInfoList);
		refundHis.setCreateTime(System.currentTimeMillis());
		
		
		return refundHis;
	}
	/**
	 * 查找退款记录是否存在
	 * 
	 * @param subOrderId
	 * @param refundHisList
	 * @param refundOrderBean
	 * @return
	 */
	private boolean isRefundExists(String subOrderId,
			List<RefundHistory> refundHisList,
			RefundOrderBean refundOrderBean) {
		RefundHistory refundHis = null;
		List<RefundShoppingInfo> shoppingList = null;
		RefundShoppingInfo shoppingInfo = null;
		List<RefundProduct> refundProductList = null;
		RefundPaymentInfo paymentInfo = null;
		List<RefundPaymentInfo> paymentList = null;
		RefundProduct refundProduct = null;
		RefundProductBean productBean = null;
		String productId = null;
		String state = null;
		int quantity = 0;
		int quantityVip = 0;
		
		// 检查子订单中存在正在退款的商品
		refundProductList = new ArrayList<RefundProduct>();
		for (int i = 0; i < refundHisList.size(); i++){
			refundHis = refundHisList.get(i);
			state = refundHis.getRefundState();
			
			if (state.equals(RefundState.REFUND_INIT.getCode())
					|| state.equals(RefundState.REFUND_PROCESSING.getCode())
					|| state.equals(RefundState.REFUND_AUDIT_PROCESSING.getCode())
					|| state.equals(RefundState.REFUND_AUDIT_PASSED.getCode())) {
				if (refundOrderBean.getRefundResource().equals(RefundResource.USER_REFUND.getCode())
						&& refundHis.getShoppingInfo().get(0).getSubOrderId().equals(subOrderId)) {
					return true;
				} else if (refundOrderBean.getRefundResource().equals(RefundResource.USER_REFUND.getCode()) && state.equals(RefundState.REFUND_PROCESSING.getCode())) {
					shoppingList = refundHis.getShoppingInfo();
					for (int j = 0; j < shoppingList.size(); j++){
						shoppingInfo = shoppingList.get(j);
						// 查找对应子订单信息
						if (shoppingInfo.getSubOrderId().equals(subOrderId)){
							refundProductList.addAll(shoppingInfo.getProducts());
						}
					}
					
					paymentList = refundHis.getPaymentInfo();
					for (int k = 0; k < paymentList.size(); k++) {
						paymentInfo = paymentList.get(k);
						if (paymentInfo.getType().equals(PaymentMethod.cash.getCode())) {
							refundOrderBean.setRefundedCash(refundOrderBean.getRefundedCash() + paymentInfo.getRefundValue());
						} else if (paymentInfo.getType().equals(PaymentMethod.froadPoints.getCode())
								|| paymentInfo.getType().equals(PaymentMethod.bankPoints.getCode())) {
							refundOrderBean.setRefundedPoint(refundOrderBean.getRefundedPoint()	+ paymentInfo.getRefundValue());
						}
					}
				}
			} else if (state.equals(RefundState.REFUND_SUCCESS.getCode())
					|| state.equals(RefundState.REFUND_MANUAL_SUCCESS.getCode())) {
				shoppingList = refundHis.getShoppingInfo();
				for (int j = 0; j < shoppingList.size(); j++){
					shoppingInfo = shoppingList.get(j);
					// 查找对应子订单信息
					if (shoppingInfo.getSubOrderId().equals(subOrderId)){
						refundProductList.addAll(shoppingInfo.getProducts());
					}
				}
				
				paymentList = refundHis.getPaymentInfo();
				for (int k = 0; k < paymentList.size(); k++) {
					paymentInfo = paymentList.get(k);
					if (paymentInfo.getType().equals(PaymentMethod.cash.getCode())) {
						refundOrderBean.setRefundedCash(refundOrderBean.getRefundedCash() + paymentInfo.getRefundValue());
					} else if (paymentInfo.getType().equals(PaymentMethod.froadPoints.getCode())
							|| paymentInfo.getType().equals(PaymentMethod.bankPoints.getCode())) {
						refundOrderBean.setRefundedPoint(refundOrderBean.getRefundedPoint()	+ paymentInfo.getRefundValue());
					}
				}
			}
		}
		
		/**
		 * 获取已退商品信息-已退商品数量记录
		 */
		for (int k = 0; k < refundProductList.size(); k++){
			refundProduct = refundProductList.get(k);
			productId = refundProduct.getProductId();
			productBean = refundOrderBean.getProductMap().get(productId);
			
//			List<ProductMongo> buyProducts = subOrder.getProducts();
//			ProductMongo buyProduct = null;
//			for (ProductMongo productMongo : buyProducts) {
//				if(productId.endsWith(productMongo.getProductId())){
//					buyProduct = productMongo;
//					break;
//				}
//			}
//			
//			int buyQuantity = buyProduct.getQuantity() == null ? 0 : buyProduct.getQuantity();
//			int refundedQuantity = productBean.getQuantity();
//			int wantRefund = productBean.getRequestQuantity(); //申请退款的数量
//			if(refundedQuantity < buyQuantity){
//				//普通未退完
//				if(buyQuantity - refundedQuantity < wantRefund){ // 又退VIP又退普通
//					productBean.setQuantity(buyQuantity - refundedQuantity);
//					productBean.setVipQuantity(wantRefund - productBean.getQuantity());
//				}else{
//					productBean.setQuantity(wantRefund);
//					productBean.setVipQuantity(0);
//				}
//				
//			}else{
//				//普通已退完
//				productBean.setQuantity(0);
//				productBean.setVipQuantity(wantRefund);
//			}
//			refundOrderBean.addProduct(productBean);
			
			if (null != productBean) {
				quantity = productBean.getRefundedQuantity();
				if (refundProduct.getQuantity() != null){
					quantity += refundProduct.getQuantity();
				}
				if (refundProduct.getVipQuantity() != null){
					quantityVip += refundProduct.getVipQuantity();
				}
				productBean.setRefundedQuantity(quantity);
				productBean.setRefundedVipQuantity(quantityVip);
				refundOrderBean.addProduct(productBean);
			}
		}
		
		return false;
	}

	/**
	 * 获取订单记录
	 * 
	 * @param orderSupport
	 * @param refundOrderBean
	 * @param subOrderId
	 * @return
	 */
	private OrderMongo retrieveOrder(OrderSupport orderSupport,
			String clientId, RefundOrderBean refundOrderBean) {
		OrderMongo order = null;

		order = orderSupport.getOrderByOrderId(clientId, refundOrderBean.getOrderId());
		
		if (null != order){
			refundOrderBean.setOrderId(order.getOrderId());
			refundOrderBean.setTotalPrice(order.getTotalPrice());
			refundOrderBean.setTotalCash(order.getRealPrice());
			if (order.getFftPoints() > 0) {
				refundOrderBean.setTotalFftPoint(order.getFftPoints());
			}
			if (order.getBankPoints() > 0){
				refundOrderBean.setTotalBankPoint(order.getBankPoints());
			}
			if (order.getPointRate() != null && !order.getPointRate().trim().equals("")) {
				refundOrderBean.setPointRate(Double.parseDouble(order.getPointRate().toString()));
			}
			if (order.getGivePoints() != null){
				refundOrderBean.setTotalCreditPoint(order.getGivePoints());
			}
			refundOrderBean.setMemberCode(order.getMemberCode());
		}
		
		return order;
	}
	
	/**
	 * 获取原子订单商品信息
	 * 
	 * @param orderLogic
	 * @param refundRequestVo
	 * @param refundOrderBean
	 * @return
	 */
	private SubOrderMongo retrieveSubOrder(
			OrderSupport orderSupport, RefundRequestVo refundRequestVo,
			RefundOrderBean refundOrderBean) {
		SubOrderMongo subOrder = null;
		List<ProductMongo> productList = null;
		ProductMongo product = null;
		String productId = null;
		boolean isFound = false;
		RefundProductBean productBean = null;
		
		subOrder = orderSupport.getSubOrderByClient(refundRequestVo.getClientId(), refundRequestVo.getOrderId(), refundRequestVo.getSubOrderId());
		productList= subOrder.getProducts();
		refundOrderBean.setOrderId(subOrder.getOrderId());
		refundOrderBean.setMemberCode(subOrder.getMemberCode());
		refundOrderBean.setMerchantId(subOrder.getMerchantId());
		refundOrderBean.setMerchantName(subOrder.getMerchantName());
		refundOrderBean.setSubOrderType(subOrder.getType());
		
		for (int j = 0; j < refundRequestVo.getProductListSize(); j++){
			productId = refundRequestVo.getProductList().get(j).getProductId();
			
			// 检查退款商品是否存在于原订单中
			for (int i = 0; i < productList.size(); i++){
				product = productList.get(i);
				if (product.getProductId().equals(productId)){
					isFound = true;
					break;
				}
			}
			
			// 如果退款商品不是原子订单中的商品, 返回null, 不允许退款
			if (!isFound){
				return null;
			}
			
			productBean = new RefundProductBean();
			productBean.setProductId(product.getProductId());
			productBean.setProductName(product.getProductName());
			productBean.setImage(product.getProductImage());
			productBean.setSubOrderId(subOrder.getSubOrderId());
			productBean.setSubOrderType(subOrder.getType());
			productBean.setPrice(product.getMoney());
			productBean.setQuantity(product.getQuantity());
			productBean.setVipPrice(product.getVipMoney());
			productBean.setVipQuantity(product.getVipQuantity());
			productBean.setCreditPoint(EmptyChecker.isNotEmpty(product.getPoints())?product.getPoints():0);
			productBean.setDeliveryFee(product.getDeliveryMoney());
			productBean.setRequestQuantity(refundRequestVo.getProductList().get(j).getQuantity());
			productBean.setDeliveryOption(product.getDeliveryOption());
			
			productBean.setCashArray(product.getCashArray());
			productBean.setVipCashArray(product.getVipCashArray());
			productBean.setPointArray(product.getPointArray());
			productBean.setVipPointArray(product.getVipPointArray());
			
			productBean.setVipCutMoneyArray(product.getVipCutMoneyArray());
			productBean.setCutMoneyArray(product.getCutMoneyArray());
			
			refundOrderBean.addProduct(productBean);
			// reset
			isFound = false;
		}
		
		return subOrder;
	}
	
	/**
	 * 检查待退款商品是否已配送
	 * 
	 * @param subOrder
	 * @return
	 */
	private boolean isDelivered(SubOrderMongo subOrder){
		Iterator<ProductMongo> iterator = null;
		ProductMongo product = null;
		
		// 团购无配送状态，直接检查有无可退券
		if (subOrder.getType().equals(SubOrderType.group_merchant.getCode())){
			return false;
		}
		
		// 名优特惠，检查子订单状态
		if (subOrder.getType().equals(SubOrderType.special_merchant.getCode())) {
			if (null != subOrder.getDeliveryState()
					&& (subOrder.getDeliveryState().equals(ShippingStatus.shipped.getCode())
							|| subOrder.getDeliveryState().equals(ShippingStatus.receipt.getCode()) 
							|| subOrder.getDeliveryState().equals(ShippingStatus.token.getCode()))) {
				return true;
			} else {
				return false;
			}
		}

		// 预售商品，检查子订单商品状态
		if (subOrder.getType().equals(SubOrderType.presell_org.getCode())) {
			iterator = subOrder.getProducts().iterator();
			
			while (iterator.hasNext()){
				product = iterator.next();
				// 已发货, 已收货, 已提货不允许退款
				if (null != product.getDeliveryState() && (product.getDeliveryState().equals(ShippingStatus.shipped.getCode())
						|| product.getDeliveryState().equals(ShippingStatus.receipt.getCode())
						|| product.getDeliveryState().equals(ShippingStatus.token.getCode()))) {
					return true;
				}
			}
		}
		
		//精品已经出库的、发货的、出货中不可退款
		if(StringUtils.equals(SubOrderType.boutique.getCode(), subOrder.getType())){
			// 已发货, 已收货, 出货中不允许退款
			if (null != subOrder.getDeliveryState() && (subOrder.getDeliveryState().equals(ShippingStatus.shipped.getCode())
					|| subOrder.getDeliveryState().equals(ShippingStatus.receipt.getCode())
					|| subOrder.getDeliveryState().equals(ShippingStatus.shipping.getCode()))) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 验证退款数量是否超出原订单数量
	 * 
	 * @param refundOrderBean
	 * @return
	 */
	private boolean verifyRefundQuantity(RefundOrderBean refundOrderBean) {
		Iterator<RefundProductBean> iterator = null;
		RefundProductBean productBean = null;
		
		iterator = refundOrderBean.getProductMap().values().iterator();
		while(iterator.hasNext()){
			productBean = iterator.next();
			// 如请求退款商品数量大于可退款数量,不允许退款
			if (productBean.getRequestQuantity() > ((productBean.getQuantity() + productBean.getVipQuantity()) - productBean.getRefundedQuantity() - productBean.getRefundedVipQuantity())) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 更新退货表
	 * 
	 * @param refundSupport
	 * @param refundOrderBean
	 * @param resultCode
	 * @param responseVo
	 */
	private void updateRefundResponse(RefundSupport refundSupport,
			RefundOrderBean refundOrderBean, ResultCode resultCode,
			RefundResponseVo responseVo) {
		ResultVo resultVo = null;
		String refundState = null;
		boolean isPointOnly = true;
		RefundPaymentInfo pointPayInfo = null;
		RefundApprovalHandler approvalHandler = null;
		String paymentId = null;
		Map<String, Boolean> noticeMap = new HashMap<String, Boolean>();
		
		if(ResultCode.success.equals(resultCode)) {
			refundState = RefundState.REFUND_PROCESSING.getCode();
		} else {
			refundState = RefundState.REFUND_FAILED.getCode();
		}
		
		if (null != refundOrderBean.getPayInfoList() && refundOrderBean.getPayInfoList().size() > 0) {
			// 如为纯积分退款，不需要通过财务审核，直接更新退款状态
			for (int i = 0; i < refundOrderBean.getPayInfoList().size(); i++){
				if (refundOrderBean.getPayInfoList().get(i).getType().equals(PaymentMethod.cash.getCode())){
					isPointOnly = false;
				} else if (refundOrderBean.getPayInfoList().get(i).getType().equals(PaymentMethod.froadPoints.getCode())) {
					pointPayInfo = refundOrderBean.getPayInfoList().get(i);
				} else if (refundOrderBean.getPayInfoList().get(i).getType().equals(PaymentMethod.bankPoints.getCode())) {
					pointPayInfo = refundOrderBean.getPayInfoList().get(i);
				}
			}
			
			// 纯积分退款，更新最终状态
			if (isPointOnly){
				paymentId = pointPayInfo.getPaymentId();
				if (pointPayInfo.getResultCode().equals(ResultCode.success.getCode())){
					refundState = RefundState.REFUND_SUCCESS.getCode();
				} else if (pointPayInfo.getResultCode().equals(ResultCode.failed.getCode())) {
					refundState = RefundState.REFUND_FAILED.getCode();
				}
			}
		}
		
		if (responseVo.getRefundInfo() != null) {
			responseVo.getRefundInfo().setRefundStatus(refundState);
		} else {
			responseVo.setRefundInfo(new RefundInfoVo());
		}
		
		
		if (null == responseVo.getResultVo() && resultCode != null){
			resultVo = new ResultVo();
			resultVo.setResultCode(resultCode.getCode());
			resultVo.setResultDesc(resultCode.getMsg());
			responseVo.setResultVo(resultVo);
		}
		
		if (!refundOrderBean.isQueryOnly()){
			LogCvt.info(new StringBuffer(refundOrderBean.getRefundId()).append(" 更新退款状态：").append(refundState).toString());
			updateRefundHistory(refundSupport, refundOrderBean, refundState);
			
			approvalHandler = new RefundApprovalHandlerImpl();
			if (isPointOnly){
				if (refundState.equals(RefundState.REFUND_SUCCESS.getCode())) {
					noticeMap.put(paymentId, true);
				} else {
					noticeMap.put(paymentId, false);
				}
				approvalHandler.updateApprovalResult(noticeMap,refundOrderBean.getRefundId());
			} else if (refundState.equals(RefundState.REFUND_FAILED.getCode()) && refundOrderBean.getPayInfoList() != null){
				for (int i = 0; i < refundOrderBean.getPayInfoList().size(); i++){
					paymentId = refundOrderBean.getPayInfoList().get(i).getPaymentId();
					noticeMap.put(paymentId, false);
				}
				approvalHandler.updateApprovalResult(noticeMap,refundOrderBean.getRefundId());
			}
		}
	}
	
	/**
	 * 更新退款记录状态
	 * 
	 * @param refundSupport
	 * @param refundOrderBean
	 * @param refundState
	 */
	private void updateRefundHistory(RefundSupport refundSupport, RefundOrderBean refundOrderBean, String refundState){
		DBObject queryObj = null;
		DBObject valueObj = null;
		DBObject updateObj = null;
		List<RefundProduct> updatedProductList = null;
		RefundProduct updatedProduct = null;
		String productId = null;
		RefundProductBean productBean = null;
		
		queryObj = new BasicDBObject(FieldMapping.ID.getMongoField(), refundOrderBean.getRefundId());
		
		valueObj = new BasicDBObject();
		valueObj.put(FieldMapping.REFUND_STATE.getMongoField(), refundState);
		if (refundState.equals(RefundState.REFUND_SUCCESS.getCode())
				|| refundState.equals(RefundState.REFUND_FAILED.getCode())) {
			valueObj.put(FieldMapping.REFUND_TIME.getMongoField(), System.currentTimeMillis());
		}
		
		if (null != refundOrderBean.getPayInfoList() && refundOrderBean.getPayInfoList().size() > 0){
			updatedProductList = new ArrayList<RefundProduct>();
//			for (int i = 0; i < refundOrderBean.getShoppingInfoList().get(0).getProducts().size(); i++){
//				updatedProduct = refundOrderBean.getShoppingInfoList().get(0).getProducts().get(i);
//				productId = updatedProduct.getProductId();
//				productBean = refundOrderBean.getProductMap().get(productId);
//			}
			
			valueObj.put(FieldMapping.PAYMENT_INFO.getMongoField(), JSON.parse(JSonUtil.toJSonString(refundOrderBean.getPayInfoList())));
			
			//if (updatedProductList.size() > 0){
				RefundHistory his = refundSupport.getByRefundId(refundOrderBean.getRefundId());
				RefundShoppingInfo rsi = his.getShoppingInfo().get(0);
				List<RefundProduct> rps = rsi.getProducts();
				for (RefundProduct refundProduct : rps) {
					refundProduct.setRefundTotalCash(refundOrderBean.getProductMap().get(refundProduct.getProductId()).getRefundTotalPrice());
				}
//				rsi.setProducts(updatedProductList);
				valueObj.put(FieldMapping.SHOPPING_INFO.getMongoField(), JSON.parse(JSonUtil.toJSonString(his.getShoppingInfo())));
			//}
		}
		
		updateObj = new BasicDBObject();
		updateObj.put("$set", valueObj);
		
		refundSupport.findAndModifyByDBObject(queryObj, updateObj);
	}
	
	
	/**
	 * 更新券表
	 * 
	 * @param refundId
	 * @param subOrder
	 * @param refundOrderBean
	 * @param responseVo
	 * @return
	 */
	private boolean updateTicket(String refundId, SubOrderMongo subOrder,
			RefundOrderBean refundOrderBean,
			RefundResponseVo responseVo) {
		String orderId = null;
		String subOrderId = null;
		String subOrderType = null;
		TicketSupport ticketSupport = null;
		ResultVo resultVo = null;
		
		orderId = subOrder.getOrderId();
		subOrderId = subOrder.getSubOrderId();
		subOrderType = subOrder.getType();
		
		// 根据orderId,subOrderId查找券
		ticketSupport = new TicketSupportImpl();
		
		resultVo = new ResultVo();
		
		if (subOrderType.equals(ProductType.presell.getCode())){
			/**
			 * 预售商品: 券存在且有效, 更新有效券商品数量, 插入或更新券退款数量(同一ticket id)
			 */
			if (!updatePresellTicket(refundId, orderId, subOrderId, refundOrderBean, ticketSupport)){
				resultVo.setResultCode(ResultCode.refund_ticket_not_found.getCode());
				resultVo.setResultDesc(ResultCode.refund_ticket_not_found.getMsg());
				responseVo.setResultVo(resultVo);
				return false;
			}
		} else if (subOrderType.equals(ProductType.group.getCode())){
			/**
			 * 团购商品
			 *  1) 若退款数量>有效券数量, 不允许退款
			 *  2) 若退款数量<=有效券数量, 更新等量券为已退款
			 */
			if (!updateGroupTicket(refundId, orderId, subOrderId, refundOrderBean, ticketSupport)){
				resultVo.setResultCode(ResultCode.refund_group_ticket_insufficient.getCode());
				resultVo.setResultDesc(ResultCode.refund_group_ticket_insufficient.getMsg());
				responseVo.setResultVo(resultVo);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 更新预售券信息
	 * 
	 * @param refundId
	 * @param orderId
	 * @param subOrderId
	 * @param refundOrderBean
	 * @param ticketSupport
	 * @return
	 */
	private boolean updatePresellTicket(String refundId, String orderId,
			String subOrderId, RefundOrderBean refundOrderBean,
			TicketSupport ticketSupport) {
		Ticket ticket = null;
		Ticket tmpTicket = null;
		String productId = null;
		Integer diff = 0;
		int actualRefundQuantity = 0;
		DBObject valueObj = null;
		
		Map<String, Ticket> validTicketMap = null;
		Iterator<RefundProductBean> iterator = null;
		RefundProductBean productBean = null;
		
		validTicketMap = retrievePresellTickets(orderId, subOrderId, ticketSupport);
		iterator = refundOrderBean.getProductMap().values().iterator();
		valueObj = new BasicDBObject();
		
		while (iterator.hasNext()){
			productBean = iterator.next();
			productId = productBean.getProductId();
			
			if (productBean.getDeliveryOption().equals(DeliveryType.home.getCode())) {
				// 更新实际退款数量
				productBean.setActualRefundQuantity(productBean.getRequestQuantity());
				refundOrderBean.addProduct(productBean);
				continue;
			}
			
			if (null == validTicketMap.get(productId)){
				// 券过期
				return false;
			} else {
				ticket = validTicketMap.get(productId);
				diff = ticket.getQuantity() - productBean.getRequestQuantity();
				
				if (diff == 0){
					// 全部商品退款，直接更新原纪录
					actualRefundQuantity = productBean.getActualRefundQuantity() + ticket.getQuantity();
				} else if (diff > 0) {
					// 只退部分商品，实际退货商品为请求数量
					actualRefundQuantity = productBean.getActualRefundQuantity() + productBean.getRequestQuantity();
					
					// 扣除退款商品数量
					ticket.setQuantity(diff);
					valueObj.put(FieldMapping.QUANTITY.getMongoField(), diff);
				} else {
					// 券可退商品数量少于请求退款商品数量，只退券可退数量
					actualRefundQuantity = productBean.getActualRefundQuantity() + ticket.getQuantity();
				}
				
				// 全部商品数量已退，更新为已退款；否则只更新数量，不更新状态
				if (diff <= 0){
					if (ticket.getStatus().equals(TicketStatus.init.getCode())){
						ticket.setStatus(TicketStatus.init_refunded.getCode());
					} else {
						ticket.setStatus(TicketStatus.refunding.getCode());
					}
					valueObj.put(FieldMapping.STATUS.getMongoField(), ticket.getStatus());
					valueObj.put(FieldMapping.REFUND_ID.getMongoField(), refundId);
				}
				
				if (!refundOrderBean.isQueryOnly()){
					ticketSupport.updateById(valueObj, JSonUtil.toObjectId(ticket.get_id()));
				}
				
				// 插入新的券退货记录
				if (diff > 0){
					tmpTicket = new Ticket();
					tmpTicket.setClientId(ticket.getClientId());
					tmpTicket.setCreateTime(ticket.getCreateTime());
					tmpTicket.setExpireTime(ticket.getExpireTime());
					tmpTicket.setForgCode(ticket.getForgCode());
					tmpTicket.setImage(ticket.getImage());
					tmpTicket.setLorgCode(ticket.getLorgCode());
					tmpTicket.setMemberCode(ticket.getMemberCode());
					tmpTicket.setMemberName(ticket.getMemberName());
					tmpTicket.setMerchantId(ticket.getMerchantId());
					tmpTicket.setMerchantName(ticket.getMerchantName());
					tmpTicket.setMobile(ticket.getMobile());
					tmpTicket.setOrderId(orderId);
					tmpTicket.setOrgCode(ticket.getOrgCode());
					tmpTicket.setOrgName(ticket.getOrgName());
					tmpTicket.setPrice(ticket.getPrice());
					tmpTicket.setProductId(ticket.getProductId());
					tmpTicket.setProductName(ticket.getProductName());
					tmpTicket.setQuantity(productBean.getRequestQuantity());
					tmpTicket.setRefundTime(System.currentTimeMillis());
					if (ticket.getStatus().equals(TicketStatus.init.getCode())){
						tmpTicket.setStatus(TicketStatus.init_refunded.getCode());
					} else {
						tmpTicket.setStatus(TicketStatus.refunding.getCode());
					}
					tmpTicket.setRefundId(refundId);
					tmpTicket.setSubOrderId(subOrderId);
					tmpTicket.setTicketId(ticket.getTicketId());
					tmpTicket.setType(ticket.getType());
					tmpTicket.setTorgCode(ticket.getTorgCode());
					if (!refundOrderBean.isQueryOnly()){
						ticketSupport.addTicket(tmpTicket);
					}
				}
				
				// 更新实际退款数量
				productBean.setActualRefundQuantity(actualRefundQuantity);
				refundOrderBean.addProduct(productBean);
			}
		}
		
		return true;
	}
	
	/**
	 * 获取有效的预售券
	 * 
	 * @param orderId
	 * @param subOrderId
	 * @param ticketSupport
	 * @return
	 */
	private Map<String, Ticket> retrievePresellTickets(String orderId, String subOrderId, TicketSupport ticketSupport) {
		Map<String, Ticket> ticketMap = null;
		List<Ticket> ticketList = null;
		Ticket ticket = null;
		
		ticketList = ticketSupport.getTickets(orderId, subOrderId, null);
		
		if (null != ticketList) {
			ticketMap = new HashMap<String, Ticket>();
			for (int i = 0; i < ticketList.size(); i++) {
				ticket = ticketList.get(i);
				// 有效券
				if (ticket.getStatus().equals(TicketStatus.init.getCode()) || ticket.getStatus().equals(TicketStatus.sent.getCode())){
					ticketMap.put(ticket.getProductId(), ticket);
				}
			}
		}
		
		return ticketMap;
	}
	
	/**
	 * 更新团购券
	 * 
	 * @param refundId
	 * @param orderId
	 * @param subOrderId
	 * @param entityMap
	 * @param refundOrderBean
	 * @return
	 */
	private boolean updateGroupTicket(String refundId, String orderId,
			String subOrderId, RefundOrderBean refundOrderBean,
			TicketSupport ticketSupport) {
		Map<String, List<Ticket>> ticketMap = null;
		List<Ticket> ticketList = null;
		Map<String, RefundProductBean> tmpEntityMap = null;
		Iterator<RefundProductBean> iterator = null;
		RefundProductBean productBean = null;
		Ticket ticket = null;
		String productId = null;
		DBObject valueObj = null;
		
		ticketMap = retrieveGroupTickets(orderId, subOrderId,refundOrderBean.getRefundResource(), ticketSupport);
		
		tmpEntityMap = new HashMap<String, RefundProductBean>();
		tmpEntityMap.putAll(refundOrderBean.getProductMap());
		iterator = tmpEntityMap.values().iterator();
		
		/**
		 * 券码状态
		 * 		如果是系统自动发起, 则券状态修改为: expired_refunding-已过期退款中)
		 *      如果是客户主动发起, 则券状态修改为: refunding-退款中)
		 */
		String ticketStatus = StringUtils.equals(RefundResource.SYSTEM_REFUND.getCode(), refundOrderBean.getRefundResource()) ? TicketStatus.expired_refunding.getCode() : TicketStatus.refunding.getCode(); 
		
		while (iterator.hasNext()){
			productBean = iterator.next();
			productId = productBean.getProductId();
			ticketList = ticketMap.get(productId);
			
			// 有效券数量<退款商品数量，退款失败
			if(null == ticketList || ticketList.size() < productBean.getRequestQuantity()){
				return false;
			} else {
				valueObj = new BasicDBObject();
				// 团购为一个商品对应一张券，更新等量的券为已退款状态
				
				for (int i = 0; i < productBean.getRequestQuantity(); i++){
					ticket = ticketList.get(i);

					// 更新券为已退款状态
					valueObj.put(FieldMapping.REFUND_TIME.getMongoField(), System.currentTimeMillis());
//					valueObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.refunding.getCode());
					valueObj.put(FieldMapping.STATUS.getMongoField(), ticketStatus);
					valueObj.put(FieldMapping.REFUND_ID.getMongoField(), refundId);
					if (!refundOrderBean.isQueryOnly()){
						ticketSupport.updateById(valueObj, JSonUtil.toObjectId(ticket.get_id()));
					}
				}
				
				// 更新实际退款数量
				if (productBean.getRequestQuantity() > ticketList.size()) {
					productBean.setActualRefundQuantity(ticketList.size());
				} else {
					productBean.setActualRefundQuantity(productBean.getRequestQuantity());
				}
				refundOrderBean.addProduct(productBean);
			}
		}
		
		return true;
	}
	
	/**
	 * 获取团购券
	 * 
	 * @param orderId
	 * @param subOrderId
	 * @param ticketSupport
	 * @return
	 */
	private Map<String, List<Ticket>> retrieveGroupTickets(String orderId,
			String subOrderId,String refundResource, TicketSupport ticketSupport) {
		Map<String, List<Ticket>> ticketMap = null;
		List<Ticket> itemList = null;
		List<Ticket> ticketList = null;
		Ticket ticket = null;
		String productId = null;
		
		ticketList = ticketSupport.getTickets(orderId, subOrderId, null);
		if (null != ticketList) {
			ticketMap = new HashMap<String, List<Ticket>>();
			for (int i = 0; i < ticketList.size(); i++) {
				ticket = ticketList.get(i);
				productId = ticket.getProductId();
				
				// 有效券(用户主动发起的退款)||已过期(系统定时任务发起的退款,会先把券状态改成已过期)
				String status = ticket.getStatus();
				if ((status.equals(TicketStatus.sent.getCode()) && RefundResource.USER_REFUND.getCode().equals(refundResource)) // 用户主动发起的退款,必须是未消费才能退
						|| (status.equals(TicketStatus.expired.getCode()) && RefundResource.SYSTEM_REFUND.getCode().equals(refundResource)) // 系统定时任务发起的退款,必须是已过期才能退
				){
					if (null != ticketMap.get(productId)){
						itemList = ticketMap.get(productId);
					} else {
						itemList = new ArrayList<Ticket>();
					}
					itemList.add(ticket);
					ticketMap.put(productId, itemList);
				}
			}
		}
		
		return ticketMap;
	}
	
	/**
	 * 发起退款交易
	 * 
	 * @param refundOrderBean
	 * @param responseVo
	 * @return
	 */
	private void doRefundPayment(RefundOrderBean refundOrderBean,RefundResponseVo responseVo,OrderMongo order) {
		
//		Iterator<RefundProductBean> iterator = null;
		RefundProductBean productBean = null;
		String productId = null;
		int totalPoint = 0;// 原订单总消费积分
		int totalCreditPoint = 0;// 原订单总赠送积分
		int totalCost = 0;// 订单运费
		double pointRate = 0.0d;
		int reqRefundAmt = 0; // 应退金额
		int actualRefundCash = 0; // 实际退款现金
		double actualRefundPoint = 0.0; // 实际退还积分
		StringBuffer logInfo = null;
		PaymentCoreLogic payCoreLogic = null;//pay 2.0
		ResultVo resultVo = null;
		ResultBean resultBean = null;
		RefundTempBean payResult = null;
		List<RefundPaymentInfo> paymentInfoList = null;
		RefundPaymentInfo paymentInfo = null;
		resultVo = new ResultVo();
//		iterator = refundOrderBean.getProductMap().values().iterator();
		
		//积分兑换比例
		pointRate = refundOrderBean.getPointRate();
		
		/**
		 * 退还了赠送积分的Map数据  K 商品ID  V subOrderId
		 */
		boolean isNewRule = true;
		Map<String, String> refundPresentPointData = new HashMap<String, String>();
//		while (iterator.hasNext()){
		for (Map.Entry<String, RefundProductBean> entry : refundOrderBean.getProductMap().entrySet()) {
			productBean = entry.getValue();
			productId = productBean.getProductId();
			
			if (refundOrderBean.getRefundResource().equals(RefundResource.SYSTEM_REFUND.getCode())
					|| productBean.getSubOrderType().equals(SubOrderType.special_merchant.getCode())
					|| productBean.getSubOrderType().equals(SubOrderType.boutique.getCode())) {
				productBean.setActualRefundQuantity(productBean.getRequestQuantity());
			}
			
			//获取订单创建时间以确定此次退款是走积分拆分逻辑还是老的退款逻辑
			
			long createTime = order.getCreateTime();
			
			//TODO: 积分规则上线时间
			long ruleActiveTime = Const.POINT_NEW_RULE;
			//TODO: 积分规则上线时间

			
			if(createTime < ruleActiveTime){//订单创建时间小于积分规则生效时间则走老逻辑
				isNewRule = false;
				//==============老逻辑退款规则
				if (productBean.getRefundedQuantity() <= productBean.getQuantity()) {//普通商品未退完
					int wantToRefundQuantity = productBean.getActualRefundQuantity();
					//能够退款的普通商品数量
					int canRefundQuantity = productBean.getQuantity() - productBean.getRefundedQuantity();
					
					if (canRefundQuantity < productBean.getActualRefundQuantity()){ //余下的小于申请的
						logInfo = new StringBuffer();
						LogCvt.info(logInfo.append(productId).append(" 部分按普通价格退款，商品数量为： ").append(canRefundQuantity).toString());
						reqRefundAmt += Arith.mul(productBean.getPrice(), canRefundQuantity);
						productBean.setActualRefundQuantity(canRefundQuantity);
						
						logInfo = new StringBuffer();
						LogCvt.info(logInfo.append(productId).append(" 部分按VIP价格退款，商品数量为： ").append((wantToRefundQuantity - canRefundQuantity)).toString());
						reqRefundAmt += Arith.mul(productBean.getVipPrice(), (wantToRefundQuantity - canRefundQuantity));
						productBean.setActualRefundVipQuantity(wantToRefundQuantity - canRefundQuantity);
					} else {
						logInfo = new StringBuffer();
						LogCvt.info(logInfo.append(productId)
								.append(" 全部按普通价格退款，商品数量为： ").append(productBean.getActualRefundQuantity())
								.toString());
						reqRefundAmt += Arith.mul(productBean.getPrice(), productBean.getActualRefundQuantity());
						productBean.setActualRefundVipQuantity(0);
						productBean.setActualRefundQuantity(productBean.getActualRefundQuantity());
					}
				} else { //全退VIP
					logInfo = new StringBuffer();
					LogCvt.info(logInfo.append(productId).append(" 全部按VIP价格退款，商品数量为： ").append(productBean.getActualRefundQuantity()).toString());
					reqRefundAmt += Arith.mul(productBean.getVipPrice(), productBean.getActualRefundQuantity());
				}
				//==============老逻辑退款规则
			}else{
				//==============积分拆分规则
				if (productBean.getRefundedQuantity() <= productBean.getQuantity() &&  productBean.getQuantity() > 0) {//普通商品购买数量还没有正式退完此次退款中至少要退普通商品
					int wantToRefundQuantity = productBean.getActualRefundQuantity();
					//能够退款的普通商品数量
					int canRefundQuantity = productBean.getQuantity() - productBean.getRefundedQuantity();
					
					if (canRefundQuantity < productBean.getActualRefundQuantity()){ //申请退款的商品大于总共退款的普通商品，还需要退款VIP商品
						logInfo = new StringBuffer();
						LogCvt.info(logInfo.append(productId).append(" 部分按普通价格退款，商品数量为： ").append(canRefundQuantity).toString());
						productBean.setActualRefundQuantity(canRefundQuantity);
						
						logInfo = new StringBuffer();
						LogCvt.info(logInfo.append(productId).append(" 部分按VIP价格退款，商品数量为： ").append(wantToRefundQuantity - canRefundQuantity).toString());
						productBean.setActualRefundVipQuantity(wantToRefundQuantity - canRefundQuantity);
	
						Integer[] refundAmount = BaseSubassembly.countRefundOfCommonAndVIPProduct(productBean,order.getPaymentMethod(),order.getPointRate());
						reqRefundAmt += refundAmount[1];
						actualRefundPoint += refundAmount[0];
					} else { //只需要退款普通商品
						logInfo = new StringBuffer();
						Integer[] refundAmount = BaseSubassembly.countRefundOfAllCommonProduct(productBean,order.getPaymentMethod(),order.getPointRate());
						reqRefundAmt += refundAmount[1];
						actualRefundPoint += refundAmount[0];
						productBean.setActualRefundVipQuantity(0);
						productBean.setActualRefundQuantity(productBean.getActualRefundQuantity());
						LogCvt.info(logInfo.append(productId).append(" 全部按普通价格退款，商品数量为： ").append(productBean.getActualRefundQuantity()).toString());
					}
				} else { //普通商品已经全部退款完毕，只需要退款VIP商品
					logInfo = new StringBuffer();
					productBean.setActualRefundVipQuantity(productBean.getActualRefundQuantity());
					Integer[] refundAmount = BaseSubassembly.countRefundOfAllVIPProduct(productBean,order.getPaymentMethod(),order.getPointRate());
					reqRefundAmt += refundAmount[1];
					actualRefundPoint += refundAmount[0];
					LogCvt.info(logInfo.append(productId).append(" 全部按VIP价格退款，商品数量为： ").append(productBean.getActualRefundQuantity()).toString());
				}
				//==============积分拆分规则
			}
			
			/**
			 * 只有所有商品退款才可退还赠送积分和运费
			 */
			if (productBean.getRequestQuantity() + (productBean.getRefundedQuantity() + productBean.getRefundedQuantity())== productBean.getQuantity() + productBean.getVipQuantity()){
				totalCreditPoint += productBean.getCreditPoint();
				totalCost += productBean.getDeliveryFee();
				reqRefundAmt += productBean.getDeliveryFee();
				refundPresentPointData.put(productId, productBean.getSubOrderId());
			}
			
			refundOrderBean.getProductMap().put(productId, productBean);
		}
		
		if(reqRefundAmt == 0 && actualRefundPoint == 0){
			//计算出实际退款的积分和现金都为0，用户退款后并收不到退款金额，不允许退款
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("该退款无法返回用户支付金额，请尝试更改退款数量");
			responseVo.setResultVo(resultVo);
			return;
		}
		
		logInfo = new StringBuffer();
		LogCvt.info(logInfo.append("总计退还运费为： ").append(totalCost).append("； 总计扣回赠送积分为：").append(totalCreditPoint).toString());
		
		// 消费积分：非方付通积分，则银行积分
		if (refundOrderBean.getTotalFftPoint() > 0) {
			
			totalPoint = refundOrderBean.getTotalFftPoint();
			
		} else if (refundOrderBean.getTotalBankPoint() > 0) {
			
			totalPoint = refundOrderBean.getTotalBankPoint();
			
		}
		
		payCoreLogic = new PaymentCoreLogicImpl();
		
		/**
		 * 先退现金，后退积分
		 *  1. 请求退款金额小于可退金额，全部以现金形式退款
		 *  2. 请求退款金额大于可退金额，先退全部可退现金，剩余部分已积分形式退还
		 */
		if(isNewRule){
			if (actualRefundPoint == 0){ //不需要退还积分
			actualRefundCash = reqRefundAmt;
				if (!refundOrderBean.isQueryOnly()){
					resultBean = payCoreLogic.refundUserPaymentCapital(
							refundOrderBean.getOrderId(),
							Arith.div(Double.parseDouble(String.valueOf(totalCreditPoint)), 1000),
							Arith.div(actualRefundPoint,1000),
							Arith.div(Double.parseDouble(String.valueOf(actualRefundCash)), 1000));
					logInfo = new StringBuffer();
					LogCvt.info(logInfo.append(refundOrderBean.getRefundId())
							.append(" 全部使用现金方式退款： ")
							.append(reqRefundAmt)
							.append(" 退还赠送积分： ")
							.append(totalCreditPoint)
							.toString());
				}
			} else {
				actualRefundCash = reqRefundAmt;
				logInfo = new StringBuffer();
				if (actualRefundPoint > (totalPoint - refundOrderBean.getRefundedPoint())){
					LogCvt.info(logInfo.append("订单可退积分小于请求退款积分，申请退款失败： ").append(refundOrderBean.getRefundId()).toString());
					resultVo.setResultCode(ResultCode.refund_point_insufficient.getCode());
					resultVo.setResultDesc(ResultCode.refund_point_insufficient.getMsg());
					responseVo.setResultVo(resultVo);
					return;
				}
				
				if (!refundOrderBean.isQueryOnly()){
					resultBean = payCoreLogic.refundUserPaymentCapital(
							refundOrderBean.getOrderId(),
							Arith.div(Double.parseDouble(String.valueOf(totalCreditPoint)), 1000),
							Arith.div(actualRefundPoint,1000),
							Arith.div(Double.parseDouble(String.valueOf(actualRefundCash)), 1000));
					
					logInfo = new StringBuffer();
					LogCvt.info(logInfo.append(refundOrderBean.getRefundId()).append(" 退款金额为： ")
							.append(Arith.div(reqRefundAmt,1000)).append(" 退还消费积分为： ")
							.append(Arith.div(actualRefundPoint,1000)).append(" 退还赠送积分： ")
							.append(Arith.div(totalCreditPoint,1000)).toString());
				}
			}
		}else{
			
//			if(refundOrderBean.getTotalCash() > 0){
//				if (reqRefundAmt <= (refundOrderBean.getTotalCash() - refundOrderBean.getRefundedCash())){
//					actualRefundCash = reqRefundAmt;
//					if (!refundOrderBean.isQueryOnly()){
//						resultBean = payCoreLogic.refundUserPaymentCapital(
//								refundOrderBean.getOrderId(),
//								Arith.div(Double.parseDouble(String.valueOf(totalCreditPoint)), 1000),
//								Arith.div(actualRefundPoint,1000),
//								Arith.div(Double.parseDouble(String.valueOf(actualRefundCash)), 1000));
//						logInfo = new StringBuffer();
//						LogCvt.info(logInfo.append(refundOrderBean.getRefundId())
//								.append(" 全部使用现金方式退款： ")
//								.append(reqRefundAmt)
//								.append(" 退还赠送积分： ")
//								.append(totalCreditPoint)
//								.toString());
//					}
//				} else {
//					actualRefundCash = reqRefundAmt;
//					actualRefundPoint = Arith.mul(reqRefundAmt - actualRefundCash - refundOrderBean.getTotalCash(), pointRate);
//					actualRefundCash = (int) (actualRefundCash - actualRefundPoint);
//					logInfo = new StringBuffer();
//					if (actualRefundPoint > (totalPoint - refundOrderBean.getRefundedPoint())){
//						LogCvt.info(logInfo.append("订单可退积分小于请求退款积分，申请退款失败： ").append(refundOrderBean.getRefundId()).toString());
//						resultVo.setResultCode(ResultCode.refund_point_insufficient.getCode());
//						resultVo.setResultDesc(ResultCode.refund_point_insufficient.getMsg());
//						responseVo.setResultVo(resultVo);
//						return;
//					}
//					if (!refundOrderBean.isQueryOnly()){
//						resultBean = payCoreLogic.refundUserPaymentCapital(
//								refundOrderBean.getOrderId(),
//								Arith.div(Double.parseDouble(String.valueOf(totalCreditPoint)), 1000),
//								Arith.div(actualRefundPoint,1000),
//								Arith.div(Double.parseDouble(String.valueOf(actualRefundCash)), 1000));
//						
//						logInfo = new StringBuffer();
//						LogCvt.info(logInfo.append(refundOrderBean.getRefundId()).append(" 退款金额为： ")
//								.append(Arith.div(reqRefundAmt,1000)).append(" 退还消费积分为： ")
//								.append(Arith.div(actualRefundPoint,1000)).append(" 退还赠送积分： ")
//								.append(Arith.div(totalCreditPoint,1000)).toString());
//					}
//				}
//			}else{
//				//纯积分支付，只退积分
//				actualRefundCash = 0;
//				actualRefundPoint = Arith.mul(reqRefundAmt, pointRate);
//				logInfo = new StringBuffer();
//				if (actualRefundPoint > (totalPoint - refundOrderBean.getRefundedPoint())){
//					LogCvt.info(logInfo.append("订单可退积分小于请求退款积分，申请退款失败： ").append(refundOrderBean.getRefundId()).toString());
//					resultVo.setResultCode(ResultCode.refund_point_insufficient.getCode());
//					resultVo.setResultDesc(ResultCode.refund_point_insufficient.getMsg());
//					responseVo.setResultVo(resultVo);
//					return;
//				}
//				if (!refundOrderBean.isQueryOnly()){
//					resultBean = payCoreLogic.refundUserPaymentCapital(
//							refundOrderBean.getOrderId(),
//							Arith.div(Double.parseDouble(String.valueOf(totalCreditPoint)), 1000),
//							Arith.div(actualRefundPoint,1000),
//							Arith.div(Double.parseDouble(String.valueOf(actualRefundCash)), 1000));
//					
//					logInfo = new StringBuffer();
//					LogCvt.info(logInfo.append(refundOrderBean.getRefundId()).append(" 退款金额为： ")
//							.append(Arith.div(reqRefundAmt,1000)).append(" 退还消费积分为： ")
//							.append(Arith.div(actualRefundPoint,1000)).append(" 退还赠送积分： ")
//							.append(Arith.div(totalCreditPoint,1000)).toString());
//				}
//			}
			if (reqRefundAmt <= (refundOrderBean.getTotalCash() - refundOrderBean.getRefundedCash())){
				actualRefundCash = reqRefundAmt;
				actualRefundPoint = 0.0;
				
				if (!refundOrderBean.isQueryOnly()){
					resultBean = payCoreLogic.refundUserPaymentCapital(refundOrderBean.getOrderId(),
							Arith.div(Double.parseDouble(String.valueOf(totalCreditPoint)), 1000), actualRefundPoint,
							Arith.div(Double.parseDouble(String.valueOf(actualRefundCash)), 1000));
					
					logInfo = new StringBuffer();
					LogCvt.info(logInfo.append(refundOrderBean.getRefundId()).append(" 全部使用现金方式退款： ")
							.append(reqRefundAmt).append(" 退还赠送积分： ")
							.append(totalCreditPoint).toString());
				}
			} else {
				actualRefundCash = refundOrderBean.getTotalCash() - refundOrderBean.getRefundedCash();
				actualRefundPoint = Arith.mul(Arith.div((reqRefundAmt - actualRefundCash), 1000), pointRate);
				
				logInfo = new StringBuffer();
				if (actualRefundPoint > (totalPoint - refundOrderBean.getRefundedPoint())){
					LogCvt.info(logInfo.append("订单可退积分小于请求退款积分，申请退款失败： ").append(refundOrderBean.getRefundId()).toString());
					resultVo.setResultCode(ResultCode.refund_point_insufficient.getCode());
					resultVo.setResultDesc(ResultCode.refund_point_insufficient.getMsg());
					responseVo.setResultVo(resultVo);
					return;
				}
				
				if (!refundOrderBean.isQueryOnly()){
					resultBean = payCoreLogic.refundUserPaymentCapital(
							refundOrderBean.getOrderId(),
							Arith.div(Double.parseDouble(String.valueOf(totalCreditPoint)), 1000), actualRefundPoint,
							Arith.div(Double.parseDouble(String.valueOf(actualRefundCash)), 1000));
					
					logInfo = new StringBuffer();
					LogCvt.info(logInfo.append(refundOrderBean.getRefundId()).append(" 退款金额为： ")
							.append(reqRefundAmt).append(" 退还消费积分为： ")
							.append(actualRefundPoint).append(" 退还赠送积分： ")
							.append(totalCreditPoint).toString());
				}
			}
			actualRefundPoint = Arith.mul(actualRefundPoint, 1000);
			
		}
		
		if (!refundOrderBean.isQueryOnly()){
			if (!resultBean.getCode().equals(ResultCode.success.getCode())){
				resultVo.setResultCode(resultBean.getCode());
				resultVo.setResultDesc(resultBean.getMsg());
				responseVo.setResultVo(resultVo);
				monitorService.send(MonitorPointEnum.Order_Refund_Pay_Failed_Count);//退款支付异常次数
			}
			
			payResult = (RefundTempBean) resultBean.getData();
			paymentInfoList = new ArrayList<RefundPaymentInfo>();
			
			// 现金退款支付成功则返回支付流水
			if (actualRefundCash != 0) {
				paymentInfo = new RefundPaymentInfo();
				paymentInfo.setType(PaymentMethod.cash.getCode());
				paymentInfo.setRefundValue(actualRefundCash);
				
				if (null != payResult) {
					paymentInfo.setPaymentId(payResult.getPaymentCashPaymentId());
				}
				
				if (!resultBean.getCode().endsWith(ResultCode.success.getCode())){
					paymentInfo.setResultCode(resultBean.getCode());
					paymentInfo.setResultDesc(resultBean.getMsg());
				}
				
				paymentInfoList.add(paymentInfo);
			}
			
			// 积分退还支付成功则返回支付流水
			if (actualRefundPoint != 0) {
				paymentInfo = new RefundPaymentInfo();
				if (refundOrderBean.getTotalFftPoint() > 0){
					paymentInfo.setType(PaymentMethod.froadPoints.getCode());
				} else {
					paymentInfo.setType(PaymentMethod.bankPoints.getCode());
				}
				paymentInfo.setRefundValue((int)actualRefundPoint);
				
				if (null != payResult) {
					paymentInfo.setPaymentId(payResult.getPaymentPointPaymentId());
				}
				
				if (!resultBean.getCode().endsWith(ResultCode.success.getCode())){
					paymentInfo.setResultCode(resultBean.getCode());
					paymentInfo.setResultDesc(resultBean.getMsg());
				} else {
					paymentInfo.setResultCode(ResultCode.success.getCode());
				}
				
				paymentInfoList.add(paymentInfo);
			}
			
			// 赠送积分扣除支付成功则返回支付流水
			if (totalCreditPoint != 0) {
				paymentInfo = new RefundPaymentInfo();
				paymentInfo.setType(PaymentMethod.creditPoints.getCode());
				paymentInfo.setRefundValue(totalCreditPoint);
				
				if (null != payResult) {
					paymentInfo.setPaymentId(payResult.getPresentPointPaymentId());
					
					if (payResult.getRefundPresentPointState().equals(RefundState.REFUND_SUCCESS)) {
						
						//-------告知订单模块某些商品退款赠送积分成功
						for (Map.Entry<String, String> data : refundPresentPointData.entrySet()) {
							orderSupport.updateRefundPointStatus(refundOrderBean.getClientId(), data.getValue(), data.getKey(), true);
						}
						paymentInfo.setResultCode(ResultCode.success.getCode());
					} else {
						//-------告知订单模块某些商品退款赠送积分失败
						for (Map.Entry<String, String> data : refundPresentPointData.entrySet()) {
							orderSupport.updateRefundPointStatus(refundOrderBean.getClientId(), data.getValue(), data.getKey(), false);
						}
						monitorService.send(MonitorPointEnum.Order_Refund_Return_Creditpoint_Failed_Count);//退款时扣回赠送积分异常次数
					}
				}
				
				paymentInfoList.add(paymentInfo);
			}
			refundOrderBean.setPayInfoList(paymentInfoList);
		}
		
		responseVo.getRefundInfo().setRefundAmount(Arith.div(actualRefundCash, 1000));
		responseVo.getRefundInfo().setRefundPoints(Arith.div(actualRefundPoint,1000));
	}

	@Override
	public RefundTicketsResponseVo refundTickets(
			RefundTicketsRequestVo requestVo) {
		RefundTicketsResponseVo responseVo = null;
		Map<String, Integer> productMap = null;
		Map<String, Map<String, Integer>> subOrderMap = null;
		Map<String, String> clientMap = null;
		RefundRequestVo refundRequest = null;
		RefundTicketVo refundTicketVo = null;
		RefundProductVo refundProductVo = null;
		List<RefundProductVo> refundProductList = null;
		Iterator <String> subOrderIt = null;
		Iterator <String> productIt = null;
		String subOrderId = null;
		String productId = null;
		int quantity = 0;
		String clientId = null;
		
		try {
			LogCvt.info(new StringBuffer("系统退款：").append(JSonUtil.toJSonString(requestVo)).toString());
			
			responseVo = new RefundTicketsResponseVo();
			subOrderMap = new HashMap<String, Map<String,Integer>>();
			clientMap = new HashMap<String, String>();
			
			if (requestVo.getTicketListSize() > 0) {
				// 根据子订单+商品划分退款
				for (int i = 0; i < requestVo.getTicketListSize(); i++) {
					refundTicketVo = requestVo.getTicketList().get(i);
					subOrderId = refundTicketVo.getSuborderId();
					productId = refundTicketVo.getProductId();
					quantity = refundTicketVo.getQuantity();
					clientId = refundTicketVo.getClientId();
					
					if (subOrderMap.get(subOrderId) != null) {
						productMap = subOrderMap.get(subOrderId);
						
						if (null == productMap || productMap.isEmpty()) {
							productMap = new HashMap<String, Integer>();
							productMap.put(productId, quantity);
							subOrderMap.put(subOrderId, productMap);
						} else {
							Integer quantityForMap = productMap.get(productId);
							productMap.put(refundTicketVo.getProductId(), null == quantityForMap ? quantity : quantityForMap + quantity);
							subOrderMap.put(subOrderId, productMap);
						}
					} else {
						productMap = new HashMap<String, Integer>();
						productMap.put(productId, quantity);
						subOrderMap.put(subOrderId, productMap);
					}
					clientMap.put(subOrderId, clientId);
				}
			}
			
			// 按子订单+商品退款
			subOrderIt = subOrderMap.keySet().iterator();
			refundRequest = new RefundRequestVo();
			refundProductVo = new RefundProductVo();
			refundProductList = new ArrayList<RefundProductVo>();
			while (subOrderIt.hasNext()) {
				subOrderId = subOrderIt.next();
				refundRequest.setSubOrderId(subOrderId);
				
				productIt = subOrderMap.get(subOrderId).keySet().iterator();
				refundProductList.clear();
				while (productIt.hasNext()) {
					productId = productIt.next();
					refundProductVo.setProductId(productId);
					refundProductVo.setQuantity(subOrderMap.get(subOrderId).get(productId));
					refundProductList.add(refundProductVo);
				}
				refundRequest.setProductList(refundProductList);
				refundRequest.setClientId(clientMap.get(subOrderId));
				
				// 调用退款接口
				refund(RefundResource.SYSTEM_REFUND.getCode(), refundRequest);
			}
			
			responseVo.setResultVo(new ResultVo(ResultCode.success.getCode(), ""));
		} catch (Exception e) {
			LogCvt.error(new StringBuffer("团购提货码过期退款失败：").append(requestVo.toString()).toString(), e);
			responseVo.setResultVo(new ResultVo(ResultCode.failed.getCode(), e.getMessage()));
		}
		
		responseVo.setFailedList(new ArrayList<RefundTicketVo>());
		responseVo.setSuccessList(new ArrayList<RefundTicketVo>());
		
		return responseVo;
	}
	
	/**
	 * 检查预售商品是否超出预售期
	 * 
	 * @param subOrder
	 * @param refundRequestVo
	 * @return
	 */
	private boolean isExceedPresellTime(SubOrderMongo subOrder, RefundRequestVo refundRequestVo){
		boolean isExceeded = false;
		List<RefundProductVo> productVoList = null;
		RefundProductVo productVo = null;
		Map<String, String> redisMap = null;
		long curTime = 0l;
		long presellStartTime = 0l;
		long presellEndTime = 0l;
		
		curTime = System.currentTimeMillis();
		productVoList = refundRequestVo.getProductList();
		for (int i = 0; i < productVoList.size(); i++){
			productVo = productVoList.get(i);
			redisMap = commonLogic.getProductRedis(subOrder.getClientId(), subOrder.getMerchantId(), productVo.getProductId());
			if (redisMap != null && !redisMap.isEmpty()){
				presellStartTime = Long.valueOf(redisMap.get(FieldMapping.START_TIME.getMongoField()));
				presellEndTime = Long.valueOf(redisMap.get(FieldMapping.END_TIME.getMongoField()));
				
				if ((curTime < presellStartTime) || (curTime > presellEndTime)) {
					isExceeded = true;
					break;
				}
			}
		}
		
		return isExceeded;
	}
	
	/**
	 * 更新子订单退款状态
	 * 
	 * @param subOrder
	 */
	private void updateSubOrderStatus(SubOrderMongo subOrder) {
		OrderLogic orderLogic = null;
		
		try {
			orderLogic = new OrderLogicImpl();
			
			orderLogic.updateOrderAfterRefund(subOrder.getClientId(),
					subOrder.getOrderId(), subOrder.getSubOrderId(),
					SubOrderRefundState.REFUND_PROCESSING.getCode(), false);
		} catch (Exception e){
			LogCvt.error(new StringBuffer("退款更新子订单退款中状态失败：").append(subOrder.getSubOrderId()).toString(), e);
		}
	}

	@Override
	public RefundResponseVo refundVipStatus(String orderId,String clientId,long memberCode,String option) {
		RefundResponseVo refundResponseVo = new RefundResponseVo();
		ResultVo resultVo = new ResultVo();
		OrderMongo order = orderSupport.getOrderById(clientId, orderId);
		
		//校验订单类型相关
		if(order == null){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("退款会员资格所购买的订单不存在");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		if(1 != order.getIsVipOrder()){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("订单类型错误");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		//校验订单类型相关
		
		Map<String, String> vipOrders = RedisCommon.getUserVipOrderInfoRedis(clientId,memberCode);
		if(vipOrders == null || vipOrders.size() == 0){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("退款会员资格所购买的订单不存在");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		String vipCount = vipOrders.get("open_vip_count");
		if(vipCount == null || vipCount.trim().length() == 0){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("您还未购买会员资格，无法进行退款");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		int count = Integer.parseInt(vipCount);
		if(count > 1){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("您已经体验过一个月会员，不能申请退款");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		if(!"6".equals(order.getOrderStatus())){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("该订单状态不允许退款");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		Date date = TimeHelper.parseDate(vipOrders.get("due_date"), TimeType.yyyyMMddHHmmss);
		date = TimeHelper.offsetDate(date, Calendar.YEAR, -1); //推算出开通的时间
		date = TimeHelper.offsetDate(date, Calendar.DAY_OF_YEAR, 30); //推算出体验结束时间
		if(new Date().after(date)){
			LogCvt.info("当前系统时间大于体检结束时间 体验结束时间: " + TimeHelper.formatDate(TimeType.DEFAULT, date));
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("您已经体验过一个月会员，不能申请退款");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		
		//校验该订单是否曾经发起过退款或者是退款已经完毕/退款中等状态
		List<RefundHistory> refundHistorys = new RefundSupportImpl().findRefundHistoryList(orderId);
		if(refundHistorys != null && refundHistorys.size() > 0){
			int refunded = 0;
			int refunding = 0;
			for (RefundHistory refundHistory : refundHistorys) {
				if(RefundState.REFUND_SUCCESS.getCode().equals(refundHistory.getRefundState())){
					refunded ++;
				}
				if(RefundState.REFUND_PROCESSING.getCode().equals(refundHistory.getRefundState())){
					refunding ++;
				}
			}
			if(refunded > 0){//曾经发起过退款
				LogCvt.info("曾经发起过退款");
				resultVo.setResultCode("9999");
				resultVo.setResultDesc("您已经体验过一个月会员，不能申请退款");
				refundResponseVo.setResultVo(resultVo);
				return refundResponseVo;
			}
			if(refunding > 0){//有正在处理的退款
				resultVo.setResultCode("9999");
				resultVo.setResultDesc("您有正在处理的退款申请，当前不能再次发起退款");
				refundResponseVo.setResultVo(resultVo);
				return refundResponseVo;
			}
		}
		
		if(option == null || option.equals("1")){
			resultVo.setResultDesc("可发起退款");
			resultVo.setResultCode("0000");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		
		boolean flag = new AWIPSThirdpartyImpl().cancelVIPOrder(memberCode, com.froad.util.payment.BaseSubassembly.acquireBankOrg(order.getClientId()));
		LogCvt.info("取消会员系统VIP资格flag: " + flag);
		if(!flag){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("取消会员失败，请稍后重试");
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		
		
		ResultBean resultBean = new com.froad.logic.impl.payment.RefundLogicImpl().proxyUserToAutoRefundAllUserPay(order, false, ProxyRefundType.VIP_ORDER_REFUND, "会员资格退款");
		if(!EsyT.isResultBeanSuccess(resultBean)){
			resultVo.setResultCode("9999");
			resultVo.setResultDesc(resultBean.getMsg());
			refundResponseVo.setResultVo(resultVo);
			return refundResponseVo;
		}
		
		String msg = null;
		if(com.froad.util.payment.BaseSubassembly.isPurePointsPayment(order.getPaymentMethod())){
			OrderMongo orderUpdate = new OrderMongo();
			orderUpdate.setOrderId(order.getOrderId());
			orderUpdate.setClientId(order.getClientId());
			orderUpdate.setOrderStatus(OrderStatus.sysclosed.getCode());
			new OrderLogicImpl().updateOrderForPay(orderUpdate);
			RedisCommon.updateUserVIPOrderRedis(order.getClientId(),order.getMemberCode(),false);
			
			new RefundSupportImpl().findAndModifyByDBObject(new BasicDBObject("_id", (String)resultBean.getData()), new BasicDBObject("$set",new BasicDBObject("refund_time",new Date().getTime())));
			msg = "退款成功";
		}else{
			msg = "退款申请成功";
		}
		
		RefundHistory refundHistory = new RefundSupportImpl().getByRefundId((String)resultBean.getData());
		
		
		resultVo.setResultCode(Const.SUCCESS_CODE);
		resultVo.setResultDesc(msg);
		refundResponseVo.setResultVo(resultVo);
		RefundInfoVo refundInfo = new RefundInfoVo();
		refundInfo.setRefundId((String)resultBean.getData());
		
		List<RefundPaymentInfo> payInf = refundHistory.getPaymentInfo();
		for (RefundPaymentInfo refundPaymentInfo : payInf) {
			if("1".equals(refundPaymentInfo.getType())){//现金
				refundInfo.setRefundAmount(Arith.div(refundPaymentInfo.getRefundValue(),1000));
			}else{
				refundInfo.setRefundPoints(Arith.div(refundPaymentInfo.getRefundValue(),1000));
			}
		}
		
		refundResponseVo.setRefundInfo(refundInfo);
		return refundResponseVo;
	}


	@Override
	public RefundResponseVo doRefundOfBoutiqueBoss(String subOrderId,String refundReason, String productId, int quantity,String clientId) {
		SubOrderMongo subMongo = orderSupport.getSubOrderBySubOrderId(clientId, subOrderId);
		if(subMongo == null){
			RefundResponseVo res = new RefundResponseVo();
			ResultVo resultVo = new ResultVo();
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("子订单信息不存在");
			res.setResultVo(resultVo);
			return res;
		}
		if(!SubOrderType.boutique.getCode().equals(subMongo.getType())){
			RefundResponseVo res = new RefundResponseVo();
			ResultVo resultVo = new ResultVo();
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("非精品商城订单不允许发起退款");
			res.setResultVo(resultVo);
			return res;
		}
		RefundRequestVo refundRequestVo = new RefundRequestVo();
		refundRequestVo.setOption("2");
		refundRequestVo.setClientId(clientId);
		refundRequestVo.setOrderId(subMongo.getOrderId());
		refundRequestVo.setSubOrderId(subOrderId);
		
		List<RefundProductVo> proList = new ArrayList<RefundProductVo>();
		RefundProductVo pro = new RefundProductVo();
		pro.setProductId(productId);
		pro.setQuantity(quantity);
		proList.add(pro);
		
		refundRequestVo.setProductList(proList);
		refundRequestVo.setReason(refundReason);

		return refund("1", refundRequestVo);
	}
}
