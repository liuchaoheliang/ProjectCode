package com.froad.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

import com.froad.sso.SSOCons;


public class Command {
	
	//订单类型
	public static final String TRAN_TYPE_COMMOM = "commom";//一般订单
	public static final String TRAN_TYPE_GROUP = "group";//团购订单
	
	//支付类型
	public static final String PAY_TYPE_DIRECTLY= "0";//直接
	public static final String PAY_TYPE_PRE_AUTHORIZATION = "1";//预授权
	public static final String PAY_TYPE_GUARANTEE = "2";//担保
	
	public static Integer TRAN_SUCCESSED = 0; /* 交易成功 */
	public static Integer TRAN_WAITPAY = 1; /* 等待买家付款 */
	public static Integer TRAN_WAITSENDGOODS = 2; /* 等待卖家发货 */
	public static Integer TRAN_CLOSED = 3; /* 交易关闭 */
	public static Integer TRAN_REQPAY_SUCCESSED = 4;// 发送支付请求成功
	public static Integer TRAN_REQPAY_FAILED = 5;// 发送支付请求失败
	public static Integer TRAN_CANCELPAY_SUCCESSED = 6;// 取消支付请求成功
	public static Integer TRAN_CANCELPAY_FAILED = 7;// 取消支付请求失败
	public static Integer TRAN_REFUND_SUCCESSED = 8;// 退款成功
	public static Integer TRAN_REFUND_FAILED = 9;// 退款失败
	public static Integer TRAN_REQSENDGOODS_SUCCESSED = 10;// 请求发货成功
	public static Integer TRAN_REQSENDGOODS_FAILED = 11;// 请求发货失败
	public static Integer TRAN_OPERATERSENDGOODS_FAILED = 12;// 第三方发货失败
	public static Integer TRAN_PAYFAILED = 13;// 支付失败
	public static Integer TRAN_ORDER_SUCCESSED = 14;// 预订成功
	public static Integer TRAN_ORDER_FAILED = 15;// 预订失败
	public static Integer TRAN_REFUND_APPLY=16;//申请退款
	public static Integer TRAN_REQSENDGOODS_TIMEOUT=17;//请求发货超时
	public static Integer TRAN_ISCANCEL_SUCCESSED = 18;//请求取消订单成功
	public static Integer TRAN_ISCANCEL_FAIL = 19;// 请求取消订单失败
	public static Integer TRAN_EXCEPTION = 20;// 异常
	public static Integer TRAN_EXCEPTION_PROCESSING = 21;// 异常处理中
	public static Integer TRAN_RETURN_APPLY = 22;// 申请退货
	public static Integer TRAN_RETURN_AGREED = 23;// 同意退货
	public static Integer TRAN_REQSENDGOODS_END = 24;//卖家已发货
	public static Integer TRAN_REFUND_AGREED = 25;// 同意退款
	public static Integer TRAN_RETURN_SENDING = 26;// 退货发货中
	public static Integer TRAN_RETURN_SUCCESSED = 27;// 退货成功
	public static Integer TRAN_PAY_POINT_SUCCESSED = 31;//消费积分成功
	public static Integer TRAN_PAY_POINT_EXCEPTION = 32;//消费积分异常
	public static Integer TRAN_PAY_POINT_FAILED = 33;//消费积分失败
	public static Integer TRAN_SENDPOINT_FAILD = 99;//赠送积分失败
	
	public  static	 Long  TRAN_CLOSE_TIMEINTERVAL=new Long(12*60*60*1000);      /*交易关闭间隔时间*/

	
	public  static	 int  TRAN_PAGE_COUNT= 10;      /*订单列表 每页显示订单条数*/
	
	
	public static String GOODS_ONLINE = "1001"; /* 直充 */
	public static String GOODS_STOCK = "2001"; /* 囤货 */
	
	public static  int GOODS_STATUS_INSTORE=1;//商品库存有货
	public static  int GOODS_STATUS_OUTOFSTORE=0;//商品库存无货
	
	public  static	 Integer  TRANGOODS_SENDED=0;	  /*已发货 */
	public  static	 Integer  TRANGOODS_UNSENDED=1;   /* 未发货(已发出请求) */
	
