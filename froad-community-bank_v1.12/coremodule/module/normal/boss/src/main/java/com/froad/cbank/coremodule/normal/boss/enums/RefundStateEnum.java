package com.froad.cbank.coremodule.normal.boss.enums;

public enum RefundStateEnum {
	
	REFUND_INIT("1","未退款"),
	REFUND_PROCESSING("2","退款中"),
	REFUND_SUCCESS("3","退款完成"),
	REFUND_PART("4","部分退款")
	;
    
	RefundStateEnum(String code,String description){
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
