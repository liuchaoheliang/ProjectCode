package com.froad.cbank.coremodule.module.normal.user.pojo;

public class ShippingDetailPojo {
	/**
	   * 物流公司ID
	   */
	  public String deliveryCorpId; // required
	  /**
	   * 物流公司名称
	   */
	  public String deliveryCorpName; // required
	  /**
	   * 物流单号
	   */
	  public String trackingNo; // required
	  /**
	   * 发货时间
	   */
	  public long shippingTime; // required
	  /**
	   * 收货时间
	   */
	  public long receiptTime; // required
	  /**
	   * 收货状态
	   */
	  public String shippingStatus; // required
	  /**
	   * 备注
	   */
	  public String remark; // required
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
	public long getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(long shippingTime) {
		this.shippingTime = shippingTime;
	}
	public long getReceiptTime() {
		return receiptTime;
	}
	public void setReceiptTime(long receiptTime) {
		this.receiptTime = receiptTime;
	}
	public String getShippingStatus() {
		return shippingStatus;
	}
	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	  
	  

}
