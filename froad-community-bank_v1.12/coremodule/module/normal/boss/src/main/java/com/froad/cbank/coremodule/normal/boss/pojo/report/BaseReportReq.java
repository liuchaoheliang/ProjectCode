package com.froad.cbank.coremodule.normal.boss.pojo.report;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 报表请求
 * @ClassName BaseReportReq
 * @author zxl
 * @date 2015年11月3日 上午10:09:41
 */
public class BaseReportReq extends Page{
	
	/**
	 * 报表类型
	 */
	private int type;
	
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
	 * 是否分日查询 1 是 0否
	 */
	private int daily;
	
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
	 * 机构
	 */
	private String orgCode;
	
	

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
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
