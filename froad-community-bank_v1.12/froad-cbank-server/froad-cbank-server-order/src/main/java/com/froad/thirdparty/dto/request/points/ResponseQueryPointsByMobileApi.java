package com.froad.thirdparty.dto.request.points;


public class ResponseQueryPointsByMobileApi {

	private String mobilePhone;
	
	private String orgNo;
	
	private String errorMsg;
	
	private PointsAccount accountPointsInfo;
	
	private System system;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public PointsAccount getAccountPointsInfo() {
		return accountPointsInfo;
	}

	public void setAccountPointsInfo(PointsAccount accountPointsInfo) {
		this.accountPointsInfo = accountPointsInfo;
	}

}
