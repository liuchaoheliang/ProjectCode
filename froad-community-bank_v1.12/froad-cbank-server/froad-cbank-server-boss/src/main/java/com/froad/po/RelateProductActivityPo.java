/**
 * Project Name:froad-cbank-server-boss
 * File Name:RelateProductActivityPo.java
 * Package Name:com.froad.po
 * Date:2015年10月29日上午10:39:08
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

/**
 * ClassName:RelateProductActivityPo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月29日 上午10:39:08
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class RelateProductActivityPo implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 583307721866060047L;

	// 商品id
	private String productId;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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
