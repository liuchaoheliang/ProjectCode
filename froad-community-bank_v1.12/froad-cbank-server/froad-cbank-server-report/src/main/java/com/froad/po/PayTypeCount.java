package com.froad.po;

public class PayTypeCount {
	private String paymentMethod;
	private Long totalOrder;
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Long getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(Long totalOrder) {
		this.totalOrder = totalOrder;
	}
	
}
