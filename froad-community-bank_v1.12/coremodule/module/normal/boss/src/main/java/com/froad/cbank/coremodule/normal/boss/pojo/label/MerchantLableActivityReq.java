package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月22日 下午5:16:10
 * @desc 商户活动列表req
 */
public class MerchantLableActivityReq extends Page {
	
	private String id;
	private String clientId;
	private String activityName;
	//活动编号
	private String activityNo;
	 /**
	   * 状态: 启1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5 *
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	
	
	
}
