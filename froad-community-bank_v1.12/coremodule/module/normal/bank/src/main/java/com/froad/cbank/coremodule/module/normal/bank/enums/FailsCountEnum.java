/**
 * Project Name:coremodule-bank
 * File Name:ClientIdEnum.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.enums
 * Date:2015年9月9日上午9:38:29
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.enums;

/**
 * 
 * ClassName: ClientIdEnum Function: 客服端id枚举 date: 2015年9月9日 上午9:38:46
 *
 * @author 明灿
 * @version
 */
public enum FailsCountEnum {

	VE("0"), // 普通
	bankVe("1"), // 联合
	NOT_EXIST("8001");// 用户不存在返回码

	FailsCountEnum(String code) {
		this.code = code;
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
