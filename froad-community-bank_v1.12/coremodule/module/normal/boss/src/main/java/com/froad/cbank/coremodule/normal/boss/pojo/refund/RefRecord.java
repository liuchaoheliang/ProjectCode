package com.froad.cbank.coremodule.normal.boss.pojo.refund;
/**
 * 类描述：退款记录响应对象
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road
 * @time:  2015-5-1下午5:45:46
 */
public class RefRecord {
	private String paymentId;//退款支付流水
	private String paymentType;//支付类型（1-现金、2-联盟积分、3-银行积分、6-赠送积分）
	private String payTypeName;
	private String refundValue;//退还金额或积分
	private String paymentTime;//退款支付时间
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getRefundValue() {
		return refundValue;
	}
	public void setRefundValue(String refundValue) {
		this.refundValue = refundValue;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
}
