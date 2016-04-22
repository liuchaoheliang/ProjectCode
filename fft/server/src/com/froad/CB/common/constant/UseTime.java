package com.froad.CB.common.constant;

 //规则在什么时候使用， 计算交易金额时，还是支付成功后
public enum UseTime {
	COUNT_TRANS_CURRENCY("01"),//在计算交易金额时使用规则
	PAY_SUCCESS("02");//在支付成功后使用规则
	private String value;
	private UseTime(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
