package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

public class OrderDetailVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 952541352994383562L;

	private String orderId;
	private String createTime; // 创建时间
	private String orderState; // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
	private String pointDiscount; //积分抵扣
	private String subTotalMoney; //应付款总计
	private String businessType; // 业务类型
	private String outletId;  //门店ID
	private String outletName; //门店名称
	private String company; //物流公司
	private String orderNumber; //物流单号
	private String shippingDate; //发货时间
	
	private List<SubOrderVo> subOrderVoList;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public List<SubOrderVo> getSubOrderVoList() {
		return subOrderVoList;
	}

	public void setSubOrderVoList(List<SubOrderVo> subOrderVoList) {
		this.subOrderVoList = subOrderVoList;
	}
	
}
