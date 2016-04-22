package com.froad.util.command;


public class BankCommand {
	public static  String NOCARD_BANKID = "2000000001";//无卡登陆 默认银行
	public static  String NOCARD_STYLE = "froad";//无卡登陆 默认样式
	public static  String NOCARD_CODE = "0000";//无卡登陆 客户端响应码
	public static  String CQRC_BANKID = "0000000011";//重庆农商行
	public static  String UNIONPAY_BANKID = "2000000002";//银联无卡支付
	public static  String PC_BANKID = "1000000001";//pc版 渠道号
	
	public static String channelDetail(String id){
		if(PC_BANKID.equals(id)){
			return "1";
		} else {
			return "2";
		}
	}
}
