package com.froad.test;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.froad.util.DateUtil;

public class TestDate {
	
	public static void main(String[] args) {
		
		int manyDay = getNowYearDay();
		System.out.println(manyDay);
		
		Date date = DateUtils.addDays(new Date(), manyDay);
		System.out.println(DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, date));
		
		
		long million = manyDay * DateUtils.MILLIS_PER_DAY;
		System.out.println(million);
	}
	
	private static Date getConfigDate(String time) {
		String[] configTimes = time.split(":");
		int hour = Integer.valueOf(configTimes[0]);
		int minute = Integer.valueOf(configTimes[1]);
		
		Date now = new Date();
		
		Date d1 = DateUtils.setHours(new Date(), hour);
		Date d2 = DateUtils.setMinutes(d1, minute);
//		Date d3 = DateUtils.setSeconds(d2, 0);
		
		Date setting = d2;
		if(now.getTime() > d2.getTime()){
			setting = DateUtils.addDays(d2, 1);
		}
		setting = DateUtils.addSeconds(setting, 0);
		return setting;
	}
	
	
	public static Date changeFirstTime(String time){
		Calendar calendar = Calendar.getInstance();
		// 现在的时间
		Date now = new Date();
		
		// 配置的时间
		Date age = new Date();
		calendar.setTime(age);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(3, 5)));
		age = calendar.getTime();
		
		// 如果比当前时间(小时和分钟)小-那日期加一天
		if( now.getTime() > age.getTime() ){
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
		}
		
		// 秒设置为0
		calendar.set(Calendar.SECOND, 0);
		age = calendar.getTime();
		return age;
	}
	
	
	
	public static int getNowYearDay(){
		Calendar a = Calendar.getInstance();
		int day = a.getActualMaximum(Calendar.DAY_OF_YEAR);
		return day;
	}
	
	/** 
	 * 取得当月天数 
	 * */  
	public static int getCurrentMonthLastDay()  
	{  
	    Calendar a = Calendar.getInstance();  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}  
	
	/** 
	 * 得到指定月的天数 
	 * */  
	public static int getMonthLastDay(int year, int month)  
	{  
	    Calendar a = Calendar.getInstance();  
	    a.set(Calendar.YEAR, year);  
	    a.set(Calendar.MONTH, month - 1);  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}  
	
	public static int getNowYear(){
		Calendar a = Calendar.getInstance();
		int year = a.get(Calendar.YEAR);
		return year;
	}
	
	public static int getNowMonth(){
		Calendar a = Calendar.getInstance();
		int month = a.get(Calendar.MONTH);
		return month + 1;
	}
	
	public static int getNowDayOfMonth(){
		Calendar a = Calendar.getInstance();
		int day = a.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	public static String getYesterDay(){
		Date y = DateUtils.addDays(new Date(), -1);
		String yd = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, y);
		return yd;
	}
	
	
//	public static int getNextDayOfMonth(){
//		
//		Calendar a = Calendar.getInstance();
//		
//	}
}
