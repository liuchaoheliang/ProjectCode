package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

public class OrderVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 952541352994383562L;

	private String subOrderId;
	private String createTime; // 创建时间
	private String orderState; // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
	private String pointDiscount; //积分抵扣
	private String subTotalMoney; //应付款总计
	private String businessType; // 业务类型
	private String outletId;  //门店ID
	private String outletName; //门店名称
	private String merchantId; //商户ID
	private String merchantName; //商户名称
	private String company; //物流公司
	private String orderNumber; //物流单号
	private String shippingDate; //发货时间
	public String refundState;  //退款状态
	
	private List<BankProductVo> productList;

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getPointDiscount() {
		return pointDiscount;
	}

	public void setPointDiscount(String pointDiscount) {
		this.pointDiscount = pointDiscount;
	}

	public String getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(String subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public List<BankProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<BankProductVo> productList) {
		this.productList = productList;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
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

}
