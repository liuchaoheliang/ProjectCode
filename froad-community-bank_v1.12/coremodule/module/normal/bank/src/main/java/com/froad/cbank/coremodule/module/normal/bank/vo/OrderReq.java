package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 订单
 * @author ylchu
 *
 */
public class OrderReq extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 418443641596964988L;
	
	private String orgCode;//所属机构
	private String type;//类型
	private String orderId;//订单id
	private String orderStatus;//订单状态
	private String merchantName;//商户名称
	private String subOrderId;	//子订单ID
	private String userId;//用户id
	private String token;//安全令牌
	/**预售*/
	private String deliveryOption;//配送方式(0:配送;1:自提)
	/**积分兑换查询类型 线上积分兑换、线下积分兑换(默认)*/
	private String pointOrderType;//积分订单类型  (0:线上积分兑换;1:线下积分兑换)

	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public String getPointOrderType() {
		return pointOrderType;
	}
	public void setPointOrderType(String pointOrderType) {
		this.pointOrderType = pointOrderType;
	}
	
}