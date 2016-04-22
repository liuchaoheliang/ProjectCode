package com.froad.util.command;

public enum OrderType {
	POINTS(SellerRuleType.POINTS.getValue()),//积分 
	PREFERENTIAL(SellerRuleType.PREFERENTIAL.getValue()); //直接优惠
	
	private String value;
	private OrderType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	public static OrderType getOrderType(String value){
		if("00".equals(value)){
			return POINTS;
		}else if("01".equals(value)){
			return PREFERENTIAL;
		}else{
			return null;
		}
	}
}
