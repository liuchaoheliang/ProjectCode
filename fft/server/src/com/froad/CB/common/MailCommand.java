package com.froad.CB.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import common.Logger;

public class MailCommand {
	 public static Logger logger = Logger.getLogger(MailCommand.class);
	 public static String MailServerHost = "mail.f-road.com.cn";
	 public static String MailServerPort = "25";
	 public static String USER_NAME = "froadmall@f-road.com.cn";
	 public static String PASSWORD = "123froad";
	 public static String FROMADDRESS = "froadmall@f-road.com.cn";
	 public static String DAIGOU_TOADDRESS = "zhangyu@f-road.com.cn";
	 public static String GAME_TOADDRESS = "zhubo@f-road.com.cn";
	 public static String ORDER_CYCLE_TOADDERSS =   "zhubo@f-road.com.cn";// 订单轮循通知地址
	 public static String ORDER_CYCLE_CCADDERSS[] =   {"chenxiangde@f-road.com.cn","meiwenxiang@f-road.com.cn"};// 订单轮循抄送地址
	 public static String DAIGOU_CCADDERSS[] =   {"orderform01@f-road.com.cn","orderform02@f-road.com.cn"};// 订单轮循抄送地址
	 public static String POINT_SEND_FAIL[] =   {"liqiaopeng@f-road.com.cn","meiwenxiang@f-road.com.cn"};//积分赠送失败抄送地址	 
	 public static String TRAN_REQSENDGOODS_FAILED_TOADDRESS = "value-fail@f-road.com.cn";
	 public static HashMap<String, String> BUSINESS_MAIL_TOADDRESS;//客服代销匹配地址
	 public static String BUSINESS_MAIL_OTHER_TOADDRESS;//客服代销无匹配地址
	 public static List<String> REFUND_MAIL_ADDR=new ArrayList<String>();//退款失败，邮件通知地址
	 static{
		   REFUND_MAIL_ADDR.add("zhubo@f-road.com.cn");
		   REFUND_MAIL_ADDR.add("meiwenxiang@f-road.com.cn");
		   REFUND_MAIL_ADDR.add("fangquan@f-road.com.cn");
		   REFUND_MAIL_ADDR.add("huhuabo@f-road.com.cn");
		   REFUND_MAIL_ADDR.add("lijinkui@f-road.com.cn");
		   Properties props = new Properties();
		   InputStream inputStream = Command.class.getClassLoader().getResourceAsStream("config/mail.properties");
		   
		   try {
			   props.load(inputStream);
			   inputStream.close();
			   USER_NAME = props.getProperty("USER_NAME");
			   PASSWORD = props.getProperty("PASSWORD");
			   FROMADDRESS = props.getProperty("FROMADDRESS");
			   DAIGOU_TOADDRESS = props.getProperty("DAIGOU_TOADDRESS");
			   GAME_TOADDRESS = props.getProperty("GAME_TOADDRESS");
			   TRAN_REQSENDGOODS_FAILED_TOADDRESS = props.getProperty("TRAN_REQSENDGOODS_FAILED_TOADDRESS");
			   ORDER_CYCLE_TOADDERSS = props.getProperty("ORDER_CYCLE_TOADDERSS");
			   String ccAderss = props.getProperty("ORDER_CYCLE_CCADDERSS");
			   ORDER_CYCLE_CCADDERSS = ccAderss == null || "".equals(ccAderss.trim())  ? null : ccAderss.split("[|]");
			   BUSINESS_MAIL_OTHER_TOADDRESS = props.getProperty("BUSINESS_MAIL_OTHER_TOADDRESS");
			   String dagouCcAderss = props.getProperty("DAIGOU_CCADDERSS");
			   DAIGOU_CCADDERSS = dagouCcAderss == null || "".equals(dagouCcAderss.trim())  ? null : dagouCcAderss.split("[|]");
			   String business = props.getProperty("BUSINESS_MAIL_TOADDRESS");
			   String[] businessMail = business == null || "".equals(business.trim()) ? null : business.split("[|]");
			   BUSINESS_MAIL_TOADDRESS = new HashMap<String, String>();
			   for (int i = 0; i < businessMail.length; i++) {
				   String[] mail = businessMail[i].split("[:]");
				   BUSINESS_MAIL_TOADDRESS.put(mail[0], mail[1]);
			   }
			   
			   logger.info("USER_NAME:"+USER_NAME+"\n"+"PASSWORD:"+PASSWORD+"\n"+"FROMADDRESS:"+FROMADDRESS+"\n"
				   		+"TOADDRESS:"+DAIGOU_TOADDRESS+"\n"+"ORDER_CYCLE_TOADDERSS:"+ORDER_CYCLE_TOADDERSS+"\n"+"ORDER_CYCLE_CCADDERSS:"+ccAderss+"\n"
				   		+"BUSINESS_MAIL_TOADDRESS:"+business+"\n" + "DAIGOU_CCADERSS:"+dagouCcAderss+"\n");
			   
		   } catch (FileNotFoundException e) {
			   logger.error("异常", e);
			  } catch (IOException e) {
				  logger.error("异常", e);
			  }
		}
	
}
