/**
 * Project Name:coremodule-bank
 * File Name:SettlementReqVo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年8月17日下午1:14:21
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:SettlementReqVo
 * Date:     2015年8月17日 下午1:14:21
 * @author   明灿
 * @version  
 * @see 	 
 */
public class SettlementReqVo extends BaseVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;// 账单编号
	private String type;// 结算类型 (1-团购,2-名优特惠,3-面对面)
	private String ticket;// 券码
	/** 用户id */
	private String userId;
	/** 安全令牌 */
	private String token;
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
