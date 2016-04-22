package com.froad.util.command;

//规则明细的类别-也就是流转可以流转哪几种东西
public enum RuleDetailCategory {
	Currency("00"),//资金
	Points("01"),//积分
	VProduct("10");//虚拟商品
	
	private String value;
	private RuleDetailCategory(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public static RuleDetailCategory getRuleDetailCategory(String value){
		if("00".equals(value))
			return Currency;
		else if("01".equals(value))
			return Points;
		else if("10".equals(value))
			return VProduct;
		else
			return null;
	}
}
