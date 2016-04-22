package com.froad.CB.common.constant;

/**
  * 类描述：规则类型
  * @version: 1.0
  * @Copyright www.f-road.com.cn Corporation 2013 
  * @author: 刘丽 liuli@f-road.com.cn
  * @time: 2013-2-4 上午09:29:11
 */
public enum RuleType {
	POINTS("00"),//返利积分 
	PREFERENTIAL("01"), //直接优惠
	WITHDRAW("02"),//提现
	PAY("10"),//收款-模板中是所有可能的收款，具体用什么来付款由用户输入的信息临时决定，只有支付的东西的量>0才放入pay表中
	SEND_Product("20");//发虚货
	//	PAY_CURRENCY("10"),//收款-货币
//	PAY_BANK_POINTS("11"),//收款-银行积分
//	PAY_POINTS("12"),//收款-分分通积分
//	PAY_CURRENCY_POINTS("13"),//收款-货币+分分通积分
//	PAY_CURRENCY_BANK_POINTS("14");//收款-货币+银行积分
	//收款-货币，收款-银行积分，收款-分分通积分，收款-货币+银行积分，收款-货币+分分通积分，返利积分，折扣，提现
	
	private String value;
	private RuleType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	public static RuleType getRuleType(String value){
		if("00".equals(value)){
			return POINTS;
		}else if("01".equals(value)){
			return PREFERENTIAL;
		}else if("02".equals(value)){
			return WITHDRAW;
		}else if("20".equals(value)){
			return SEND_Product;
		}else if("10".equals(value)){
			return PAY;
//			return PAY_CURRENCY;
//		}else if("11".equals(value)){
//			return PAY_BANK_POINTS;
//		}else if("12".equals(value)){
//			return PAY_POINTS;
//		}else if("13".equals(value)){
//			return PAY_CURRENCY_POINTS;
//		}else if("14".equals(value)){
//			return PAY_CURRENCY_BANK_POINTS;
		}else{
			return null;
		}
	}
}
