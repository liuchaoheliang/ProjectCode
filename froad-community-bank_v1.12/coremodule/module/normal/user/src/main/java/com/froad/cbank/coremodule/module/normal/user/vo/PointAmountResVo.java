package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * 积分数量兑换金额VO
 */
public class PointAmountResVo {
	
	/**
	 * 积分兑换金额
	 */
	private double amount;
	/**
	 * 积分数量
	 */
	private String point;
	/**
	 * 积分比例
	 */
	private String exchageRate;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getExchageRate() {
		return exchageRate;
	}
	public void setExchageRate(String exchageRate) {
		this.exchageRate = exchageRate;
	}

}
