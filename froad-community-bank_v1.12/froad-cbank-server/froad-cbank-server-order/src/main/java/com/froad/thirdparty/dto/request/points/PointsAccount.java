package com.froad.thirdparty.dto.request.points;


public class PointsAccount{
	
	private String accountId;
	private String orgNo;
	private String orgName;
	private String points;
	private String frozenPoints;
	private String exchageRate;
	private String bankId;
	private String userId;//银行返回的客户号，做交易时用到
	private String displayName;
	
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
	public String getExchageRate() {
		return exchageRate;
	}
	public void setExchageRate(String exchageRate) {
		this.exchageRate = exchageRate;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
