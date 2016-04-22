package com.froad.common;

import java.util.HashMap;
import java.util.Map;

public class SellerCommand {
	/**交易类型transType**/
	public static final String POINTS_EXCH_PRODUCT="01";//积分兑换
	public static final String GROUP="02";//团购交易
	public static final String POINTS_REBATE="03";//返利积分   
	public static final String POINTS_EXCH_CASH="04";//积分提现交易
	public static final String COLLECT="05";//纯收款交易
	public static final String PRESENT_POINTS="06";//送积分交易
	public static Map<String, String> SELLER_TYPE_NAME_MAP = null;
	
	static {
		SELLER_TYPE_NAME_MAP = new HashMap<String, String>();
		
		SELLER_TYPE_NAME_MAP.put(POINTS_EXCH_PRODUCT, "积分兑换");
		SELLER_TYPE_NAME_MAP.put(GROUP, "团购交易");
		SELLER_TYPE_NAME_MAP.put(POINTS_REBATE, "手机银行卡付款后积分返利");
		SELLER_TYPE_NAME_MAP.put(POINTS_EXCH_CASH, "积分提现");
		SELLER_TYPE_NAME_MAP.put(COLLECT, "收款交易");
		SELLER_TYPE_NAME_MAP.put(PRESENT_POINTS, "现金或刷卡后赠送积分比例");
	}
	
}
