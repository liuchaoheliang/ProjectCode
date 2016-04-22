package com.froad.util.command;

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
	
	public static UseTime getUseTime(String value){
		if("01".equals(value)){
			return COUNT_TRANS_CURRENCY;
		}else if("02".equals(value)){
			return PAY_SUCCESS;
		}else{
			return null;
		}
	}
}
