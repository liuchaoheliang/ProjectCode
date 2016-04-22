package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月27日 下午4:41:46
 * @desc 关联商户voReq
 */
public class RelateMerchantActivityVoReq {
	/**
	 * 营业执照 *
	 */
	@NotEmpty(value="营业执照不能为空")
	private String license; // optional
	/**
	 * 权重 *
	 */
	@NotEmpty(value="权重不能为空")
	private String weight; // optional
	/**
	 * 活动编号 *
	 */
	private String activityNo; // optional
	/**
	 * 客户端号 *
	 */
	@NotEmpty(value="客户端不能为空")
	private String clientId; // optional
	/**
	 * 操作员 *
	 */
	private String operator; // optional
	
	@NotEmpty(value="活动id不能为空")
	private Long  activityId;
	
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
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
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	
	

}
