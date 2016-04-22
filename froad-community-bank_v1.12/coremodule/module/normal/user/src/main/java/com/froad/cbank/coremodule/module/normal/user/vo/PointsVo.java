package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * @Description: 积分支付渠道
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月23日 上午11:34:35
 */
public class PointsVo {

	private String bankName;
	/**
	 * 支付渠道名称
	 */
	private String payChannelName;
	/**
	 * LOGO url
	 */
	private String iconUrl;
	
	/**
	 * 积分兑换比例
	 */
	private String pointRate;
	
	
	

	
	public String getPointRate() {
		return pointRate;
	}
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
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
	public String getPayChannelName() {
		return payChannelName;
	}
	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}

}
