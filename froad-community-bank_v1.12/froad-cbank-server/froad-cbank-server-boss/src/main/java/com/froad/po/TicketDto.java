/**
 * Project Name:froad-cbank-server-boss
 * File Name:TicketDto.java
 * Package Name:com.froad.po
 * Date:2015年8月7日下午12:42:23
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;

/**
 * ClassName:TicketDto
 * Reason:	 TODO ADD REASON.
 * Date:     2015年8月7日 下午12:42:23
 * @author   kevin
 * @version  
 * @see 	 
 */
public class TicketDto extends Ticket implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 748982937587642148L;
	
	//收(提)货人姓名
	private String deliveryUserName;

	//支付时间
	private Long paymentTime;
	
	//提货开始时间
	private Long deliveryStartTime;
	
	//预售结束时间
	private Long deliveryEndTime;
	
	//提（收）货类型
	private String deliveryType;

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Long getDeliveryStartTime() {
		return deliveryStartTime;
	}

	public void setDeliveryStartTime(Long deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}

	public Long getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(Long deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeliveryUserName() {
		return deliveryUserName;
	}

	public void setDeliveryUserName(String deliveryUserName) {
		this.deliveryUserName = deliveryUserName;
	}

	
	
}
