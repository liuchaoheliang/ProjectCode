package com.froad.util.command;

public enum GoodCategory {
	Goods_Category_Lottery("彩票"),//彩票
	Goods_Category_Recharge_Phone("话费充值"),//话费充值
	Points_Exch_Currency("积分提现");//积分提现
	private String value;
	private GoodCategory(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	public static GoodCategory getGoodCategory(String value){
		if("彩票".equals(value)){
			return Goods_Category_Lottery;
		}else if("话费充值".equals(value)){
			return Goods_Category_Recharge_Phone;
		}else if("积分提现".equals(value)){
			return Points_Exch_Currency;
		}else{
			return null;
		}
	}
	
	
	
}
