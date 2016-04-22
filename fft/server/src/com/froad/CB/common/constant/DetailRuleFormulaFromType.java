package com.froad.CB.common.constant;

/**
  * 类描述：规则明细的公式的来源类型
  * @version: 1.0
  * @Copyright www.f-road.com.cn Corporation 2013 
  * @author: 刘丽 liuli@f-road.com.cn
  * @time: 2013-2-4 下午05:29:35
 */
public enum DetailRuleFormulaFromType {
	ATTRIBUTE("00"),//属性
	CONSTANT("01"); //常量
	private String value;
	private DetailRuleFormulaFromType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	public static DetailRuleFormulaFromType getDetailRuleFormulaFromType(String value){
		if("00".equals(value)){
			return ATTRIBUTE;
		}else if("01".equals(value)){
			return CONSTANT;
		}else{
			return null;
		}
	}
}
