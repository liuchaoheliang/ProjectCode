package com.froad.fft.thirdparty.dto.request.points;


public class PointsAccount{
	
	private String accountId;
	private String orgNo;
	private String orgName;
	private String points;
	private String frozenPoints;
	private String bankId;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getFrozenPoints() {
		return frozenPoints;
	}
	public void setFrozenPoints(String frozenPoints) {
		this.frozenPoints = frozenPoints;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
}
