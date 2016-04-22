package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 红包规则列表查询条件请求实体类
 * @author yfy
 */
public class VouchersRuleListReq extends Page {
	/**
	 * 规则id
	 */
	private String activeId;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 规则名称
	 */
	private String activeName;
	/**
	 * 活动类型
	 */
	private String activeType;
	/**
	 * 状态0-待提交1-审核中2-审核不通过3-启动 4-禁止
	 */
	private String status;
	
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
