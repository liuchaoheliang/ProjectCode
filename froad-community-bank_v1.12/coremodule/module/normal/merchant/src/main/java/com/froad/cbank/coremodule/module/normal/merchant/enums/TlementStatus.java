package com.froad.cbank.coremodule.module.normal.merchant.enums;

public enum TlementStatus {
	
	unsettlemnt("0","未结算"),
	settlementing("1","结算中"),
	settlementsucc("2","结算成功"),
	settlementfailed("3","结算失败");
	
	private String code;
	private String describe;
	
	private TlementStatus(String code, String describe){
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
