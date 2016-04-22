package com.froad.CB.common.constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.froad.CB.common.EnvCommand;


	/**
	 * 类描述：交易相关的常量
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Nov 2, 2012 4:14:40 PM 
	 */
public class TranCommand {
	
	private static final Logger logger=Logger.getLogger(TranCommand.class);
	
	public static final String EXCEPTION="9999";
	public static final String INVALID_RES_XML="9101";//无效的响应报文
	
	/***交易相关的响应码 用于webservice接口的返回***/
	public static final String RESP_CODE_ADD_OK="01";//交易添加成功
	public static final String RESP_CODE_ADD_FAIL="02";//交易添加失败
	public static final String RESP_CODE_PAY_REQ_OK="03";//支付请求发送成功
	public static final String RESP_CODE_PAY_REQ_FAIL="04";//支付请求发送失败

	/**交易状态state**/
	public static final String TRAN_PROCESSING="01"; //交易处理中
	public static final String TRAN_SUCCESS="02"; //交易成功
	public static final String TRAN_FAIL="03";//交易失败
	public static final String TRAN_CLOSE="04";//交易关闭
	
	/**交易类型transType**/
	public static final String POINTS_EXCH_PRODUCT="01";//积分兑换
	public static final String GROUP="02";//团购交易
	public static final String POINTS_REBATE="03";//返利积分   
	public static final String POINTS_EXCH_CASH="04";//积分提现交易
	public static final String COLLECT="05";//纯收款交易
	public static final String PRESENT_POINTS="06";//送积分交易
	public static final String CHECK_IN="07";//签到交易(用户签到送积分)
	public static final String PRESELL="08";//精品预售交易
	public static final List<String> TRANS_TYPES=new ArrayList<String>();
	static{
		TRANS_TYPES.add(POINTS_EXCH_PRODUCT);
		TRANS_TYPES.add(GROUP);
		TRANS_TYPES.add(POINTS_REBATE);
		TRANS_TYPES.add(POINTS_EXCH_CASH);
		TRANS_TYPES.add(COLLECT);
		TRANS_TYPES.add(PRESENT_POINTS);
		TRANS_TYPES.add(CHECK_IN);
		TRANS_TYPES.add(PRESELL);
	}
	
	/**支付方式payMethod**/
	public static final String POINTS_FFT="00";//方付通积分支付
	public static final String POINTS_BANK="01";//银行积分支付
	public static final String CASH="02";//现金支付
	public static final String POINTS_FFT_CASH="03";//方付通积分+现金
	public static final String POINTS_BANK_CASH="04";//银行积分+现金
	public static final String POINTS_FFT_CASH_SCOPE="05";//方付通积分+现金 范围
	public static final String POINTS_BANK_CASH_SCOPE="06";//银行积分+现金  范围
	public static final String POINTS_FFT_ALIPAY_SCOPE = "07";//方付通积分+支付宝 范围
    public static final String POINTS_BANK_ALIPAY_SCOPE = "08";//银行积分+支付宝  范围
    public static final String ALIPAY = "09";//支付宝
	public static final List<String> PAY_METHODS=new ArrayList<String>();
	static{
		PAY_METHODS.add(POINTS_FFT);
		PAY_METHODS.add(POINTS_BANK);
		PAY_METHODS.add(CASH);
		PAY_METHODS.add(POINTS_FFT_CASH);
		PAY_METHODS.add(POINTS_BANK_CASH);
		PAY_METHODS.add(POINTS_FFT_CASH_SCOPE);
		PAY_METHODS.add(POINTS_BANK_CASH_SCOPE);
		PAY_METHODS.add(POINTS_FFT_ALIPAY_SCOPE);
		PAY_METHODS.add(POINTS_BANK_ALIPAY_SCOPE);
		PAY_METHODS.add(ALIPAY);
	}
	
	/**支付渠道payChannel**/
	public static final String PAY_CHANNEL_PHONE="1";//手机贴膜卡
	public static final String PAY_CHANNEL_CARD="2";//银行卡
	public static final String PAY_CHANNEL_ALIPAY="3";//支付宝支付
	public static final List<String> PAY_CHANNELS=new ArrayList<String>();
	static{
		PAY_CHANNELS.add(PAY_CHANNEL_PHONE);
		PAY_CHANNELS.add(PAY_CHANNEL_CARD);
		PAY_CHANNELS.add(PAY_CHANNEL_ALIPAY);
	}
	
