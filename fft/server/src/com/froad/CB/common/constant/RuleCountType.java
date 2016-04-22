package com.froad.CB.common.constant;

//计算规则类型
public enum RuleCountType {
	formula("00"),//使用公式来计算规则
	clazz("01");//使用类来计算规则
	
	private String value;
	private RuleCountType(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public static RuleCountType getRuleCountType(String value){
		if("00".equals(value))
			return formula;
		else if("01".equals(value))
			return clazz;
		else
			return null;
	}
}
