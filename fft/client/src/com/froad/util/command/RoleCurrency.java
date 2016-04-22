package com.froad.util.command;

import java.util.HashMap;
import java.util.Map;


public class RoleCurrency {
	public static final Map<String, String> rolePayType_roleCurrency=new HashMap();
	static{
		//交易--纯收款  没有折扣
		rolePayType_roleCurrency.put(RuleDetailType.PAY_Currency.getValue(), "setCurrencyValueRealAll");
//		rolePayType_roleCurrency.put(Role.SELLER.getValue()+"-"+SellerRuleDetailType.PAY.getValue(), "setSellRecievCurreVal");
		//买积分  
		rolePayType_roleCurrency.put(RuleDetailType.SEND_POINTS.getValue(), "setFftPointsValueAll");
//		rolePayType_roleCurrency.put(SellerRuleDetailType.PAY_POINTS.getValue(), "setFftPointCurreVal");
		//积分手续费
		rolePayType_roleCurrency.put(RuleDetailType.POINTS_Factorage.getValue(), "setFftFactorage");
//		rolePayType_roleCurrency.put(Role.FFT.getValue()+"-"+SellerRuleDetailType.POINTS_Factorage.getValue(), "setFftFacPointCurreVal");
//		//补贴
//		rolePayType_roleCurrency.put(Role.FFT.getValue()+"-"+SellerRuleDetailType.FFT_GIVEN.getValue(), "setFftAllowanceCurreVal");
//		rolePayType_roleCurrency.put(Role.SELLER.getValue()+"-"+SellerRuleDetailType.FFT_GIVEN.getValue(), "setSellAllowanceCurreVal");
//		
//		//折扣-直接优惠
//		rolePayType_roleCurrency.put(Role.BUYER.getValue()+"-"+SellerRuleDetailType.Preferential.getValue(), "setCurrencyValueRealAll");
//		rolePayType_roleCurrency.put(Role.SELLER.getValue()+"-"+SellerRuleDetailType.Preferential.getValue(), "setSellRecievCurreVal");
//		
//		//赠送积分数-直接优惠
//		rolePayType_roleCurrency.put(Role.SELLER.getValue()+"-"+SellerRuleDetailType.SEND_POINTS.getValue(), "setPointsValueRealAll");
//		
//		//提现金额-积分提现
		rolePayType_roleCurrency.put(RuleDetailType.PAY_TO_FFT.getValue(), "setFftFactorage");
	}
}
