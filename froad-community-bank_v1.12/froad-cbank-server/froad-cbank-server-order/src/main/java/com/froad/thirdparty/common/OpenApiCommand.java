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
 * *******************************************************
 * <p> 工程: fft-server </p>
 * <p> 类名: OpenApiCommand.java </p>
 * <p> 描述: *-- <b>OPENAPI常量配置</b> --* </p>
 * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 * <p> 时间: 2014年1月16日 下午1:41:48 </p>
 * *******************************************************
 */
public class OpenApiCommand
{

	public static String MERCHANT_ACCT_QUERY_URL;
	
    /**
     * 自定义密钥
     */
    public static String OPENAPI_CUSTOM_KEY;

    /**
     * 转账显示名
     */
    public static String TRANSFER_DISPLAY = "社区银行";

    /**
     * 订单显示名
     */
    public static String ORDER_DISPLAY = "社区银行";

    /**
     * 账户校验或查询接口成功*
     */
    public static final String SUCCESS = "00";
    public static final String FAIL = "01";

    /**
     * ----------- 账单平台返回的stateCode -------------*
     */
    public static String STATE_CODE_PAYED = "0";//已支付
    public static String STATE_CODE_FAILED = "1";//已失败
    public static String STATE_CODE_REFUNDED = "0";//已退款
    
    public static String ORDER_TYPE_TRANS="1001";//交易订单
    public static String ORDER_TYPE_REFUND="1002";//退款订单
    
    public static String QUERY_SINGLE="1";//单笔订单查询
    public static String QUERY_MULTI="2";//多笔订单查询

    /**
     * 公钥 *
     */
    public static String OPENAPI_RSA_PRIVATE_KEY;
    public static String OPENAPI_RSA_PRIVATE_PWD;
    public static String OPENAPI_PUBLICKEY;
    public static String OPENAPI_VERSION;//调用OpenApi接口版本号

    /**
     * 币种： 人民币*
     */
    public static String CURRENCY = "1";

    /**
     * openapi平台 URL
     */
    private static String OPENAPI_URL;

    /**
     * 退款申请 URL
     */
    public static String REFUND_URL;

    /**
     * 退款申请 URL(合并支付的退款)
     */
    public static String REFUND_COMBINE_URL;

    /**
     * 转账 URL
     */
    public static String TRANSFER_URL;

    /**
     * 校验查询 URL
     */
    public static String ACCOUNT_CHECK_URL;

    /**
     * 代收 URL
     */
    public static String AGENCY_COLLECT_URL;

    /**
     * 代扣 URL
     */
    public static String AGENCY_DEDUCT_URL;

    /**
     * 合并订单支付 URL
     */
    public static String COMBINE_ORDER_URL;

    /**
     * openapi异步退款通知
     */
    public static String REFUND_NOTICE_URL;
    
    /**openapi异步退款通知(部分退款)**/
    public static String REFUND_PART_NOTICE_URL;

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
    /**
     * 快捷支付签约
     */
    public static String FAST_CARD_SIGN_URL;
    /**
     * 快捷支付解约
     */
    public static String FAST_CARD_SIGN_CANCEL_URL;
    /**
     * 产生手机验证码
     */
    public static String FAST_CREATE_MOBILE_TOCKEN_URL;
    /**
     * 发送手机短信
     */
    public static String FAST_SEND_SMS_URL;

    /**
     * 设置限额
     */
    public static String FASET_SET_LIMIT_URL;
    
    /**
     * 账单平台查询接口地址
     */
    public static String QUERY_TRANS_URL;
    
    /**
     * 设置白名单收款账户
     */
    public static String WHITE_LIST_SET_URL;
    
    /**
     * 登录验证
     */
    public static String EMPLOYEE_CODE_VERIFY_URL;
    
    /**
     * 审核状态查询
     */
    public static String AUDIT_STATUS_QUERY_URL;
    
    /**
     * 银行卡账户校验
     */
    public static String BANK_CARD_ACCOUNT_CHECK_URL;

