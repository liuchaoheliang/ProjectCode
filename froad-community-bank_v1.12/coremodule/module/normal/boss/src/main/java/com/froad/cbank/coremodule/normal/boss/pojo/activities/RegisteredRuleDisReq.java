package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 注册促销规则禁用请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月3日 下午2:25:31
 */
public class RegisteredRuleDisReq {
	@NotEmpty("活动ID为空")
	private String activeId;
	@NotEmpty("客户端ID为空")
	private String clientId;
	
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
}
