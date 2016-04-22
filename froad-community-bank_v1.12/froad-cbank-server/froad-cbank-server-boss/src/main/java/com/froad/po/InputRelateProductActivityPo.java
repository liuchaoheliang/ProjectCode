/**
 * Project Name:froad-cbank-server-boss
 * File Name:InputRelateProductActivityPo.java
 * Package Name:com.froad.po
 * Date:2015年10月29日上午11:37:06
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

/**
 * ClassName:InputRelateProductActivityPo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月29日 上午11:37:06
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class InputRelateProductActivityPo implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -7565442291394604862L;

	// 序号
	private Long id;

	// 商品名称
	private String productName;

	// 商品ID
	private String productId;

	// 权重
	private String weight;

	// 活动编号
	private String activityNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

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

	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	
}
