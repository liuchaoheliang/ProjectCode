package com.froad.util.command;

public enum TransType {
	Trans_Points_Exch_Product("01")//积分兑换
	,Trans_Group_buy("02")//团购交易    
	,Trans_Points("03")// 返利积分   
	,Trans_Points_Exch_Currency("04");//积分提现交易
	private String value;
	private TransType(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	public static TransType getTransType(String value){
		if("01".equals(value)){
			return Trans_Points_Exch_Product;
		}else if("02".equals(value)){
			return Trans_Group_buy;
		}else if("03".equals(value)){
			return Trans_Points;
		}else if("04".equals(value)){
			return Trans_Points_Exch_Currency;
		}else{
			return null;
		}
	}
}
