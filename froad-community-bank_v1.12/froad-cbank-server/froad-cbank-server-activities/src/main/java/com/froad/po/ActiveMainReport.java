package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class ActiveMainReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;
	private Integer orderMoney;
	private Date payTime;
	private Integer priceMarket;
	private Integer vipPriceMarket;
	private String memberCode;
	private String phone;
	private String detail;
	private String clientId;
	private String orderMarketType;
	private String clientName;

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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

}
