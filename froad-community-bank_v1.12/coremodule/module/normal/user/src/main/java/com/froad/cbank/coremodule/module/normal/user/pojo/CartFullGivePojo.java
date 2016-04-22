/**
 * Project Name:coremodule-user
 * File Name:ActivePojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2016年2月23日下午2:58:28
 * Copyright (c) 2016, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;


/**
 * ClassName: CartFullGivePojo
 * Function: 满赠活动
 * date: 2016年2月24日 下午3:23:10
 *
 * @author 刘超 liuchao@f-road.com.cn
 * @version 
 */
public class CartFullGivePojo {
	private String fullGiveActiveId;
	private String fullGiveActiveName;
	/**
	 * giveType:红包  or 积分
	 */
	private String giveType;
	
	/**
	 * giveMoney:赠送值
	 */
	private Double giveMoney;
	
	
	public String getFullGiveActiveId() {
		return fullGiveActiveId;
	}
	public void setFullGiveActiveId(String fullGiveActiveId) {
		this.fullGiveActiveId = fullGiveActiveId;
	}
	public String getFullGiveActiveName() {
		return fullGiveActiveName;
	}
	public void setFullGiveActiveName(String fullGiveActiveName) {
		this.fullGiveActiveName = fullGiveActiveName;
	}
	public String getGiveType() {
		return giveType;
	}
	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}
	public Double getGiveMoney() {
		return giveMoney;
	}
	public void setGiveMoney(Double giveMoney) {
		this.giveMoney = giveMoney;
	}
	
}
