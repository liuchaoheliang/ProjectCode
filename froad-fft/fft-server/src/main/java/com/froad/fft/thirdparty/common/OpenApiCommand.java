package com.froad.fft.thirdparty.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.froad.fft.common.Constants;
import com.froad.fft.sysconfig.SysConfigInitialize;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: OpenApiCommand.java </p>
 *<p> 描述: *-- <b>OPENAPI常量配置</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年1月16日 下午1:41:48 </p>
 ********************************************************
 */
public class OpenApiCommand {

	/**自定义密钥*/
	public static String OPENAPI_CUSTOM_KEY;
	
	/**转账显示名*/
	public static String TRANSFER_DISPLAY = "方付通|生活平台";
	
	/**订单显示名*/
	public static String ORDER_DISPLAY = "方付通|分分通消费";
	
	/**账户校验或查询接口成功**/
	public static final String SUCCESS="00";
	public static final String FAIL="01";
	
	/**----------- 账单平台返回的stateCode -------------**/
	public static String STATE_CODE_PAYED="0";//已支付
	public static String STATE_CODE_FAILED="1";//已失败
	public static String STATE_CODE_REFUNDED="0";//已退款
	
	/** 公钥 **/
	public static String OPENAPI_RSA_PRIVATE_KEY;
	public static String OPENAPI_RSA_PRIVATE_PWD;	
	public static String OPENAPI_PUBLICKEY;	
	public static String OPENAPI_VERSION;//调用OpenApi接口版本号
	
	/**币种： 人民币**/
	public static String CURRENCY="1";
	
	/**openapi平台 URL*/
	private static String OPENAPI_URL;
	
	/**退款申请 URL*/
	public static String REFUND_URL;
	
	/**退款申请 URL(合并支付的退款)*/
	public static String REFUND_COMBINE_URL;
	
	/**转账 URL*/
	public static String TRANSFER_URL;
	
	/**校验查询 URL*/
	public static String ACCOUNT_CHECK_URL;
	
	/**代收 URL*/
	public static String AGENCY_COLLECT_URL;
	
	/**代扣 URL*/
	public static String AGENCY_DEDUCT_URL;
	
	/**合并订单支付 URL*/
	public static String COMBINE_ORDER_URL;
	
	/**
	 * openapi异步退款通知
	 */
	public static String REFUND_NOTICE_URL;
	
	/**
	 * openapi异步代收通知
	 */
	public static String AGENCY_COLLECT_NOTICE_URL;
	
	/**
	 * openapi异步代扣通知
	 */
	public static String AGENCY_DEDUCT_NOTICE_URL;
	
	/**
	 * openapi异步合并订单支付通知
	 */
	public static String COMBINE_ORDER_NOTICE_URL;
	
	
	static{
		Logger logger = Logger.getLogger(OpenApiCommand.class);
		Properties props = new Properties();
		final String configFileName = "thirdparty.properties";
		File file = new File(SysConfigInitialize.BASE_CONFIG_PATH + configFileName);
		try {
			InputStream inputStream =new FileInputStream(file);
			props.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			logger.error("加载配置文件: "+configFileName+" 相关信息错误", e);
		} catch (IOException e) {
			logger.error("加载配置文件: "+configFileName+" 相关信息错误", e);
		}finally{
			logger = null;
		}
		
		OPENAPI_CUSTOM_KEY = props.getProperty("OPENAPI_CUSTOM_KEY");
		OPENAPI_URL = props.getProperty("OPENAPI_URL");
		OPENAPI_RSA_PRIVATE_KEY = props.getProperty("OPENAPI_RSA_PRIVATE_KEY");
		OPENAPI_RSA_PRIVATE_PWD = props.getProperty("OPENAPI_RSA_PRIVATE_PWD");
		OPENAPI_PUBLICKEY = props.getProperty("OPENAPI_PUBLICKEY");
		OPENAPI_VERSION = props.getProperty("OPENAPI_VERSION");
		
		REFUND_URL = OPENAPI_URL + "gateway/refund.api";
		REFUND_COMBINE_URL=OPENAPI_URL+"gateway/combine_refund.api";
		ACCOUNT_CHECK_URL = OPENAPI_URL + "accountcheck.api";
		TRANSFER_URL = OPENAPI_URL + "transfer.api";
		AGENCY_COLLECT_URL = OPENAPI_URL + "agencyfund.api";	
		AGENCY_DEDUCT_URL = OPENAPI_URL + "deduction.api";
		COMBINE_ORDER_URL = OPENAPI_URL + "gateway/direct/combine_transaction.api";
		
		REFUND_NOTICE_URL = Constants.SERVER_IP_ADDR + "refund.api";
		AGENCY_COLLECT_NOTICE_URL = Constants.SERVER_IP_ADDR + "agencyCollect.api";
		AGENCY_DEDUCT_NOTICE_URL = Constants.SERVER_IP_ADDR + "agencyDeduct.api";
		COMBINE_ORDER_NOTICE_URL = Constants.SERVER_IP_ADDR + "combineOrder.api";
		
	}
}
