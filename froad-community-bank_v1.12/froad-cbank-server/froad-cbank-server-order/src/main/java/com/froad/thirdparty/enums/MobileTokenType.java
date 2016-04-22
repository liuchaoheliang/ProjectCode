package com.froad.thirdparty.enums;

public enum MobileTokenType {
	PAY("1", "支付"), SIGN("2", "签约"), SIGNCANCEL("3", "解约");

	private String code;
	private String describe;

	private MobileTokenType(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}

	public String getCode() {
		return code;
	}

	public String getDescribe() {
		return describe;
	}

	@Override
	public String toString() {
		return this.code;
	}

}
