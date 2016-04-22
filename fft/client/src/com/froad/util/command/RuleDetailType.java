package com.froad.util.command;

//规则详细信息的类型
public enum RuleDetailType {
	PAY_Currency("000"),//收款-货币付款
	PAY_POINTS("001"),//收分分通积分-用分分通积分付款
	PAY_BANK_POINTS("002"),//收银行积分-用银行积分付款
	
	
	BUY_POINTS("100"),//付购买积分的金额
	POINTS_Factorage("101"),//付购买积分的手续费
	SEND_POINTS("102"),//送分分通积分
	
	DEDUCT_POINTS("200"),//积分提现-扣分分通积分
	PAY_TO_Buyer("201"),//积分提现-给买家充钱
	PAY_TO_FFT("202");//积分提现-扣提现手续费
	
	private String value;
	private RuleDetailType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	
	public static RuleDetailType getRuleDetailType(String value){
		if("000".equals(value)){
			return PAY_Currency;
		}else if("001".equals(value)){
			return PAY_POINTS;
		}else if("002".equals(value)){
			return PAY_BANK_POINTS;
		}else if("100".equals(value)){
			return BUY_POINTS;
		}else if("101".equals(value)){
			return POINTS_Factorage;
		}else if("102".equals(value)){
			return SEND_POINTS;
		}else if("200".equals(value)){
			return DEDUCT_POINTS;
		}else if("201".equals(value)){
			return PAY_TO_Buyer;
		}else if("202".equals(value)){
			return PAY_TO_FFT;
		}else{
			return null;
		}
	}
}
