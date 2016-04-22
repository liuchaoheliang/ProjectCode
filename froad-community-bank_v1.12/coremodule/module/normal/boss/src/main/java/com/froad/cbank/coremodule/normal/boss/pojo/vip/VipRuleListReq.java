package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月16日 下午3:02:22
 * @version 1.0
 * @desc VIP规则列表请求参数
 */
public class VipRuleListReq extends Page {
	private String clientId;
	/*
	 * 0 - 未生效
	 * 1 - 已生效
	 * 2 - 已作废
	 */
	private String status;
	private String activitiesName;
	
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
	public String getActivitiesName() {
		return activitiesName;
	}
	public void setActivitiesName(String activitiesName) {
		activitiesName = activitiesName == null ? "" : activitiesName;
		this.activitiesName = activitiesName;
	}
}
