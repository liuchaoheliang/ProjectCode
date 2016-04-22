/**
 * Project Name:froad-cbank-server-boss
 * File Name:InputRelateOutletActivityPo.java
 * Package Name:com.froad.po
 * Date:2015年10月28日下午2:46:18
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

/**
 * ClassName:InputRelateOutletActivityPo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月28日 下午2:46:18
 * @author   asus
 * @version  
 * @see 	 
 */
public class InputRelateOutletActivityPo implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	// 序号
	private Long id;
	
	// 门店名称
	private String outletName;
	
	// 门店id
	private String outletId;
	
	// 权重
	private String weight;
	
	// 活动编号
	private String activityNo;
	
	// 活动标签id
	private Long activityId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

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

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

}
