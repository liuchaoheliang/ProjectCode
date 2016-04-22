package com.froad.cbank.coremodule.module.normal.user.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月14日 下午8:22:04
 * @version 1.0
 * @desc 日期工具
 */
public class DateUtils {
	
	
	
	private static final String END_DATE_HHMMSS = " 23:59:59";
	
	private static final String BEGIN_DATE_HHMMSS = " 00:00:00";
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
	/*
	 * 根据当前日期计算范围日期
	 */
	public static Date addDays(Date date, int days) {
		if (date == null) {
			return date;
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);//bugfix at 2015/06/05
		ca.add(Calendar.DATE, days);
		return ca.getTime();
	}
	
    /** 
     * 以baseDate为标准，返回n天前或n天后的Date 
     *  days为负数表示返回N天前日期
     * 王炜华
     */  
    public static Date getDateBeforeOrAfterNDays(int days, Date baseDate) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(baseDate);  
        calendar.add(Calendar.DAY_OF_MONTH, days);  
        return calendar.getTime();  
    } 
    
    /** 
     * 以baseDate为标准，返回n月前或n月后的Date 
     *  month为负数表示返回N月前日期
     * 王炜华
     */  
    public static Date getDateBeforeOrAfterNMonths(int month, Date baseDate) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(baseDate);  
        calendar.add(Calendar.MONTH, month);  
        return calendar.getTime();  
    } 
    
    /**
     * 获取开始时间字符串
     * 返回yyyy-MM-dd格式日期
     * 王炜华
     * @param startDate
     * @return
     */
    public static String getBeginDateString(Date beginDate){ 
    	
    	return dateFormat.format(beginDate)+BEGIN_DATE_HHMMSS;
    	
    }
    
    /**
     * 获取截止时间字符串
     * 返回yyyy-MM-dd格式日期
     * 王炜华
     * @param startDate
     * @return
     */
    public static String getEndDateString(Date endDate){ 
    	
    	return dateFormat.format(endDate)+END_DATE_HHMMSS;
    	
    }
    
    
    /**
     * 获取开始时间字符串
     * 返回yyyy-MM-dd格式日期
     * 王炜华
     * @param startDate
     * @return
     */
    public static Date getBeginDate(Date beginDate){ 
    	
    	Date date =null;
    	try {
    		date= dateTimeFormat.parse(getBeginDateString(beginDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
    
    /**
     * 获取截止时间字符串
     * 返回yyyy-MM-dd格式日期
     * 王炜华
     * @param startDate
     * @return
     */
    public static Date getEndDate(Date endDate){ 
    	
    	Date date =null;
    	try {
    		date= dateTimeFormat.parse(getEndDateString(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    	
    }
       
	
    public static Date parseDateTime(String dateTimeStr){
    	Date date =null;
    	try {
    		date= dateTimeFormat.parse(dateTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
}
