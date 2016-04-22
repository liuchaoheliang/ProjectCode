package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 订单
 * 
 * @author ylchu
 *
 */
public class BoutiqueOrderReq extends BaseVo implements Serializable {

	private static final long serialVersionUID = -502919366821931158L;
	private String clientId;// 客户端
	private String subOrderId;// 订单id
	private String orderStatus;// 订单状态
	private String providerName;// 供应商名称
	private String productName;// 商品名称

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
