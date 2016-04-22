package com.froad.db.chonggou.mongo.refund;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.JSonUtil;

public class RefundPaymentInfo {
	/**
	 * 支付ID
	 */
	private String paymentId = null;
	/**
	 * 类型-积分(联盟或者银行)或现金
	 */
	private String type = null;
	/**
	 * 退款金额
	 */
	private Integer refundValue = null;
	/**
	 * 结果码
	 */
	private String resultCode = null;
	/**
	 * 结果描述
	 */
	private String resultDesc = null;
	/**
	 * @return the paymentId
	 */
	@JSONField(name="payment_id")
	public String getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	@JSONField(name="payment_id")
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @return the type
	 */
	@JSONField(name="type")
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	@JSONField(name="type")
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the refundValue
	 */
	@JSONField(name="refund_value")
	public Integer getRefundValue() {
		return refundValue;
	}
	/**
	 * @param refundValue the refundValue to set
	 */
	@JSONField(name="refund_value")
	public void setRefundValue(Integer refundValue) {
		this.refundValue = refundValue;
	}
	/**
	 * @return the resultCode
	 */
	@JSONField(name="result_code")
	public String getResultCode() {
		return resultCode;
	}
	/**
	 * @param resultCode the resultCode to set
	 */
	@JSONField(name="result_code")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 * @return the resultDesc
	 */
	@JSONField(name="result_desc")
	public String getResultDesc() {
		return resultDesc;
	}
	/**
	 * @param resultDesc the resultDesc to set
	 */
	@JSONField(name="result_desc")
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	/**
	 * 生成JSON字符串
	 * 
	 * @return
	 */
	public String toJsonString(){
		return JSonUtil.toJSonString(this);
	}
}
