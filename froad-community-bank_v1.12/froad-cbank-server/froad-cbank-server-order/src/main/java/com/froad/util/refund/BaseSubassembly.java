/**
 * Project Name:Froad Cbank Server Order
 * File Name:BaseSubassembly.java
 * Package Name:com.froad.util.refund
 * Date:2015-11-9下午3:42:28
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.util.refund;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.RefundProductBean;
import com.froad.logback.LogCvt;
import com.froad.util.Arith;
import com.froad.util.payment.Const;


/**
 * ClassName:BaseSubassembly
 * Reason:	 TODO ADD REASON.
 * Date:     2015-11-9 下午3:42:28
 * @author   Zxy
 * @version  
 * @see 	 
 */
public class BaseSubassembly {
	
	
	
	private static List<Integer> priceArray(Integer[] priceOrgAray,int startIndex,int skewingIndex){
		List<Integer> priceArray = new ArrayList<Integer>();
		if(priceOrgAray != null && priceOrgAray.length > 0){
			for(int i = startIndex ; i < (startIndex + skewingIndex) ; i ++){
				priceArray.add(priceOrgAray[i]);
			}
		}else{
			for(int i = startIndex ; i < (startIndex + skewingIndex) ; i ++){
				priceArray.add(0);
			}
		}
		return priceArray;
	}
	
//	private static void priceProcutMoney(Integer[] priceOrgAray,Integer[] pricePointAray,int startIndex,int skewingIndex,
//			RefundProductBean refundProduct,int price,String payType,String pointRate){
//		if(com.froad.util.payment.BaseSubassembly.isPurePointsPayment(payType)){
//			refundProduct.setRefundTotalPrice(0);
//			return;
//		}
//		
//		boolean isPureCash = false;
//		Integer rate = 0;
//		if(com.froad.util.payment.BaseSubassembly.isPureCashPayment(payType)){
//			isPureCash = true;
//		}else{
//			rate = Integer.parseInt(pointRate);
//		}
//		
//		int total = 0;
//		List<Integer> priceArray = new ArrayList<Integer>();
//		List<Integer> pricePointArray = new ArrayList<Integer>();
//		
//		for(int i = startIndex ; i < (startIndex + skewingIndex) ; i ++){
//			if(priceOrgAray == null || priceOrgAray.length == 0){
//				priceArray.add(0);
//			}else{
//				priceArray.add(priceOrgAray[i]);
//			}
//		}
//		for(int i = startIndex ; i < (startIndex + skewingIndex) ; i ++){
//			if(pricePointAray == null || pricePointAray.length == 0){
//				pricePointArray.add(0);
//			}else{
//				pricePointArray.add(pricePointAray[i]);
//			}
//		}
//		
//		for (int i = 0 ; i < priceArray.size() ; i ++) {
//			total += price - priceArray.get(i) - (isPureCash ? 0 : Arith.mul(Arith.divFloor(Arith.div(pricePointArray.get(i),Const.HDOP_1000),rate),Const.HDOP_1000));
//		}
//		
//		if(refundProduct.getRefundTotalPrice() == null){
//			refundProduct.setRefundTotalPrice(0);
//		}
//		
//		refundProduct.setRefundTotalPrice(refundProduct.getRefundTotalPrice() + total);
//	}
	
	
	private static void priceProcutMoney(Integer[] cashPointAray, int startIndex, int skewingIndex,RefundProductBean refundProduct, int price, String payType,String pointRate) {

		int total = 0;
		List<Integer> cashPointArray = new ArrayList<Integer>();

		for (int i = startIndex; i < (startIndex + skewingIndex); i++) {
			if (cashPointAray == null || cashPointAray.length == 0) {
				cashPointArray.add(0);
			} else {
				cashPointArray.add(cashPointAray[i]);
			}
		}

		if (refundProduct.getRefundTotalPrice() == null) {
			refundProduct.setRefundTotalPrice(0);
		}
		
		if(cashPointArray.size() != 0){
			for (Integer integer : cashPointArray) {
				total += integer;
			}
		}

		

		refundProduct.setRefundTotalPrice(refundProduct.getRefundTotalPrice() + total);
	}
	
	/**
	 * 退款的全部是普通商品
	 * countRefundOfAllCommonProduct:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-11-9 下午3:45:25
	 * @return
	 *
	 */
	public static Integer[] countRefundOfAllCommonProduct(RefundProductBean refundProduct,String payType,String pointRate){
		
		int wantToRefundQuantity = refundProduct.getActualRefundQuantity();
		int refundedQuantity = refundProduct.getRefundedQuantity();//已经退款的数量
		
		Integer[] refundAmount = new Integer[2];
		
		Integer pointAmount = 0;
		Integer cashAmount = 0;
		
		//计算出退款商品的价格组段
		List<Integer> cashArray = priceArray(refundProduct.getCashArray(), refundedQuantity, wantToRefundQuantity);
		List<Integer> pointArray = priceArray(refundProduct.getPointArray(), refundedQuantity, wantToRefundQuantity);
		priceProcutMoney(refundProduct.getCashArray(), refundedQuantity, wantToRefundQuantity, refundProduct, refundProduct.getPrice(),payType,pointRate);
		for (Integer integer : cashArray) {
			cashAmount += integer;
		}
		for (Integer integer : pointArray) {
			pointAmount += integer;
		}
		
		refundAmount[0] = pointAmount;
		refundAmount[1] = cashAmount;
		
		LogCvt.info("按照积分拆分逻辑退款处理后的数据为: {product_id:" + refundProduct.getProductId() 
				+ ",refunded_qutity:" + refundedQuantity
				+ ",want_to_refund_qutity:" + wantToRefundQuantity + "} 实际计算积分:现金金额 --> " + JSONObject.toJSONString(refundAmount));
		return refundAmount;
	}
	
