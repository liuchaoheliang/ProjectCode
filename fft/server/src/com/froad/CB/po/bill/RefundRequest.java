package com.froad.CB.po.bill;

import java.math.BigDecimal;

import com.froad.util.Util;

/**
 * 类描述：退款请求
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Mar 23, 2013 1:30:56 PM
 */
public class RefundRequest {

	private String refundOrderID;//退款订单号
	private String refundAmount;//退款金额
	private String refundType;// 见paycommand退款
	private String refundReason;//退款原因
	private String orderId;//原订单号transId
	private String orderAmount;//订单金额
	private String orderSupplier;//订单供应商(合并支付的退款必填)
	/**添加辅助字段**/
	private Boolean combinePay;//原支付是否走的合并支付接口
	

	public String getRefundOrderID() {
		return refundOrderID;
	}

	public void setRefundOrderID(String refundOrderID) {
		this.refundOrderID = refundOrderID;
	}

	public String getRefundAmount() {
		if(refundAmount==null||"".equals(refundAmount.trim())){
			return refundAmount;
		}
		return Util.formatMoney(new BigDecimal(refundAmount.trim())).toString();
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Boolean getCombinePay() {
		return combinePay;
	}

	public void setCombinePay(Boolean combinePay) {
		this.combinePay = combinePay;
	}

	public String getOrderSupplier() {
		return orderSupplier;
	}

	public void setOrderSupplier(String orderSupplier) {
		this.orderSupplier = orderSupplier;
	}

}
