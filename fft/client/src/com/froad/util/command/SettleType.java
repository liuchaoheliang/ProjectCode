package com.froad.util.command;

//结算类型
public enum SettleType {
	REALTIME("01")//实时结算
	,PERIOD("02");//期结
	private String value;
	private SettleType(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	public static SettleType getSettleType(String value){
		if("01".equals(value)){
			return REALTIME;
		}else if("02".equals(value)){
			return PERIOD;
		}else{
			return null;
		}
	}
	
}
