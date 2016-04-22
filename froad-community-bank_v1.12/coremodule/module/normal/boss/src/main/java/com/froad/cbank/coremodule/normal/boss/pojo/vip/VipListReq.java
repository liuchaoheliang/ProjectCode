package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;


/**
 * VIP会员列表请求类
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月30日 上午9:36:30
 */
public class VipListReq extends Page {
	private Long beginTime;
	private Long endTime;
	private String bankOrg;//银行渠道
	private String labelId;//机构ID
	private String clientChannel;//开通方式
	private Long userId;
	private String token;
	
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getBankOrg() {
		return bankOrg;
	}
	public void setBankOrg(String bankOrg) {
		this.bankOrg = bankOrg;
	}
	public String getClientChannel() {
		return clientChannel;
	}
	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
