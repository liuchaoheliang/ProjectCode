/**
 * Project Name:coremodule-user
 * File Name:FindActivePojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2015年11月6日下午4:53:37
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * ClassName:FindActivePojo Reason: TODO ADD REASON. Date: 2015年11月6日 下午4:53:37
 * 
 * @author 刘超 liuchao@f-road.com.cn
 * @version
 * @see
 */
public class FindActivePojo {
	/**
	 * 活动id
	 */
	public String activeId; // required
	/**
	 * 活动名称
	 */
	public String activeName; // required
	/**
	 * 活动类型
	 */
	public String activeType; // required
	/**
	 * 活动描述
	 */
	public String description;
	
	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getActiveType() {
		return activeType;
	}

	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
