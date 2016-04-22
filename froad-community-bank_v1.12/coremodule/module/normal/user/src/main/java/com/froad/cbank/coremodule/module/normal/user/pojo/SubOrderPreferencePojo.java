/**
 * Project Name:coremodule-user
 * File Name:SubOrderProductPojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2015年11月9日下午7:35:53
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;
/**
 * ClassName:SubOrderPreferencePojo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月9日 下午7:35:53
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class SubOrderPreferencePojo {
	
	private String productId;
	
	private Integer cutMoney;
	
	private Integer vipCutMoney;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(Integer cutMoney) {
		this.cutMoney = cutMoney;
	}

	public Integer getVipCutMoney() {
		return vipCutMoney;
	}

	public void setVipCutMoney(Integer vipCutMoney) {
		this.vipCutMoney = vipCutMoney;
	}
	
}
