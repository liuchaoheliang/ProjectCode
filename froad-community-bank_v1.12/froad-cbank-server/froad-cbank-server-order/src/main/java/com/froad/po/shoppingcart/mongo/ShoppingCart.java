package com.froad.po.shoppingcart.mongo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  用户购物车Mongo结构
  * @ClassName: ShoppingCart
  * @Description: TODO
  * @author share 2015年3月17日
  * @modify share 2015年3月17日
 */
public class ShoppingCart implements Serializable{
	
	 private String id; 						
	 
	 private String clientId;		// 客户端
	 
	 private long memberCode;		// 会员号
	 
	private String merchantId; 		// 商户ID
	
	private String merchantName; 	// 商户名称
	
	private String type; 			// 类型
	
	private int merchantStatus; 	// 商户状态
	
	 private String productId; 		// 商品ID
		
	 private String productName; 	// 商品名称
	
	 private String productImage; 	// 商品图片
	
	 private int money; 			// 价格
	
	 private int vipMoney; 			// vip价格
	
	 private int quantity; 			// 数量
	
	 private int vipQuantity = 0; 	// VIP数量
	
	 private String status; 		// 状态
	
	 private String orgCode; 		// 机构编号
	
	 private String orgName; 		// 机构名称
	
	 private Integer orgStatus; 	// 机构状态
	
	 private long time; 			// 更新时间
	 
	private int deliveryMoney;		// 运费
	 
	 @JSONField(serialize=false,deserialize=false)
	 private String errCode	= "0000";	// 0000：ok，其他异常
	 
	 @JSONField(serialize=false,deserialize=false)	
	 private String errMsg   = "ok";	// 提示语
	
	@JSONField(name="_id")
	public String getId() {
		return id;
	}

	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
	}

	@JSONField(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}
	@JSONField(name="merchant_id")
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	@JSONField(name="type")
	public String getType() {
		return type;
	}
	@JSONField(name="type")
	public void setType(String type) {
		this.type = type;
	}
	@JSONField(name="merchant_status")
	public int getMerchantStatus() {
		return merchantStatus;
	}
	@JSONField(name="merchant_status")
	public void setMerchantStatus(int merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	@JSONField(name="merchant_name")
	public String getMerchantName() {
		return merchantName;
	}
	
	@JSONField(name="merchant_name")
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@JSONField(name="product_id")
	public String getProductId() {
		return productId;
	}
	@JSONField(name="product_id")
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@JSONField(name="product_name")
	public String getProductName() {
		return productName;
	}
	@JSONField(name="product_name")
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@JSONField(name="product_image")
	public String getProductImage() {
		return productImage;
	}
	@JSONField(name="product_image")
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	@JSONField(name="money")
	public int getMoney() {
		return money;
	}
	@JSONField(name="money")
	public void setMoney(int money) {
		this.money = money;
	}
	@JSONField(name="vip_money")
	public int getVipMoney() {
		return vipMoney;
	}
	
	@JSONField(name="vip_money")
	public void setVipMoney(int vipMoney) {
		this.vipMoney = vipMoney;
	}
	@JSONField(name="quantity")
	public int getQuantity() {
		return quantity;
	}
	@JSONField(name="quantity")
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@JSONField(name="vip_quantity")
	public int getVipQuantity() {
		return vipQuantity;
	}
	@JSONField(name="vip_quantity")
	public void setVipQuantity(int vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	@JSONField(name="status")
	public String getStatus() {
		return status;
	}
	@JSONField(name="status")
	public void setStatus(String status) {
		this.status = status;
	}
	@JSONField(name="time")
	public long getTime() {
		return time;
	}
	@JSONField(name="time")
	public void setTime(long time) {
		this.time = time;
	}
	@JSONField(name="org_code")
	public String getOrgCode() {
		return orgCode;
	}
	@JSONField(name="org_code")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@JSONField(name="org_name")
	public String getOrgName() {
		return orgName;
	}
	@JSONField(name="org_name")
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@JSONField(name="org_status")
	public Integer getOrgStatus() {
		return orgStatus;
	}
	@JSONField(name="org_status")
	public void setOrgStatus(Integer orgStatus) {
		this.orgStatus = orgStatus;
	}
	@JSONField(name="client_id")
	public String getClientId() {
		return clientId;
	}
	@JSONField(name="client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	@JSONField(name="member_code")
	public long getMemberCode() {
		return memberCode;
	}
	@JSONField(name="member_code")
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	@JSONField(name="delivery_money")
	public int getDeliveryMoney() {
		return deliveryMoney;
	}
	@JSONField(name="delivery_money")
	public void setDeliveryMoney(int deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}

