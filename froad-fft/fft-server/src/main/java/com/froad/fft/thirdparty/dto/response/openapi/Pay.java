package com.froad.fft.thirdparty.dto.response.openapi;

public class Pay {

	private String payType;
	
	private String payOrg;
	
	private String payOrgNo;
	
	private String payAmount;
	
	private String froadBillNo;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayOrg() {
		return payOrg;
	}

	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}

	public String getPayOrgNo() {
		return payOrgNo;
	}

	public void setPayOrgNo(String payOrgNo) {
		this.payOrgNo = payOrgNo;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getFroadBillNo() {
		return froadBillNo;
	}

	public void setFroadBillNo(String froadBillNo) {
		this.froadBillNo = froadBillNo;
	}
	
	
}
