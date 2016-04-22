package com.froad.thirdparty.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;


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
	public static String OBJECT_TYPE_WITHDRAW="2";
	
	/**证件类型：身份证**/
	public static String CERT_TYPE_ID="011";
	
	/**成功的响应码**/
	public static String RESP_CODE_SUCCESS="0000";
	
	/**方付通积分机构号**/
	public static String FROAD_ORG_NO;
	
	/** 积分 **/
	public static String POINTS_RSA_PRIVATE_KEY;
	public static String POINTS_RSA_PRIVATE_PWD;	
	public static String POINTS_PUBLICKEY;
	public static String POINTS_PUBLICKEY_WITHDRAW_NOTICE;//接收积分提现通知时使用
	
	public static String POINTS_PARTENER_NO_AN_HUI_NONG_XIN="100010043";
	
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
	
	//签约关系通知url
	public static String NOTIFY_RELATION_URL;
	
	//充值卡信息查询接口地址
	public static String QUERY_DEPOSIT_URL;
	//账户充值接口地址
	public static String CARD_DEPOSIT_URL;
	
	/**获取银行短信验证码url**/
	public static String BANK_MSG_CODE_URL;
	
	/**银行验证码验证url**/
	public static String VALIDATE_CHECK_CODE_URL;
	
	/**根据手机号查询银行积分url**/
	public static String QUERY_BANK_POINTS_BY_MOBILE_URL;
	
	/**安徽手机消费接口url**/
	public static String PAY_POINTS_BY_MOBILE_URL;
	
	/**查询积分比例**/
	public static String QUERY_RATIO_URL;
	
	/**银行账户签约**/
	public static String BIND_BANK_ACCOUNT_URL;
	
	/**银行账户签约**/
	public static String UNBIND_BANK_ACCOUNT_URL;
	
	/**用户消费积分分页-老接口**/
	public static String QUERY_MEMBER_PROTOCOL_URL;
	
	public static String CONTRACT_RELATIONSHIP_QUERY_URL;
	
	/**主动确认订单状态**/
	public static String QUERY_ORDER_STATUS_URL;

	/**用户消费积分分页-详情-新接口**/
    public static String QUERY_TRADE_DETAILS_URL;
    
    /**用户消费积分分页-合计-新接口**/
    public static String TRADE_POINT_STOTALS_URL;
	 /**
     * 服务端地址
     */
    public static String SERVER_IP_ADDR;
	
	static{
		Properties props = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"thirdparty.properties"));
			props.load(fr);
		} catch (FileNotFoundException e) {
			LogCvt.error("加载配置文件信息错误", e);
		} catch (IOException e) {
			LogCvt.error("加载配置文件信息错误", e);
		}
		
		FROAD_ORG_NO = props.getProperty("FROAD_ORG_NO");
		POINTS_URL = props.getProperty("POINTS_URL");
		POINTS_RSA_PRIVATE_KEY = props.getProperty("POINTS_RSA_PRIVATE_KEY");
		POINTS_RSA_PRIVATE_PWD = props.getProperty("POINTS_RSA_PRIVATE_PWD");
		POINTS_PUBLICKEY = props.getProperty("POINTS_PUBLICKEY");
		POINTS_PUBLICKEY_WITHDRAW_NOTICE=props.getProperty("POINTS_PUBLICKEY_WITHDRAW_NOTICE");
		
		SERVER_IP_ADDR = props.getProperty("SERVER_IP_ADDR");
		
		POINTS_PARTENER_NO_AN_HUI_NONG_XIN = props.getProperty("POINTS_PARTENER_NO_AN_HUI_NONG_XIN", POINTS_PARTENER_NO_AN_HUI_NONG_XIN);
		
		QUERY_POINTS_URL = POINTS_URL + "getParAccountPoints.api";
		CONSUME_POINTS_URL = POINTS_URL + "payPoints.api";
		REFUND_POINTS_URL = POINTS_URL + "refundPoints.api";
		DONATE_POINTS_URL = POINTS_URL + "presentPoints.api";
		FILL_POINTS_URL = POINTS_URL + "fillPoints.api";
		WITHDRAW_URL = POINTS_URL + "cashapp.api";
		NOTIFY_RELATION_URL = POINTS_URL + "notifyAccount.api";
		QUERY_DEPOSIT_URL = POINTS_URL + "card/querydeposit.api";
		CARD_DEPOSIT_URL = POINTS_URL + "card/deposit.api";
		BANK_MSG_CODE_URL = POINTS_URL + "bankMsgCode.api";
		VALIDATE_CHECK_CODE_URL = POINTS_URL + "validateCheckCode.api";
		QUERY_BANK_POINTS_BY_MOBILE_URL = POINTS_URL + "queryBankPointsByMobile.api";
		PAY_POINTS_BY_MOBILE_URL = POINTS_URL + "payPointsByMobile.api";
		QUERY_RATIO_URL = POINTS_URL + "queryRatio.api";
		BIND_BANK_ACCOUNT_URL = POINTS_URL + "bindAccount.api";
		UNBIND_BANK_ACCOUNT_URL = POINTS_URL + "unBindAccount.api";
		QUERY_MEMBER_PROTOCOL_URL = POINTS_URL + "queryMemberProtocols.api";
		QUERY_TRADE_DETAILS_URL = POINTS_URL + "queryTradeDetails.api";
		TRADE_POINT_STOTALS_URL = POINTS_URL + "tradePointstotals.api";
		QUERY_ORDER_STATUS_URL = POINTS_URL + "queryOrderStatus.api";
		CONTRACT_RELATIONSHIP_QUERY_URL = POINTS_URL + "contractRelationshipQuery.api";
		
		WITHDRAW_NOTICE_URL = SERVER_IP_ADDR + "withdrawPoints.api";
		
	}
}
