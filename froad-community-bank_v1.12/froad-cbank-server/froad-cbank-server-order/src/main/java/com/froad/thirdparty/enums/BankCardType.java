package com.froad.thirdparty.enums;

/**
 * 银行卡类型
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum BankCardType {

	DEBITCARD("D", "借记卡");

	private String code;
	private String describe;

	private BankCardType(String code, String describe) {
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
