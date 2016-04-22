package com.froad.cbank.coremodule.normal.boss.enums;

public enum TakeStateEnum {
	
	sent("1","未提货"),
	consumed("2","已提货"),
    expired("3","已过期"),
    refunded("4","已退款"),
    refunding("5","退款中"),
    refund_failed("6","退款失败"),
    expired_refunded("7","已过期退款")
	;
	TakeStateEnum(String code,String description){
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
