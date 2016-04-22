package com.froad.util.command;

//期结状态
public enum SettleState {
	CREATE("00"),//创建状态，则该状态表示待期结
//	CREATE("00"),//创建状态，若是实时结算该状态表示待下推，若是期结，则该状态表示待期结
//	SentToOpenAPI("11"),//已下推给OpenAPI
//	SentToOpentAPISuccess("12"),//下推给OpenAPI，成功
//	SentToOpentAPIFail("13"),//下推给OpenAPI，失败
//	PaySuccess("14"),//支付成功
//	PayFail("15"),//支付失败
	
	PayingForPeriod("20"),//期结中
	PayForPeriodSuccess("21"),//期结成功
	PayForPeriodFail("22");//期结失败
	
	private String value;
	private SettleState(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	
}
