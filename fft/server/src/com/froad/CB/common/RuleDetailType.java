package com.froad.CB.common;


//规则详细信息的类型
public enum RuleDetailType {
	PAY_COLLECT("000"),//资金支付
	PAY_POINTS("001"),//分分通积分支付
	PAY_BANK_POINTS("002"),//银行积分支付
	
	
	BUY_POINTS("100"),//付购买积分的金额
	POINTS_Factorage("101"),//付购买积分的手续费
	SEND_POINTS("102"),//送分分通积分
	
	Preferential("010"),//直接优惠  --卖家收款
	FFT_GIVEN("011"),//对于直接优惠方付通给的补贴
	
	
	DEDUCT_POINTS("200"),//积分提现-扣分分通积分
	PAY_TO_Buyer("201"),//积分提现-给买家充钱
	PAY_TO_FFT("202"),//积分提现-扣提现手续费
	
	SEND_VProduct("300");//发货(虚拟商品)
	private String value;
	private RuleDetailType(String value){
		this.value=value;
	}
	
	public String getValue(){
		return value;
	}
	
}