    public static  String respCode_SUCCESS="0";//成功
    public static  String respCode_FAIL="1";//失败
    public static String respCode_WAIT_CONFIRM="4";//等待系统确认
    public static  String respCode_CZING="2";//充值中
    public static  String respCode_LOST="-1";//丢失订单信息
    public static String respCode_EXCEPTION="9";//异常
        
    public static  String BILL_CurrencyType="163";//人民币币种
    public static  String BILL_COMMAND_REQPAY="Create"; //请求支付
    public static  String BILL_COMMAND_Refund="Return"; //退款
    public static  String BILL_COMMAND_Query="Query"; //查询
    public static  String BILL_COMMAND_CANCEL="Cancel"; //取消支付
    public static  String BILL_MERCHANTID="000001";//商户ID
    public static  Integer BILL_CONNECTIONTIMEOUT=3000;//账单平台连接时间
    public static  Integer BILL_SOTIMEOUT=3000;//账单平台读取数据时间
    public static  String BILL_FROAD_NO="XXXXXXXXXX";//账单平台缺省ID
    
//   public static  Integer EC_GOODSID_START=1;//电子商品自动发货最小ID
//   public static  Integer EC_GOODSID_END=100;//电子商品自动发货最大ID
   
   public static final String OFCARD_GAMEZHICHONG_CLASSID="22";//欧飞大类:网络游戏充值
   public static  String PUBACCOUNT ="123456789";//公司对公账户,给账单平台做收款人用,先随便写
  
//   public static  String SJYH_CQRC_URL="http://10.223.127.156:8080";
   public static  String channelId_TIEMOKA ="00";
   public static  String channelId_SD ="01";
   
   //积分兑充类型
   public static String POINT_FILLTYPE_PHONE="01";
   public static String POINT_FILLTYPE_USERNAME="02";
   
   	//商户信息状态
   	public static String FBU_CREATE_STATE ="10";// 创建
	public static String FBU_VERIFY_STATE = "20";// 录入
	public static String FBU_USERED_STATE = "30";// 启用
	public static String FBU_STOP_STATE = "40";// 停用
	public static String FBU_DELETE_STATE = "50";// 删除
    
   public static final String PHONE_VALIDATE_KEY = "PHONE_VALIDATE_KEY";
   public static final String REGISTER_VALIDATE_CODE = "validateCode";
   public static final String SEND_VALMSG_FAILED="11111";
   
   
   //图片服务器地址
//   public static final String IMAGE_SERVER_URL="http://116.228.153.46:9091";
// 图片文件上传目录 test
   public static String IMAGE_SERVER_URL;//home/froadmall/tomcat6.0.33-froadmall/webapps
  // public static final String IMAGE_SERVER_URL = "http://127.0.0.1:9091/communityBusiness_client";//本地
   
//   public static final String IMAGE_SERVER_URL = "http://210.14.71.130:10002";
   
   //商品是否上架
   public static final String IS_RACK_YES="1";
   public static final String IS_RACK_NO="0";
   
   /**********************读取交易属性文件里的配置信息************************/
	public static String UPLOAD_IMAGE_DIR;//
	public static String JUEDUI_URL;//
	public static String ALLOWED_UPLOAD_IMAGE_EXTENSION;//
   public static String UPLOAD_LIMIT;//
   //public static String SHORTMESSAGE_URL;//
   
   //水印图片路径
   public static String WATERMARK_IMAGE_PATH;//
   //水印位置（无、左上、右上、居中、左下、右下）
   //no, topLeft, topRight, center, bottomLeft, bottomRight
   public static String WATERMARK_POSITION;
   //水印透明度
   public static String WATERMARK_ALPHA;

   //图片（大）宽度,高度
   public static String BIG_IMAGE_WIDTH;
   public static String BIG_IMAGE_HEIGHT;
   //图片（小）宽度,高度
   public static String SMALL_IMAGE_WIDTH;
   public static String SMALL_IMAGE_HEIGHT;
   //图片（缩略图）宽度,高度
   public static String THUMBNAIL_IMAGE_WIDTH;
   public static String THUMBNAIL_IMAGE_HEIGHT;

   
   //登录次数错误上限
   public static Integer LOING_FAILURE_MAX_TIMES;
   public static String IS_LOGIN_FAILURE_LOCK;
   
