package com.froad.util.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PayCommand {
	public static String PAYTYPE_ALL="10";//全部	
	public static String PAYTYPE_FILM="20";//贴膜卡
	public static String PAYTYPE_SD="30";//SD卡	
	public static String PAYTYPE_SDCUSTOMIZED="41";//银联无卡支付	
	public static String PAYTYPE_UNIONPAYNOCARD="31";//SD卡定制支付
	
	public static String RESPCODE_SUCCESS="0000";//响应码:成功
	
	public static String ACCOUNT_MARKED_TYPE="02";
	
	//------------------- 支付状态 ------------------- //
	public static final String PAY_STATUS_SUCCESS = "0";//已支付
	public static final String PAY_STATUS_FAILURE = "1";//已失败
	public static final String PAY_STATUS_NOPAY = "2";//未支付
	public static final String PAY_STATUS_EXECUTION = "3";//支付中
	
	public static String VERSION="1.0";//支付系统版本
	
	//------------------- 客户端信息 ------------------- //
	public static String CLIENT_PC="100";//PC渠道
	public static String CLIENT_ANDROID="200";//安卓渠道	
	public static String CLIENT_IPHONE="300";//IPhone渠道
	public static final List<String> CLIENT_TYPES=new ArrayList<String>();
	static{
		CLIENT_TYPES.add(CLIENT_PC);
		CLIENT_TYPES.add(CLIENT_ANDROID);
		CLIENT_TYPES.add(CLIENT_IPHONE);
	}
	
	
	public static HashMap<String, String> CLIENT_ID;
	public static HashMap<String, String> PAY_TYPE;
	
	static {
		CLIENT_ID = new HashMap<String, String>();
		CLIENT_ID.put("100", "pc");
		CLIENT_ID.put("200", "android");
		CLIENT_ID.put("201", "android_sd");
		CLIENT_ID.put("300", "iphone");
		CLIENT_ID.put("400", "ipad");
		PAY_TYPE = new HashMap<String, String>();
		PAY_TYPE.put("10", "全部");
		PAY_TYPE.put("20", "贴膜卡");
		PAY_TYPE.put("30", "SD卡");
		PAY_TYPE.put("41", "银联无卡支付");
		PAY_TYPE.put("31", "SD卡定制支付");
	}
	
	//------------------- 交易者类型 -------------------//
	public static final String TRADER_TYPE_BUYER="1";
	public static final String TRADER_TYPE_MERCHANT="1";
	public static final String TRADER_TYPE_BANK="2";
	public static final String TRADER_TYPE_FROAD="3";
	
	//-------------------是否为方付通内部卖家--------------//
	public static final String IS_INTERNAL_NO="0";//否
	public static final String IS_INTERNAL_YES="1";//是
	
	//------------------typeDetail字段的前缀描述----------//
	public static final String DESC_COLLECT="collect_";//收款
	public static final String DESC_DEDUCT="deduct_";//代扣(商户买积分)
	public static final String DESC_PRESENT="present_";//增送积分
}
