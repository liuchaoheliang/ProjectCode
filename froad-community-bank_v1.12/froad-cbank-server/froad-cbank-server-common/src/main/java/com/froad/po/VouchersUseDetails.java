package com.froad.po;

import java.util.Date;

 /**
  * @ClassName: VouchersUseDetails
  * @Description: 劵码使用明细
  * @author froad-shenshaocheng 2015年11月30日
  * @modify froad-shenshaocheng 2015年11月30日
 */
public class VouchersUseDetails {
	/**
	 * 序号
	 */	
	private Integer rowNum;
	
	/**
	 * 红包券码
	 */	
	private String vouchersId;
	
	/**
	 * 红包金额
	 */	
	private Integer vouchersMoney;
	
	/**
	 * 使用金额
	 */	
	private Integer vouchersUseMoney;
	
	/**
	 * 订单编号
	 */	
	private String orderId;
	
	/**
	 * 订单金额
	 */	
	private Integer orderMoney;
	
	/**
	 * 支付时间
	 */
	
	private Date payTime;
	
	/**
	 * 用户编号
	 */	
	private Long memberCode;
	
	/**
	 * 电话号码
	 */	
	private String phone;
	
	/**
	 * 交易明细
	 */	
	private String detail;
	
	/**
	 * 客户端
	 */	
	private String clientId;
	
	/**
	 * 商品ID
	 */	
	private String productId;
	
	/**
	 * 普通价格优惠总价
	 */	
	private Double normalPriceMarket;
	
	/**
	 * vip价格优惠总价
	 */	
	private Double vipPriceMarket;

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getVouchersId() {
		return vouchersId;
	}

	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}

	public Integer getVouchersMoney() {
		return vouchersMoney;
	}

	public void setVouchersMoney(Integer vouchersMnry) {
		this.vouchersMoney = vouchersMnry;
	}

	public Integer getVouchersUseMoney() {
		return vouchersUseMoney;
	}

	public void setVouchersUseMoney(Integer vouchersUseMoney) {
		this.vouchersUseMoney = vouchersUseMoney;
	}

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

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
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

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getNormalPriceMarket() {
		return normalPriceMarket;
	}

	public void setNormalPriceMarket(Double normalPriceMarket) {
		this.normalPriceMarket = normalPriceMarket;
	}

	public Double getVipPriceMarket() {
		return vipPriceMarket;
	}

	public void setVipPriceMarket(Double vipPriceMarket) {
		this.vipPriceMarket = vipPriceMarket;
	}
	
	
}
