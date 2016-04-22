package com.froad.util.command;

import java.io.UnsupportedEncodingException;
import com.froad.util.MD5;

public class MallCommand {
	//自动登录 验证
	public final static String AUTO_LOGIN_JUDGE_MARK_VERSION = "2.0";
	public final static String AUTO_LOGIN_JUDGE_MARK_KEY = "froad";
	public final static String AUTO_LOGIN_LOGINTYPE_CS = "00";//代销单 客服登陆
	public final static String AUTO_LOGIN_LOGINTYPE_USER = "01";//代销单 用户登陆
	public final static String AUTO_LOGIN_KEY = "AUTO_LOGIN_KEY";//自动登陆
	
	//客服代销单参数
	public final static String CUSTOMER_SERVICE_BUY_VERSION = "version";//版本信息
	public final static String CUSTOMER_SERVICE_BUY_LOGINTYPE = "loginType";//00 客服 01 代客户
	public final static String CUSTOMER_SERVICE_BUY_CSNAME = "CSName";//客服用户名
	public final static String CUSTOMER_SERVICE_BUY_COMPANY = "company";//
	public final static String CUSTOMER_SERVICE_BUY_UNAME = "uname";//用户名（用户中文名）
	public final static String CUSTOMER_SERVICE_BUY_BANK = "bank";//开户银行
	public final static String CUSTOMER_SERVICE_BUY_SEBANK = "seBank";//二级银行
	public final static String CUSTOMER_SERVICE_BUY_USERTYPE = "userType";//客户类型
	public final static String CUSTOMER_SERVICE_BUY_CONWAY = "conWay";//咨询途径
	public final static String CUSTOMER_SERVICE_BUY_CALLSTEL = "callsTel";//来电号码
	public final static String CUSTOMER_SERVICE_BUY_PAYTEL = "payTel";//支付手机号
	public final static String CUSTOMER_SERVICE_BUY_PAYTYPE = "payType";
	public final static String CUSTOMER_SERVICE_BUY_REQID = "reqID";
	public final static String CUSTOMER_SERVICE_BUY_MD5STR = "md5_str";
	public final static String CUSTOMER_SERVICE_PAY_BANKID = "PayChannel";
	
	//客服代销单参数储存
	public final static String CUSTOMER_SERVICE_BUY_CNAME = "CName";
	public final static String PAY_PLATFORM = "platform";//支付平台
	public final static String SESSION_LASTTIME = "lastTime";
	public final static String SESSION_BANKID = "bankid";
	
	
	
	
	
	//自动登录 key
	public final static String AUTO_LOGIN_FLAG = "isAuto";
	public final static String AUTO_LOGIN_NAME = "username";
	
	//登陆验证  key
	public final static String LOGIN_FAILURE_SHOW_USERNAME = "login_username";
	public final static String LOGIN_USER_ID = "login_userId";
	public final static String USER_ID = "userId";
	public final static String LOGIN_VALIDATE_CODE = "validateCode";
	
	public final static String FIRST_LOGIN = "firstLogin";
	
	//商户标识
	public final static String LOGIN_MERCHANT_OR_USER = "isMerchantFlag";
	public final static String LOGIN_MERCHANT_OR_CLERK = "merchant_or_clerk";
	public final static String MERCHANT_ID = "merchant_id";
	public final static String USERID_BECODE = "userId_becode";
	public final static String SELLER_LIST = "sellerList";
	
	public final static String MERCHANTUSERSET_LOGIN_NAME = "merchantuserset_login_name";
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		MD5 md5 = new MD5();
		String mymd5 = md5.getMD5ofStr("user18fft" + MallCommand.AUTO_LOGIN_JUDGE_MARK_KEY);
		
		System.out.println(mymd5);
	}
}