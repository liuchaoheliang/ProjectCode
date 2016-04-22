/**
 * Project Name:froad-cbank-server-boss
 * File Name:RelateOutletActivityPo.java
 * Package Name:com.froad.po
 * Date:2015年10月27日下午3:28:33
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

/**
 * ClassName:RelateOutletActivityPo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月27日 下午3:28:33
 * @author   asus
 * @version  
 * @see 	 
 */
public class RelateOutletActivityPo implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -1224812831654356846L;
	
	// 门店ID
	private String outletId;
	
	// 权重
	private String weight;

	// 活动编号
	private String activityNo;
	
	//活动Id
	private Long activityId;
	
	// 客户端号
	private String clientId;
	
	// 操作员
	private String operator;

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
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
