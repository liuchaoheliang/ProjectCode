package com.froad.enums;

/**
 * 支付渠道类型
 * @author ll
 *
 */
public enum PaymentChannel {

//	 20：贴膜卡   41：银联无卡支付 50：网银支付 51：快捷支付 1：方付通积分 2：银行积分
	froad_point(1,"方付通积分"),
	bank_point(2,"银行积分"),
	foil_card(20,"贴膜卡支付"),
	union_pay(41,"银联无卡支付"),
	cyberbank_pay(50,"网银支付"),
	fast_pay(51,"快捷支付"),
	timely_pay(55,"即时支付")
	;
	
	 
	  
	  
	  
	private int code;
	
	private String msg;
	
	private PaymentChannel(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
