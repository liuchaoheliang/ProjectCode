package com.froad.cbank.coremodule.module.normal.bank.vo.report;

/**
 * 金额指标
 * @author bruce
 *
 */
public class AmountQuotaResp {
	
	private Long orderAmount; //金额
	
	private Long orderTotalPrice; //累积金额
	
	private Long refundAmount; //退款金额
	
	private Long refundTotalAmount;//累积退款金额
	
	private Long amountTurnover;//成交金额=金额-退款金额
	 
	private Long amountCumulatiTurnover;//累积成交金额=累积金额-累积退款金额

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(Long orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getRefundTotalAmount() {
		return refundTotalAmount;
	}

	public void setRefundTotalAmount(Long refundTotalAmount) {
		this.refundTotalAmount = refundTotalAmount;
	}

	public Long getAmountTurnover() {
		return amountTurnover;
	}

	public void setAmountTurnover(Long amountTurnover) {
		this.amountTurnover = amountTurnover;
	}

	public Long getAmountCumulatiTurnover() {
		return amountCumulatiTurnover;
	}

	public void setAmountCumulatiTurnover(Long amountCumulatiTurnover) {
		this.amountCumulatiTurnover = amountCumulatiTurnover;
	}
	 
}
