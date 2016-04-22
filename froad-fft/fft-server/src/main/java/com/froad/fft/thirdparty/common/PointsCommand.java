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
 * 积分相关常量
 * @author FQ
 *
 */
public class PointsCommand {

	//方付通的会员标识类型
	public static String ACCOUNT_MARKED_TYPE_PHONE="01";
	public static String ACCOUNT_MARKED_TYPE_USERNAME="02";
	
	/**消费积分的对象类型 1：积分消费 2：积分提现**/
	public static String OBJECT_TYPE_CONSUME="1";
	
	/**方付通积分机构号**/
	public static String FROAD_ORG_NO;
	
	/** 积分 **/
	public static String POINTS_RSA_PRIVATE_KEY;
	public static String POINTS_RSA_PRIVATE_PWD;	
	public static String POINTS_PUBLICKEY;
	public static String POINTS_PUBLICKEY_WITHDRAW_NOTICE;//接收积分提现通知时使用
	
	/**积分平台URL**/
	private static String POINTS_URL;

	//查询积分 url
	public static String QUERY_POINTS_URL;	
	
	//消耗积分 url
	public static String CONSUME_POINTS_URL;
	
	//退还积分 url
	public static String REFUND_POINTS_URL;
	
	//赠送积分 url
	public static String DONATE_POINTS_URL;
	
	//兑充积分 url
	public static String FILL_POINTS_URL;
	
	//积分提现申请url
	public static String WITHDRAW_URL;
	
	/**积分提现通知URL**/
	public static String WITHDRAW_NOTICE_URL;

	static{
		Logger logger = Logger.getLogger(PointsCommand.class);
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
		
		FROAD_ORG_NO = props.getProperty("FROAD_ORG_NO");
		POINTS_URL = props.getProperty("POINTS_URL");
		POINTS_RSA_PRIVATE_KEY = props.getProperty("POINTS_RSA_PRIVATE_KEY");
		POINTS_RSA_PRIVATE_PWD = props.getProperty("POINTS_RSA_PRIVATE_PWD");
		POINTS_PUBLICKEY = props.getProperty("POINTS_PUBLICKEY");
		POINTS_PUBLICKEY_WITHDRAW_NOTICE=props.getProperty("POINTS_PUBLICKEY_WITHDRAW_NOTICE");
		
		QUERY_POINTS_URL = POINTS_URL + "getParAccountPoints.api";
		CONSUME_POINTS_URL = POINTS_URL + "payPoints.api";
		REFUND_POINTS_URL = POINTS_URL + "refundPoints.api";
		DONATE_POINTS_URL = POINTS_URL + "presentPoints.api";
		FILL_POINTS_URL = POINTS_URL + "fillPoints.api";
		WITHDRAW_URL = POINTS_URL + "cashapp.api";
		
		WITHDRAW_NOTICE_URL = Constants.SERVER_IP_ADDR + "withdrawPoints.api";
		
	}
}
