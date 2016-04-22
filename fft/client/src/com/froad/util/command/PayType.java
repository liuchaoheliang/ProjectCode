package com.froad.util.command;

public enum PayType {
	PAY_POINTS(RuleDetailType.PAY_POINTS.getValue()),//购买积分
	POINTS_Factorage(RuleDetailType.POINTS_Factorage.getValue()),//积分手续费
//	Preferential(RuleDetailType.Preferential.getValue()),//直接优惠  --卖家收款
//	FFT_GIVEN(RuleDetailType.FFT_GIVEN.getValue()),//对于直接优惠方付通给的补贴
//	PAY(RuleDetailType.PAY.getValue()),//收款-(即交易)没有折扣
	SEND_POINTS(RuleDetailType.SEND_POINTS.getValue()),//送积分
	DEDUCT_POINTS(RuleDetailType.DEDUCT_POINTS.getValue()),//扣积分
	PAY_TO_Buyer(RuleDetailType.PAY_TO_Buyer.getValue());//给买家充钱
	private String value;
	private PayType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	
	public static PayType getPayType(String value){
		if("001".equals(value)){
			return PAY_POINTS;
		}else if("002".equals(value)){
			return POINTS_Factorage;
		}else if("100".equals(value)){
			return SEND_POINTS;
		}else if("200".equals(value)){
			return DEDUCT_POINTS;
		}else if("201".equals(value)){
			return PAY_TO_Buyer;
		}else{
			return null;
		}
	}
}

