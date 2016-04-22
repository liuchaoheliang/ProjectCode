package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class DeliveryVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private Long deliveryId;        //提货ID
	private String takeCode;        //提货码
	private Integer number;          //数量
	private String vaildDate;       //有效日期
	private String productId;       //商品ID
	private String productName;     //商品名称
	private String takePerson;      //提货人
	private String phone;           //联系电话
	private String takeState;       //提货状态
	private String startDate;       //下单时间
	private String price;       //下单时间
	private String isPass; // 是否过期(0:未过期;1:过期)
	private String orderId; //订单编号
	private String orgName; //所属机构
	private String superOrgName; //上级机构
	
	
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSuperOrgName() {
		return superOrgName;
	}
	public void setSuperOrgName(String superOrgName) {
		this.superOrgName = superOrgName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getTakeCode() {
		return takeCode;
	}
	public void setTakeCode(String takeCode) {
		this.takeCode = takeCode;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getVaildDate() {
		return vaildDate;
	}
	public void setVaildDate(String vaildDate) {
		this.vaildDate = vaildDate;
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
	public String getTakePerson() {
		return takePerson;
	}
	public void setTakePerson(String takePerson) {
		this.takePerson = takePerson;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTakeState() {
		return takeState;
	}
	public void setTakeState(String takeState) {
		this.takeState = takeState;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	
}
