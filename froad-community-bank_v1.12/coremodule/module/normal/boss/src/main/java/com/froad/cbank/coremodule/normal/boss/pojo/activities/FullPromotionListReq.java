package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 满额促销列表查询条件请求实体类
 * @author yfy
 */
public class FullPromotionListReq extends Page {
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 状态0-待提交1-审核中2-审核不通过3-启动 4-禁止
	 */
	private String status;
	/**
	 * 活动开始时间
	 */
	private String startTime;
	/**
	 * 活动结束时间
	 */
	private String endTime;
	
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	
}
