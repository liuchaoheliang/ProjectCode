package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankPointSettlementMerchantDetailResVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6818740729107073688L;
	/**
	 * 所属商户
	 */
	public String merchantName; // required
	/**
	 * 联盟积分总计
	 */
	public double froadPointCount; // required
	/**
	 * 银行积分总计
	 */
	public double bankPointCount; // required
	/**
	 * 银行积分比例
	 */
	public String bankPointRate; // required
	/**
	 * 订单数量
	 */
	public int orderCount; // required
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public double getFroadPointCount() {
		return froadPointCount;
	}

	public void setFroadPointCount(double froadPointCount) {
		this.froadPointCount = froadPointCount;
	}

	public double getBankPointCount() {
		return bankPointCount;
	}

	public void setBankPointCount(double bankPointCount) {
		this.bankPointCount = bankPointCount;
	}
	public String getBankPointRate() {
		return bankPointRate;
	}
	public void setBankPointRate(String bankPointRate) {
		this.bankPointRate = bankPointRate;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}


}
