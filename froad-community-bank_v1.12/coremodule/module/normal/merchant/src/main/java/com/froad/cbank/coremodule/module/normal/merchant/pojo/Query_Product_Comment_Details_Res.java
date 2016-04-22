package com.froad.cbank.coremodule.module.normal.merchant.pojo;


public class Query_Product_Comment_Details_Res{
	/**
	 * 评论id
	 */
	private String commentId; 
	/**
	 * 商品Id
	 */
	private String productId; 
	/**
	 * 商品名称
	 */
	private String productName; 
	/**
	 * 排序
	 */
	private Short orderValue; 
	/**
	 * 客户端id
	 */
	private Long clientId; 
	/**
	 * 商户ID
	 */
	private String merchantId; 
	/**
	 * 商户名称
	 */
	private String merchantName; 
	/**
	 * 交易编号
	 */
	private String orderId; 
	/**
	 * 交易类型
	 */
	private String orderType; 
	/**
	 * 用户编码
	 */
	private String memberCode; 
	/**
	 * 评论人
	 */
	private String memberName; 
	/**
	 * 评价星级
	 */
	private Short starLevel; 
	/**
	 * 评价内容
	 */
	private String commentDescription; 
	/**
	 * 回复评价内容
	 */
	private String recomment; 
	/**
	 * 评价时间
	 */
	private Long createTime; 
	/**
	 * 机构码
	 */
	private String orgCode; 
	/**
	 * 回复时间
	 */
	private Long recommentTime; 
	/**
	 * 回复人
	 */
	private String merchantUserName; 
	/**
	 * 商品电话
	 */
	private String phone; 
	/**
	 * 商品图片
	 */
	private String imagePic; 
	/**
	 * 商品类型
	 */
	private String type; 
	
	/**
	 * 大订单号
	 */
	private String bigOrderId;
	
	private boolean isverify;
	
	
	public boolean isIsverify() {
		return isverify;
	}

	public void setIsverify(boolean isverify) {
		this.isverify = isverify;
	}

	public String getBigOrderId() {
		return bigOrderId;
	}

	public void setBigOrderId(String bigOrderId) {
		this.bigOrderId = bigOrderId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
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

	public Short getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Short orderValue) {
		this.orderValue = orderValue;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public Short getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Short starLevel) {
		this.starLevel = starLevel;
	}

	public String getCommentDescription() {
		return commentDescription;
	}

	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}

	public String getRecomment() {
		return recomment;
	}

	public void setRecomment(String recomment) {
		this.recomment = recomment;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getRecommentTime() {
		return recommentTime;
	}

	public void setRecommentTime(Long recommentTime) {
		this.recommentTime = recommentTime;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImagePic() {
		return imagePic;
	}

	public void setImagePic(String imagePic) {
		this.imagePic = imagePic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
