package com.froad.cbank.coremodule.module.normal.merchant.enums;

public enum UserType {
	
	admin("100000000","超级管理员"),
	normalUser("100000001","普通用户");
	
	private String code;
	private String describe;
	
	private UserType(String code, String describe){
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
