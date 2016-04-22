package com.froad.common.beans;

import com.froad.enums.PaymentMethod;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;
import com.froad.util.payment.BaseSubassembly;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PayTempBean
 * @Description: 支付相关的临时传输Bean
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月18日 下午4:25:33
 */
public class PayTempBean {

	private String clientId;
	private String orderId; 
	private String pointOrgNo;
	private String cashOrgNo;
	private PaymentMethod payType; 
	private int cashType; 
	private int pointRatio; 
	private double pointAmount; 
	private double cashAmount;
	private String foilCardNum;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public int getCashType() {
		return cashType;
	}
	public void setCashType(int cashType) {
		this.cashType = cashType;
	}
	public int getPointRatio() {
		return pointRatio;
	}
	public void setPointRatio(int pointRatio) {
		this.pointRatio = pointRatio;
	}
	public double getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(double pointAmount) {
		this.pointAmount = pointAmount;
	}
	public double getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}
	public String getFoilCardNum() {
		return foilCardNum;
	}
	public void setFoilCardNum(String foilCardNum) {
		this.foilCardNum = foilCardNum;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public PayTempBean(){};
	
	public PayTempBean(DoPayOrdersVoReq req){
		this.orderId = req.getOrderId();
		this.pointOrgNo = req.getPointOrgNo();
		this.cashOrgNo = req.getCashOrgNo();
		this.payType = BaseSubassembly.codeToPaymentMethod(req.getPayType());
		this.cashType = req.getCashType();
		this.pointAmount = req.getPointAmount();
		this.cashAmount = req.getCashAmount();
		this.foilCardNum = req.getFoilCardNum();
		this.clientId = req.getClientId();
	}
	
    public PaymentMethod getPayType() {
        return payType;
    }
    
    public void setPayType(PaymentMethod payType) {
        this.payType = payType;
    }
	
}
