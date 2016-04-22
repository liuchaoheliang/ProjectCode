package com.froad.common;

public class PayState {

	public static String PAY_EXCEPTION="9001";//交易异常
	public static String PAY_CANCEL ="9002";//交易关闭
	
	public static String PAY_WAIT ="1001";//等待买家付款-待下推交易
	public static String PAY_REQUEST_SUCCESS ="1002";//发送支付请求成功--下推交易成功
	public static String PAY_REQUEST_FAIL = "1003";//发送支付请求失败--下推交易失败
	public static String PAY_SUCCESS = "1004";//付款成功  --支付交易成功
	public static String PAY_FAIL = "1005";//付款失败--支付交易失败
	
	public static String POINT_PAY_SUCCESS="2001";//积分支付成功
	public static String POINT_PAY_FAIL="2002";//积分支付失败
	public static String POINT_REFUND_SUCCESS="2003";//积分退还成功
	public static String POINT_REFUND_FAIL="2004";//积分退还失败
	public static String POINT_REBATE_SUCCESS="2005";//积分返利成功
	public static String POINT_REBATE_FAIL="2006";//积分返利失败
	public static String POINT_PRESENT_SUCCESS="2007";//积分赠送成功
	public static String POINT_PRESENT_FAIL="2008";//积分赠送失败
	
	public static String REFUND_REQ_SUCCESS ="3001";//发送退款请求成功
	public static String REFUND_REQ_FAIL ="3002";//发送退款请求失败
	public static String REFUND_SUCCESS ="3003";//退款成功
	public static String REFUND_FAIL ="3004";//退款失败
	public static String REFUND_EXCEPTION ="3005";//退款异常
	
	public static String HFCZ_REQ_SUCCESS="4001";//发送话费充值请求成功
	public static String HFCZ_REQ_FAIL="4002";//发送话费充值请求失败
	public static String HFCZ_SUCCESS="4003";//话费充值成功
	public static String HFCZ_FAIL="4004";//话费充值失败
	
	public static String LOTTERY_REQ_SUCCESS="4101";//发送彩票请求成功
	public static String LOTTERY_REQ_FAIL="4102";//发送彩票请求失败
	public static String LOTTERY_SUCCESS="4103";//彩票购买成功
	public static String LOTTERY_FAIL="4104";//彩票购买失败
	public static String LOTTERY_WIN="4105";//彩票中奖
	
	public static String SETTLE_WAIT="6000";//待期结
	public static String SETTLING="6001";//期结中
	public static String SETTLE_SUCCESS = "6002";//期结完成
	public static String SETTLE_FAIL = "6003";//期间失败
}
