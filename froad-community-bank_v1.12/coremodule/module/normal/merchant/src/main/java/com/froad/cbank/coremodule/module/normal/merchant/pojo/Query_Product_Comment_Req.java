package com.froad.cbank.coremodule.module.normal.merchant.pojo;


public class Query_Product_Comment_Req extends BasePojo {
	private String starLevel;
	private String merchantName;
	private String productName;
	private String pointStartTime;
	private String pointEndTime;
	private String isReply;
	private String memberName;
	private String merchantId;
	private String outletId;
	private String productId;
	private String orderId;
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPointStartTime() {
		return pointStartTime;
	}

	public void setPointStartTime(String pointStartTime) {
		this.pointStartTime = pointStartTime;
	}

	public String getPointEndTime() {
		return pointEndTime;
	}

	public void setPointEndTime(String pointEndTime) {
		this.pointEndTime = pointEndTime;
	}

	public String getIsReply() {
		return isReply;
	}

	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
