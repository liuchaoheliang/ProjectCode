package com.froad.util.command;

	/**
	 * 类描述：系统参数指令
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: Oct 9, 2012 9:54:52 AM 
	 */
public class Command {
	
	public static final String CURRENCY="RMB";
	
	/**通用的状态**/
	public static final String STATE_CREATE="10";
	
	public static final String STATE_RECORD="20";
	
	public static final String STATE_START="30";
	
	public static final String STATE_STOP="40";
	
	public static final String STATE_DELETE="50";
	
	public static final String certificationResult_success="1";
	public static final String certificationResult_fail="2";
	
	public static String price_availiable="1";//定价启用
	public static String price_unavailiable="0";//定价未启用
	/* 社区商盟: 一句话描述 */
	public static String 社区商盟;
	public static String respCode_SUCCESS = "0";// 成功
	public static String respCode_FAIL = "1";// 失败
	
	public static Integer HTTP_CONNECTIONTIMEOUT = 600000;// 第三方连接时间
	public static Integer HTTP_SOTIMEOUT = 180000;// 第三方读取数据时间
	
	/**交易状态**/
	public static String Tran_CREATED="00"; //交易已创建
	public static String Tran_Count_Complete="1000";//交易计算完成
	
	public static String TRAN_WAIT_PAY ="3001";//等待买家付款-待下推交易
	
	public static String TRAN_REQPAY_SUCCESSED ="4001";// 发送支付请求成功--下推交易成功（即已下推交易）
	public static String TRAN_REQPAY_FAILED = "4002";// 发送支付请求失败--下推交易失败
	public static String TRAN_PAY_SUCCESSED = "5001";// 付款成功  --支付交易成功
	public static String TRAN_PAY_FAILED = "5002";// 付款失败--支付交易失败
	
	public static String TRAN_EXCEPTION="9001";//交易异常
	public static String TRAN_TIMEOUT_PAY_REQUEST="9002";//支付请求发送超时
	public static String TRAN_CANCEL ="3002";//交易关闭
	
	public static String Tran_Points_CREATED="2000"; //交易已创建
	public static String Tran_Count_Points_Complete="2001";//已计算完积分交易
	public static String TRAN_WAIT_PAY_Points ="2002";//待下推积分交易
	public static String TRAN_Points_REQPAY_SUCCESSED ="2401";// 发送支付请求成功--下推积分交易成功（即已下推交易）
	public static String TRAN_Points_REQPAY_FAILED = "2402";// 发送支付请求失败--下推积分交易失败
	public static String TRAN_Points_PAY_SUCCESSED = "2501";// 付款成功  --支付积分交易成功
	public static String TRAN_Points_PAY_FAILED = "2502";// 付款失败--支付积分交易失败

	
	public static String TRAN_WAIT_PAY_For_Period ="6000";// 待期结
	public static String TRAN_PAYING_For_Period = "6001";// 期结中
	public static String TRAN_PAY_For_Period_SUC = "6002";// 期结完成
	public static String TRAN_PAY_For_Period_Fail = "6003";// 期间失败
}
