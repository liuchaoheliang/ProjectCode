/**
 * Project Name:coremodule-bank
 * File Name:OrderTypeEnum.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.enums
 * Date:2015年9月9日下午6:04:19
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.enums;
/**
 * ClassName:订单优化订单类型枚举
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月9日 下午6:04:19
 * @author   明灿
 * @version  
 * @see 	 
 */
public enum OrderTypeEnum {

	FACE_TO_FACE("0", "面对面订单"), 
	GROUP("1", "团购订单"), 
	PRESALE("2", "精品预售订单"),
	SPECIAL("3", "名优特惠订单"),
	ONLINE("4", "精品预售订单"),
	OFFLINE("5", "精品预售订单"),
	BOUTIQUE("6", "精品商城订单");
	
	OrderTypeEnum(String code,String description){
		this.code = code;
		this.description = description;
	}
	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}

}
