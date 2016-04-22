package com.froad.fft.thirdparty.dto.request.points;

public class PresentPointsInfo {
	
	private String presentPointsNo;
	
	private String objectNo;

	private String orgNo;
	
	private String points;	
	
	private String accountId;
	
	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getPresentPointsNo() {
		return presentPointsNo;
	}

	public void setPresentPointsNo(String presentPointsNo) {
		this.presentPointsNo = presentPointsNo;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	
}
