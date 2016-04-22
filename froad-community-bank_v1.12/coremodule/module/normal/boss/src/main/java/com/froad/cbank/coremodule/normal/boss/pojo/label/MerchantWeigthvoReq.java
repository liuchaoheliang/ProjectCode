package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月27日 下午3:12:04
 * @desc 调整关联商户权重req
 */
public class MerchantWeigthvoReq {
	
	@NotEmpty(value="id不能为空")
	private String id;
	@NotEmpty(value="客户端不能为空")
	private String clientId;
	@NotEmpty(value="权重不能为空")
	private String weight;
	private String operator;
	 /**
	   * 活动编号 *
	   */
	private String activityNo; // required
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	
	
	
}
