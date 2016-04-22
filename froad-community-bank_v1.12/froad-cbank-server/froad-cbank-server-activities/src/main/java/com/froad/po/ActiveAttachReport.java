package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class ActiveAttachReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;
	private Integer orderMoney;
	private Date payTime;
	private String merchantId;
	private String productName;
	private Integer productPrice;
	private Integer productVipPrice;
	private Integer priceCount;
	private Integer vipPriceCount;
	private Integer priceMarket;
	private Integer vipPriceMarket;
	private String memberCode;
	private String phone;
	private String clientId;
	private String orderMarketType;
	private String clientName;
	private String merchantName;
	private String payBillNO;
	private String returnBillNO;
	private String returnMoney;
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Integer orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPriceMarket() {
		return priceMarket;
	}

	public void setPriceMarket(Integer priceMarket) {
		this.priceMarket = priceMarket;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrderMarketType() {
		return orderMarketType;
	}

	public void setOrderMarketType(String orderMarketType) {
		this.orderMarketType = orderMarketType;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductVipPrice() {
		return productVipPrice;
	}

	public void setProductVipPrice(Integer productVipPrice) {
		this.productVipPrice = productVipPrice;
	}

	public Integer getPriceCount() {
		return priceCount;
	}

	public void setPriceCount(Integer priceCount) {
		this.priceCount = priceCount;
	}

	public Integer getVipPriceCount() {
		return vipPriceCount;
	}

	public void setVipPriceCount(Integer vipPriceCount) {
		this.vipPriceCount = vipPriceCount;
	}

	public Integer getVipPriceMarket() {
		return vipPriceMarket;
	}

	public void setVipPriceMarket(Integer vipPriceMarket) {
		this.vipPriceMarket = vipPriceMarket;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPayBillNO() {
		return payBillNO;
	}

	public void setPayBillNO(String payBillNO) {
		this.payBillNO = payBillNO;
	}

	public String getReturnBillNO() {
		return returnBillNO;
	}

	public void setReturnBillNO(String returnBillNO) {
		this.returnBillNO = returnBillNO;
	}

	public String getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(String returnMoney) {
		this.returnMoney = returnMoney;
	}

}
