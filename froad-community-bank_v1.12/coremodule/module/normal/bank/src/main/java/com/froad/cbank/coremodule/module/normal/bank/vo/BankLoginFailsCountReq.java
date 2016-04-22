/**
 * Project Name:coremodule-bank
 * File Name:BankLoginFailsCountReq.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年12月18日下午12:32:54
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:BankLoginFailsCountReq Reason: 下午12:32:54
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class BankLoginFailsCountReq implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1900815139122444359L;
	private String userName;// 登录名
	private String type;// 0:普通用户 1:联合登录

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
