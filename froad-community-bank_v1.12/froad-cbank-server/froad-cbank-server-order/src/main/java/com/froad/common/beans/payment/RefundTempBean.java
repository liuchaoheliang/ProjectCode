package com.froad.common.beans.payment;

import com.froad.enums.RefundState;


/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: RefundTempBean
 * @Description: 退款的传输数据
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月28日 上午11:09:50
 */
public class RefundTempBean {

	/**
	 * 退款的赠送积分流水
	 */
	private String presentPointPaymentId;
	
	/**
	 * 退款的用户付积分流水
	 */
	private String paymentPointPaymentId;
	
	/**
	 * 退款的用户付现金流水
	 */
	private String paymentCashPaymentId;
	
	/**
	 * 
	 */
	private RefundState refundPresentPointState;//赠送积分退款结果
	
	/**
	 * 
	 */
	private RefundState refundPointState; //用户积分退款结果
	
	/**
	 * 
	 */
	private RefundState refundCashState; //用户现金退款结果
	
	
	
	public String getPresentPointPaymentId() {
		return presentPointPaymentId;
	}

	public void setPresentPointPaymentId(String presentPointPaymentId) {
		this.presentPointPaymentId = presentPointPaymentId;
	}

	public String getPaymentPointPaymentId() {
		return paymentPointPaymentId;
	}

	public void setPaymentPointPaymentId(String paymentPointPaymentId) {
		this.paymentPointPaymentId = paymentPointPaymentId;
	}

	public String getPaymentCashPaymentId() {
		return paymentCashPaymentId;
	}

	public void setPaymentCashPaymentId(String paymentCashPaymentId) {
		this.paymentCashPaymentId = paymentCashPaymentId;
	}

	public RefundState getRefundPointState() {
		return refundPointState;
	}

	public void setRefundPointState(RefundState refundPointState) {
		this.refundPointState = refundPointState;
	}

	public RefundState getRefundPresentPointState() {
		return refundPresentPointState;
	}

	public void setRefundPresentPointState(RefundState refundPresentPointState) {
		this.refundPresentPointState = refundPresentPointState;
	}

	public RefundState getRefundCashState() {
		return refundCashState;
	}

	public void setRefundCashState(RefundState refundCashState) {
		this.refundCashState = refundCashState;
	}
	
	
	
}
