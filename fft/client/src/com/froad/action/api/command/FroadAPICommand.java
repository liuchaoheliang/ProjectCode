package com.froad.action.api.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

import com.froad.util.Command;
 


public class FroadAPICommand {
	private static Properties API_Config ;
	private static Properties validateMsg;
	public static  String picHead=Command.IMAGE_SERVER_URL;
	public static final String pointsSuccessResultCode="0000";
	
	public static final String KEY = "00CBA177EA725E7C";
	public static final String Charset = "utf-8";
	
	public static final String FROAD_CLIENT_SESSION_SMCODE = "SESSION_SMCODE";//短信验证码
	public static final String FROAD_CLIENT_SESSION_SIGN = "SESSION_SIGN";
	public static final String FROAD_CLIENT_PUBLIC_NO = "00";
	public static final String FROAD_CLIENT_LOGIN_NO = "0101";
	
	public final static String MOBILE_SM_LOGIN = "01";//手机登陆验证码短信
	public final static String MOBILE_SM_REGISERED = "02";//注册验证码短信
	public final static String MOBILE_SM_NO_KEY = "mobile";//注册验证码短信
	
	//手机登陆验证码短信 code key
	public final static String MOBILE_SM_SESSION_SMCODE_LOGIN = FROAD_CLIENT_SESSION_SMCODE + MOBILE_SM_LOGIN;//手机登陆验证码短信
	//注册验证码短信 code key
	public final static String MOBILE_SM_SESSION_SMCODE_REGISERED =FROAD_CLIENT_SESSION_SMCODE + MOBILE_SM_REGISERED;//注册验证码短信
	
	//手机登陆验证码短信 mobile key
	public final static String MOBILE_SM_SESSION_MOBILE_LOGIN = MOBILE_SM_SESSION_SMCODE_LOGIN + MOBILE_SM_NO_KEY;//手机登陆手机
	//注册验证码短信 mobile key
	public final static String MOBILE_SM_SESSION_MOBILE_REGISERED = MOBILE_SM_SESSION_SMCODE_REGISERED + MOBILE_SM_NO_KEY;//注册手机
	
	public static final String FROAD_CLIENT_SUCCESS = "0000";
	public static final String FROAD_CLIENT_SIGN = "0001";
	public static final String FROAD_CLIENT_EXCEPTION = "0009";
	
	/**积分相关的常量**/
	public static final String ACCOUNT_MARKED_TYPE="02";
	public static String  fftToCash="1"; 
	public static String  bankToCash="2";
	
	/**话费充值的常量**/
	public static final String SPUserName_HFCZ = "froadmall";
	public static final String SPpassword_HFCZ = "froadmall";
	public static String CODE_FAIL="0001";//失败
//	public static final String hfczSPkey_HFCZ = "froad";//测试用froad,生产用froadmall
	public static final String OFCARD_GAMEZHICHONG_CLASSID="22";//欧飞大类:网络游戏充值
	
	/**交易的常量  **/
	public static final String  Client_type = "100";   //客户端类型pc
	
	public static final String  trans_type_group = "02";    //团购交易
	public static final String  trans_type_carry = "04";   //积分提现交易
	public static final String  trans_type_Exchange = "01";  //积分兑换
	public static final String  trans_type_PointBack = "03"; //返利积分 
	
	 
	
	/**交易状态**/
	public static final String TRAN_PROCESSING="01"; //交易处理中
	public static final String TRAN_SUCCESS="02"; //交易成功
	public static final String TRAN_FAIL="03";//交易失败
	
	public static final String ON_RACK="1";
	/***商品分类**/
	public static final String GoodsCategory_HFCZ ="100001006"; //话费
	public static final String GoodsCategory_Lottery ="100001005"; //彩票
	public static final String GoodsCategory_PointsGoods ="100001010"; //实体商品商品
	public static final String GoodsCategory_CarryPoints ="100001007"; //积分提现
	
	/******图片像素*********/
	public static final String PIC_BIG="_big";//大图
	public static final String PIC_SAMLL="_small";//小图
	public static final String PIC_THUMBNAIL="_thumbnail";//缩略图
	
	public static String serverVersion;
	public static String apkPath;
	public static String ipaPath;
	public static String logPath;
	public static final Map<String, String> statusMap;//状态描述
	 
	public static String  serverPic= ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+ ServletActionContext.getRequest().getContextPath()+"/upload/clientPic/moren.gif";
	
	static{
			statusMap = new TreeMap<String, String>();
			statusMap.put("wait_pay", "等待买家付款");
			statusMap.put("wait_send", "等待卖家发货");
			statusMap.put("sent", "卖家已发货");
			statusMap.put("success", "交易成功");
			statusMap.put("closed", "交易关闭");
			statusMap.put("exception", "交易异常");
			statusMap.put("refund", "交易退款");
		 	API_Config = new Properties();
			validateMsg = new Properties();
			
		   try {
			   String filename=com.froad.common.EnvCommand.ENV_PATH+"mobile_client.properties";
		    	File file=new File(filename);
		    	InputStream inputStream1 =new FileInputStream(file);
				InputStream inputStream2 = FroadAPICommand.class.getClassLoader().getResourceAsStream("config/froadClientAPI_Error.properties");
			   API_Config.load(inputStream1);
			    inputStream1.close();
			  //  picHead = API_Config.getProperty("picHead");
			    serverVersion = API_Config.getProperty("version");
			    ipaPath = API_Config.getProperty("ipaPath");
			    logPath = API_Config.getProperty("logPath");
			    apkPath = API_Config.getProperty("apkPath");
			   // picHead = API_Config.getProperty("picHead");
			   validateMsg.load(inputStream2);
			   inputStream2.close();
		   } catch(IOException e){
			   e.printStackTrace();
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	 }
	public static String makePicURL(String pic){
		if(pic==null||"".equals(pic)) 
			return serverPic;
		if(pic.startsWith("http")){
			return pic;
		}else{
			return picHead+pic;
		} 
	}
	
	public static String makePicURL(String pic,String type){
		//String url="http://116.228.153.46:8686/communityBusiness_client/upload/merchant/100001001/photos/1.jpg";
		//return url;
		if(pic==null||"".equals(pic)) 
			return serverPic;
		
		int l = pic.length();
		if(pic.startsWith("http")){
			pic = pic.substring(l-4) + type + pic.substring(0, l-4);
			return pic;
		}else{
		//	 System.out.println("pic2:"+picHead+pic);
			return picHead+pic.substring(0, l-4) + type + pic.substring(l-4);
		} 
	}
	public static String getAPI_Path(String key){
		if(key == null)
			return null;
		else
			return API_Config.getProperty(key);
	}
	public static String getValidateMsg(String key){
		if(key == null)
			return "未知";
		else
			return validateMsg.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(logPath);
		System.out.println(apkPath);
	}
}
