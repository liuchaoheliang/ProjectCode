package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * 贴膜卡信息VO
 */
public class SmartCardVo {
	
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 支付渠道名称
	 */
	private String payChannelName;
	/**
	 * 银行LOGO url
	 */
	private String iconUrl;
	/**
	 * 快捷银行卡号
	 */
	private String fastCardNo;
	
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
	public String getFastCardNo() {
		return fastCardNo;
	}
	public void setFastCardNo(String fastCardNo) {
		this.fastCardNo = fastCardNo;
	}
	public String getPayChannelName() {
		return payChannelName;
	}
	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}
	
	
}