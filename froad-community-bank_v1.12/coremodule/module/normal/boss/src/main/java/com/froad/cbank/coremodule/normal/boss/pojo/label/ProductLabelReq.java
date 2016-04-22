package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 商品标签请求
 * @ClassName ProductLabelReq
 * @author zxl
 * @date 2015年10月28日 下午2:43:01
 */
public class ProductLabelReq extends Page{
	
	/**
	 * 客户端
	 */
	private String clientId;
	
	/**
	 * 活动名
	 */
	private String activityName;
	
	/**
	 * 状态
	 */
	private String status;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
