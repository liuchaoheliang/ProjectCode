package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * 卡密支付VO
 * @author wufei
 *
 */
public class CardCloseVo {

	/**
	 * 支付渠道名称
	 */
	private String payChannelName;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行LOGO url
	 */
	private String iconUrl;
	

	public String getPayChannelName() {
		return payChannelName;
	}

	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	
	
}
