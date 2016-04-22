package com.froad.enums;

public enum PaymentReason {
	
	settle("0", "结算"),
	refund("1", "退款"),
	payment("2", "支付"),
	pay_point("3", "支付赠送积分"),
	refund_point("4", "退还赠送积分"),
	auto_refund("5","系统自动退款");
	;
	
	private String code;
	
	private String describe;
	
	private PaymentReason(String code, String describe){
		this.code = code;
		this.describe = describe;
	}

	public String getCode() {
		return code;
	}

	public String getDescribe() {
		return describe;
	}
	
	
	
}
