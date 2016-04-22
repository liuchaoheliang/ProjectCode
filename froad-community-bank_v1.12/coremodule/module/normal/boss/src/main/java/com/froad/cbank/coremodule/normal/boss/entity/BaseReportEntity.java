package com.froad.cbank.coremodule.normal.boss.entity;

import org.apache.ibatis.type.Alias;

@Alias("baseReport")
public class BaseReportEntity {
	
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 客户端
	 */
	private String clientId;

	/**
	 * 业务类型 
	 * 0--全部、1--团购、2--预售
	 */
	private String businessType;
	/**
	 * 注册渠道
	 * 0--全部、1--银行渠道、3--注册渠道
	 */
	private String channel;
	
	/**
	 * 是否分日查询 1 是 0否
	 */
	private int daily;
	
	/**
	 * 机构
	 */
	private String orgCode;
	
	
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
