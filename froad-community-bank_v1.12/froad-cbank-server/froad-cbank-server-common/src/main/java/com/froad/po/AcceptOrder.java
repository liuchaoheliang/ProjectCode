package com.froad.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 受理订单对象
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 下午3:57:51
 * @version v1.0
 */
public class AcceptOrder implements Serializable {
	
	private static final long serialVersionUID = 4816314361292162944L;

	/**
	 * 请求受理号
	 */
	private String reqId;
	
	/**
	 * 客户端ID
	 */
	private String clientId;
	
	/**
	 * 商户ID
	 */
	private String merchantId;
	
	/**
	 * 用户编号
	 */
	private Long memberCode;
	
	/** 
	 * 会员名称 
	 */
	private String memberName;
	
	/** 
	 * 是否是VIP用户 
	 */
	private boolean isVip;
	
	
	/**
	 * 产品ID
	 */
	private String productId;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 产品类型
	 */
	private String productType;
	
	/**
	 * 商品图片
	 */
	private String productImage;
	
	/**
	 * 商品配送方式
	 */
	private String deliveryOption;
	
	/**
	 * 秒杀价格
	 */
	private Double secPrice;
	
	/**
	 * VIP秒杀价格
	 */
	private Double vipSecPrice;
	
	/**
	 * 运费
	 */
	private Double deliveryMoney;
	
	/**
	 * 购买的数量
	 */
	private Integer buyNum;
	
	/** 支付类型 */
	private Integer payType;
	
	/** 现金支付类型 */
	private Integer cashType;
	
	/** 积分机构号 */
	private String pointOrgNo;
	
	/** 现金机构号 */
	private String cashOrgNo;
	
	/** 积分值 */
	private Double pointAmount;
	
	/** 现金值 */
	private Double cashAmount;

	/** 贴膜卡卡号 */
	private String foilCardNum;
	
	/** 创建来源 */
	private String createSource;
	
	/** 提货类型 */
	private String deliveryType;
	/** 收货信息编号 */
	private String recvId;
	/** 接手券和短信的手机号 */
	private String phone;
	/** 预售商品自提网点|线下积分兑换网点 */
	private String orgCode;
	/** 预售商品自提网点名称|线下积分兑换网点名称 */
	private String orgName;
	
	/** 进入订单队列时间 */
	private Date orderQueueDate;
	
	/** 订单ID */
	private String orderId;
	/** 进入支付队列时间 */
	private Date payQueueDate;
	
	public AcceptOrder(){}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public Double getSecPrice() {
		return secPrice;
	}

	public void setSecPrice(Double secPrice) {
		this.secPrice = secPrice;
	}
	
	public Double getVipSecPrice() {
		return vipSecPrice;
	}

	public void setVipSecPrice(Double vipSecPrice) {
		this.vipSecPrice = vipSecPrice;
	}

	public Double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getCashType() {
		return cashType;
	}

	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}

	public String getPointOrgNo() {
		return pointOrgNo;
	}

	public void setPointOrgNo(String pointOrgNo) {
		this.pointOrgNo = pointOrgNo;
	}

	public String getCashOrgNo() {
		return cashOrgNo;
	}

	public void setCashOrgNo(String cashOrgNo) {
		this.cashOrgNo = cashOrgNo;
	}

	public Double getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(Double pointAmount) {
		this.pointAmount = pointAmount;
	}

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getFoilCardNum() {
		return foilCardNum;
	}

	public void setFoilCardNum(String foilCardNum) {
		this.foilCardNum = foilCardNum;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}
	
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getRecvId() {
		return recvId;
	}

	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getOrderQueueDate() {
		return orderQueueDate;
	}

	public void setOrderQueueDate(Date orderQueueDate) {
		this.orderQueueDate = orderQueueDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getPayQueueDate() {
		return payQueueDate;
	}

	public void setPayQueueDate(Date payQueueDate) {
		this.payQueueDate = payQueueDate;
	}

	@Override
	public String toString(){
		return JSONObject.toJSONString(this);
	}
	
}
