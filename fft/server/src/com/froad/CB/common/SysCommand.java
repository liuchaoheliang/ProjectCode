package com.froad.CB.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import common.Logger;

public class SysCommand {
	private static Logger logger = Logger.getLogger(SysCommand.class);
	public static String SERVER_URL;// 服务端地址
	public static String BILL_NOTICE_URL;// 代收、代扣通知地址
	public static String MESSAGE_URL;// 短信平台地址
	public static String SMS_URL;//最新的短信平台地址
	public static String REFUND_NOTICE_URL;// 退款通知地址
	
	/**方付通商城基础地址**/
	public static String FROAD_URL;
	
	/**查询会员接口**/
	public static String USER_ADD_UPDATE_URL;	
	
	/** 手机客户端自动注册用户测试接口 **/
	public static String USER_AUTO_ADD_URL;
	
	/**商城API入口地址**/
	public static String FROADMALL_URL;
	
	/**话费充值的通知**/
	public static String HFCZ_NOTICE_URL;
	
	/**彩票发货通知**/
	public static String LOTTERY_SHIPMENTS_NOTICE_URL;
	
	/**彩票中奖通知**/
	public static String LOTTERY_WIN_NOTICE_URL;
	
	/**账单平台基础地址**/
	public static String BILL_URL;
	
	/**查询或校验的测试地址**/
	public static String CHECK_URL;
	
	/**代收接口测试地址**/
	public static String AGENCY_FUND_URL;
	
	/**代扣接口测试地址**/
	public static String DEDUCT_URL;
	
	/**转账的测试地址**/
	public static String TRANSFER_URL;
	
	/**退款接口(代收)**/
	public static String REFUND_URL;
	
	/**退款请求地址(合并支付的退款)**/
	public static String REFUND_COMBINE_URL;
	
	/**积分平台的基础地址**/
	public static String POINTS_URL;
	
	/**查询会员积分：测试地址**/
	public static String QUERY_POINTS_URL;

	/**消费积分地址**/
	public static String PAY_POINTS_URL;

	/**退还积分地址**/
	public static String REFUND_POINTS_URL;

	/**增送积分地址**/
	public static String PRESENT_POINTS_URL;

	/**积分返利地址**/
	public static String REBATE_POINTS_URL;
	
	/**积分兑充地址**/
	public static String FILL_POINTS_URL;
	
	/**积分提现地址**/
	public static String WITHDRAW_POINTS_URL;
	

	/**积分提现通知地址**/
	public static String WITHDRAW_POINTS_NOTICE_URL;

    /**订单合并支付请求地址**/
	public static String COMBINE_ORDER_REQUEST_URL;
	
	/**订单合并支付通知地址**/
	public static String COMBINE_ORDER_NOTICE_URL;

	//========================账户账务平台hessian地址
	public static String USER_SPEC_SERVICE_URL;
	
	
	
	
	static {
		logger.info("==============初始化URL==============");
		Properties props = new Properties();
		try {
			String filename=EnvCommand.ENV_PATH+"url.properties";
 	    	File file=new File(filename);
 	 		InputStream inputStream =new FileInputStream(file);
			props.load(inputStream);
			inputStream.close();
			MESSAGE_URL = props.getProperty("MESSAGE_URL");
			
			SMS_URL = props.getProperty("SMS_URL");
			
			SERVER_URL = props.getProperty("SERVER_URL");
			POINTS_URL=props.getProperty("POINTS_URL");
			BILL_URL=props.getProperty("BILL_URL");
			FROAD_URL=props.getProperty("FROAD_URL");
			
			USER_SPEC_SERVICE_URL = props.getProperty("USER_SPEC_SERVICE_URL");
			
			BILL_NOTICE_URL = SERVER_URL + "/BillInformServlet";
			WITHDRAW_POINTS_NOTICE_URL=SERVER_URL + "/WithdrawPointsServlet";
			COMBINE_ORDER_NOTICE_URL=SERVER_URL+"/CombineOrderServlet";
			REFUND_NOTICE_URL = SERVER_URL + "/RefundServlet";
			HFCZ_NOTICE_URL=SERVER_URL + "/HFCZServlet";
			LOTTERY_SHIPMENTS_NOTICE_URL=SERVER_URL + "/LotteryShipmentsServlet";
			LOTTERY_WIN_NOTICE_URL=SERVER_URL + "/LotteryWinServlet";
			
			QUERY_POINTS_URL=POINTS_URL+"/getParAccountPoints.api";
			PAY_POINTS_URL=POINTS_URL+"/payPoints.api";
			REFUND_POINTS_URL=POINTS_URL+"/refundPoints.api";
			PRESENT_POINTS_URL=POINTS_URL+"/presentPoints.api";
			REBATE_POINTS_URL=POINTS_URL+"/fillAndPresend.api";
			FILL_POINTS_URL=POINTS_URL+"/fillPoints.api";
			WITHDRAW_POINTS_URL=POINTS_URL+"/cashapp.api";
			
			AGENCY_FUND_URL=BILL_URL+"/agencyfund.api";
			DEDUCT_URL=BILL_URL+"/deduction.api";
			TRANSFER_URL=BILL_URL+"/transfer.api";
			REFUND_URL=BILL_URL+"/gateway/refund.api";
			REFUND_COMBINE_URL=BILL_URL+"/gateway/combine_refund.api";
			CHECK_URL=BILL_URL+"/accountcheck.api";
            COMBINE_ORDER_REQUEST_URL=BILL_URL+"/gateway/direct/combine_transaction.api";
			
			USER_ADD_UPDATE_URL=FROAD_URL+"/userAPI";	
			USER_AUTO_ADD_URL=FROAD_URL+"/userAPI.do";
			FROADMALL_URL=FROAD_URL+"/froadmallapi.do";


			logger.info("==============URL初始化完毕==============");
		} catch (FileNotFoundException e) {
			logger.error("异常", e);
		} catch (IOException e) {
			logger.error("异常", e);
		}
	}

	public static void main(String[] args) {
		System.out.println(LOTTERY_SHIPMENTS_NOTICE_URL);
		System.out.println(LOTTERY_WIN_NOTICE_URL);
	}
}
