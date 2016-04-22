package com.froad.po;

import java.util.Date;

public class CommonParam {
	
	private Date begDate;
	
	private Date endDate;
	
	private String clientId;
	
	private String orgCode;

	private Boolean flag;
	
	public Date getBegDate() {
		return begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
}
