package com.froad.cbank.coremodule.normal.boss.enums;

public enum ProductTypeEnum {
	
	GROUP("1","团购"),
	PRESALE("2","预售"),
	PREFERENTIAL("3","名优特惠"),
	LINEDOWN("4","在线积分兑换"),
	LINEUP("5","网点礼品"),
	CASHIER("6","面对面")
	;
	ProductTypeEnum(String code,String description){
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
