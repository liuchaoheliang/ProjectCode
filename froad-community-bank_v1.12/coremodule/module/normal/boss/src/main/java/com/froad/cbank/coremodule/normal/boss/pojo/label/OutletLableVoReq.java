package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 新增或修改门店推荐活动实体类
 * @author yfy
 * @date 2015年10月22日  下午16:11:11
 */
public class OutletLableVoReq {
	
	/**
	 * 活动ID
	 */
	private String activityId;
	/**
	 * 客户端ID
	 */
	@NotEmpty("客户端不能为空")
	private String clientId;
	/**
	 * 活动名称 
	 */
	@NotEmpty("活动名称不能为空")
	private String activityName;
	/**
	 * 活动编号 
	 */
	@NotEmpty("活动编号不能为空")
	private String activityNo;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 描述
	 */
	@NotEmpty("描述ID不能为空")
	private String activityDesc;
	/**
	 * 活动Logo
	 */
	private String logoUrl;
	
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
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
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
}
