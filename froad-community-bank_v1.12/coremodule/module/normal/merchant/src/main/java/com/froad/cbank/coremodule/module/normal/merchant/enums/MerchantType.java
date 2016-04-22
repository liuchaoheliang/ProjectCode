package com.froad.cbank.coremodule.module.normal.merchant.enums;

public enum MerchantType {
	
	TH_Merchant("100000000","特惠商户"),
	ZJYH_Merchant("100000001","直接优惠");
	
	private String code;
	private String describe;
	
	private MerchantType(String code, String describe){
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
