package com.froad.fft.enums.trans;

/**
 *  支付渠道
 * @author FQ
 *
 */
public enum TransPayChannel {

	filmCard("20","贴膜卡支付"),
	alipay("53","支付宝支付"),
	internetBank("50","网银支付");
	
	
	private String code;
	private String describe;
	
	private TransPayChannel(String code,String describe){
		this.code=code;
		this.describe=describe;
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
