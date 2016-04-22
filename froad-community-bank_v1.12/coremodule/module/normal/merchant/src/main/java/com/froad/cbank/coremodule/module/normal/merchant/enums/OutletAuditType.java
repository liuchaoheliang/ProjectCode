package com.froad.cbank.coremodule.module.normal.merchant.enums;

public enum OutletAuditType {
	DSH("0","待审核"),
	SHTG("1","审核通过"),
	SHBTG("2","审核不通过"),
	WTJ("3","未提交"),
	SC("4","删除"),
	SHDTB("4","审核待同步"),
	QB("5","全部");
	private String code;
	private String describe;
	
	private OutletAuditType(String code, String describe){
		this.code = code;
		this.describe = describe;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	

}