	/**
	 * 退款的全部是VIP商品
	 * countRefundOfAllVIPProduct:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-11-9 下午4:30:17
	 * @param refundProduct
	 * @param order
	 * @param pointRate
	 * @return
	 *
	 */
	public static Integer[] countRefundOfAllVIPProduct(RefundProductBean refundProduct,String payType,String pointRate){
		int wantToRefundVipQuantity = refundProduct.getActualRefundVipQuantity();
		int refundedVipQuantity = refundProduct.getRefundedVipQuantity();
		
		Integer[] refundAmount = new Integer[2];
		Integer pointAmount = 0;
		Integer cashAmount = 0;

		
		//计算出退款商品的价格组段
		List<Integer> cashVipArray = priceArray(refundProduct.getVipCashArray(), refundedVipQuantity, wantToRefundVipQuantity);
		List<Integer> pointVipArray = priceArray(refundProduct.getVipPointArray(), refundedVipQuantity, wantToRefundVipQuantity);
		priceProcutMoney(refundProduct.getVipCashArray(),refundedVipQuantity, wantToRefundVipQuantity, refundProduct, refundProduct.getVipPrice(),payType,pointRate);
		for (Integer integer : cashVipArray) {
			cashAmount += integer;
		}
		for (Integer integer : pointVipArray) {
			pointAmount += integer;
		}
		
		refundAmount[0] = pointAmount;
		refundAmount[1] = cashAmount;
				
		
		LogCvt.info("按照积分拆分逻辑退款处理后的数据为: {product_id:" + refundProduct.getProductId() 
				+ ",refunded_vip_qutity:" + refundedVipQuantity
				+ ",want_to_refund_vip_qutity:" + wantToRefundVipQuantity + "} 实际计算积分:现金金额 --> " + JSONObject.toJSONString(refundAmount));
		return refundAmount;
	}
	
	public static Integer[] countRefundOfCommonAndVIPProduct(RefundProductBean refundProduct,String payType,String pointRate){

		int refundedQuantity = refundProduct.getRefundedQuantity();//已经退款的普通数量
		int wantToRefundQuantity = refundProduct.getQuantity() - refundedQuantity;//想要退款的普通商品数量（全部）
		
		int refundedVipQuantity = 0;//组合退VIP&普通，则此次请求一定是第一次退vip 历史vip退款一定为0
		int wantToRefundVipQuantity = refundProduct.getActualRefundVipQuantity();
				
		Integer[] refundAmount = new Integer[2];
		Integer pointAmount = 0;
		Integer cashAmount = 0;
		
		//先处理普通的
		List<Integer> cashArray = priceArray(refundProduct.getCashArray(), refundedQuantity, wantToRefundQuantity);
		List<Integer> pointArray = priceArray(refundProduct.getPointArray(), refundedQuantity, wantToRefundQuantity);
		priceProcutMoney(refundProduct.getCashArray(), refundedQuantity, wantToRefundQuantity, refundProduct, refundProduct.getPrice(),payType,pointRate);

		for (Integer integer : cashArray) {
			cashAmount += integer;
		}
		for (Integer integer : pointArray) {
			pointAmount += integer;
		}
		
		List<Integer> cashVipArray = priceArray(refundProduct.getVipCashArray(), refundedVipQuantity, wantToRefundVipQuantity);
		List<Integer> pointVipArray = priceArray(refundProduct.getVipPointArray(), refundedVipQuantity, wantToRefundVipQuantity);
		priceProcutMoney(refundProduct.getVipCashArray(),refundedVipQuantity, wantToRefundVipQuantity, refundProduct, refundProduct.getVipPrice(),payType,pointRate);

		for (Integer integer : cashVipArray) {
			cashAmount += integer;
		}
		for (Integer integer : pointVipArray) {
			pointAmount += integer;
		}
		refundAmount[0] = pointAmount;
		refundAmount[1] = cashAmount;
		LogCvt.info("按照积分拆分逻辑退款处理后的数据为: {product_id:" + refundProduct.getProductId() 
				+ ",refunded_qutity:" + refundedQuantity
				+ ",want_to_refund_qutity:" + wantToRefundQuantity
				+ ",refunded_vip_qutity:" + refundedVipQuantity
				+ ",want_to_refund_vip_qutity:" + wantToRefundVipQuantity + "} 实际计算积分:现金金额 --> " + JSONObject.toJSONString(refundAmount));
		return refundAmount;
	}

}
