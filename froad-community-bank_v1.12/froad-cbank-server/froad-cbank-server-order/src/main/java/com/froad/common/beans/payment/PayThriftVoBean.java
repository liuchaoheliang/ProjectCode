package com.froad.common.beans.payment;

/**
 * 将thriftVo转为轻量的Class进行操作
* <p>Function: PayThrifetVoBean</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-22 上午11:21:19
* @version 1.0
 */
public class PayThriftVoBean {

	private String clientId; 
	private String orderId; 
	private String pointOrgNo; 
	private String cashOrgNo; 
	private int payType; 
	private int cashType; 
	private double pointAmount; 
	private double cashAmount; 
	private String foilCardNum; 
	private Integer pointRatio;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
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
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public int getCashType() {
		return cashType;
	}
	public void setCashType(int cashType) {
		this.cashType = cashType;
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
	public Integer getPointRatio() {
		return pointRatio;
	}
	public void setPointRatio(Integer pointRatio) {
		this.pointRatio = pointRatio;
	}
	
}
