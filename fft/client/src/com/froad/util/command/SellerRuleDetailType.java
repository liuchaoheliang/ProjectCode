package com.froad.util.command;

//卖家规则详细信息的类型
public enum SellerRuleDetailType {
	
	PAY_POINTS("001"),//购买积分
	POINTS_Factorage("002"),//积分手续费
	Preferential("010"),//直接优惠  --卖家收款
	FFT_GIVEN("011"),//对于直接优惠方付通给的补贴
	PAY("000"),//收款-(即交易)没有折扣
	SEND_POINTS("100"),//送积分
	DEDUCT_POINTS("200"),//扣积分
	PAY_TO_Buyer("201");//给买家充钱
	private String value;
	private SellerRuleDetailType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	
	public static SellerRuleDetailType getSellerRuleDetailType(String value){
		if("001".equals(value)){
			return PAY_POINTS;
		}else if("002".equals(value)){
			return POINTS_Factorage;
		}else if("010".equals(value)){
			return Preferential;
		}else if("011".equals(value)){
			return FFT_GIVEN;
		}else if("000".equals(value)){
			return PAY;
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
