/**
 * Project Name:Froad Cbank Server Order
 * File Name:BaseSubassembly.java
 * Package Name:com.froad.util.settlement
 * Date:2015-11-5下午2:02:18
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.util.settlement;

import java.util.List;

import com.froad.enums.PaymentMethod;
import com.froad.enums.SettlementStatus;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.po.settlement.Settlement;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.SettlementSupportImpl;
import com.froad.util.Arith;

/**用于辅助结算模块抽出来的公共常用的方法
 * ClassName:BaseSubassembly
 * Reason:	 TODO ADD REASON.
 * Date:     2015-11-5 下午2:02:18
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class BaseSubassembly {

	
	/**
	 * 计算出面对面订单的积分抵扣的现金值 并补全实体对象
	 * countDeductibleCashValueOfFace2face:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-11-5 下午2:15:18
	 * @param order
	 *
	 */
	public static void countDeductibleCashValueOfFace2face(Settlement selttlement,OrderMongo order){
		String payMethodCode = order.getPaymentMethod();
		if(order.getFftPoints() == null){
			order.setFftPoints(0);
		}
		if(order.getBankPoints() == null){
			order.setBankPoints(0);
		}
		LogCvt.info("payMethod: " + payMethodCode);
		
		//重新查询一遍订单，因为传入的order中没有积分值的字段
		order = new OrderSupportImpl().getOrderById(order.getClientId(), order.getOrderId());
		
		if(PaymentMethod.cash.getCode().equals(payMethodCode)){//如果是纯现金支付则未有积分抵扣现金
			selttlement.setDeductiblePointType("");
			selttlement.setDeductibleCashValue(0);
			selttlement.setDeductiblePointValue(0);
		}else{
			//包含积分
			if(PaymentMethod.froadPoints.getCode().equals(payMethodCode) || PaymentMethod.froadPointsAndCash.getCode().equals(payMethodCode)){
				LogCvt.info("包含方付通积分的面对面结算,orderId= " + selttlement.getOrderId() + ",fftPoint=" + order.getFftPoints());
				selttlement.setDeductiblePointType("1");
				selttlement.setDeductibleCashValue((int) Arith.divFloor(order.getFftPoints(),
						com.froad.util.payment.BaseSubassembly.acquireFroadPointPointRate(order.getClientId())));
				selttlement.setDeductiblePointValue(order.getFftPoints());
				//使用的是方付通积分 -- 方付通积分/积分比例，则得到价值的现金值
			}else{
				LogCvt.info("包含银行积分的面对面结算,orderId= " + selttlement.getOrderId()+ ",bankPoint=" + order.getBankPoints());
				selttlement.setDeductiblePointType("2");
				selttlement.setDeductibleCashValue((int) Arith.divFloor(order.getBankPoints(),
						com.froad.util.payment.BaseSubassembly.acquireBankPointPointRate(order.getClientId())));
				selttlement.setDeductiblePointValue(order.getBankPoints());
			}
			selttlement.setPayCash(order.getRealPrice());
		}
		
		
	}
	
	/**
	 * 计算出名优特惠的积分抵扣现金值，并补全对象
	 * countDeductibleCashValueOfSpecial:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-11-6 上午10:10:51
	 * @param settlement
	 * @param product
	 * @param order
	 *
	 */
	public static void countDeductibleCashValueOfSpecial(Settlement settlement,ProductMongo product,OrderMongo order){
		String payMethodCode = order.getPaymentMethod();
		//确定积分的类型
		String pointType = "";
		Integer pointRate = 1;
		if(!PaymentMethod.cash.getCode().equals(payMethodCode)){
			if(PaymentMethod.froadPoints.getCode().equals(payMethodCode) || PaymentMethod.froadPointsAndCash.getCode().equals(payMethodCode)){
				pointType = "1";
				pointRate = com.froad.util.payment.BaseSubassembly.acquireFroadPointPointRate(order.getClientId());
			}else{
				pointType = "2";
//				settlement.setDeductiblePointType("2");
				pointRate = com.froad.util.payment.BaseSubassembly.acquireBankPointPointRate(order.getClientId());
			}
		}else{
			settlement.setDeductiblePointType(pointType);
			settlement.setDeductibleCashValue(0);
			settlement.setDeductiblePointValue(0);
			return;
		}
		settlement.setDeductiblePointType(pointType);
		
		Integer totalCutPoint = product.getTotalPoint();
		if(totalCutPoint != null && totalCutPoint != 0){
			settlement.setDeductiblePointValue(totalCutPoint);
			settlement.setDeductibleCashValue((int)Arith.divFloor(totalCutPoint,pointRate));
			settlement.setPayCash(product.getTotalCash());
		}
	}
	
	/**
	 * 计算出团购的积分抵扣现金值，并补全对象
	 * countDeductibalCashValueOfGroup:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-11-6 下午5:11:29
	 * @param product
	 * @param settlement
	 *
	 */
	public static int countDeductibalCashValueOfGroup(ProductMongo product,Settlement settlement){
		
		Integer quantity = product.getQuantity();
		Integer vipQuantity = product.getVipQuantity();
		
		OrderMongo order = new OrderSupportImpl().getOrderById(settlement.getClientId(),settlement.getOrderId());
		
		//确定积分的类型
		String payMethodCode = order.getPaymentMethod();
		String pointType = "";
		Integer pointRate = 1;
		if(!PaymentMethod.cash.getCode().equals(payMethodCode)){
			if(PaymentMethod.froadPoints.getCode().equals(payMethodCode) || PaymentMethod.froadPointsAndCash.getCode().equals(payMethodCode)){
				pointType = "1";
				pointRate = com.froad.util.payment.BaseSubassembly.acquireFroadPointPointRate(order.getClientId());
			}else{
				pointType = "2";
//				settlement.setDeductiblePointType("2");
				pointRate = com.froad.util.payment.BaseSubassembly.acquireBankPointPointRate(order.getClientId());
			}
		}
		
		
		Integer productCountHistory = 0;//历史已经结算了多少个
		Integer productVipCountHistory = 0;
		List<Settlement> settlementHistorys = new SettlementSupportImpl().querySettlement(settlement.getSubOrderId(),settlement.getProductId());
		if(settlementHistorys != null && settlementHistorys.size() != 0){
			for (Settlement settlementHistory : settlementHistorys) {
				if(!SettlementStatus.settlementfailed.getCode().equals(settlementHistory.getSettleState())){
					productCountHistory += settlementHistory.getProductCount() == null ? 0 : settlementHistory.getProductCount();
					productVipCountHistory += settlementHistory.getProductVipCount() == null ? 0 : settlementHistory.getProductVipCount();
				}
			}
		}
		
		LogCvt.info("子订单: " + settlement.getSubOrderId() + " 商品id:" +  settlement.getPaymentId() 
			+ " --> 已结算商品个数: " + productCountHistory + " 已结算vip商品个数: " + productVipCountHistory);
		
		//TODO productCountHistory 还要加上退款了多少个，退款的也相当于占用了结算的
		//查询该订单的历史退款记录
		List<RefundHistory> refundHis = new RefundSupportImpl().getListByOrderId(settlement.getOrderId());
		
		
		RefundShoppingInfo shoppingInfo = null;
		List<RefundProduct> products = null;
		
		Integer refundCount = 0;
		Integer refundVipCount = 0;
		
		if(refundHis != null && refundHis.size() > 0){
			for (RefundHistory refundHistory : refundHis) {
				shoppingInfo = refundHistory.getShoppingInfo().get(0);
				products = shoppingInfo.getProducts();
				
				if(products != null && products.size() > 0){
					for (RefundProduct refundProduct : products) {
						if(settlement.getProductId().equals(refundProduct.getProductId())){//退款中的商品信息是结算的该商品信息
							
							//处于退款中和退款完成的退款记录
							if("2".equals(refundHistory.getRefundState()) || "3".equals(refundHistory.getRefundState())){
								 refundCount += refundProduct.getQuantity();
								 refundVipCount += refundProduct.getVipQuantity();
							 }
							
						}
					}
				}
			}
		}
		
		LogCvt.info("子订单: " + settlement.getSubOrderId() + " 商品id:" +  settlement.getPaymentId()  +
				" 已退款商品个数: " + refundCount + " 已退款vip商品个数: " + refundVipCount);
		
		//已退款的数量当成已结算 ，在新计算后默认就没有了
		productCountHistory += refundCount;
		productVipCountHistory += refundVipCount;
		
		if(vipQuantity == null || vipQuantity == 0){ //没有VIP购买的数量
			
			settlement.setProductCount((settlement.getProductCount() == null ? 0 : settlement.getProductCount()) + 1);
			settlement.setProductVipCount(settlement.getProductVipCount());
			settlement.setMoney(settlement.getMoney() + product.getMoney());
			
			Integer point = priceArray(product.getPointArray(), productCountHistory,settlement.getProductCount());
			settlement.setDeductiblePointType(pointType);
			settlement.setDeductiblePointValue(point);
			settlement.setDeductibleCashValue((int)Arith.divFloor(point, pointRate));
			
			//用户支付的现金（不包含优惠的金额）
			Integer payCash = priceArray(product.getCashArray(), productCountHistory,settlement.getProductCount());
			settlement.setPayCash(payCash);
			
			return product.getMoney();
			
		}else{//有VIP购买的数量：按照产品（朱博）需求先结算普通数量的价格，再结算VIP的价格
			if(quantity - productCountHistory - 1 >= 0){//普通价格的商品还没有结算完，先结算普通商品
				
				settlement.setProductCount((settlement.getProductCount() == null ? 0 : settlement.getProductCount()) + 1);
				settlement.setProductVipCount(settlement.getProductVipCount());
				settlement.setMoney(settlement.getMoney() + product.getMoney());
				
				Integer point = priceArray(product.getPointArray(), productCountHistory,settlement.getProductCount());
				settlement.setDeductiblePointType(pointType);
				settlement.setDeductiblePointValue(point);
				settlement.setDeductibleCashValue((int)Arith.divFloor(point, pointRate));
				
				//用户支付的现金（不包含优惠的金额）
				Integer payCash = priceArray(product.getCashArray(), productCountHistory,settlement.getProductCount());
				settlement.setPayCash(payCash);
				
				return product.getMoney();
			}else{//VIP商品结算
				settlement.setProductCount(settlement.getProductCount());
				settlement.setProductVipCount((settlement.getProductVipCount() == null ? 0 : settlement.getProductVipCount()) + 1);
				
				Integer point = priceArray(product.getVipPointArray(), productVipCountHistory,settlement.getProductVipCount());
				settlement.setDeductiblePointType(pointType);
				settlement.setDeductiblePointValue(point);
				settlement.setDeductibleCashValue((int)Arith.divFloor(point, pointRate));
				settlement.setMoney(settlement.getMoney() + product.getVipMoney());
				
				//用户支付的现金（不包含优惠的金额）
				Integer payCash = priceArray(product.getVipCashArray(), productVipCountHistory,settlement.getProductVipCount());
				settlement.setPayCash(payCash);
				
				return product.getVipMoney();
			}
		}
		
	}
	
	private static Integer priceArray(Integer[] priceOrgAray,int startIndex,int skewingIndex){
		
		Integer totalPoint = 0;
		if(priceOrgAray != null && priceOrgAray.length > 0){
			for(int i = startIndex ; i < (startIndex + skewingIndex) ; i ++){
				if(i >= priceOrgAray.length){
					break;
				}
				totalPoint += priceOrgAray[i];
			}
		}
		return totalPoint;
	}
	
}
