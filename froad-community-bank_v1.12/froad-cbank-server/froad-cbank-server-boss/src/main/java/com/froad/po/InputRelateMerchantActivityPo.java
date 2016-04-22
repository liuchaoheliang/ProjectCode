/**
 * Project Name:froad-cbank-server-boss
 * File Name:InputRelateMerchantActivityPo.java
 * Package Name:com.froad.po
 * Date:2015年10月27日下午2:07:36
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

/**
 * ClassName:InputRelateMerchantActivityPo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月27日 下午2:07:36
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class InputRelateMerchantActivityPo implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -433375709921595047L;
	
	// 序号
	private Long id;
	
	// 商户名称
	private String merchantName;
	
	// 商户ID
	private String merchantId;
	
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
