/**
 * Project Name:coremodule-user
 * File Name:PreferenceViewVo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.vo
 * Date:2015年11月9日下午4:52:52
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.vo;
/**
 * ClassName:PreferenceViewVo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月9日 下午4:52:52
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class PreferenceViewVo {
	
	/**
	 * productId:商品Id
	 */
	private String productId;
	/**
	 * productTotalMoney:商品总价
	 */
	private Double productTotalMoney;
	/**
	 * vipTotalMoney:VIP商品总价
	 */
	private Double vipTotalMoney;
	/**
	 * productName:商品名称
	 */
	private String productName;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Double getProductTotalMoney() {
		return productTotalMoney;
	}
	public void setProductTotalMoney(Double productTotalMoney) {
		this.productTotalMoney = productTotalMoney;
	}
	public Double getVipTotalMoney() {
		return vipTotalMoney;
	}
	public void setVipTotalMoney(Double vipTotalMoney) {
		this.vipTotalMoney = vipTotalMoney;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
