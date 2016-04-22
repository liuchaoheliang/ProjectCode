package com.froad.cbank.coremodule.normal.boss.enums;

public enum RoleType {
	
	admin("100000000","超级管理员"),
	outletAdmin("100000001","门店管理员"),
	outletOper("100000002","门店操作员");
	
	private String code;
	private String describe;
	
	private RoleType(String code, String describe){
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
