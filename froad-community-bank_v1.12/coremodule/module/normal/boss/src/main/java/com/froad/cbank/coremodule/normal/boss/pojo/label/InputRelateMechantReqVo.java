package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月28日 下午2:07:15
 * @desc 批量关联商户活动请求vo
 */
public class InputRelateMechantReqVo {
	/**
	 * 活动编号 *
	 */
	@NotEmpty(value="活动编号不能为空")
	private String activityNo; // optional
	/**
	 * 客户端号 *
	 */
	@NotEmpty(value="客户端不能为空")
	private String clientId; // optional
	/**
	 * 当前操作员 *
	 */
	private String operator; // optional
	
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
