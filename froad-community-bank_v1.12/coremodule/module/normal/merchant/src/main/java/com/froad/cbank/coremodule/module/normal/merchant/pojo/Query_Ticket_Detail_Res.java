package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Ticket_Detail_Res {

	private String ticketId;
	private String merchantId;
	private String merchantName;
	private String productId;
	private String productName;
	private Integer quantity;
	private String status;
	private String statusMsg;
	private String mobile;
	private String memberName;
	private String orderId;
	private String orgName;
	private long consumeTime;
	private String outletId;
	private String outletName;
	private Long expireTime;
	private String refundTime;
	private Long createTime;
	private String imageUrl;
	private String memberCode;

	private Long merchantUserId;
	/**
	 * 商户操作员
	 */
	private String merchantUserName;
	/**
	 * 子订单类型
	 */
	private String type;

	private String price;

	  /**
	   * 子订单号
	   */
	private String subOrderId; // required
	private String settleState;//   0未结算  1结算中   2已结算   3结算失败
	
	public String getSettleState() {
		return settleState;
	}

	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(Long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public long getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(long consumeTime) {
		this.consumeTime = consumeTime;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}
