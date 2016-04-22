package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderRequestType;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrderLogic;
import com.froad.logic.PaymentLogic;
import com.froad.logic.SettlementLogic;
import com.froad.logic.ShoppingCartLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.impl.PaymentLogicImpl;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.logic.impl.ShoppingCartLogicImpl;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.logic.impl.order.OrderLogicHelper;
import com.froad.logic.impl.payment.DoPayTrailingImpl;
import com.froad.logic.impl.payment.PaymentCoreLogicImpl;
import com.froad.logic.payment.DoPayTrailing;
import com.froad.logic.payment.PaymentCoreLogic;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.OrderInfo;
import com.froad.po.OrderQueryCondition;
import com.froad.po.Store;
import com.froad.po.base.PageEntity;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.order.OrderResultData;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.impl.validation.OrderValidation;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.AddMerchantVo;
import com.froad.thrift.vo.order.AddOrderVoReq;
import com.froad.thrift.vo.order.AddOrderVoRes;
import com.froad.thrift.vo.order.AddPrefPayOrderReq;
import com.froad.thrift.vo.order.AddPrefPayOrderRes;
import com.froad.thrift.vo.order.AddProductVo;
import com.froad.thrift.vo.order.AddQrcodeOrderVoReq;
import com.froad.thrift.vo.order.AddQrcodeOrderVoRes;
import com.froad.thrift.vo.order.AddVIPOrderVoReq;
import com.froad.thrift.vo.order.AddVIPOrderVoRes;
import com.froad.thrift.vo.order.CashierVoReq;
import com.froad.thrift.vo.order.CashierVoRes;
import com.froad.thrift.vo.order.CloseOrderVoReq;
import com.froad.thrift.vo.order.CloseOrderVoRes;
import com.froad.thrift.vo.order.DeleteOrderVoReq;
import com.froad.thrift.vo.order.DeleteOrderVoRes;
import com.froad.thrift.vo.order.DeliverInfoDetailVo;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoReq;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoRes;
import com.froad.thrift.vo.order.GetOrderByQrcodeVoReq;
import com.froad.thrift.vo.order.GetOrderByQrcodeVoRes;
import com.froad.thrift.vo.order.GetOrderDetailVoReq;
import com.froad.thrift.vo.order.GetOrderDetailVoRes;
import com.froad.thrift.vo.order.GetOrderPaymentResultVoReq;
import com.froad.thrift.vo.order.GetOrderPaymentResultVoRes;
import com.froad.thrift.vo.order.GetOrderSummaryVoReq;
import com.froad.thrift.vo.order.GetOrderSummaryVoRes;
import com.froad.thrift.vo.order.GetPointExchangeDetailVoReq;
import com.froad.thrift.vo.order.GetPointExchangeDetailVoRes;
import com.froad.thrift.vo.order.GetPointExchangeListVoReq;
import com.froad.thrift.vo.order.GetPointExchangeListVoRes;
import com.froad.thrift.vo.order.GetQrcodeOrderDetailVoReq;
import com.froad.thrift.vo.order.GetQrcodeOrderDetailVoRes;
import com.froad.thrift.vo.order.GetQrcodeOrderSummaryVoReq;
import com.froad.thrift.vo.order.GetQrcodeOrderSummaryVoRes;
import com.froad.thrift.vo.order.GetSubOrderProductVoReq;
import com.froad.thrift.vo.order.GetSubOrderProductVoRes;
import com.froad.thrift.vo.order.GetSubOrderVoReq;
import com.froad.thrift.vo.order.GetSubOrderVoRes;
import com.froad.thrift.vo.order.GetVipDiscountVoReq;
import com.froad.thrift.vo.order.GetVipDiscountVoRes;
import com.froad.thrift.vo.order.OrderDetailVo;
import com.froad.thrift.vo.order.OrderSummaryVo;
import com.froad.thrift.vo.order.PointExchangeVo;
import com.froad.thrift.vo.order.ProductDetailVo;
import com.froad.thrift.vo.order.QrcodeOrderDetailVo;
import com.froad.thrift.vo.order.QrcodeOrderSummaryVo;
import com.froad.thrift.vo.order.ReceiptOrderReq;
import com.froad.thrift.vo.order.RefundPayingOrderVoReq;
import com.froad.thrift.vo.order.RefundPayingOrderVoRes;
import com.froad.thrift.vo.order.ShippingOrderVoReq;
import com.froad.thrift.vo.order.ShippingOrderVoRes;
import com.froad.thrift.vo.order.StoreVoReq;
import com.froad.thrift.vo.order.StoreVoRes;
import com.froad.thrift.vo.order.SubOrderDetailVo;
import com.froad.thrift.vo.order.UpdateSubOrderLogisticVoReq;
import com.froad.thrift.vo.order.UpdateSubOrderLogisticVoRes;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;
import com.froad.util.PropertiesUtil;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: OrderServiceImpl
 * @Description: 订单管理外部接口实现
 * @Author: zhangkai
 * @Date: 2015年3月17日 上午10:00:49
 */
public class OrderServiceImpl extends BizMonitorBaseService implements OrderService.Iface {
	
	public OrderServiceImpl() {}
	
	public OrderServiceImpl(String name, String version) {
		super(name, version);
	}
	
	private OrderLogic orderLogic = new OrderLogicImpl();

	private ShoppingCartLogic shoppingCartLogic = new ShoppingCartLogicImpl();
	
	private OrderValidation orderValidation = new OrderValidation();
	
	private PaymentLogic paymentLogic = new PaymentLogicImpl();
	
	private PaymentCoreLogic paymentCoreLogic = new PaymentCoreLogicImpl();
	
	private MonitorService monitorService = new MonitorManager();
    /**4
     * 结算信息操作
     */
    private final SettlementLogic settlementLogic = new  SettlementLogicImpl();
    
    
    private CommonLogic commonLogic = new CommonLogicImpl();
    
    
    private DoPayTrailing doPayTrailiing = new DoPayTrailingImpl();
       
    
    /**
     * 获取精品商城创建订单相关的供货商信息
     * isBoutiqueAddOrderVoReq:(这里用一句话描述这个方法的作用).
     *
     * 2015年12月3日 上午11:12:25
     * @param addOrderVoReq
     * @return
     *
     */
    private Map<String, List<AddMerchantVo>> isBoutiqueAddOrderVoReq(AddOrderVoReq addOrderVoReq) {
    	Map<String, List<AddMerchantVo>> merListMap = new HashMap<String, List<AddMerchantVo>>();
    	List<AddMerchantVo> boutiqueMerList = new ArrayList<AddMerchantVo>();
    	List<AddMerchantVo> noBoutiqueMerList = new ArrayList<AddMerchantVo>();
    	
    	List<AddMerchantVo> allMerList = addOrderVoReq.getAddMerchantVoList();
    	if(allMerList != null && allMerList.size() > 0) {
    		for(AddMerchantVo m : allMerList) {
    			Map<String, String>  providerMap = commonLogic.getProviderRedis(m.getMerchantId());
    			if(providerMap != null && !providerMap.isEmpty()) {
    				boutiqueMerList.add(m);
    			} else {
    				noBoutiqueMerList.add(m);
    			}
    		}
    	}
    	
    	merListMap.put("boutique", boutiqueMerList);
    	merListMap.put("noboutique", noBoutiqueMerList);
    	return merListMap;
    }

