package com.froad.cbank.coremodule.normal.boss.pojo.product;

public class ActivityProductPointsRes {
	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 送积分值
	 */
	private double givePoints;
	/**
	 * 送分状态:{"0":"赠送失败"，"1":"已赠送"，"2":"未赠送"，"3":"退分失败"，"4":"退分成功"}
	 */
	private String giveState;
	/**
	 * 送分时间
	 */
	private String paymentTime;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getGivePoints() {
		return givePoints;
	}

	public void setGivePoints(double givePoints) {
		this.givePoints = givePoints;
	}
	
	public String getGiveState() {
		return giveState;
	}

	public void setGiveState(String giveState) {
		this.giveState = giveState;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

}
