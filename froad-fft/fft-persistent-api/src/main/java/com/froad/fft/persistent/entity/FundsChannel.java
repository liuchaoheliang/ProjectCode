package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.PayChannel;

/**
 * 资金渠道
 * 
 * @author FQ
 */
public class FundsChannel extends BaseEntity {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private String shortName;// 资金机构名称
	private String fullName;// 资金机构全名
	private PayChannel channelType;// 渠道类型
	private String payOrg;// 资金机构编号
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public PayChannel getChannelType() {
		return channelType;
	}
	public void setChannelType(PayChannel channelType) {
		this.channelType = channelType;
	}
	public String getPayOrg() {
		return payOrg;
	}
	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}
	
}
