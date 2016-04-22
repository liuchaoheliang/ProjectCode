package com.froad.cbank.coremodule.normal.boss.pojo.order;
/**
 * 类描述：团购、预售券实体
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-13下午5:24:44 
 */
public class TicketInfoVo {	
	private String id;
	private String orderCode;//订单编号
	private String ticketNo;//券号
	private String ticketNo2;//明文券号
	private String clientId;
	private String saleTime;//购买时间
	private String ticketSendTime;//券发送时间
	private String productName;//商品名称
	private String userName;//用户名
	private String mobile;//手机号
	private String opTime;//消费、过期、退款时间
	private String ticketStatu;//券状态
	
	  /**
	   * 商户ID
	   */
	private String merchantId; // required
	  /**
	   * 商户名
	   */
	private String merchantName; // required
	  /**
	   * 商品类型
	   */
	private String productId; // required

	  /**
	   * 商品数量
	   */
	private int quantity; // required
	  /**
	   * 券状态
	   */
	private String status; // required
	  /**
	   * 券状态信息
	   */
	private String statusMsg; // required
	  /**
	   * 提货人编号
	   */
	private String memberCode; // required
	  /**
	   * 提货人
	   */
	private String memberName; // required
	/**
	 * 提货人收货地址
	 */
	private String memberAddr;
	  /**
	   * 大订单号
	   */
	private String orderId; // required
	/**
	 * 提货方式
	 */
	private String deliveryType;
	/**
	 * 提货期
	 */
	private String deliveryPeriod;
	  /**
	   * 预售自提网店名称
	   */
	private String orgName; // required
	/**
	 * 预售自提网点地址
	 */
	private String orgAddr;
	  /**
	   * 消费时间(可空，如已消费则返回long值的string，如无则返回空值)
	   */
	private String consumeTime; // required
	  /**
	   * 消费门店ID(可空，如已消费则返回long值的string，如无则返回空值)
	   */
	private String outletId; // required
	  /**
	   * 消费门店名称(可空，如已消费则返回long值的string，如无则返回空值)
	   */
	private String outletName; // required
	  /**
	   * 有效期
	   */
	private long expireTime; // required
	  /**
	   * 退款时间(可空，如已退款则返回long值的string，如无则返回空值)
	   */
	private String refundTime; // required
	  /**
	   * 券生成时间
	   */
	private long createTime; // required
	  /**
	   * 券二维码地址
	   */
	private String imageUrl; // required
	  /**
	   * 商户操作员
	   */
	private long merchantUserId; // required
	  /**
	   * 商户操作员名字
	   */
	private String merchantUserName; // required
	  /**
	   * 子订单类型
	   */
	private String type; // required
	  /**
	   * 商品价格
	   */
	private String price; // required
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public String getTicketSendTime() {
		return ticketSendTime;
	}
	public void setTicketSendTime(String ticketSendTime) {
		this.ticketSendTime = ticketSendTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOpTime() {
		return opTime;
	}
	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
	public String getTicketStatu() {
		return ticketStatu;
	}
	public void setTicketStatu(String ticketStatu) {
		this.ticketStatu = ticketStatu;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
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
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
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
	public String getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
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
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public long getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public String getMerchantUserName() {
		return merchantUserName;
	}
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryPeriod() {
		return deliveryPeriod;
	}
	public void setDeliveryPeriod(String deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}
	public String getMemberAddr() {
		return memberAddr;
	}
	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}
	public String getOrgAddr() {
		return orgAddr;
	}
	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}
	public String getTicketNo2() {
		return ticketNo2;
	}
	public void setTicketNo2(String ticketNo2) {
		this.ticketNo2 = ticketNo2;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
