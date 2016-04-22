package com.froad.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;

/**
 * 各种时间配置
 * 
 * @author lf 2015.06.11
 * 
 * */
public class AllkindsTimeConfig {

	private static Properties config = null;
	
	// 定时关闭订单 - 某个小时之前的(单位=小时)
	public static final String CLOSE_TRANS_ANHOUR_BEFORE = "CloseTrans_anHour_Before";
	private static final int CLOSE_TRANS_ANHOUR_BEFORE_DEFAULT = 2;

	// 定时确认收货 - 某天之前的(单位=天)
	public static final String CONFIRM_RECEIPT_ANDAY_BEFORE = "ConfirmReceipt_anDay_before";
	private static final int CONFIRM_RECEIPT_ANDAY_BEFORE_DEFAULT = 7;

	// 定时处理过期商户和门店 - 续约年数(单位=年)
	public static final String PROCESS_EXPIRED_RENEWAL_YEARS = "ProcessExpired_renewalYears";
	private static final int PROCESS_EXPIRED_RENEWAL_YEARS_DEFAULT = 1;
	
	// 定时移除验证码图片 - 某个小时之前的(单位=小时)
	public static final String REMOVE_IMAGE_ANHOUR_BEFORE = "RemoveImage_anHour_Before";
	private static final int REMOVE_IMAGE_ANHOUR_BEFORE_DEFAULT = 2;
	
	// 定时返回库存 - 多少分钟之前的(单位=分钟)
	public static final String RETURN_STORE_ANMINUTE_BEFORE = "ReturnStore_anMinute_Before";
	private static final int RETURN_STORE_ANMINUTE_BEFORE_DEFAULT = 30;
	//定时导出预售配送商品 -多少天之前(单位=天)
	public static final String EXPORTPRESELL_ANDAY_BEFORE = "ExportPresell_anDay_before";
	private static final int EXPORTPRESELL_ANDAY_BEFORE_DEFAULT = 1;
	
