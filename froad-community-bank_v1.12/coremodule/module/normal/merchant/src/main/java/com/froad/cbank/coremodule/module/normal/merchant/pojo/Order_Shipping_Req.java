package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Order_Shipping_Req extends BasePojo {

	/**
	 * 订单ID
	 */
	@NotEmpty(value="订单号不能为空")
	private String orderId; 
	/**
	 * 子订单ID
	 */
	private String subOrderId; 
	/**
	 * 物流公司ID
	 */
	@NotEmpty(value="物流公司ID不能为空")
	private String deliveryCorpId; 
	/**
	 * 物流公司名称
	 */
	@NotEmpty(value="物流公司名称不能为空")
	private String deliveryCorpName; 
	/**
	 * 物流单号
	 */
	@NotEmpty(value="物流单号不能为空")
	private String trackingNo; 
	/**
	 * 备注
	 */
	private String remark; 
	/**
	 * 商户用户号
	 */
	private String merchantUserId; 

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

	public String getDeliveryCorpId() {
		return deliveryCorpId;
	}

	public void setDeliveryCorpId(String deliveryCorpId) {
		this.deliveryCorpId = deliveryCorpId;
	}

	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}

	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

}