	/*******账户校验时用到checkType********/
	public static final String CHECK_PHONE="1";//验证手机号
	
	public static final String CHECK_CARD="2";//验证银行卡
	
	/**openapi版本号**/
	public static final String VERSION="1.1.0";
	
	/**账户校验或查询接口成功**/
	public static final String SUCCESS="00";
	public static final String FAIL="01";
	
	/*******异常信息的前缀*******/
	public static final String EXCEPTION_PREFIX="银行异常:";
	
	/**交易品属性ID:goodsRackAttribute表的id**/
	 public static final String ID_PHONE_NO="100001001";//充值号码
	 public static final String ID_ADDRESS="100001002";//充值号码归属地
	 public static final String ID_OPERATOR="100001003";//运营商
	 public static final String ID_PRICE="100001004";//面值
	 
	 /**彩票的交易属性**/
	 public static final String ID_AWD_AMOUNT="100001008";//中奖金额
	 public static final String ID_PRIZE_GRADE="100001014";//中奖等级
	 public static final String ID_PRIZE_COUNT="100001015";//中奖注数
	 
	 public static final String ID_NUM_COUNT="100001006";//投注注数
	 public static final String ID_PERIOAD="100001005";//投注期号
	 public static final String ID_NUMBER="100001007";//投注号码
	 public static final String ID_LOTTERY_NO="100001009";//彩票编码
	 public static final String ID_PLAY_TYPE="100001010";//玩法
	 public static final String ID_BUY_AMOUNT="100001011";//投注倍数
	 public static final String ID_AMOUNT="100001012";//投注金额
	 public static final String ID_LOTTERY_PHONE="100001013";//联系电话
	 public static final String ID_NUM_TYPE="100001016";//单复和合胆
	 
	 /**商品分类表ID:goodsCategory表的id**/
     public static final String CATEGORY_REBATE="100001001";//返利商品
     public static final String CATEGORY_VIRTUAL="100001002";//虚拟产品
     public static final String CATEGORY_LOTTORY="100001005";//彩票
     public static final String CATEGORY_HFCZ="100001006";//话费充值
     public static final String CATEGORY_PRODUCT="100001003";//农特产品
     public static final String CATEGORY_WITHDRAW="100001007";//积分提现
     public static final String CATEGORY_GROUP="100001008";//团购商品
     
     /***绑定买家时用到的响应码****/
     public static final String RESP_CODE_IS_USER="00";//成功绑定用户
     public static final String RESP_CODE_IS_BUYER="01";//成功绑定买家
     public static final String RESP_CODE_FAIL="02";//绑定失败
     
     /**合作伙伴ID**/
 	public static String PARTNER_ID;
 	
 	/**转账机构号**/
	public static String TRANSFER_ORG;
	
	public static String RSA_PRIVATE_KEY;
	
	 /**********************读取交易属性文件里的配置信息************************/
 	public static String FFT_ORG_NO;//分分通积分机构号
 	
 	public static String BANK_ORG_NO;//银行积分机构号
 	
 	public static String ALIPAY_ORG_NO;//支付宝机构号
 	
	public static String CHANNEL_ID;//资金渠道(FundsChannel)表里的编号
 	
 	static{
    	 Properties props = new Properties();
 	     try{
 	    	String filename=EnvCommand.ENV_PATH+"trans.properties";
 	    	File file=new File(filename);
 	 		InputStream inputStream =new FileInputStream(file);
 			props.load(inputStream);
 			inputStream.close();
 		 }catch(IOException e) {
 			logger.error("交易配置文件读取出现IO异常",e);
 		 }catch(Exception e){
 			logger.error("交易配置文件读取Exception",e);
 		 }
 		CHANNEL_ID = props.getProperty("CHANNEL_ID");
 		FFT_ORG_NO = props.getProperty("FFT_ORG_NO");
 		BANK_ORG_NO = props.getProperty("BANK_ORG_NO");
 		ALIPAY_ORG_NO = props.getProperty("ALIPAY_ORG_NO");
 		PARTNER_ID = props.getProperty("PARTNER_ID");
 		TRANSFER_ORG = props.getProperty("TRANSFER_ORG");
 		RSA_PRIVATE_KEY = props.getProperty("RSA_PRIVATE_KEY");
     }
 	
 	public static void main(String[] args) {
		System.out.println(CHANNEL_ID);
	}
	 
}
