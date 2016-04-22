package com.froad.CB.common.constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.froad.CB.common.EnvCommand;

import common.Logger;


public class PayCommand {
	
	private static Logger logger = Logger.getLogger(PayCommand.class);

	/**ORDER_TYPE  1201：即时交易-实体商品-直接优惠  1202：即时交易-实体商品-返利积分 5000:支付宝支付  **/
	public static final String ORDER_TYPE_CASH="1201";
	
	public static final String ORDER_TYPE_SCORE="1202";
	
	public static final String ORDER_TYPE_COMBINE="5000";
	
	/**币种： 人民币**/
	public static String CURRENCY="1";
	
	public static String MERCHANTNAME="方付通";//商户名
	
//	public static String DISPLAY_NAME="分分通";//订单显示名
	
	public static String DES_KEY="froad";
	
	public static String PUBLICKEY_POINTS;
	//新公钥
	public static String PUBLICKEY;
	public static String RSAPWD;//密码
	//通知公钥
	public static String PUBLIC_KEY_NOTICE;
	
	/** 支付类型 **/
	public static String PAY_TYPE_FILM="20";//贴膜卡
	
	public static String PAY_TYPE_BANK_CARD="30";//银行卡
	
	public static String PAY_TYPE_ALIPAY="53";//支付宝支付
	
	/**资金流转记录表(pay表)里的type字段：支付现金**/
	public static String TYPE_CASH="00";//支付现金
	
	/**资金流转记录表(pay表)里的type字段：扣积分**/
	public static String TYPE_POINTS="01";//扣积分
	
	/***退款**/
	public static String REFUND_ALL="01";//全额退款
	public static String REFUND_PART="02";//部分退款
	
	/**版本**/
	public static String VERSION="1.0";//支付系统版本
	
	/**通用的成功响应码**/
	public static String RESPCODE_SUCCESS="0000";//响应码:成功
	/**转账类型 1：单笔 2：多笔**/
	public static String TRANSFER_TYPE_SINGLE="1";
	public static String TRANSFER_TYPE_MULTI="2";
	
	//------------------- 编码方式 ------------------- //
	public static String CHARSET="UTF-8";
	public static String CHARSET_UTF8="1";//UTF-8
	public static String CHARSET_GBK="2";//GBK
	//------------------- 签名类型 ------------------- //
	public static String SIGNTYPE_RSA="1";//RSA
	public static String SIGNTYPE_MD5="2";//MD5
	//----------- 账单平台返回的stateCode ------------- //
	public static String STATE_CODE_PAYED="0";//已支付
	public static String STATE_CODE_FAILED="1";//已失败
	public static String STATE_CODE_NOT_PAYED="2";//未支付
	public static String STATE_CODE_PAYING="3";//支付中
	public static String STATE_CODE_REFUNDED="4";//已退款
	public static String STATE_CODE_CANCEL="5";//已取消
	public static String STATE_CODE_REFUND_FAILED="6";//退款失败
	//------------------- 交易者类型 -------------------//
	public static final String TRADER_TYPE_BUYER="1";
	public static final String TRADER_TYPE_MERCHANT="1";
	public static final String TRADER_TYPE_BANK="2";
	public static final String TRADER_TYPE_FROAD="3";
	//------------------- 客户端信息 ------------------- //
	public static String CLIENT_PC="100";//PC渠道
	public static String CLIENT_ANDROID="200";//android
	public static String CLIENT_IOS="300";//ios
	
	
	static {
		Properties props = new Properties();
		String filename=EnvCommand.ENV_PATH+"trans.properties";
		try {
			File file=new File(filename);
			InputStream inputStream =new FileInputStream(file);
			props.load(inputStream);
			inputStream.close();
			PUBLIC_KEY_NOTICE=props.getProperty("PUBLIC_KEY_NOTICE");
			PUBLICKEY_POINTS = props.getProperty("PUBLICKEY_POINTS");
			PUBLICKEY = props.getProperty("PUBLICKEY");
			RSAPWD=props.getProperty("RSAPWD");
			logger.info("==============URL初始化完毕==============");
		} catch (FileNotFoundException e) {
			logger.error("异常", e);
		} catch (IOException e) {
			logger.error("异常", e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(RSAPWD);
	}
}
