
package com.froad.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.JSonUtil;

/**
 * 券表
 * 
 * @author lf 2015.03.21
 * @modify lf 2015.03.21
 * 
 * */
public class Ticket {
	
	/**
	 * 自增主键ID
	 */
	private String _id = null;
	
	/**
	 * 创建时间
	 */
	private Long createTime = null;
	
	/**
	 * 客户端ID
	 */
	private String clientId = null;
	
	/**
	 * 第一级机构码
	 */
	private String forgCode = null;
	
	/**
	 * 第二级机构码
	 */
	private String sorgCode = null;
	
	/**
	 * 第三级机构码
	 */
	private String torgCode = null;
	
	/**
	 * 第四级机构码
	 */
	private String lorgCode = null;
	
	/**
	 * 商户ID
	 */
	private String merchantId = null;
	
	/**
	 * 商户名
	 */
	private String merchantName = null;
	
	/**
	 * 会员代号
	 */
	private String memberCode = null;
	
	/**
	 * 会员名称
	 */
	private String memberName = null;
	
	/**
	 * 订单ID
	 */
	private String orderId = null;
	
	/**
	 * 子订单ID
	 */
	private String subOrderId = null;
	
	/**
	 * 券ID
	 */
	private String ticketId = null;
	
	/**
	 * 手机号
	 */
	private String mobile = null;
	
	/**
	 * 类型
	 */
	private String type = null;

	/**
	 * 商品ID
	 */
	private String productId = null;
	
	/**
	 * 商品名称
	 */
	private String productName = null;
	
	/**
	 * 商品数量
	 */
	private Integer quantity = null;
	
	/**
	 * 预售自提网点
	 */
	private String orgCode = null;
	
	/**
	 * 预售自提网点名称
	 */
	private String orgName = null;
	
	/**
	 * 券状态: 0-待发送 1-已发送 2-已消费 3-已过期 4-已退款
	 */
	private String status = null;
	
	/**
	 * 退款ID
	 */
	private String refundId = null;
	
	/**
	 * 消费时间
	 */
	private Long consumeTime = null;
	
	/**
	 * 消费门店ID
	 */
	private String outletId = null;
	
	/**
	 * 消费门店名称
	 */
	private String outletName = null;
	
	/**
	 * 过期时间
	 */
	private Long expireTime = null;
	
	/**
	 * 退款时间
	 */
	private Long refundTime = null;
	
	/**
	 * 券消费商户操作员
	 */
	private Long merchantUserId = null;
	
	/**
	 * 券消费商户操作员名
	 */
	private String merchantUserName = null;
	
	/**
	 * 券二维码URL
	 */
	private String image = null;
	
	/**
	 * 商品单价
	 */
	private Integer price = null;
	
