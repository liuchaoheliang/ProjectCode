package com.froad.thirdparty.dto.request.openapi;

public class LimitReq {

	private String bankCardNo;
	
	private String singlePenLimit;
	
	private String dailyLimit;
	
	private String monthlyLimit;
	
	private String payOrg;

	private String partnerID;
	
	public String getBankCardNo() {
		return bankCardNo;
	}

	public String getSinglePenLimit() {
		return singlePenLimit;
	}

	public String getDailyLimit() {
		return dailyLimit;
	}

	public String getMonthlyLimit() {
		return monthlyLimit;
	}

	public String getPayOrg() {
		return payOrg;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public LimitReq(String bankCardNo, String singlePenLimit,
			String dailyLimit, String monthlyLimit, String payOrg,
			String partnerID) {
		this.bankCardNo = bankCardNo;
		this.singlePenLimit = singlePenLimit;
		this.dailyLimit = dailyLimit;
		this.monthlyLimit = monthlyLimit;
		this.payOrg = payOrg;
		this.partnerID = partnerID;
	}

	
	
	
}
