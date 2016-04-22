/**
 * Project Name:froad-cbank-server-order
 * File Name:QueryOrderByBankDto.java
 * Package Name:com.froad.po
 * Date:2015年12月15日上午11:38:44
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

import com.froad.thrift.vo.PageVo;

/**
 * 精品商城银行管理平台订单查询DTO
 * ClassName:QueryOrderByBankDto
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月15日 上午11:38:44
 * @author   kevin
 * @version  
 * @see 	 
 */
public class QueryBoutiqueOrderByBankDto implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 2400778636034544708L;
	
	public String clientId;
 
	public String subOrderId;

	public String orderStatus;

	public String providerName;
	
	public long startTime;

	public long endTime;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
	
	

}
