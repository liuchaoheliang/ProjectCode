package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.enums.ShippingStatus;

/**
 * 子订单商品发货表Mongo
 */
public class ShippingOrderMongo implements java.io.Serializable {

	private static final long serialVersionUID = -3207519501764181105L;

	/**
	 * 订单号
	 */
	private String id;
	
	/**
	 * 发货状态
	 */
	private String shippingStatus;
	
	/**
	 * 物流公司ID
	 */
	private String deliveryCorpId;
	
	/**
	 * 物流公司名称
	 */
	private String deliveryCorpName;
	
	/**
	 * 物流单号
	 */
	private String trackingNo;
	
	/**
	 * 发货时间
	 */
	private Long shippingTime;
	
	/**
	 * 收货时间
	 */
	private Long receiptTime = 0L;
	
	/**
	 * 备注
	 */
	private String remark = "";
	
	/**
	 * 商户用户号
	 */
	private String merchantUserId = "";

	@JSONField(name="_id")
	public String getId() {
		return id;
	}

	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
	}

	@JSONField(name="shipping_status")
	public String getShippingStatus() {
		return shippingStatus;
	}

	@JSONField(name="shipping_status")
	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	@JSONField(name="delivery_corp_id")
	public String getDeliveryCorpId() {
		return deliveryCorpId;
	}

	@JSONField(name="delivery_corp_id")
	public void setDeliveryCorpId(String deliveryCorpId) {
		this.deliveryCorpId = deliveryCorpId;
	}

	@JSONField(name="delivery_corp_name")
	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}

	@JSONField(name="delivery_corp_name")
	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}

	@JSONField(name="tracking_no")
	public String getTrackingNo() {
		return trackingNo;
	}

	@JSONField(name="tracking_no")
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	@JSONField(name="shipping_time")
	public Long getShippingTime() {
		return shippingTime;
	}

	@JSONField(name="shipping_time")
	public void setShippingTime(Long shippingTime) {
		this.shippingTime = shippingTime;
	}

	@JSONField(name="receipt_time")
	public Long getReceiptTime() {
		return receiptTime;
	}

	@JSONField(name="receipt_time")
	public void setReceiptTime(Long receiptTime) {
		this.receiptTime = receiptTime;
	}

	@JSONField(name="remark")
	public String getRemark() {
		return remark;
	}

	@JSONField(name="remark")
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSONField(name="merchant_user_id")
	public String getMerchantUserId() {
		return merchantUserId;
	}

	@JSONField(name="merchant_user_id")
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	
}


