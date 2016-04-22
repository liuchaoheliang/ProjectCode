package com.froad.cons;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.froad.db.redis.impl.ClientRedisServiceImpl;
import com.froad.util.TimeHelper;
import com.froad.util.TimeHelper.TimeType;

/**
 * 对账系统相关常量
* <p>Function: AccountCheckingConst</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015年7月30日 下午5:11:56
* @version 1.0
 */
public class AccountCheckingConst {

	/**
	 * 生成的文件缓存根目录
	 */
	private static String BASE_TEMP_RUI = File.separator + "tmp" + File.separator;
//	private static String BASE_TEMP_RUI = "f:" + File.separator;
	private static String FILE_SUFFIX = ".txt";
	private static Map<String, Map<String,String>> clientInfo = new HashMap<String, Map<String,String>>();
	
	/**
	 * 方付通对公户帐号名
	 */
	public static String FROAD_ACC_NAME = "方付通对公户";
	
	/**
	 * 方付通对公户帐号
	 */
	public static String FROAD_ACC_NO = "方付通对公户帐号";
	
	/**
	 * 贴膜卡支付编码
	 */
	public static String  FOILCARD_PAY = "2020";
	
	/**
	 * 快捷支付支付编码
	 */
	public static String SHORTCUT_PAY = "2051";
	
	/**
	 * 现金退款编码
	 */
	public static String CASHRETURN_PAY = "2040";
	
	/**
	 * 即时支付编码
	 */
	public static String IMMEDIATE_PAY = "2055";
	
	/**
	 * 银行积分支付
	 */
	public static String YHJFZF_POINT = "1020";
	/**
	 * 银行积分兑换
	 */
	public static String YHJFDH_POINT = "1030";
	/**
	 * 银行积分赠送
	 */
	public static String YHJFZS_POINT = "1080";
	/**
	 * 银行积分退分
	 */
	public static String YHJFTF_POINT = "1040";
	/**
	 * 联盟积分消费
	 */
	public static String LMJFXF_POINT = "1120";
	/**
	 * 联盟积分退分
	 */
	public static String LMJFTF_POINT = "1140";
	/**
	 * 联盟积分赠送
	 */
	public static String LMJFZS_POINT = "1180";
	/**
	 * 联盟积分充值
	 */
	public static String LMJFCZ_POINT = "1160";
	/**
	 * 联盟积分转增
	 */
	public static String LMJFZZ_POINT = "1142";
	
	/**
	 * 银行积分加现金
	 */
	public static String YHJFJXJ = "2010";
	
	/**
	 * 联盟积分加现金
	 */
	public static String LMJFJXJ = "1121";
	
	/**
	 * 对账文件名
	 * ClassName: AccountFileName
	 * Function: TODO ADD FUNCTION
	 * date: 2015年8月25日 上午11:23:19
	 *
	 * @author Zxy
	 * @version AccountCheckingConst
	 */
	public enum AccountFileName{
		
		CASH("UBANK_C"),
		POINT("UBANK_P"),
		DETAILS("UBANK_D");
		
		private String fileName;
		
		private AccountFileName(String fileName){
			this.fileName = fileName;
		}
		
		public String getURI(){
			return BASE_TEMP_RUI + fileName + TimeHelper.formatDate(TimeType.yyyyMMdd, TimeHelper.skewingDate(new Date(),Calendar.DAY_OF_YEAR, -1)) + FILE_SUFFIX;
		}
	}
	
	/**
	 * 
	 * getClientInfo:获取client缓存信息
	 * 主要目的是在同一个业务中 防止频繁请求redis去获取缓存，而是存在程序内存中 节约开销
	 * @author Zxy
	 * 2015年8月26日 上午11:07:03
	 * @param clientId
	 * @return
	 *
	 */
	public static Map<String,String> getClientInfo(String clientId){
		Map<String, String> client = clientInfo.get(clientId);
		if(client == null){
			client = new ClientRedisServiceImpl().getClientByClientId(clientId);
			if(client == null){
				return null;
			}
			clientInfo.put(clientId, client);
			return client;
		}
		return client;
	}
	
	/**
	 * 一个业务完成后重置clientInfo，防止后期clientInfo有更改
	 * resetClientInfo:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年8月26日 上午11:07:59
	 *
	 */
	public static void resetClientInfo(){
		clientInfo.clear();
	}
}
