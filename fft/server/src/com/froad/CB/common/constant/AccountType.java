package com.froad.CB.common.constant;


public enum AccountType {
	Factorage("01"),//01-积分手续费账户，
	Receive("02"),//02-交易收款账户，
	Pay("03"),//03-交易出款账户，
	Allowance("04"),//04-补贴账户
	Points_Currency("05"),//05-积分资金账户
	Points("06"),//06-积分账户
	Withdraw_For_Points("07");//07-积分提现账户
	private String value;
	private AccountType(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	public static AccountType getAccountType(String value){
		if("01".equals(value)){
			return Factorage;
		}else if("02".equals(value)){
			return Receive;
		}else if("03".equals(value)){
			return Pay;
		}else if("04".equals(value)){
			return Allowance;
		}else if("05".equals(value)){
			return Points_Currency;
		}else if("06".equals(value)){
			return Points;
		}else if("07".equals(value)){
			return Withdraw_For_Points;
		}else{
			return null;
		}
	}
	
}