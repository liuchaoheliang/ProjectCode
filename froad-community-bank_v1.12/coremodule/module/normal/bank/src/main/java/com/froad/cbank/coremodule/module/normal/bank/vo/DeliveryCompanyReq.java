package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 物流公司
 * 
 * @author ylchu
 *
 */
public class DeliveryCompanyReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2061603726033901344L;

	private String name;
	private Integer orderNumber;
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