	@Override
	public AddOrderVoRes addOrder(AddOrderVoReq addOrderVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[订单创建]-创建订单开始......");
		LogCvt.info("请求参数：" + JSON.toJSONString(addOrderVoReq,true));
		AddOrderVoRes addOrderVoRes = new AddOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("订单创建成功");
		
		/**-------------------------------start-----------------------------------------*/
		//分离精品商城商户和其他商户信息
		Map<String, List<AddMerchantVo>> merListMap = isBoutiqueAddOrderVoReq(addOrderVoReq);
		
		List<AddMerchantVo> noBoutiqueMerList = merListMap.get("noboutique");
		//订单中是否有非精品商城的商品购买
		ResultBean validResult = null;
		OrderInfo orderInfo = null;
		boolean isHasNoboutique = false;
		if(noBoutiqueMerList.size() > 0) {
			isHasNoboutique = true;
			addOrderVoReq.setAddMerchantVoList(noBoutiqueMerList);
			validResult = orderValidation.validateAddOrderParam(addOrderVoReq);
			orderInfo = (OrderInfo) validResult.getData();
		}
		/**-------------------------------end-----------------------------------------*/
		/**
		 * 1.参数校验（商户、商品、门店、配送、活动、限购、vip等有效性校验 、库存校验、 VO转PO）
		 */
		/*ResultBean validResult = null;
		OrderInfo orderInfo = null;
		if(isHasNoboutique) {
			validResult = orderValidation.validateAddOrderParam(addOrderVoReq);
			orderInfo = (OrderInfo) validResult.getData();
		}*/
		
		/**-----------------------------start-------------------------------------------*/
		List<AddMerchantVo> boutiqueMerList = merListMap.get("boutique");
		if(boutiqueMerList.size() > 0) {
			String orderId = null;
			addOrderVoReq.setAddMerchantVoList(boutiqueMerList);
			if(isHasNoboutique) {//如果有非精品商城商品的下单
				orderId = orderInfo.getOrder().getOrderId();
				ResultBean validBoutiqueRb = orderValidation.validateAddBoutiqueOrderParam(orderId, isHasNoboutique, addOrderVoReq);
				OrderInfo boutiqueOrderInfo = (OrderInfo)validBoutiqueRb.getData();
				orderInfo.getMerchantReturnVoList().addAll(boutiqueOrderInfo.getMerchantReturnVoList());
				
				if(!StringUtils.equals(ResultCode.success.getCode(), validBoutiqueRb.getCode())){
					LogCvt.error(validBoutiqueRb.getMsg());
					resultVo.setResultCode(validBoutiqueRb.getCode());
					resultVo.setResultDesc(validBoutiqueRb.getMsg());
					addOrderVoRes.setResultVo(resultVo);
					if(EmptyChecker.isNotEmpty(boutiqueOrderInfo)){
						addOrderVoRes.setMerchantReturnVoList(orderInfo.getMerchantReturnVoList());
					}
					LogCvt.info("[订单创建]-创建订单失败！耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderVoRes,true));
					return addOrderVoRes;
				} else {
					if(StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())) {
						orderInfo.getShopingListReq().addAll(boutiqueOrderInfo.getShopingListReq());
						orderInfo.getStoreList().addAll(boutiqueOrderInfo.getStoreList());
						orderInfo.getSubOrderList().addAll(boutiqueOrderInfo.getSubOrderList());
						
						//合并价格、积分；
						OrderMongo nobouOrder = orderInfo.getOrder();
						OrderMongo bouOrder = boutiqueOrderInfo.getOrder();
						nobouOrder.setTotalPrice(nobouOrder.getTotalPrice() + bouOrder.getTotalPrice());
						nobouOrder.setVipDiscount(nobouOrder.getVipDiscount() + bouOrder.getVipDiscount());
						if (nobouOrder.getBankPoints() == null && bouOrder.getBankPoints() != null) {
							nobouOrder.setBankPoints(bouOrder.getBankPoints());
						}
						
						if(nobouOrder.getFftPoints() == null && bouOrder.getFftPoints() != null) {
							nobouOrder.setFftPoints(bouOrder.getFftPoints());
						}
						orderInfo.setOrder(nobouOrder);
					}
				}
			} else {//如果只有精品商城商品购买
				validResult = orderValidation.validateAddBoutiqueOrderParam(orderId, isHasNoboutique, addOrderVoReq);
				orderInfo = (OrderInfo)validResult.getData();
			}
		}
		
		/**------------------------------end------------------------------------------*/
		
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			addOrderVoRes.setResultVo(resultVo);
			if(EmptyChecker.isNotEmpty(orderInfo)){
				addOrderVoRes.setMerchantReturnVoList(orderInfo.getMerchantReturnVoList());
			}
			LogCvt.info("[订单创建]-创建订单失败！耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderVoRes,true));
			return addOrderVoRes;
		}
		
		//线下积分兑换-支付失败
		if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode()) 
				&& EmptyChecker.isNotEmpty(orderInfo.getOfflinePayFlag()) && orderInfo.getOfflinePayFlag() == false){
		    //创建订单
			ResultBean addOfflineOrderResult = orderLogic.addOrder(orderInfo.getOrder(),orderInfo.getSubOrderList());
			if(!StringUtils.equals(ResultCode.success.getCode(), addOfflineOrderResult.getCode())){
				orderValidation.sendMonitor(orderInfo.getSubOrderList(), false);
				LogCvt.error(addOfflineOrderResult.getMsg());
				resultVo.setResultCode(addOfflineOrderResult.getCode());
				resultVo.setResultDesc(addOfflineOrderResult.getMsg());
				addOrderVoRes.setResultVo(resultVo);
				return addOrderVoRes;
			}
		
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("积分支付失败");
			
			addOrderVoRes.setResultVo(resultVo);
			addOrderVoRes.setOrderId(orderInfo.getOrder().getOrderId());
			addOrderVoRes.setTotalPrice(Arith.div(orderInfo.getOrder().getTotalPrice(), 1000));
			addOrderVoRes.setMerchantReturnVoList(orderInfo.getMerchantReturnVoList());
			LogCvt.info("[订单创建]-线下积分兑换-订单创建成功，积分支付失败！总耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
			return addOrderVoRes;
		}
		
		//是否参与了营销活动
		boolean isJoinMarketActive = false;
		//是否纯红包支付
		boolean isRedPacketOrder = false;
		
		/**
		 * 检查营销活动(标准版：只有团购和预售有)
		 */
		if(!StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode()) && !StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.online_point_order.getCode()) && orderInfo.getIsJoinMarketActive()){
			
			ResultBean activeResult = orderLogic.checkOrderForMarketActive(orderInfo.getOrder(),orderInfo.getSubOrderList());
			
			if(!activeResult.isSuccess()){
				
				orderValidation.sendMonitor(orderInfo.getSubOrderList(), false);
				
				LogCvt.error(activeResult.getMsg());
				resultVo.setResultCode(activeResult.getCode());
				resultVo.setResultDesc(activeResult.getMsg());
				addOrderVoRes.setResultVo(resultVo);
				return addOrderVoRes;
			}
			
			//纯红包支付时
			OrderResultData orderResultData = (OrderResultData) activeResult.getData();
			if(EmptyChecker.isNotEmpty(orderResultData)){
				isRedPacketOrder = orderResultData.isRedPacketOrder();
			}
			isJoinMarketActive = true;
		}
		
		/**
		 * 订单创建成功后，创建营销订单，更新大订单营销ID
		 */
		if(!StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode()) && !StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.online_point_order.getCode()) && orderInfo.getIsJoinMarketActive()){
			ResultBean marketResult = orderLogic.createMarketOrderForMarketActive(orderInfo.getOrder(),orderInfo.getSubOrderList());
			if(!marketResult.isSuccess()){
				LogCvt.error(marketResult.getMsg());
				resultVo.setResultCode(marketResult.getCode());
				resultVo.setResultDesc(marketResult.getMsg());
				addOrderVoRes.setResultVo(resultVo);
				return addOrderVoRes;
			}
			/*orderLogic.updateOrderForMarketActive(orderInfo.getOrder());*/
		}
		
		/**
		 * 2.减库存
		 */
		ResultBean freezeStoreResult = orderLogic.reduceStore(orderInfo.getStoreList());
		if(!StringUtils.equals(ResultCode.success.getCode(), freezeStoreResult.getCode())){
			
			orderValidation.sendMonitor(orderInfo.getSubOrderList(), false);
			
			orderLogic.createOrderFailureGoBackForMarketActive(orderInfo.getOrder(),orderInfo.getSubOrderList(), isJoinMarketActive);
			
			LogCvt.error(freezeStoreResult.getMsg());
			resultVo.setResultCode(freezeStoreResult.getCode());
			resultVo.setResultDesc(freezeStoreResult.getMsg());
			addOrderVoRes.setResultVo(resultVo);
			LogCvt.info("[订单创建]-创建订单失败！耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderVoRes,true));
			return addOrderVoRes;
		}
		
		/**
		 * 3.插入订单表和子订单表
		 */
		ResultBean addOrderResult = orderLogic.addOrder(orderInfo.getOrder(),orderInfo.getSubOrderList());
		if(!StringUtils.equals(ResultCode.success.getCode(), addOrderResult.getCode())){
			
			orderValidation.sendMonitor(orderInfo.getSubOrderList(), false);
			
			orderLogic.createOrderFailureGoBackForMarketActive(orderInfo.getOrder(),orderInfo.getSubOrderList(), isJoinMarketActive);
			
			LogCvt.error(addOrderResult.getMsg());
			resultVo.setResultCode(addOrderResult.getCode());
			resultVo.setResultDesc(addOrderResult.getMsg());
			addOrderVoRes.setResultVo(resultVo);
			
			//插入失败，还库存
			ResultBean freeStoreResult = orderLogic.increaseStore(orderInfo.getStoreList());
			if(!StringUtils.equals(ResultCode.success.getCode(), freeStoreResult.getCode())){
				LogCvt.error(freeStoreResult.getMsg());
				resultVo.setResultCode(freeStoreResult.getCode());
				resultVo.setResultDesc(freeStoreResult.getMsg());
				addOrderVoRes.setResultVo(resultVo);
				LogCvt.info("[订单创建]-创建订单失败！耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderVoRes,true));
				return addOrderVoRes;
			}
			
			return addOrderVoRes;
		}
		
		/**
		 * 4.订单创建完成，将库存更新到mysql
		 */
		ResultBean updateProductStoreResult = orderLogic.updateProductStore(orderInfo.getStoreList());
		if(!StringUtils.equals(ResultCode.success.getCode(), updateProductStoreResult.getCode())){
			LogCvt.error("[订单创建]-警告：更新库存到Mysql失败，原因：" + updateProductStoreResult.getMsg());
			monitorService.send(MonitorPointEnum.Order_Createupdatestore_Failed_Count);
			orderValidation.sendMonitor(orderInfo.getSubOrderList(), false);
		}
		
		/**
		 * 5.订单创建完成清空购物车
		 */
		ResultBean clearResult = shoppingCartLogic.clearOrderShoppingCart(orderInfo.getShopingListReq());
		if(!StringUtils.equals(ResultCode.success.getCode(), clearResult.getCode())){
			LogCvt.info("[订单创建]-清空购物车失败");
			LogCvt.error(clearResult.getMsg());
			
			orderLogic.createOrderFailureGoBackForMarketActive(orderInfo.getOrder(),orderInfo.getSubOrderList(), isJoinMarketActive);
			
			resultVo.setResultCode(clearResult.getCode());
			resultVo.setResultDesc(clearResult.getMsg());
			addOrderVoRes.setResultVo(resultVo);
			LogCvt.info("[订单创建]-创建订单失败！耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderVoRes,true));
			return addOrderVoRes;
		}

		/**
		 * 6.订单创建完成，创建订单缓存、订单创建时间缓存到redis
		 */
		orderLogic.addOrderRedis(orderInfo.getOrder(),orderInfo.getSubOrderList());
		
		//线下积分兑换-支付成功，更新商品销售数量;纯红包支付订单，更新商品销售量
		if((StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode()) 
				&& EmptyChecker.isNotEmpty(orderInfo.getOfflinePayFlag()) && orderInfo.getOfflinePayFlag() == true) || isRedPacketOrder){
			ResultBean sellCountResult = orderLogic.updateProductSellCount(orderInfo.getOrder());
			if(!sellCountResult.isSuccess()){
				
				monitorService.send(MonitorPointEnum.Order_Createupdatesellcount_Failed_Count);
				orderValidation.sendMonitor(orderInfo.getSubOrderList(), false);
				
				LogCvt.error("[订单创建]-线下积分兑换-更新商品销售数量失败，原因：" + sellCountResult.getMsg());
				resultVo.setResultCode(sellCountResult.getCode());
				resultVo.setResultDesc(sellCountResult.getMsg());
				addOrderVoRes.setResultVo(resultVo);
				LogCvt.info("[订单创建]-创建订单失败！耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderVoRes,true));
				return addOrderVoRes;
			}
		}
		
		//纯红包支付订单，调营销平台更新营销订单接口,调用支付后逻辑
		if(isRedPacketOrder){
//			orderLogic.updateMarketOrder(orderInfo.getOrder(),true);
			ResultBean doPayResult = doPayTrailiing.doPaySuccess(orderInfo.getOrder());
			OrderLogger.info("订单模块", "面对面创建订单", "纯红包支付订单，调用支付成功后处理逻辑处理结果："+JSON.toJSONString(doPayResult), null);
		}
		
		//大数据平台-调用日志
		if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode())) {
			if(EmptyChecker.isNotEmpty(orderInfo.getOfflinePayFlag()) && orderInfo.getOfflinePayFlag() == true){
				orderLogic.createOrderLog(orderInfo.getOrder(),orderInfo.getSubOrderList(), "ORDERADD");
				orderLogic.createOrderLog(orderInfo.getOrder(),orderInfo.getSubOrderList(), "ORDERPAYSUCCESS");
			}else{
				orderLogic.createOrderLog(orderInfo.getOrder(),orderInfo.getSubOrderList(), "ORDERADD");
				orderLogic.createOrderLog(orderInfo.getOrder(),orderInfo.getSubOrderList(), "ORDERMODIFY");
			}
		}else{
			orderLogic.createOrderLog(orderInfo.getOrder(),orderInfo.getSubOrderList(), "ORDERADD");
		}
		
		
		orderValidation.sendMonitor(orderInfo.getSubOrderList(), true);
		
		addOrderVoRes.setResultVo(resultVo);
		addOrderVoRes.setOrderId(orderInfo.getOrder().getOrderId());
		addOrderVoRes.setTotalPrice(Arith.div(orderInfo.getOrder().getTotalPrice(), 1000));
		addOrderVoRes.setMerchantReturnVoList(orderInfo.getMerchantReturnVoList());
		addOrderVoRes.setVipDiscount(Arith.div(orderInfo.getOrder().getVipDiscount(), 1000));
		addOrderVoRes.setFroadPoint(addOrderVoReq.getFftPoint());
		addOrderVoRes.setBankPoint(addOrderVoReq.getBankPoint());
		OrderResultData orderResultData = OrderLogicHelper.getCashAfterOrderCreate(orderInfo.getOrder(),addOrderVoReq,isRedPacketOrder);
		addOrderVoRes.setCash(orderResultData.getCash());
		addOrderVoRes.setIsNeedCash(orderResultData.getIsNeedCash());
		LogCvt.info("[订单创建]-订单创建成功！总耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderVoRes,true));
		return addOrderVoRes;
	}
	
	/**
	 * 数据拷贝：子订单信息--订单确认信息
	 * @param subOrderList 子订单信息
	 * @return
	 */
	public List<SubOrderDetailVo> DataCopyForOrderConfirm(List<SubOrderMongo> subOrderList){
		List<SubOrderDetailVo> subOrderConfirmList = new ArrayList<SubOrderDetailVo>();//子订单确认信息
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrderMongo : subOrderList){
				SubOrderDetailVo subOrderDetailVo = new SubOrderDetailVo();
				subOrderDetailVo.setSubOrderId(subOrderMongo.getSubOrderId());
				subOrderDetailVo.setMerchantId(subOrderMongo.getMerchantId());
				subOrderDetailVo.setMerchantName(subOrderMongo.getMemberName());
				subOrderDetailVo.setType(subOrderMongo.getType());//子订单类型
				double subTotalMoney = 0.00;
				List<ProductDetailVo> productDetailVoList = new ArrayList<ProductDetailVo>();
				if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
					for(ProductMongo productMongo : subOrderMongo.getProducts()){
						//商品确认详情
						ProductDetailVo productDetailVo = new ProductDetailVo();
						productDetailVo.setProductId(productMongo.getProductId());
						productDetailVo.setProductName(productMongo.getProductName());
						productDetailVo.setProductImage(productMongo.getProductImage());
						productDetailVo.setMoney(productMongo.getMoney());
						productDetailVo.setVipMoney(productMongo.getVipMoney());
						productDetailVo.setQuantity(productMongo.getQuantity());
						productDetailVo.setVipQuantity(productMongo.getVipQuantity());
						double productTotalMoney = Arith.div(productMongo.getMoney(), 1000)*productMongo.getQuantity()+Arith.div(productMongo.getVipMoney(), 1000)*productMongo.getVipQuantity();
						productDetailVo.setTotalMoney(productTotalMoney);
						subTotalMoney += productTotalMoney;
						productDetailVoList.add(productDetailVo);
					}
				}
				subOrderDetailVo.setProductDetailVoList(productDetailVoList);
				subOrderDetailVo.setSubTotalMoney(subTotalMoney);
				subOrderConfirmList.add(subOrderDetailVo);
			}
		}
		return subOrderConfirmList;
	}

	@Override
	public GetOrderSummaryVoRes getOrderList(
			GetOrderSummaryVoReq getOrderSummaryVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取订单概要-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getOrderSummaryVoReq,true));
		GetOrderSummaryVoRes getOrderSummaryVoRes = new GetOrderSummaryVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取订单概要成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetOrderSummaryParam(getOrderSummaryVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getOrderSummaryVoRes.setResultVo(resultVo);
			return getOrderSummaryVoRes;
		}
		
		//VO转PO
		OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId(getOrderSummaryVoReq.getClientId());
		orderQueryCondition.setMemberCode(getOrderSummaryVoReq.getMemberCode());
		orderQueryCondition.setStartTime(getOrderSummaryVoReq.getStartTime() == 0 ? null : getOrderSummaryVoReq.getStartTime());
		orderQueryCondition.setEndTime(getOrderSummaryVoReq.getEndTime() == 0 ? null : getOrderSummaryVoReq.getEndTime());
		orderQueryCondition.setOrderStatus(getOrderSummaryVoReq.getOrderStatus());
		PageEntity<OrderQueryCondition> pageParam = new PageEntity<OrderQueryCondition>();
		pageParam.setCondition(orderQueryCondition);
		pageParam.convert(getOrderSummaryVoReq.getPage());
		
		//获取订单概要
		ResultBean queryResult = orderLogic.getOrderSummary(pageParam);
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getOrderSummaryVoRes.setResultVo(resultVo);
			return getOrderSummaryVoRes;
		}
		
		Object[] obj = (Object[]) queryResult.getData();
		getOrderSummaryVoRes.setOrderSummaryVoList((List<OrderSummaryVo>) obj[0]);
		getOrderSummaryVoRes.setPage((PageVo) obj[1]);
		getOrderSummaryVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getOrderSummaryVoRes));
		LogCvt.info("订单管理模块-获取订单概要-结束");
		return getOrderSummaryVoRes;
	}

	@Override
	public GetOrderDetailVoRes getOrderDetail(
			GetOrderDetailVoReq getOrderDetailVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取订单详情-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getOrderDetailVoReq,true));
		GetOrderDetailVoRes getOrderDetailVoRes = new GetOrderDetailVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取订单详情成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetOrderDetailParam(getOrderDetailVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getOrderDetailVoRes.setResultVo(resultVo);
			return getOrderDetailVoRes;
		}
		
		//VO转PO
		OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setOrderId(getOrderDetailVoReq.getOrderId());
		orderQueryCondition.setClientId(getOrderDetailVoReq.getClientId());
		
		//获取订单详情
		ResultBean queryResult = orderLogic.getOrderDetail(orderQueryCondition);
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getOrderDetailVoRes.setResultVo(resultVo);
			return getOrderDetailVoRes;
		}
		
		getOrderDetailVoRes.setResultVo(resultVo);
		getOrderDetailVoRes.setOrderDetailVo((OrderDetailVo) queryResult.getData());
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getOrderDetailVoRes,true));
		LogCvt.info("订单管理模块-获取订单详情-结束");
		return getOrderDetailVoRes;
	}
	
	@Override
	public GetQrcodeOrderSummaryVoRes getQrcodeOrderList(
			GetQrcodeOrderSummaryVoReq getQrcodeOrderSummaryVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取面对面支付订单概要-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getQrcodeOrderSummaryVoReq,true));
		GetQrcodeOrderSummaryVoRes getQrcodeOrderSummaryVoRes = new GetQrcodeOrderSummaryVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取面对面支付订单概要成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetQrcodeOrderSummaryParam(getQrcodeOrderSummaryVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getQrcodeOrderSummaryVoRes.setResultVo(resultVo);
			return getQrcodeOrderSummaryVoRes;
		}
		
		//VO转PO
		OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId(getQrcodeOrderSummaryVoReq.getClientId());
		orderQueryCondition.setMemberCode(getQrcodeOrderSummaryVoReq.getMemberCode());
		orderQueryCondition.setStartTime(getQrcodeOrderSummaryVoReq.getStartTime());
		orderQueryCondition.setEndTime(getQrcodeOrderSummaryVoReq.getEndTime());
		orderQueryCondition.setOrderStatus(getQrcodeOrderSummaryVoReq.getOrderStatus());
		PageEntity<OrderQueryCondition> pageParam = new PageEntity<OrderQueryCondition>();
		pageParam.setCondition(orderQueryCondition);
		pageParam.convert(getQrcodeOrderSummaryVoReq.getPage());
		
		//获取订单概要
		ResultBean queryResult = orderLogic.getQrcodeOrderSummary(pageParam);
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getQrcodeOrderSummaryVoRes.setResultVo(resultVo);
			return getQrcodeOrderSummaryVoRes;
		}
		
		getQrcodeOrderSummaryVoRes.setResultVo(resultVo);
		Object[] obj = (Object[]) queryResult.getData();
		getQrcodeOrderSummaryVoRes.setOrderSummaryVoList((List<QrcodeOrderSummaryVo>) obj[0]);
		getQrcodeOrderSummaryVoRes.setPage((PageVo) obj[1]);
		getQrcodeOrderSummaryVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getQrcodeOrderSummaryVoRes));
		LogCvt.info("订单管理模块-获取面对面支付订单概要-结束");
		return getQrcodeOrderSummaryVoRes;
	}

	@Override
	public GetQrcodeOrderDetailVoRes getQrcodeOrderDetail(
			GetQrcodeOrderDetailVoReq getQrcodeOrderDetailVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取面对面支付订单详情-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getQrcodeOrderDetailVoReq,true));
		GetQrcodeOrderDetailVoRes getQrcodeOrderDetailVoRes = new GetQrcodeOrderDetailVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取面对面支付订单详情成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetQrcodeOrderDetailParam(getQrcodeOrderDetailVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getQrcodeOrderDetailVoRes.setResultVo(resultVo);
			return getQrcodeOrderDetailVoRes;
		}
		
		//VO转PO
		OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setOrderId(getQrcodeOrderDetailVoReq.getOrderId());
		orderQueryCondition.setMemberCode(getQrcodeOrderDetailVoReq.getMemberCode());
		orderQueryCondition.setClientId(getQrcodeOrderDetailVoReq.getClientId());
		
		//获取订单详情
		ResultBean queryResult = orderLogic.getQrcodeOrderDetail(orderQueryCondition);
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getQrcodeOrderDetailVoRes.setResultVo(resultVo);
			return getQrcodeOrderDetailVoRes;
		}
		
		getQrcodeOrderDetailVoRes.setResultVo(resultVo);
		getQrcodeOrderDetailVoRes.setQrcodeOrderDetailVo((QrcodeOrderDetailVo) queryResult.getData());
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getQrcodeOrderDetailVoRes,true));
		LogCvt.info("订单管理模块-获取面对面支付订单详情-结束");
		return getQrcodeOrderDetailVoRes;
	}

	@Override
	public DeleteOrderVoRes deleteOrder(DeleteOrderVoReq deleteOrderVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[取消订单]-取消订单开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(deleteOrderVoReq,true));
		DeleteOrderVoRes deleteOrderVoRes = new DeleteOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("取消订单成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateDeleteOrderParam(deleteOrderVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			deleteOrderVoRes.setResultVo(resultVo);
			return deleteOrderVoRes;
		}
		
		//取消订单
		ResultBean deleteResult = orderLogic.deleteOrder(deleteOrderVoReq.getClientId(),deleteOrderVoReq.getOrderId());
		if(!StringUtils.equals(ResultCode.success.getCode(), deleteResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(deleteResult.getCode());
			resultVo.setResultDesc(deleteResult.getMsg());
			deleteOrderVoRes.setResultVo(resultVo);
			return deleteOrderVoRes;
		}
		
		
		Object[] obj = (Object[]) deleteResult.getData();
		List<Store> storeList = (List<Store>) obj[0];
		boolean isRefund = (Boolean) obj[1];
		OrderMongo orderMongo = (OrderMongo) obj[2];
		
		if(orderMongo.getIsSeckill() == 1){
			deleteOrderVoRes = returnStoreForSeckill(deleteOrderVoRes,resultVo,storeList,isRefund,orderMongo);
		}else{
			deleteOrderVoRes = returnStore(deleteOrderVoRes,resultVo,storeList,isRefund,orderMongo);
		}
		
		deleteOrderVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(deleteOrderVoRes,true));
		LogCvt.info("[取消订单]-取消订单成功！");
		return deleteOrderVoRes;
	}
	
	/**
	 * 取消普通订单时-还库存
	 * @param deleteOrderVoRes
	 * @param resultVo
	 * @param storeList 库存信息
	 * @param isRefund 是否退款
	 * @param orderMongo 订单信息
	 * @return
	 */
	public DeleteOrderVoRes returnStore(DeleteOrderVoRes deleteOrderVoRes,ResultVo resultVo,List<Store> storeList,boolean isRefund,OrderMongo orderMongo){
		if(EmptyChecker.isNotEmpty(storeList)){
			LogCvt.info("[取消普通订单]-退库存操作，请求参数："+JSON.toJSONString(storeList,true));
			//加库存
			ResultBean freeStoreResult = orderLogic.increaseStore(storeList);
			if(!StringUtils.equals(ResultCode.success.getCode(), freeStoreResult.getCode())){
				LogCvt.error(freeStoreResult.getMsg());
				resultVo.setResultCode(freeStoreResult.getCode());
				resultVo.setResultDesc(freeStoreResult.getMsg());
				deleteOrderVoRes.setResultVo(resultVo);
				return deleteOrderVoRes;
			}
			
			//将库存更新到mysql
			ResultBean updateProductStoreResult = orderLogic.updateProductStore(storeList);
			if(!StringUtils.equals(ResultCode.success.getCode(), updateProductStoreResult.getCode())){
				LogCvt.error(updateProductStoreResult.getMsg());
				LogCvt.error("[取消普通订单]-更新库存到mysql失败！");
			}
		}
		
		//退款
		if(isRefund){
			/*paymentLogic.cancelPayingOrder(orderMongo);*/
			paymentCoreLogic.cancelPayingOrderToRefundPaiedPoint(orderMongo);
		}
		
		return deleteOrderVoRes;
	}
	
	/**
	 * 取消秒杀订单时-还库存
	 * @param deleteOrderVoRes
	 * @param resultVo
	 * @param storeList 库存信息
	 * @param isRefund 是否退款
	 * @param orderMongo 订单信息
	 * @return
	 */
	public DeleteOrderVoRes returnStoreForSeckill(DeleteOrderVoRes deleteOrderVoRes,ResultVo resultVo,List<Store> storeList,boolean isRefund,OrderMongo orderMongo){
		if(EmptyChecker.isNotEmpty(storeList)){
			LogCvt.info("[取消普通订单]-退库存操作，请求参数："+JSON.toJSONString(storeList,true));
			//加库存
			ResultBean freeStoreResult = orderLogic.increaseSeckillStore(storeList);
			if(!StringUtils.equals(ResultCode.success.getCode(), freeStoreResult.getCode())){
				LogCvt.error(freeStoreResult.getMsg());
				resultVo.setResultCode(freeStoreResult.getCode());
				resultVo.setResultDesc(freeStoreResult.getMsg());
				deleteOrderVoRes.setResultVo(resultVo);
				return deleteOrderVoRes;
			}
			
			//将库存更新到mysql
			ResultBean updateProductStoreResult = orderLogic.updateSeckillProductStore(storeList);
			if(!StringUtils.equals(ResultCode.success.getCode(), updateProductStoreResult.getCode())){
				LogCvt.error(updateProductStoreResult.getMsg());
				LogCvt.error("[取消普通订单]-更新库存到mysql失败！");
			}
		}else{
			LogCvt.error("[取消普通订单]-待更新库存为空");
		}
		
		//退款
		if(isRefund){
			paymentCoreLogic.cancelPayingOrderToRefundPaiedPoint(orderMongo);
		}
		
		return deleteOrderVoRes;
	}
	
	public static void main(String[] args) throws Exception {
		PropertiesUtil.load();
        OrderServiceImpl imp = new OrderServiceImpl();
        
       /* ShippingOrderVoReq req = new ShippingOrderVoReq();
        
        req.setSubOrderId("585339145335390208");
        req.setOrderId("585339084506480640");
        req.setDeliveryCorpId("1");
        req.setDeliveryCorpName("测试");
        req.setTrackingNo("12345");*/
        
       // ShippingOrderVoRes res = imp.shippingOrder(req);
     //   System.out.println(">>>" + JSonUtil.toJSonString(res));
      /*  AddOrderVoReq req = new AddOrderVoReq();
		req.setMemberCode(52001976226L);
		req.setMemberName("lingxiao8");
		req.setIsVip(false);
		req.setMemberLevel(0);
		req.setClientId("chongqing");
		req.setRecvId("100383846");
		req.setDeliverId(null);
		req.setRemark(null);
		req.setPhone("18221310726");
		req.setCreateSource("chongqing");
		req.setOrderRequestType("2");
		req.setIsShoppingCartOrder(false);
		List<AddMerchantVo> addMerchantVoList = new ArrayList<AddMerchantVo>();
		AddMerchantVo addMerchantVo = new AddMerchantVo();
		addMerchantVo.setMerchantId("0D8201D10030");
		addMerchantVoList.add(addMerchantVo);
		List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
		AddProductVo addProductVo = new AddProductVo();
		addProductVo.setProductId("141D8FD18000");
		addProductVo.setQuantity(1);
		addProductVo.setOrgCode("020110");
		addProductVo.setOrgName("江北支行猫儿石分理处");
		addProductVo.setDeliveryType("0");
		addProductVoList.add(addProductVo);
		addMerchantVo.setAddProductVoList(addProductVoList);
		req.setAddMerchantVoList(addMerchantVoList);*/
        AddOrderVoReq req = new AddOrderVoReq();
		req.setMemberCode(52001976226L);
		req.setMemberName("lingxiao8");
		req.setIsVip(false);
		req.setMemberLevel(0);
		req.setClientId("chongqing");
		req.setRecvId("100383846");
		req.setDeliverId(null);
		req.setRemark(null);
		req.setPhone("18221310726");
		req.setCreateSource("chongqing");
		req.setOrderRequestType("2");
		req.setIsShoppingCartOrder(false);
		List<AddMerchantVo> addMerchantVoList = new ArrayList<AddMerchantVo>();
		AddMerchantVo addMerchantVo = new AddMerchantVo();
		addMerchantVo.setMerchantId("0D8201310000");
		addMerchantVoList.add(addMerchantVo);
		List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
		AddProductVo addProductVo = new AddProductVo();
		addProductVo.setProductId("13C5CBD48000");
		addProductVo.setQuantity(1);
		addProductVo.setOrgCode("020110");
		addProductVo.setOrgName("江北支行猫儿石分理处");
		addProductVo.setDeliveryType("0");
		addProductVoList.add(addProductVo);
		addMerchantVo.setAddProductVoList(addProductVoList);
		req.setAddMerchantVoList(addMerchantVoList);
		
		AddMerchantVo addMerchantVo1 = new AddMerchantVo();
		addMerchantVo1.setMerchantId("0D8203C10006");
		addMerchantVoList.add(addMerchantVo1);
		List<AddProductVo> addProductVoList1 = new ArrayList<AddProductVo>();
		AddProductVo addProductVo1 = new AddProductVo();
		addProductVo1.setProductId("0D8216318005");
		addProductVo1.setQuantity(1);
		addProductVo1.setOrgCode("020110");
		addProductVo1.setOrgName("江北支行猫儿石分理处");
		addProductVo1.setDeliveryType("0");
		addProductVoList1.add(addProductVo1);
		addMerchantVo1.setAddProductVoList(addProductVoList1);
		req.setAddMerchantVoList(addMerchantVoList);
		
		imp.addOrder(req);
        
        
    }

	@Override
	public ShippingOrderVoRes shippingOrder(
			ShippingOrderVoReq shippingOrderVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-订单发货-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(shippingOrderVoReq,true));
		JSON.toJSONString(shippingOrderVoReq,true);
		ShippingOrderVoRes shippingOrderVoRes = new ShippingOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("订单发货成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateShippingOrderParam(shippingOrderVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			shippingOrderVoRes.setResultVo(resultVo);
			return shippingOrderVoRes;
		}
		
		//VO转PO
		ShippingOrderMongo shippingOrderMongo = new ShippingOrderMongo();
		shippingOrderMongo.setId(shippingOrderVoReq.getOrderId()+ "_" + shippingOrderVoReq.getSubOrderId());
		shippingOrderMongo.setDeliveryCorpId(shippingOrderVoReq.getDeliveryCorpId());
		shippingOrderMongo.setDeliveryCorpName(shippingOrderVoReq.getDeliveryCorpName());
		shippingOrderMongo.setTrackingNo(shippingOrderVoReq.getTrackingNo());
		shippingOrderMongo.setShippingTime(new Date().getTime());
		shippingOrderMongo.setRemark(shippingOrderVoReq.getRemark());
		shippingOrderMongo.setMerchantUserId(shippingOrderVoReq.getMerchantUserId());
		
		//订单发货
		ResultBean shippingResult = orderLogic.shippingOrder(shippingOrderMongo);
		if(!StringUtils.equals(ResultCode.success.getCode(), shippingResult.getCode())){
			LogCvt.error(shippingResult.getMsg());
			resultVo.setResultCode(shippingResult.getCode());
			resultVo.setResultDesc(shippingResult.getMsg());
			shippingOrderVoRes.setResultVo(resultVo);
			return shippingOrderVoRes;
		}
		
		shippingOrderVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(shippingOrderVoRes,true));
		LogCvt.info("订单管理模块-订单发货-结束");
		return shippingOrderVoRes;
	}

	@Override
	public ShippingOrderVoRes receiptOrder(ReceiptOrderReq receiptOrderReq) throws TException {
	    LogCvt.info("订单收货：" + JSON.toJSONString(receiptOrderReq,true));
	    long st = System.currentTimeMillis();
	    
	    ShippingOrderVoRes shippingOrderVoRes = new ShippingOrderVoRes();
	    
	    ShippingOrderMongo shippingOrderMongo = new ShippingOrderMongo();
	    
	    shippingOrderMongo.setId(receiptOrderReq.getOrderId()+ "_" + receiptOrderReq.getSubOrderId());
	    ResultVo resultVo = new ResultVo();
	    
	    resultVo.setResultCode(ResultCode.success.getCode());
	    resultVo.setResultDesc("订单收货成功");
	    ResultBean rb = orderLogic.receiptOrder(shippingOrderMongo);
	    if(!StringUtils.equals(ResultCode.success.getCode(), rb.getCode())){
            LogCvt.error(rb.getMsg());
            resultVo.setResultCode(rb.getCode());
            resultVo.setResultDesc(rb.getMsg());
        }
	    
//	    结算
	    ResultBean resultBean = orderLogic.getSubOrderBySubOrderId(receiptOrderReq.getClientId(),receiptOrderReq.getSubOrderId());
	    if(resultBean.isSuccess() && rb.isSuccess()) {
	        SubOrderMongo subOrder = (SubOrderMongo) resultBean.getData();
	        if(OrderType.special_merchant.getCode().equals(subOrder.getType())) {
	            ResultBean result = settlementLogic.specialSettlement(receiptOrderReq.getSubOrderId(), 1);
	            LogCvt.error("结算信息：" + result);
	        } else {
	            LogCvt.info("交易类型为：" + subOrder.getType() + ", 不属于：" + OrderType.special_merchant.getCode() + ", 不需要进行结算");
	        } 
	    }
	    
	    shippingOrderVoRes.setResultVo(resultVo);
	    LogCvt.info("订单收货-结束，耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应内容：" + JSON.toJSONString(shippingOrderVoRes,true));
	    return shippingOrderVoRes;
	}
	
	@Override
	public AddQrcodeOrderVoRes addQrcodeOrder(AddQrcodeOrderVoReq addQrcodeOrderVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-面对面创建订单-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(addQrcodeOrderVoReq,true));
		AddQrcodeOrderVoRes addQrcodeOrderVoRes = new AddQrcodeOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("面对面创建订单成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateAddQrcodeOrderParam(addQrcodeOrderVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			addQrcodeOrderVoRes.setResultVo(resultVo);
			return addQrcodeOrderVoRes;
		}
		
		//VO转PO
		OrderMongo orderMongo = (OrderMongo) validResult.getData();
		
		//是否参与了营销活动
		boolean isJoinMarketActive = false;
		//纯红包支付
		boolean isRedPacketOrder = false;
		//检查是否有红包或优惠券
		if(EmptyChecker.isNotEmpty(addQrcodeOrderVoReq.getRedPacketId()) || EmptyChecker.isNotEmpty(addQrcodeOrderVoReq.getCashCouponId())){
			ResultBean activeResult = orderLogic.checkOrderForMarketActive(orderMongo,null);
			if(!activeResult.isSuccess()){
				monitorService.send(MonitorPointEnum.Order_Createf2f_Failed_Count);
				LogCvt.error(activeResult.getMsg());
				resultVo.setResultCode(activeResult.getCode());
				resultVo.setResultDesc(activeResult.getMsg());
				addQrcodeOrderVoRes.setResultVo(resultVo);
				return addQrcodeOrderVoRes;
			}
			//纯红包支付时
			OrderResultData orderResultData = (OrderResultData) activeResult.getData();
			if(EmptyChecker.isNotEmpty(orderResultData)){
				isRedPacketOrder = orderResultData.isRedPacketOrder();
			}
			isJoinMarketActive = true;
		}
		
		/**
		 * 订单创建成功后，创建营销订单，更新大订单营销ID
		 */
		if(isJoinMarketActive){
			ResultBean marketResult = orderLogic.createMarketOrderForMarketActive(orderMongo,null);
			if(!marketResult.isSuccess()){
				LogCvt.error(marketResult.getMsg());
				resultVo.setResultCode(marketResult.getCode());
				resultVo.setResultDesc(marketResult.getMsg());
				addQrcodeOrderVoRes.setResultVo(resultVo);
				return addQrcodeOrderVoRes;
			}
			if(EmptyChecker.isEmpty(orderMongo.getMarketId())){
				OrderLogger.error("创建面对面订单", "调用营销活动平台-创建营销订单", "响应错误：marketId为空！", null);
			}
			OrderLogger.info("创建面对面订单", "调用营销活动平台-创建营销订单", "创建营销订单后订单信息："+JSON.toJSONString(orderMongo), null);
			/*orderLogic.updateOrderForMarketActive(orderMongo);*/
		}
		
		//创建面对面支付订单
		ResultBean addQrcodeOrderResult = orderLogic.addQrcodeOrder(orderMongo);
		if(!StringUtils.equals(ResultCode.success.getCode(), addQrcodeOrderResult.getCode())){
			
			orderLogic.createOrderFailureGoBackForMarketActive(orderMongo, isJoinMarketActive);
			
			monitorService.send(MonitorPointEnum.Order_Createf2f_Failed_Count);
			
			LogCvt.error(addQrcodeOrderResult.getMsg());
			resultVo.setResultCode(addQrcodeOrderResult.getCode());
			resultVo.setResultDesc(addQrcodeOrderResult.getMsg());
			addQrcodeOrderVoRes.setResultVo(resultVo);
			return addQrcodeOrderVoRes;
		}
		
		//订单创建完成，创建订单缓存、订单创建时间缓存到redis
		orderLogic.addOrderRedis(orderMongo,null);
		
		//纯红包支付订单，调营销平台更新营销订单接口
		if(isRedPacketOrder){
//			orderLogic.updateMarketOrder(orderMongo,true);
			ResultBean doPayResult = doPayTrailiing.doPaySuccess(orderMongo);
			OrderLogger.info("订单模块", "创建订单", "纯红包支付订单，调用支付成功后处理逻辑处理结果："+JSON.toJSONString(doPayResult), null);
		}
		
		//大数据平台-调用日志
		orderLogic.createOrderLog(orderMongo, null, "ORDERADD");
		
		monitorService.send(MonitorPointEnum.Order_Createf2f_Count);
		
		addQrcodeOrderVoRes.setResultVo(resultVo);
		addQrcodeOrderVoRes.setOrderId(orderMongo.getOrderId());
		addQrcodeOrderVoRes.setMerchantId(orderMongo.getMerchantId());
		addQrcodeOrderVoRes.setMerchantName(orderMongo.getMerchantName());
		addQrcodeOrderVoRes.setCost(Arith.div(orderMongo.getTotalPrice(), 1000, 3));
		addQrcodeOrderVoRes.setRemark(EmptyChecker.isNotEmpty(orderMongo.getRemark())? orderMongo.getRemark() : "");
		//纯红包支付订单（订单金额为0，满减金额不为0，有红包券或者现金券，支付的方付通积分和银行积分都为0，支付的现金也为0）
		if(isRedPacketOrder){
			addQrcodeOrderVoRes.setIsNeedCash("2");
		}
		
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addQrcodeOrderVoRes,true));
		LogCvt.info("订单管理模块-面对面创建订单-结束");
		return addQrcodeOrderVoRes;
	}

	@Override
	public GetVipDiscountVoRes getVipDiscount(
			GetVipDiscountVoReq getVipDiscountVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-面对面创建订单-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getVipDiscountVoReq,true));
		GetVipDiscountVoRes getVipDiscountVoRes = new GetVipDiscountVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("面对面创建订单成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetVipDiscountParam(getVipDiscountVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getVipDiscountVoRes.setResultVo(resultVo);
			return getVipDiscountVoRes;
		}
		
		//VO转PO
		OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId(getVipDiscountVoReq.getClientId());
		orderQueryCondition.setMemberCode(getVipDiscountVoReq.getMemberCode());
		
		//获取VIP优惠金额
		ResultBean result = orderLogic.getVipDiscount(orderQueryCondition);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
			getVipDiscountVoRes.setResultVo(resultVo);
			return getVipDiscountVoRes;
		}
		
		getVipDiscountVoRes.setResultVo(resultVo);
		getVipDiscountVoRes.setMoney((Double) result.getData());
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getVipDiscountVoRes,true));
		LogCvt.info("订单管理模块-面对面创建订单-结束");
		return getVipDiscountVoRes;
	}

	@Override
	public GetPointExchangeListVoRes getPointExchangeList(
			GetPointExchangeListVoReq getPointExchangeListVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取积分兑换订单列表-开始");
//		LogCvt.info("请求参数：" + JSonUtil.toJSonString(getPointExchangeListVoReq));
		LogCvt.info("请求参数：" + JSON.toJSONString(getPointExchangeListVoReq,true));
		GetPointExchangeListVoRes getPointExchangeListVoRes = new GetPointExchangeListVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取积分兑换订单列表成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetPointExchangeListParam(getPointExchangeListVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getPointExchangeListVoRes.setResultVo(resultVo);
			return getPointExchangeListVoRes;
		}
		
		//VO转PO
		OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId(getPointExchangeListVoReq.getClientId());
		orderQueryCondition.setMemberCode(getPointExchangeListVoReq.getMemberCode());
		orderQueryCondition.setQueryFlag(getPointExchangeListVoReq.getQueryFlag());
		//分页
		PageEntity<OrderQueryCondition> pageParam = new PageEntity<OrderQueryCondition>();
		pageParam.setCondition(orderQueryCondition);
		pageParam.convert(getPointExchangeListVoReq.getPage());
		
		//获取积分兑换订单列表
		ResultBean queryResult = orderLogic.getPointExchange(pageParam);
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getPointExchangeListVoRes.setResultVo(resultVo);
			return getPointExchangeListVoRes;
		}
		Object[] obj = (Object[]) queryResult.getData();
		getPointExchangeListVoRes.setResultVo(resultVo);
		getPointExchangeListVoRes.setPointExchangeVoList((List<PointExchangeVo>) obj[0]);
		getPointExchangeListVoRes.setPage((PageVo) obj[1]);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getPointExchangeListVoRes));
		LogCvt.info("订单管理模块-获取积分兑换订单列表-结束");
		return getPointExchangeListVoRes;
	}

	@Override
	public GetPointExchangeDetailVoRes getPointExchangeDetail(
			GetPointExchangeDetailVoReq getPointExchangeDetailVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取积分兑换订单详情-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getPointExchangeDetailVoReq,true));
		GetPointExchangeDetailVoRes getPointExchangeDetailVoRes = new GetPointExchangeDetailVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取积分兑换订单详情成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetPointExchangeDetailParam(getPointExchangeDetailVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getPointExchangeDetailVoRes.setResultVo(resultVo);
			return getPointExchangeDetailVoRes;
		}
		
		//VO转PO
		OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId(getPointExchangeDetailVoReq.getClientId());
		orderQueryCondition.setMemberCode(getPointExchangeDetailVoReq.getMemberCode());
		orderQueryCondition.setOrderId(getPointExchangeDetailVoReq.getOrderId());
		
		//获取积分兑换订单列表
		ResultBean queryResult = orderLogic.getPointExchangeDetail(orderQueryCondition);
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getPointExchangeDetailVoRes.setResultVo(resultVo);
			return getPointExchangeDetailVoRes;
		}
		
		PointExchangeVo pointExchangeVo = (com.froad.thrift.vo.order.PointExchangeVo) queryResult.getData();
		
		//获取积分兑换订单列表
		ResultBean queryOrderResult = orderLogic.getOrderByOrderId(getPointExchangeDetailVoReq.getClientId(),getPointExchangeDetailVoReq.getOrderId());
		if(!StringUtils.equals(ResultCode.success.getCode(), queryOrderResult.getCode())){
			LogCvt.error(queryOrderResult.getMsg());
			resultVo.setResultCode(queryOrderResult.getCode());
			resultVo.setResultDesc(queryOrderResult.getMsg());
			getPointExchangeDetailVoRes.setResultVo(resultVo);
			return getPointExchangeDetailVoRes;
		}
		
		OrderMongo order = (OrderMongo) queryOrderResult.getData();
		if(EmptyChecker.isEmpty(order)){
			LogCvt.error("获取订单失败，订单号：" + getPointExchangeDetailVoReq.getOrderId());
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("获取提货信息ID失败");
			getPointExchangeDetailVoRes.setResultVo(resultVo);
			return getPointExchangeDetailVoRes;
		}
		String deliveryId = order.getDeliverId();
		String recvId = order.getRecvId();
		
		ResultBean queryDeliveryResult = orderLogic.getDeliverInfoDetail(getPointExchangeDetailVoReq.getClientId(),getPointExchangeDetailVoReq.getMemberCode(),deliveryId,recvId);
		if(!StringUtils.equals(ResultCode.success.getCode(), queryDeliveryResult.getCode())){
			LogCvt.error(queryDeliveryResult.getMsg());
			resultVo.setResultCode(queryDeliveryResult.getCode());
			resultVo.setResultDesc(queryDeliveryResult.getMsg());
			getPointExchangeDetailVoRes.setResultVo(resultVo);
			return getPointExchangeDetailVoRes;
		}
		
		DeliverInfoDetailVo deliverInfoDetailVo = (DeliverInfoDetailVo) queryDeliveryResult.getData();
		
		getPointExchangeDetailVoRes.setResultVo(resultVo);
		getPointExchangeDetailVoRes.setPointExchangeVo(pointExchangeVo);
		getPointExchangeDetailVoRes.setDeliverInfoDetailVo(deliverInfoDetailVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getPointExchangeDetailVoRes,true));
		LogCvt.info("订单管理模块-获取积分兑换订单详情-结束");
		return getPointExchangeDetailVoRes;
	}

	@Override
	public StoreVoRes storeProcess(StoreVoReq storeVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[库存操作接口]-库存操作-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(storeVoReq,true));
		StoreVoRes storeVoRes = new StoreVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("[库存操作接口]-库存操作成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateStoreProcess(storeVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			storeVoRes.setResultVo(resultVo);
			return storeVoRes;
		}
		
		//获取库存集合列表
		ResultBean queryResult = orderLogic.getStoreListByOrderId(storeVoReq.getClientId(),storeVoReq.getOrderId());
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			storeVoRes.setResultVo(resultVo);
			return storeVoRes;
		}
		
		List<Store> storeList = (List<Store>) queryResult.getData();
		/*List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) queryResult.getData();
		List<Store> storeList = new ArrayList<Store>();
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrderMongo : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
					for(ProductMongo productMongo : subOrderMongo.getProducts()){
						Store store = new Store();
						store.setClientId(subOrderMongo.getClientId());
						store.setMerchantId(subOrderMongo.getMerchantId());
						store.setProductId(productMongo.getProductId());
						store.setReduceStore(productMongo.getQuantity() + productMongo.getVipQuantity());
						storeList.add(store);
					}
				}else{
					LogCvt.error("还库存操作失败，原因：子订单商品查询结果为空，订单号："+storeVoReq.getOrderId());
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("还库存操作失败，原因：子订单商品查询结果为空");
					storeVoRes.setResultVo(resultVo);
					return storeVoRes;
				}
			}
		}else{
			LogCvt.error("还库存操作失败，原因：子订单查询结果为空，订单号："+storeVoReq.getOrderId());
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("还库存操作失败，原因：子订单列表查询结果为空");
			storeVoRes.setResultVo(resultVo);
			return storeVoRes;
		}*/
		
		if(StringUtils.equals(storeVoReq.getOperationType(), "1")){
			//加库存
			LogCvt.info("[库存操作接口]-加库存操作");
			ResultBean increaseResult = orderLogic.increaseStore(storeList);
			if(!StringUtils.equals(ResultCode.success.getCode(), increaseResult.getCode())){
				LogCvt.error(increaseResult.getMsg());
				resultVo.setResultCode(increaseResult.getCode());
				resultVo.setResultDesc(increaseResult.getMsg());
				storeVoRes.setResultVo(resultVo);
				return storeVoRes;
			}
			//同步库存
			LogCvt.info("[库存操作接口]-同步操作从redis至mysql");
			ResultBean syncResult = orderLogic.updateProductStore(storeList);
			if(!StringUtils.equals(ResultCode.success.getCode(), syncResult.getCode())){
				//同步库存失败，减库存
				LogCvt.info("[库存操作接口]-加库存操作后，同步库存从redis到mysql失败，进行减库存操作...");
				ResultBean reduceResult = orderLogic.reduceStore(storeList);
				if(!StringUtils.equals(ResultCode.success.getCode(), reduceResult.getCode())){
					LogCvt.error("[库存操作接口]-减库存操作失败！原因：" + reduceResult.getMsg());
					resultVo.setResultCode(reduceResult.getCode());
					resultVo.setResultDesc(reduceResult.getMsg());
					storeVoRes.setResultVo(resultVo);
					return storeVoRes;
				}
			}
		}
		
		//获取库存集合列表
		ResultBean orderResult = orderLogic.getOrderByOrderId(storeVoReq.getClientId(),storeVoReq.getOrderId());
		if(!orderResult.isSuccess()){
			LogCvt.error("[库存操作接口]-查询大订单失败，原因：" + orderResult.getMsg());
			resultVo.setResultCode(orderResult.getCode());
			resultVo.setResultDesc(orderResult.getMsg());
			storeVoRes.setResultVo(resultVo);
			return storeVoRes;
		}
		
		/*//支付中的订单，现金+积分支付的退款处理
		OrderMongo orderMongo = (OrderMongo) orderResult.getData();
		if(StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.paying.getCode()) && 
				(StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.froadPointsAndCash.getCode()) 
						|| StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.bankPointsAndCash.getCode()))){
			ResultBean paymentResult = paymentLogic.cancelPayingOrder(orderMongo);
			ResultBean paymentResult = paymentCoreLogic.cancelPayingOrderToRefundPaiedPoint(orderMongo);
			if(!paymentResult.isSuccess()){
				LogCvt.error("[库存操作接口]-支付中订单-退积分操作失败，原因：" + paymentResult.getMsg());
				resultVo.setResultCode(paymentResult.getCode());
				resultVo.setResultDesc(paymentResult.getMsg());
				storeVoRes.setResultVo(resultVo);
				return storeVoRes;
			}
		}*/
		
		storeVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(storeVoRes,true));
		LogCvt.info("[库存操作接口]-库存操作-结束");
		return storeVoRes;
	}

	@Override
	public GetSubOrderProductVoRes getSubOrderProduct(
			GetSubOrderProductVoReq getSubOrderProductVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取子订单商品-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getSubOrderProductVoReq,true));
		GetSubOrderProductVoRes getSubOrderProductVoRes = new GetSubOrderProductVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取积分兑换订单详情成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetSubOrderProduct(getSubOrderProductVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getSubOrderProductVoRes.setResultVo(resultVo);
			return getSubOrderProductVoRes;
		}
		
		//获取积分兑换订单列表
		ResultBean queryResult = orderLogic.getSubOrderProductDetail(getSubOrderProductVoReq.getClientId(),getSubOrderProductVoReq.getOrderId(),getSubOrderProductVoReq.getSubOrderId(),getSubOrderProductVoReq.getProductId());
		if(!queryResult.isSuccess()){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getSubOrderProductVoRes.setResultVo(resultVo);
			return getSubOrderProductVoRes;
		}
		
		getSubOrderProductVoRes.setResultVo(resultVo);
		getSubOrderProductVoRes.setTotalMoney((Double) queryResult.getData());
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getSubOrderProductVoRes,true));
		LogCvt.info("订单管理模块-获取子订单商品-结束");
		return getSubOrderProductVoRes;
	}

	@Override
	public GetOrderByQrcodeVoRes getOrderByQrcode(
			GetOrderByQrcodeVoReq getOrderByQrcodeVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-通过二维码获取面对面订单-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getOrderByQrcodeVoReq,true));
		GetOrderByQrcodeVoRes getSubOrderProductVoRes = new GetOrderByQrcodeVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("通过二维码获取面对面订单成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetOrderByQrcode(getOrderByQrcodeVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getSubOrderProductVoRes.setResultVo(resultVo);
			return getSubOrderProductVoRes;
		}
		
		//通过二维码获取面对面订单
		ResultBean queryResult = orderLogic.getOrderByQrcode(getOrderByQrcodeVoReq.getClientId(),getOrderByQrcodeVoReq.getQrcode());
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getSubOrderProductVoRes.setResultVo(resultVo);
			return getSubOrderProductVoRes;
		}
		getSubOrderProductVoRes = (GetOrderByQrcodeVoRes) queryResult.getData();
		getSubOrderProductVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getSubOrderProductVoRes,true));
		LogCvt.info("订单管理模块-通过二维码获取面对面订单-结束");
		return getSubOrderProductVoRes;
	}

	@Override
	public GetMemberBuyLimitVoRes getMemberBuyLimit(
			GetMemberBuyLimitVoReq getMemberBuyLimitVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取用户可购买数量-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getMemberBuyLimitVoReq,true));
		GetMemberBuyLimitVoRes getMemberBuyLimitVoRes = new GetMemberBuyLimitVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取用户可购买数量成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetMemberBuyLimit(getMemberBuyLimitVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getMemberBuyLimitVoRes.setResultVo(resultVo);
			return getMemberBuyLimitVoRes;
		}
		
		//获取用户可购买数量
		ResultBean queryResult = orderLogic.getMemberBuyLimit(getMemberBuyLimitVoReq.getClientId(),getMemberBuyLimitVoReq.getMerchantId(),getMemberBuyLimitVoReq.getMemberCode(),getMemberBuyLimitVoReq.isVip,getMemberBuyLimitVoReq.getProductId());
		if(!StringUtils.equals(ResultCode.success.getCode(), queryResult.getCode())){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getMemberBuyLimitVoRes.setResultVo(resultVo);
			return getMemberBuyLimitVoRes;
		}
		getMemberBuyLimitVoRes = (GetMemberBuyLimitVoRes) queryResult.getData();
		getMemberBuyLimitVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getMemberBuyLimitVoRes,true));
		LogCvt.info("订单管理模块-获取用户可购买数量-结束");
		return getMemberBuyLimitVoRes;
	}

	@Override
	public GetOrderPaymentResultVoRes getOrderPaymentResult(
			GetOrderPaymentResultVoReq getOrderPaymentResultVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单管理模块-获取订单支付结果描述-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getOrderPaymentResultVoReq,true));
		GetOrderPaymentResultVoRes getOrderPaymentResultVoRes = new GetOrderPaymentResultVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("获取用户可购买数量成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateGetOrderPaymentResult(getOrderPaymentResultVoReq);
		if(!validResult.isSuccess()){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			getOrderPaymentResultVoRes.setResultVo(resultVo);
			return getOrderPaymentResultVoRes;
		}
		
		//获取订单支付结果描述
		ResultBean queryResult = orderLogic.getOrderByOrderId(getOrderPaymentResultVoReq.getClientId(),getOrderPaymentResultVoReq.getOrderId());
		if(!queryResult.isSuccess()){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			getOrderPaymentResultVoRes.setResultVo(resultVo);
			return getOrderPaymentResultVoRes;
		}
		OrderMongo order = (OrderMongo) queryResult.getData();
		
		getOrderPaymentResultVoRes.setOrderStatus(order.getOrderStatus());
		getOrderPaymentResultVoRes.setRemark(order.getRemark());
		getOrderPaymentResultVoRes.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(getOrderPaymentResultVoRes,true));
		LogCvt.info("订单管理模块-获取订单支付结果描述-结束");
		return getOrderPaymentResultVoRes;
	}

	@Override
	public AddVIPOrderVoRes addVIPOrder(AddVIPOrderVoReq addVIPOrderVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[开通VIP]-开通VIP创建订单-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(addVIPOrderVoReq,true));
		AddVIPOrderVoRes response = new AddVIPOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("VIP订单创建成功");
		
		//校验参数
		ResultBean validResult = orderValidation.addVIPOrderValidate(addVIPOrderVoReq);
		if(!validResult.isSuccess()){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			
			//如果创建过VIP订单
			if(EmptyChecker.isNotEmpty(validResult) && EmptyChecker.isNotEmpty(validResult.getData()) ){
				OrderInfo orderInfo = (OrderInfo) validResult.getData();
				if(EmptyChecker.isNotEmpty(orderInfo) && EmptyChecker.isNotEmpty(orderInfo.getOrder())){
					response.setOrderId(orderInfo.getOrder().getOrderId());
					response.setTotalPrice(Arith.div(orderInfo.getOrder().getTotalPrice(), 1000));
					
					resultVo.setResultCode(ResultCode.success.getCode());
				}
			}
			
			response.setResultVo(resultVo);
			
			return response;
		}
		
		OrderInfo orderInfo = (OrderInfo) validResult.getData();
		OrderMongo orderMongo = orderInfo.getOrder();
		
		//创建VIP订单
		ResultBean queryResult = orderLogic.addVIPOrder(orderMongo);
		if(!queryResult.isSuccess()){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			response.setResultVo(resultVo);
			return response;
		}
		
		//VIP订单创建完成，创建订单缓存、订单创建时间缓存到redis
		orderLogic.addOrderRedis(orderMongo,null);
		
		//大数据平台-调用日志
		orderLogic.createOrderLog(orderMongo, null, "ORDERADD");
		
		response.setOrderId(orderMongo.getOrderId());
		response.setTotalPrice(Arith.div(orderMongo.getTotalPrice(), 1000));
		response.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(response,true));
		LogCvt.info("订单管理模块-获取订单支付结果描述-结束");
		return response;
	}

	@Override
	public GetSubOrderVoRes getSubOrder(GetSubOrderVoReq getSubOrderVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[子订单查询]-接口：OrderService.getSubOrder-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(getSubOrderVoReq,true));
		GetSubOrderVoRes response = new GetSubOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("子订单查询成功");
		
		//校验参数
		ResultBean validResult = orderValidation.getSubOrderValidate(getSubOrderVoReq);
		if(!validResult.isSuccess()){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			response.setResultVo(resultVo);
			return response;
		}
		
		//创建VIP订单
		ResultBean queryResult = orderLogic.getSubOrder(getSubOrderVoReq.getClientId(),getSubOrderVoReq.getSubOrderId());
		if(!queryResult.isSuccess()){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			response.setResultVo(resultVo);
			return response;
		}
		
		response = (GetSubOrderVoRes) queryResult.getData();
		response.setResultVo(resultVo);
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(response,true));
		LogCvt.info("订单管理模块-获取订单支付结果描述-结束");
		return response;
	}

	@Override
	public UpdateSubOrderLogisticVoRes updateSubOrderLogistic(UpdateSubOrderLogisticVoReq req) throws TException {
		LogCvt.info("更新子订单物流配送状态:" + JSON.toJSONString(req, true));
		UpdateSubOrderLogisticVoRes res = new UpdateSubOrderLogisticVoRes();
		ResultVo resultVo = new ResultVo();
	    resultVo.setResultCode(ResultCode.success.getCode());
	    resultVo.setResultDesc("更新子订单配送状态成功");
	    
		ResultBean rtb = orderLogic.updateSubOrderLogistic(req.getClientId(), req.getSubOrderId(), req.getDeliveryState());
		if(!ResultCode.success.getCode().equals(rtb.getCode())) {
			resultVo.setResultCode(rtb.getCode());
			resultVo.setResultDesc(rtb.getMsg());
		}
		res.setResultVo(resultVo);
		
		return res;
	}

	@Override
	public CashierVoRes checkBeforeCashier(CashierVoReq cashierVoReq)
			throws TException {
		OrderLogger.info("订单模块","校验是否跳收银台-开始", "请求参数："+JSON.toJSONString(cashierVoReq,true), null);
		CashierVoRes response = new CashierVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("校验成功");
		
		//校验参数
		ResultBean validResult = orderValidation.checkBeforeCashierValidate(cashierVoReq);
		if(!validResult.isSuccess()){
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			return response;
		}
		
		//校验是否跳收银台
		ResultBean queryResult = orderLogic.checkBeforeCashier(cashierVoReq);
		if(!queryResult.isSuccess()){
			LogCvt.error(queryResult.getMsg());
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
			return response;
		}
		
		response = (CashierVoRes) queryResult.getData();
		if(!queryResult.isSuccess()){
			resultVo.setResultCode(queryResult.getCode());
			resultVo.setResultDesc(queryResult.getMsg());
		}
		response.setResultVo(resultVo);
		OrderLogger.info("订单模块", "校验是否跳收银台-结束", "响应结果：" + JSON.toJSONString(response,true),null);
		return response;
	}
	
	@Override
	public CloseOrderVoRes closeOrder(CloseOrderVoReq closeOrderVoReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[关闭订单]-关闭订单开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(closeOrderVoReq,true));
		CloseOrderVoRes closeOrderVoRes = new CloseOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("关闭订单成功");
		closeOrderVoRes.setResultVo(resultVo);
		//校验参数
		ResultBean validResult = orderValidation.validateCloseOrderParam(closeOrderVoReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			closeOrderVoRes.setResultVo(resultVo);
			return closeOrderVoRes;
		}
		
		//关闭订单
		ResultBean closeResult = orderLogic.closeOrder(closeOrderVoReq.getClientId(),closeOrderVoReq.getOrderId());
		if(!StringUtils.equals(ResultCode.success.getCode(), closeResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(closeResult.getCode());
			resultVo.setResultDesc(closeResult.getMsg());
			closeOrderVoRes.setResultVo(resultVo);
			return closeOrderVoRes;
		}
		
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(closeOrderVoRes,true));
		LogCvt.info("[关闭订单]-关闭订单成功！");
		return closeOrderVoRes;
	}

	@Override
	public AddPrefPayOrderRes addPrefPayOrder(AddPrefPayOrderReq addPrefPayOrderReq)
			throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("订单模块-创建惠付订单-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(addPrefPayOrderReq,true));
		AddPrefPayOrderRes addQrcodeOrderVoRes = new AddPrefPayOrderRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("创建惠付订单成功");
		
		//校验参数
		ResultBean validResult = orderValidation.validateAddPrefPayOrderParam(addPrefPayOrderReq);
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			addQrcodeOrderVoRes.setResultVo(resultVo);
			return addQrcodeOrderVoRes;
		}
		
		//VO转PO
		OrderMongo orderMongo = (OrderMongo) validResult.getData();
		
		//是否参与了营销活动
		boolean isJoinMarketActive = false;
		//纯红包支付
		boolean isRedPacketOrder = false;
		//检查是否有红包或优惠券
		if(EmptyChecker.isNotEmpty(addPrefPayOrderReq.getRedPacketId()) || EmptyChecker.isNotEmpty(addPrefPayOrderReq.getCashCouponId())){
			ResultBean activeResult = orderLogic.checkOrderForMarketActive(orderMongo,null);
			if(!activeResult.isSuccess()){
				monitorService.send(MonitorPointEnum.Order_Createf2f_Failed_Count);
				LogCvt.error(activeResult.getMsg());
				resultVo.setResultCode(activeResult.getCode());
				resultVo.setResultDesc(activeResult.getMsg());
				addQrcodeOrderVoRes.setResultVo(resultVo);
				return addQrcodeOrderVoRes;
			}
			//纯红包支付时
			OrderResultData orderResultData = (OrderResultData) activeResult.getData();
			if(EmptyChecker.isNotEmpty(orderResultData)){
				isRedPacketOrder = orderResultData.isRedPacketOrder();
			}
			isJoinMarketActive = true;
		}
		
		/**
		 * 惠付订单创建成功后，创建营销订单，更新大订单营销ID
		 */
		if(isJoinMarketActive){
			ResultBean marketResult = orderLogic.createMarketOrderForMarketActive(orderMongo,null);
			if(!marketResult.isSuccess()){
				LogCvt.error(marketResult.getMsg());
				resultVo.setResultCode(marketResult.getCode());
				resultVo.setResultDesc(marketResult.getMsg());
				addQrcodeOrderVoRes.setResultVo(resultVo);
				return addQrcodeOrderVoRes;
			}
			if(EmptyChecker.isEmpty(orderMongo.getMarketId())){
				OrderLogger.error("创建惠付订单", "调用营销活动平台-创建营销订单", "响应错误：marketId为空！", null);
			}
			OrderLogger.info("创建惠付订单", "调用营销活动平台-创建营销订单", "创建营销订单后订单信息："+JSON.toJSONString(orderMongo), null);
		}
		
		//创建惠付订单
		ResultBean addQrcodeOrderResult = orderLogic.addPrefPayOrder(orderMongo);
		if(!StringUtils.equals(ResultCode.success.getCode(), addQrcodeOrderResult.getCode())){
			
			orderLogic.createOrderFailureGoBackForMarketActive(orderMongo, isJoinMarketActive);
			
			monitorService.send(MonitorPointEnum.Order_Createf2f_Failed_Count);
			
			LogCvt.error(addQrcodeOrderResult.getMsg());
			resultVo.setResultCode(addQrcodeOrderResult.getCode());
			resultVo.setResultDesc(addQrcodeOrderResult.getMsg());
			addQrcodeOrderVoRes.setResultVo(resultVo);
			return addQrcodeOrderVoRes;
		}
		
		//订单创建完成，创建订单缓存、订单创建时间缓存到redis
		orderLogic.addOrderRedis(orderMongo,null);
		
		//纯红包支付订单，调营销平台更新营销订单接口
		if(isRedPacketOrder){
//			orderLogic.updateMarketOrder(orderMongo,true);
			ResultBean doPayResult = doPayTrailiing.doPaySuccess(orderMongo);
			OrderLogger.info("创建惠付订单", "纯红包支付订单-调用支付成功后处理逻辑", "处理结果："+JSON.toJSONString(doPayResult), null);
		}
		
		//大数据平台-调用日志
		orderLogic.createOrderLog(orderMongo, null, "ORDERADD");
		
		monitorService.send(MonitorPointEnum.Order_Createf2f_Count);
		
		addQrcodeOrderVoRes.setResultVo(resultVo);
		addQrcodeOrderVoRes.setOrderId(orderMongo.getOrderId());
		addQrcodeOrderVoRes.setMerchantId(orderMongo.getMerchantId());
		addQrcodeOrderVoRes.setMerchantName(orderMongo.getMerchantName());
		addQrcodeOrderVoRes.setCost(Arith.div(orderMongo.getTotalPrice(), 1000, 3));
		addQrcodeOrderVoRes.setRemark(EmptyChecker.isNotEmpty(orderMongo.getRemark())? orderMongo.getRemark() : "");
		OrderResultData orderResultData = OrderLogicHelper.getCashAfterPrefPayOrderCreate(orderMongo,addPrefPayOrderReq,isRedPacketOrder);
		addQrcodeOrderVoRes.setCost(orderResultData.getCash());
		addQrcodeOrderVoRes.setIsNeedCash(orderResultData.getIsNeedCash());
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addQrcodeOrderVoRes,true));
		LogCvt.info("订单管理模块-创建惠付订单-结束");
		return addQrcodeOrderVoRes;
	}

	@Override
	public String getVipOrderId(String clientId, long memberCode) throws TException {
		OrderLogger.info("订单模块", "获取用户最近一笔VIP订单号", "请求参数", new Object[]{"clientId",clientId,"memberCode",memberCode});
		String result = null;
		if(EmptyChecker.isNotEmpty(clientId)){
			result = orderLogic.getVipOrderId(clientId, memberCode);
		}else{
			OrderLogger.error("订单模块", "获取用户最近一笔VIP订单号", "参数错误：clientId为空", null);
		}
		return result;
	}

	/**
	 * 
	 * 【支付中订单，使用积分+现金的退积分】
	 *  定时任务查到支付中的订单，如果支付方式满足：积分+现金支付时：
	 *  面对面/普通订单：先调此接口退积分，如果积分退成功了或者积分已退，都返回成功，定时任务再关单，关单成功再调还库存接口。
	 * 
	 */
	@Override
	public RefundPayingOrderVoRes refundPayingOrder(RefundPayingOrderVoReq req)
			throws TException {
		OrderLogger.info("订单模块", "定时任务调用-（支付方式：现金+积分）支付中订单关单后退款处理", "请求参数", new Object[]{"clientId",req.getClientId(),"orderId",req.getOrderId()});
		RefundPayingOrderVoRes res = new RefundPayingOrderVoRes();
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.failed.getCode());
		resultVo.setResultDesc("支付中订单退积分失败");
		
		//校验参数
		ResultBean validResult = orderValidation.validateRefundPayingOrder(req);
		if(!validResult.isSuccess()){
			OrderLogger.error("订单模块", "定时任务调用-（支付方式：现金+积分）支付中订单关单后退积分处理", "失败："+JSON.toJSONString(validResult), null);
			resultVo.setResultCode(validResult.getCode());
			resultVo.setResultDesc(validResult.getMsg());
			res.setResultVo(resultVo);
			return res;
		}
		
		ResultBean result = orderLogic.getOrderByOrderId(req.getClientId(), req.getOrderId());
		if(!result.isSuccess()){
			OrderLogger.error("订单模块", "定时任务调用-（支付方式：现金+积分）支付中订单关单后退积分处理", "失败："+JSON.toJSONString(result), null);
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
			res.setResultVo(resultVo);
			return res;
		}
		
		//支付中的订单，现金+积分支付的退款处理
		OrderMongo orderMongo = (OrderMongo) result.getData();
		if(StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.paying.getCode()) && 
				(StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.froadPointsAndCash.getCode()) 
						|| StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.bankPointsAndCash.getCode()))){
			ResultBean paymentResult = paymentCoreLogic.cancelPayingOrderToRefundPaiedPoint(orderMongo);
			if(!paymentResult.isSuccess()){
				OrderLogger.error("订单模块", "定时任务调用-（支付方式：现金+积分）支付中订单关单后退积分处理", "失败："+JSON.toJSONString(paymentResult), null);
				resultVo.setResultCode(paymentResult.getCode());
				resultVo.setResultDesc(paymentResult.getMsg());
				res.setResultVo(resultVo);
				return res;
			}else{
				OrderLogger.info("订单模块", "定时任务调用-（支付方式：现金+积分）支付中订单关单后退积分处理", "成功："+JSON.toJSONString(paymentResult), null);
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc("支付中订单退积分成功");
			}
		}else{
			OrderLogger.error("订单模块", "定时任务调用-（支付方式：现金+积分）支付中订单关单后退积分处理", "不满足退积分条件", 
					new Object[]{"clientId",orderMongo.getClientId(),"orderId",orderMongo.getOrderId(),"orderStatus",orderMongo.getOrderStatus(),"paymentMethod",orderMongo.getPaymentMethod()});
			resultVo.setResultDesc("支付中订单退积分失败：不满足退分条件");
		}
		
		res.setResultVo(resultVo);
		return res;
	}
}
