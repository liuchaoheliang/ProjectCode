package com.froad.cbank.coremodule.module.normal.bank.vo;

public class OutletQrcodeReq {

	 private String token;
	 
	 private String outletId;
	 
	 private String type; //0 保存二维码   1 保存完整图片
	 
	 private String uid;
	 
	 
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	 
}
