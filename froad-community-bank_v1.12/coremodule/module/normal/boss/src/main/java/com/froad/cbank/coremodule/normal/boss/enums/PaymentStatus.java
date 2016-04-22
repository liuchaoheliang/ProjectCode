package com.froad.cbank.coremodule.normal.boss.enums;


/**
 * 支付状态
 */
public enum PaymentStatus {

	pay_wait("1","等待支付"),
	pay_request_success("2","支付请求发送成功"),
	pay_request_fail("3","支付请求发送失败"),
	pay_success("4","支付成功"),
	pay_fail("5","支付失败");
	
	private String code;
	
	private String describe;
	
	private PaymentStatus(String code,String describe){
		this.code=code;
		this.describe=describe;
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
	
	@Override
    public String toString() {
        return this.code;
    }

}
