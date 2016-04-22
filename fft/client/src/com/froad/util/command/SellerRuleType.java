package com.froad.util.command;

 // 卖家规则类型
public enum SellerRuleType {
	POINTS("00"),//积分 
	PREFERENTIAL("01"); //直接优惠
	private String value;
	private SellerRuleType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	public static SellerRuleType getSellerRuleType(String value){
		if("00".equals(value)){
			return POINTS;
		}else if("01".equals(value)){
			return PREFERENTIAL;
		}else{
			return null;
		}
	}
}
