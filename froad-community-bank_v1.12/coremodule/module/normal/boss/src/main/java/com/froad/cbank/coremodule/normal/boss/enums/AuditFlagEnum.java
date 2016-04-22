package com.froad.cbank.coremodule.normal.boss.enums;

public enum AuditFlagEnum {
	
	NEW("0","待审核"),
	ACCEPTED("1","审核通过"),
	REJECTED("2","审核未通过"),
	NO_NEW("3","未提交审核")
	;
	AuditFlagEnum(String code,String description){
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
