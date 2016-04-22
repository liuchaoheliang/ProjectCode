package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 注册促销规则列表请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月3日 上午2:00:01
 */
public class RegisteredRuleListReq extends Page {
	@Regular(reg = "^[\\s\\S]{0,20}$", value = "活动名称限20字符内")
	private String activeName;//活动名称
	private String clientId;//客户端ID
	private String status;//状态（0-待提交、1-审核中、2-审核不通过、3-启用、4-禁用）
	private String startTime;//活动开始时间
	private String endTime;//活动结束时间
	
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
