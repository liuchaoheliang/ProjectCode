package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 活动标签查询条件实体类
 * @author yfy
 * @date 2015年10月21日  下午13:51:31
 */
public class OutletLableListVoReq extends Page {

	/**
	 * 客户端
	 */
	private String clientId;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 活动名称
	 */
	private String activityName;
	/**
	 *活动编号
	 */
	private String activityNo;
	/**
	 *活动ID
	 */
	private Long activityId;
	
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
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
}
