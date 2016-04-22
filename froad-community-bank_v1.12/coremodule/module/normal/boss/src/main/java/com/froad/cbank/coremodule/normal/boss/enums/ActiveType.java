package com.froad.cbank.coremodule.normal.boss.enums;

public enum ActiveType {
	
	fullCut("1","满减活动"),
	fullGive("0","满送")
	;
	ActiveType(String code,String description){
		this.code = code;
		this.description = description;
	}
	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	
}
