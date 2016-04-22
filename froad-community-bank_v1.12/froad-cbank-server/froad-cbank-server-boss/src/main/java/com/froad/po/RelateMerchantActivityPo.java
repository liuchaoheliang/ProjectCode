/**
 * Project Name:froad-cbank-server-boss
 * File Name:RelatedMerchant.java
 * Package Name:com.froad.po
 * Date:2015年10月26日上午9:40:41
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

/**
 * ClassName:RelateMerchantActivityPo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月26日 上午9:40:41
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class RelateMerchantActivityPo implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 8702290921464126915L;
	
	// 营业执照
	private String license;
	
	// 权重
	private String weight;
	
	// 活动标签id
	private Long activityId;
	
	// 活动编号
	private String activityNo;
	
	// 客户端号
	private String clientId;
	
	// 操作员
	private String operator;

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

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
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

}