	//定时确认收货（精品商城）-某天之前的(单位=天)
	public static final String COMFIRMWAYBILL_ANDAY_BEFORE = "ConfirmWayBill_anDay_before";
	private static final int COMFIRMWAYBILL_ANDAY_BEFORE_DEFAULT = 7;
	private static final int DEFAULT = 0;
	//定时迁移已使用卷码-某月之前的(单位=月)
	public static final String VOUCHERUSE_ANMONTH_BEFORE = "VoucherUsed_anMonth_before";
	private static final int VOUCHERUSE_ANMONTH_BEFORE_DEFAULT = 6;
	//定时迁移已过期卷码-某月之前的(单位=月)
	public static final String VOUCHEROUTOFDATE_ANMONTH_BEFORE = "VoucherOutOfDate_anMonth_before";
	private static final int VOUCHEROUTOFDATE_ANMONTH_BEFORE_DEFAULT = 6;
	//精品商城定时快递100状态
	public static final String COMFIRMWAYBILL_STATUS = "ConfirmWayBill_Status";
	private static final String[] COMFIRMWAYBILL_STATUS_DEFAULT = {"0","1","2","3","4","5","6"};
	
	
	/**
	 * 
	 * 获取 - 定时关闭订单配置的某个小时之前的值
	 * 
	 * */
	public static int getCloseTransAnHourBefore(){
		if( config == null ){
			return CLOSE_TRANS_ANHOUR_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(CLOSE_TRANS_ANHOUR_BEFORE);
			if( sv == null ){
				return CLOSE_TRANS_ANHOUR_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时关闭订单配置的某个小时之前的值 异常"+e.getMessage(), e);
			return CLOSE_TRANS_ANHOUR_BEFORE_DEFAULT;
		}
	}
	
	/**
	 * 
	 * 获取 - 定时确认收货配置某天之前的值
	 * 
	 * */
	public static int getConfirmReceiptAnDayBefore(){
		if( config == null ){
			return CONFIRM_RECEIPT_ANDAY_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(CONFIRM_RECEIPT_ANDAY_BEFORE);
			if( sv == null ){
				return CONFIRM_RECEIPT_ANDAY_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时确认收货配置某天之前的值 异常"+e.getMessage(), e);
			return CONFIRM_RECEIPT_ANDAY_BEFORE_DEFAULT;
		}
	}
	
	/**
	 * 
	 * 获取 - 定时处理过期商户和门店配置续约年数的值
	 * 
	 * */
	public static int getProcessExpiredRenewalYears(){
		if( config == null ){
			return PROCESS_EXPIRED_RENEWAL_YEARS_DEFAULT;
		}
		try{
			String sv = config.getProperty(PROCESS_EXPIRED_RENEWAL_YEARS);
			if( sv == null ){
				return PROCESS_EXPIRED_RENEWAL_YEARS_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时处理过期商户和门店配置续约年数的值 异常"+e.getMessage(), e);
			return PROCESS_EXPIRED_RENEWAL_YEARS_DEFAULT;
		}
	}
	
	/**
	 * 
	 * 获取 - 定时移除验证码图片配置的某个小时之前的值
	 * 
	 * */
	public static int getRemoveImageAnHourBefore(){
		if( config == null ){
			return REMOVE_IMAGE_ANHOUR_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(REMOVE_IMAGE_ANHOUR_BEFORE);
			if( sv == null ){
				return REMOVE_IMAGE_ANHOUR_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时移除验证码图片配置的某个小时之前的值 异常"+e.getMessage(), e);
			return REMOVE_IMAGE_ANHOUR_BEFORE_DEFAULT;
		}
	}
	
	/**
	 * 
	 * 获取 - 定时返回库存配置的多少分钟之前的值
	 * 
	 * */
	public static int getReturnStoreAnMinuteBefore(){
		if( config == null ){
			return RETURN_STORE_ANMINUTE_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(RETURN_STORE_ANMINUTE_BEFORE);
			if( sv == null ){
				return RETURN_STORE_ANMINUTE_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时返回库存配置的多少分钟之前的值 异常"+e.getMessage(), e);
			return RETURN_STORE_ANMINUTE_BEFORE_DEFAULT;
		}
	}
	
	public static int getExportAnDayBefore(){
		if( config == null ){
			return EXPORTPRESELL_ANDAY_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(EXPORTPRESELL_ANDAY_BEFORE);
			if( sv == null ){
				return EXPORTPRESELL_ANDAY_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时导出预售配送商品的多少天之前的值 异常"+e.getMessage(), e);
			return EXPORTPRESELL_ANDAY_BEFORE_DEFAULT;
		}
	}
	
	
	/**
	 * 
	 * 获取 - 定时确认收货(精品商城)配置某天之前的值
	 * 
	 * */
	public static int getConfirmWayBillAnDayBefore(){
		if( config == null ){
			return COMFIRMWAYBILL_ANDAY_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(COMFIRMWAYBILL_ANDAY_BEFORE);
			if( sv == null ){
				return COMFIRMWAYBILL_ANDAY_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 精品商城定时确认收货配置某天之前的值 异常"+e.getMessage(), e);
			return COMFIRMWAYBILL_ANDAY_BEFORE_DEFAULT;
		}
	}
	
	/**
	 * 
	 * 获取 - 定时迁移已使用卷码某几个月之前的值
	 * 
	 * */
	public static int getVouchersUsedAnMonthBefore(){
		if( config == null ){
			return VOUCHERUSE_ANMONTH_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(VOUCHERUSE_ANMONTH_BEFORE);
			if( sv == null ){
				return VOUCHERUSE_ANMONTH_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时迁移已使用卷码某几个月之前的值 异常"+e.getMessage(), e);
			return VOUCHERUSE_ANMONTH_BEFORE_DEFAULT;
		}
	}
	
	/**
	 * 
	 * 获取 - 定时迁移已过期卷码某几个月之前的值
	 * 
	 * */
	public static int getVouchersOutOfDateAnMonthBefore(){
		if( config == null ){
			return VOUCHEROUTOFDATE_ANMONTH_BEFORE_DEFAULT;
		}
		try{
			String sv = config.getProperty(VOUCHEROUTOFDATE_ANMONTH_BEFORE);
			if( sv == null ){
				return VOUCHEROUTOFDATE_ANMONTH_BEFORE_DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("获取 - 定时迁移已过期卷码某几个月之前的值异常"+e.getMessage(), e);
			return VOUCHEROUTOFDATE_ANMONTH_BEFORE_DEFAULT;
		}
	}
	
	/**
	 * 
	 * 获取 - 精品商城快递100状态
	 * 
	 * */
	public static String[] getConfirmWayBillStatus(){
		if( config == null ){
			return COMFIRMWAYBILL_STATUS_DEFAULT;
		}
		try{
			String sv = config.getProperty(COMFIRMWAYBILL_STATUS);
			if( sv == null ){
				return COMFIRMWAYBILL_STATUS_DEFAULT;
			}
			String[] sv1=null;
			if(sv.contains(",")){
			 sv1=sv.split(",");
			 return sv1;
			}else{
			 sv1=new String[]{sv};
			 return sv1;
			}
		}catch(Exception e){
			LogCvt.error("获取 - 精品商城快递100的值异常"+e.getMessage(), e);
			return COMFIRMWAYBILL_STATUS_DEFAULT;
		}
	}
	/**
	 * 
	 * 加载配置
	 * 
	 * */
	public static void load(){
		String fileName = "allkindsTimeConfig.properties";
		try{
			config = new Properties();
			String path = System.getProperty(Constants.CONFIG_PATH) + File.separatorChar + fileName;
			config.load(new FileInputStream(path));
		}catch(Exception e){
			LogCvt.error("读取过期时间对比配置文件 "+fileName+" 异常:"+e.getMessage(), e);
			config = null;
		}
	} 

	/**
	 * 
	 * 根据 key 获取配置的值
	 * 
	 * */
	public static int getValueByKey(String key){
		if( key == null || config == null ){
			return DEFAULT;
		}
		try{
			String sv = config.getProperty(key);
			if( sv == null ){
				return DEFAULT;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("根据 "+key+" 获取配置的值 异常"+e.getMessage(), e);
			return DEFAULT;
		}
	}
	
	/**
	 * 
	 * 根据 key 获取配置的值
	 * 
	 * <br>如果没有则返回默认值
	 * 
	 * */
	public static int getValueByKey(String key, int defaultV){
		if( key == null || config == null ){
			return defaultV;
		}
		try{
			String sv = config.getProperty(key);
			if( sv == null ){
				return defaultV;
			}
			return Integer.parseInt(sv);
		}catch(Exception e){
			LogCvt.error("根据 "+key+" 获取配置的值 异常"+e.getMessage(), e);
			return defaultV;
		}
	}
}
