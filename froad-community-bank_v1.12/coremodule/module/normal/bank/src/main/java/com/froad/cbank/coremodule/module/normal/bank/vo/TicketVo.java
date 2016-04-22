package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 券
 * @author ylchu
 *
 */
public class TicketVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3916509064405187992L;

	private String takeCode;	//提货码
	private String orderId;		//订单号
	private String subOrderId;	//子订单号
	
	public String getTakeCode() {
		return takeCode;
	}
	public void setTakeCode(String takeCode) {
		this.takeCode = takeCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	
	
}
