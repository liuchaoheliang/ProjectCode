package com.froad.logic.impl.order;

public class OrderDataResult {
	/**
	 * 商品总价（不含运费）
	 */
	private double totalPrice;
	
	/**
	 * 商品实付款（含满减金额）
	 */
	private double payCashAndCutMoney;
    
	/**
	 * 商品积分抵扣的钱
	 */
	private double payPointMoney;

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getPayCashAndCutMoney() {
		return payCashAndCutMoney;
	}

	public void setPayCashAndCutMoney(double payCashAndCutMoney) {
		this.payCashAndCutMoney = payCashAndCutMoney;
	}

	public double getPayPointMoney() {
		return payPointMoney;
	}

	public void setPayPointMoney(double payPointMoney) {
		this.payPointMoney = payPointMoney;
	}

	
	
}
