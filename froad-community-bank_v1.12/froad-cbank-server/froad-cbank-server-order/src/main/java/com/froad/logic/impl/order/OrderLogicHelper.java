package com.froad.logic.impl.order;

import com.alibaba.druid.util.StringUtils;
import com.froad.enums.OrderRequestType;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.order.OrderResultData;
import com.froad.thrift.vo.order.AddOrderVoReq;
import com.froad.thrift.vo.order.AddPrefPayOrderReq;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;

public class OrderLogicHelper {

	/**
	 * 订单创建后获取现金，判断是否跳收银台
	 * @param orderMongo
	 * @param addOrderVoReq
	 * @param isRedPacketOrder 是否纯红包支付
	 * @return
	 */
	public static OrderResultData getCashAfterOrderCreate(OrderMongo orderMongo,AddOrderVoReq addOrderVoReq,boolean isRedPacketOrder){
		OrderResultData result = new OrderResultData();
		//订单应支付现金
		double cutMoney = 0;
		if(EmptyChecker.isNotEmpty(orderMongo.getCutMoney())){
			cutMoney = Arith.div(orderMongo.getCutMoney(), 1000);
		}
		double cash = Arith.div(orderMongo.getTotalPrice(), 1000);		//非积分兑换订单，有积分支付，计算订单实付现金
		if(!StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode())
				&& !StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.online_point_order.getCode())
				&& addOrderVoReq.getPointMoney()>0){
			cash = Arith.sub(cash, addOrderVoReq.getPointMoney());
			if (cash < 0) {
				OrderLogger.error("订单模块", "订单创建", "计算订单现金部分出现负数", new Object[]{"订单总价",Arith.div(orderMongo.getTotalPrice(), 1000),"订单优惠金额",cutMoney,"支付的积分对应的钱",addOrderVoReq.getPointMoney(),"现金",cash});
			}
		}
		//纯红包支付订单
//		if(orderMongo.getTotalPrice() == 0 && orderMongo.getBankPoints() == 0 && orderMongo.getFftPoints() == 0
//				&& EmptyChecker.isNotEmpty(orderMongo.getRedPacketId()) && EmptyChecker.isNotEmpty(orderMongo.getCashCouponId()) && cash == 0){
		if(isRedPacketOrder){
			result.setIsNeedCash("2");
		}else{
			if(cash > 0){
				result.setIsNeedCash("1");
			}else{
				result.setIsNeedCash("0");
			}
		}
		result.setCash(cash);
		
		OrderLogger.info("订单模块", "订单创建", "计算订单现金部分，判断是否跳收银台", new Object[]{"订单总价",Arith.div(orderMongo.getTotalPrice(), 1000),"订单优惠金额",cutMoney,"支付的积分对应的钱",addOrderVoReq.getPointMoney(),"现金",result.getCash(),"是否跳收银台",result.getIsNeedCash()});
		
		return result;
	}
	
	/**
	 * 惠付订单创建后获取现金，判断是否跳收银台
	 * @param orderMongo
	 * @param addOrderVoReq
	 * @param isRedPacketOrder 是否纯红包支付
	 * @return
	 */
	public static OrderResultData getCashAfterPrefPayOrderCreate(OrderMongo orderMongo,AddPrefPayOrderReq addOrderVoReq,boolean isRedPacketOrder){
		OrderResultData result = new OrderResultData();
		//订单应支付现金
		double cutMoney = 0;
		if(EmptyChecker.isNotEmpty(orderMongo.getCutMoney())){
			cutMoney = Arith.div(orderMongo.getCutMoney(), 1000);
		}
		double cash = Arith.div(orderMongo.getTotalPrice(), 1000);		//非积分兑换订单，有积分支付，计算订单实付现金
		if(addOrderVoReq.getPointMoney()>0){
			cash = Arith.sub(cash, addOrderVoReq.getPointMoney());
			if (cash < 0) {
				OrderLogger.error("订单模块", "订单创建", "计算订单现金部分出现负数", new Object[]{"订单总价",Arith.div(orderMongo.getTotalPrice(), 1000),"订单优惠金额",cutMoney,"支付的积分对应的钱",addOrderVoReq.getPointMoney(),"现金",cash});
			}
		}
		//纯红包支付订单
		if(isRedPacketOrder){
			result.setIsNeedCash("2");
		}else{
			if(cash > 0){
				result.setIsNeedCash("1");
			}else{
				result.setIsNeedCash("0");
			}
		}
		result.setCash(cash);
		
		OrderLogger.info("订单模块", "订单创建", "计算订单现金部分，判断是否跳收银台", new Object[]{"订单总价",Arith.div(orderMongo.getTotalPrice(), 1000),"订单优惠金额",cutMoney,"支付的积分对应的钱",addOrderVoReq.getPointMoney(),"现金",result.getCash(),"是否跳收银台",result.getIsNeedCash()});
		
		return result;
	}
}
