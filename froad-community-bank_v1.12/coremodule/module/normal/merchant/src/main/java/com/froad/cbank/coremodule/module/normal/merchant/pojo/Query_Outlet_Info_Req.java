package com.froad.cbank.coremodule.module.normal.merchant.pojo;


public class Query_Outlet_Info_Req extends BasePojo {

	private String outletId;
	private String outletName;
	private String outletFullname;
	private String address;

	/**
	 * 审核状态 0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=删除，5=全部
	 */
	private String auditState; // optional


	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getOutletFullname() {
		return outletFullname;
	}

	public void setOutletFullname(String outletFullname) {
		this.outletFullname = outletFullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
