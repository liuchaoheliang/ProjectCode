package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 物流公司订单
 * 
 * @author ylchu
 *
 */
public class DeliveryOrderReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1288782496887760099L;

	private String orderId; // 订单
	private String subOrderId; // 子订单
	private String productId; // 商品id
	private String deliveryCorpId; // 物流公司id
	private String deliveryCorpName; // 物流公司名
	private String trackingNo; // 物流单号
	private String remark;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

}
