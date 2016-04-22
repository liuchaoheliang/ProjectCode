package com.froad.cbank.coremodule.module.normal.bank.enums;

public enum IsMarketableEnum {
	
	ALL("-1","全部"),
	NO("0","未上架"),
	UP("1","已上架"),
	DOWN("2","已下架"),
	isDeleted("3","已删除"),
	disOffShelf("4","禁用下架")
	;
	IsMarketableEnum(String code,String description){
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
