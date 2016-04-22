package com.froad.po;

public class ExecuteCreateInstanceReq {

	private Origin origin;
	private String bessData;
	private String bessId;
	private String orgCode;
	private String processType;
	private String processTypeDetail;

	public ExecuteCreateInstanceReq() {
		super();
	}

	public ExecuteCreateInstanceReq(Origin origin, String bessData, String bessId, String orgCode) {
		super();
		this.origin = origin;
		this.bessData = bessData;
		this.bessId = bessId;
		this.orgCode = orgCode;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public String getBessData() {
		return bessData;
	}

	public void setBessData(String bessData) {
		this.bessData = bessData;
	}

	public String getBessId() {
		return bessId;
	}

	public void setBessId(String bessId) {
		this.bessId = bessId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getProcessTypeDetail() {
		return processTypeDetail;
	}

	public void setProcessTypeDetail(String processTypeDetail) {
		this.processTypeDetail = processTypeDetail;
	}

}