	/**
	 * @return the _id
	 */
	@JSONField(name="_id")
	public String get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	@JSONField(name="_id")
	public void set_id(String _id) {
		this._id = _id;
	}
	/**
	 * @return the createTime
	 */
	@JSONField(name="create_time")
	public Long getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	@JSONField(name="create_time")
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the clientId
	 */
	@JSONField(name="client_id")
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	@JSONField(name="client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the forgCode
	 */
	@JSONField(name="forg_code")
	public String getForgCode() {
		return forgCode;
	}
	/**
	 * @param forgCode the forgCode to set
	 */
	@JSONField(name="forg_code")
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	/**
	 * @return the sorgCode
	 */
	@JSONField(name="sorg_code")
	public String getSorgCode() {
		return sorgCode;
	}
	/**
	 * @param sorgCode the sorgCode to set
	 */
	@JSONField(name="sorg_code")
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	/**
	 * @return the torgCode
	 */
	@JSONField(name="torg_code")
	public String getTorgCode() {
		return torgCode;
	}
	/**
	 * @param torgCode the torgCode to set
	 */
	@JSONField(name="torg_code")
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	/**
	 * @return the lorgCode
	 */
	@JSONField(name="lorg_code")
	public String getLorgCode() {
		return lorgCode;
	}
	/**
	 * @param lorgCode the lorgCode to set
	 */
	@JSONField(name="lorg_code")
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	/**
	 * @return the merchantId
	 */
	@JSONField(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * @param merchantId the merchantId to set
	 */
	@JSONField(name="merchant_id")
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * @return the merchantName
	 */
	@JSONField(name="merchant_name")
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * @param merchantName the merchantName to set
	 */
	@JSONField(name="merchant_name")
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * @return the memberCode
	 */
	@JSONField(name="member_code")
	public String getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	@JSONField(name="member_code")
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the memberName
	 */
	@JSONField(name="member_name")
	public String getMemberName() {
		return memberName;
	}
	/**
	 * @param memberName the memberName to set
	 */
	@JSONField(name="member_name")
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	/**
	 * @return the orderId
	 */
	@JSONField(name="order_id")
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	@JSONField(name="order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the subOrderId
	 */
	@JSONField(name="sub_order_id")
	public String getSubOrderId() {
		return subOrderId;
	}
	/**
	 * @param subOrderId the subOrderId to set
	 */
	@JSONField(name="sub_order_id")
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	/**
	 * @return the ticketId
	 */
	@JSONField(name="ticket_id")
	public String getTicketId() {
		return ticketId;
	}
	/**
	 * @param ticketId the ticketId to set
	 */
	@JSONField(name="ticket_id")
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	/**
	 * @return the type
	 */
	@JSONField(name="type")
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	@JSONField(name="type")
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the productId
	 */
	@JSONField(name="product_id")
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	@JSONField(name="product_id")
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	@JSONField(name="product_name")
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	@JSONField(name="product_name")
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the quantity
	 */
	@JSONField(name="quantity")
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantily the quantity to set
	 */
	@JSONField(name="quantity")
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the orgCode
	 */
	@JSONField(name="org_code")
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	@JSONField(name="org_code")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	@JSONField(name="org_name")
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	@JSONField(name="org_name")
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the status
	 */
	@JSONField(name="status")
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	@JSONField(name="status")
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the refundId
	 */
	@JSONField(name="refund_id")
	public String getRefundId() {
		return refundId;
	}
	/**
	 * @param refundId the refundId to set
	 */
	@JSONField(name="refund_id")
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	/**
	 * @return the consumeTime
	 */
	@JSONField(name="consume_time")
	public Long getConsumeTime() {
		return consumeTime;
	}
	/**
	 * @param consumeTime the consumeTime to set
	 */
	@JSONField(name="consume_time")
	public void setConsumeTime(Long consumeTime) {
		this.consumeTime = consumeTime;
	}
	/**
	 * @return the outletId
	 */
	@JSONField(name="outlet_id")
	public String getOutletId() {
		return outletId;
	}
	/**
	 * @param outletId the outletId to set
	 */
	@JSONField(name="outlet_id")
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	/**
	 * @return the outletName
	 */
	@JSONField(name="outlet_name")
	public String getOutletName() {
		return outletName;
	}
	/**
	 * @param outletName the outletName to set
	 */
	@JSONField(name="outlet_name")
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	/**
	 * @return the expireTime
	 */
	@JSONField(name="expire_time")
	public Long getExpireTime() {
		return expireTime;
	}
	/**
	 * @param expireTime the expireTime to set
	 */
	@JSONField(name="expire_time")
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * @return the merchantUserId
	 */
	@JSONField(name="merchant_user_id")
	public Long getMerchantUserId() {
		return merchantUserId;
	}
	/**
	 * @return the refund_time
	 */
	@JSONField(name="refund_time")
	public Long getRefundTime() {
		return refundTime;
	}
	/**
	 * @param refund_time the refund_time to set
	 */
	@JSONField(name="refund_time")
	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}
	/**
	 * @param merchantUserId the merchantUserId to set
	 */
	@JSONField(name="merchant_user_id")
	public void setMerchantUserId(Long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	
	/**
	 * @return the merchantUserName
	 */
	@JSONField(name="merchant_user_name")
	public String getMerchantUserName() {
		return merchantUserName;
	}
	/**
	 * @param merchantUserName the merchantUserName to set
	 */
	@JSONField(name="merchant_user_name")
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
	/**
	 * @return the mobile
	 */
	@JSONField(name="sms_number")
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	@JSONField(name="sms_number")
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the image
	 */
	@JSONField(name="image")
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	@JSONField(name="image")
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}
	/**
	 * 把对象转成JSON字符串
	 * 
	 * @return
	 */
	public String toJsonString(){
		return JSonUtil.toJSonString(this);
	}
}
