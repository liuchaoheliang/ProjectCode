package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月27日 下午3:04:31
 * @desc 活动列表详情请求vo
 */
public class MerchantActivityDetailReqVo{
	
	@NotEmpty(value="活动id不能为空")
	private long activityId;
	
	@NotEmpty(value="客户端不能为空")
	private String clientId;
	
	private String activityNo;
	
	private String operator;


	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	
	
}