    static
    {
        Properties props = new Properties();
        FileReader fr = null;
        try
        {
        	fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH) + File.separatorChar+"thirdparty.properties"));
            props.load(fr);
        }
        catch (FileNotFoundException e)
        {
            LogCvt.error("加载配置文件信息错误", e);
        }
        catch (IOException e)
        {
        	LogCvt.error("加载配置文件信息错误", e);
        }finally{
			try {
				if(fr != null){
					fr.close();
				}
			} catch (IOException e) {
			}
		}

        OPENAPI_CUSTOM_KEY = props.getProperty("OPENAPI_CUSTOM_KEY");
        OPENAPI_URL = props.getProperty("OPENAPI_URL");
        OPENAPI_RSA_PRIVATE_KEY = props.getProperty("OPENAPI_RSA_PRIVATE_KEY");
        OPENAPI_RSA_PRIVATE_PWD = props.getProperty("OPENAPI_RSA_PRIVATE_PWD");
        OPENAPI_PUBLICKEY = props.getProperty("OPENAPI_PUBLICKEY");
        OPENAPI_VERSION = props.getProperty("OPENAPI_VERSION");
        
        
        //合并支付和结算通知地址
        COMBINE_ORDER_NOTICE_URL = props.getProperty("COMBINE_ORDER_NOTICE_URL");
        
        //提供给openapi反查商户地址的servlet
        MERCHANT_ACCT_QUERY_URL = props.getProperty("MERCHANT_ACCT_QUERY_URL");
        
        //退款通知地址
        REFUND_NOTICE_URL = props.getProperty("REFUND_NOTICE_URL");
        
        
//        SERVER_IP_ADDR = props.getProperty("SERVER_IP_ADDR");
        
        REFUND_URL = OPENAPI_URL + "gateway/refund.api";
        REFUND_COMBINE_URL = OPENAPI_URL + "gateway/combine_refund.api";
        ACCOUNT_CHECK_URL = OPENAPI_URL + "accountcheck.api";
        TRANSFER_URL = OPENAPI_URL + "transfer.api";
        AGENCY_COLLECT_URL = OPENAPI_URL + "agencyfund.api";
        AGENCY_DEDUCT_URL = OPENAPI_URL + "deduction.api";
        COMBINE_ORDER_URL = OPENAPI_URL + "gateway/direct/combine_transaction.api";

//      REFUND_NOTICE_URL = SERVER_IP_ADDR + "refund.api";
//      REFUND_PART_NOTICE_URL= SERVER_IP_ADDR + "partRefund.api";
//        AGENCY_COLLECT_NOTICE_URL = SERVER_IP_ADDR + "agencyCollect.api";
//        AGENCY_DEDUCT_NOTICE_URL = SERVER_IP_ADDR + "agencyDeduct.api";
//      COMBINE_ORDER_NOTICE_URL = "http://192.168.19.123:7002/TestForWar/test.api";

        FAST_CARD_SIGN_URL = OPENAPI_URL + "cardSign.api";
        FAST_CARD_SIGN_CANCEL_URL = OPENAPI_URL + "cardSignCancel.api";
        FAST_CREATE_MOBILE_TOCKEN_URL = OPENAPI_URL + "createMobileToken.api";
        FAST_SEND_SMS_URL = OPENAPI_URL + "sendsms.api";
        FASET_SET_LIMIT_URL = OPENAPI_URL + "limitSet.api";
        QUERY_TRANS_URL = OPENAPI_URL + "gateway/query.api";
        WHITE_LIST_SET_URL = OPENAPI_URL + "whiteListSet.api";
        EMPLOYEE_CODE_VERIFY_URL = OPENAPI_URL + "employeeCodeVerify.api";
        AUDIT_STATUS_QUERY_URL = OPENAPI_URL + "auditStatusQuery.api";
        BANK_CARD_ACCOUNT_CHECK_URL = OPENAPI_URL  + "bankCardAccountCheck.api";

    }
    
    public static void main(String[] args) {
		System.out.println(COMBINE_ORDER_NOTICE_URL);
	}
}
