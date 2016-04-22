package com.froad.po.order;

public class OrderResultData {

	/**
	 * 订单支付现金
	 */
	private double cash;
	
	/**
	 * 是否需要跳收银台    0 ：不跳   1： 跳   2：纯红包支付完成
	 */
	private String isNeedCash;
	
	/**
	 * 是否纯红包支付订单
	 */
	private boolean redPacketOrder;

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public String getIsNeedCash() {
		return isNeedCash;
	}

	public void setIsNeedCash(String isNeedCash) {
		this.isNeedCash = isNeedCash;
	}

	public boolean isRedPacketOrder() {
		return redPacketOrder;
	}

	public void setRedPacketOrder(boolean redPacketOrder) {
		this.redPacketOrder = redPacketOrder;
	}
	
}
