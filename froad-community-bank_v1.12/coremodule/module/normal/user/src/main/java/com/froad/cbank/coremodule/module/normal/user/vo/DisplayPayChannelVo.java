package com.froad.cbank.coremodule.module.normal.user.vo;

public class DisplayPayChannelVo {
	private boolean isDisplayUnionPoint;
	private boolean isDisplayBankPoint;
	private boolean isDisplayFastPay;
	private boolean isDisplaySmartCard;
	private boolean isDisplayCardClose;         //卡密支付
	private String unionPointOrgNo;
	private String bankPointOrgNo;
	private String fastPayOrgNo;
	private String smartCardOrgNo;
	private String cardCloseOrgNo; //卡密支付机构号
		
	
	public boolean isDisplayCardClose() {
		return isDisplayCardClose;
	}
	public void setDisplayCardClose(boolean isDisplayCardClose) {
		this.isDisplayCardClose = isDisplayCardClose;
	}
	public String getCardCloseOrgNo() {
		return cardCloseOrgNo;
	}
	public void setCardCloseOrgNo(String cardCloseOrgNo) {
		this.cardCloseOrgNo = cardCloseOrgNo;
	}
	public boolean isDisplayUnionPoint() {
		return isDisplayUnionPoint;
	}
	public void setDisplayUnionPoint(boolean isDisplayUnionPoint) {
		this.isDisplayUnionPoint = isDisplayUnionPoint;
	}
	public boolean isDisplayBankPoint() {
		return isDisplayBankPoint;
	}
	public void setDisplayBankPoint(boolean isDisplayBankPoint) {
		this.isDisplayBankPoint = isDisplayBankPoint;
	}
	public boolean isDisplayFastPay() {
		return isDisplayFastPay;
	}
	public void setDisplayFastPay(boolean isDisplayFastPay) {
		this.isDisplayFastPay = isDisplayFastPay;
	}
	public boolean isDisplaySmartCard() {
		return isDisplaySmartCard;
	}
	public void setDisplaySmartCard(boolean isDisplaySmartCard) {
		this.isDisplaySmartCard = isDisplaySmartCard;
	}
	public String getUnionPointOrgNo() {
		return unionPointOrgNo;
	}
	public void setUnionPointOrgNo(String unionPointOrgNo) {
		this.unionPointOrgNo = unionPointOrgNo;
	}
	public String getBankPointOrgNo() {
		return bankPointOrgNo;
	}
	public void setBankPointOrgNo(String bankPointOrgNo) {
		this.bankPointOrgNo = bankPointOrgNo;
	}
	public String getFastPayOrgNo() {
		return fastPayOrgNo;
	}
	public void setFastPayOrgNo(String fastPayOrgNo) {
		this.fastPayOrgNo = fastPayOrgNo;
	}
	public String getSmartCardOrgNo() {
		return smartCardOrgNo;
	}
	public void setSmartCardOrgNo(String smartCardOrgNo) {
		this.smartCardOrgNo = smartCardOrgNo;
	}
}