   public static Integer TEMP_POINT_ACTIVATE_TIMES;//积分激活一天内允许的失败次数，超过这个次数，账号被锁定
   public static BigDecimal TEMP_POINT_ACTIVATE_LOCKED_TIMES;//积分激活账号被锁定的时间目前设定为24小时
   
   
   
   //电影活动期数
   public static String ACTIVITY_BATCHNUMBER;
   public static String ACTIVITY_BEGINTIME;
   public static String ACTIVITY_ENDTIME;
   
   public static String TEMPLATE_PATH;
   
   
   //2013春节活动相关
   public static String O2OSERVER_URL;
   
    static{
   	 Properties props = new Properties();
   	
	     try{
	    	String filename=com.froad.common.EnvCommand.ENV_PATH+"common.properties";
	    	File file=new File(filename);
	    	InputStream inputStream =new FileInputStream(file);
			props.load(inputStream);
			inputStream.close();
		 }catch(IOException e) {
			e.printStackTrace();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 UPLOAD_IMAGE_DIR = props.getProperty("UPLOAD_IMAGE_DIR");
		 IMAGE_SERVER_URL = props.getProperty("IMAGE_SERVER_URL");
		 JUEDUI_URL = props.getProperty("JUEDUI_URL");
		 ALLOWED_UPLOAD_IMAGE_EXTENSION = props.getProperty("ALLOWED_UPLOAD_IMAGE_EXTENSION");
		 UPLOAD_LIMIT = props.getProperty("UPLOAD_LIMIT");
		 //SHORTMESSAGE_URL = props.getProperty("SHORTMESSAGE_URL");
		 
		 WATERMARK_IMAGE_PATH= props.getProperty("WATERMARK_IMAGE_PATH");
		 WATERMARK_POSITION= props.getProperty("WATERMARK_POSITION");
		 WATERMARK_ALPHA= props.getProperty("WATERMARK_ALPHA");
		 BIG_IMAGE_WIDTH= props.getProperty("BIG_IMAGE_WIDTH");
		 BIG_IMAGE_HEIGHT= props.getProperty("BIG_IMAGE_HEIGHT");
		 SMALL_IMAGE_WIDTH= props.getProperty("SMALL_IMAGE_WIDTH");
		 SMALL_IMAGE_HEIGHT= props.getProperty("SMALL_IMAGE_HEIGHT");
		 THUMBNAIL_IMAGE_WIDTH= props.getProperty("THUMBNAIL_IMAGE_WIDTH");
		 THUMBNAIL_IMAGE_HEIGHT= props.getProperty("THUMBNAIL_IMAGE_HEIGHT");
		 LOING_FAILURE_MAX_TIMES = Integer.parseInt(props.getProperty("LOING_FAILURE_MAX_TIMES"));
		 IS_LOGIN_FAILURE_LOCK=props.getProperty("IS_LOGIN_FAILURE_LOCK");
		 TEMP_POINT_ACTIVATE_TIMES = Integer.parseInt(props.getProperty("TEMP_POINT_ACTIVATE_TIMES"));
		 TEMP_POINT_ACTIVATE_LOCKED_TIMES = new BigDecimal(props.getProperty("TEMP_POINT_ACTIVATE_LOCKED_TIMES"));	 
		 ACTIVITY_BATCHNUMBER = props.getProperty("PERIODS");
		 ACTIVITY_BEGINTIME = props.getProperty("ACTIVITY_BEGINTIEM");
		 ACTIVITY_ENDTIME = props.getProperty("ACTIVITY_ENDTIME");
		 O2OSERVER_URL = props.getProperty("O2OSERVER_URL");
		 TEMPLATE_PATH=props.getProperty("TEMPLATE_PATH");
		 
		 SSOCons.SSO_SERVER = props.getProperty("SSO_SERVER");
		 SSOCons.LOCAL_SSO_CFG = props.getProperty("LOCAL_SSO_CFG");
    }
   
   public static void main(String[] args) {
	   System.out.println(UPLOAD_IMAGE_DIR+"\n"+JUEDUI_URL);
//	   System.out.println(BILL_URL+ "\n"+MERCHANT_SERVER_URL+ "\n"+MERCHANT_MOBILE_URL+ "\n"+MERCHANT_DEMO_URL);
   }
}