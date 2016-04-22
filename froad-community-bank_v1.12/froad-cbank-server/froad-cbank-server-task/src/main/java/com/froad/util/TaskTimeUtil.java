package com.froad.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.froad.logback.LogCvt;

public class TaskTimeUtil {
	
	/**
	 * 
	 * @Title: getConfigDate 
	 * @Description: 获取配置时间日期
	 * @author: froad-huangyihao 2015年4月29日
	 * @modify: froad-huangyihao 2015年4月29日
	 * @param time
	 * @return
	 * @throws
	 */
	public static Date getConfigDate(String time){
		if(Checker.isEmpty(time)){
			return new Date();
		}
		String[] configTimes = time.split(":");
		int hour = Integer.valueOf(configTimes[0]);
		int minute = Integer.valueOf(configTimes[1]);
		
		Date now = new Date();
		
		Date d1 = DateUtils.setHours(new Date(), hour);
		Date d2 = DateUtils.setMinutes(d1, minute);
		Date setting = d2;
		// 如果比当前时间(小时和分钟)小-那日期加一天
		if(now.getTime() > d2.getTime()){
			setting = DateUtils.addDays(d2, 1);
		}
		// 秒设置为0
		setting = DateUtils.addSeconds(setting, 0);
		return setting;
	}
	public static Date getConfigDate2(String time){
		return new Date(System.currentTimeMillis()+getAccumulateDelayTime(time));
	}
	/**
	 * 
	 * @Title: getDistanceTime 
	 * @Description: 获取配置时间与当前世界相隔的毫秒数
	 * @author: froad-huangyihao 2015年4月29日
	 * @modify: froad-huangyihao 2015年4月29日
	 * @param time
	 * @return
	 * @throws
	 */
	public static long getDistanceTime(String time){
		if(Checker.isEmpty(time)){
			return 0;
		}
		long decTime = getConfigDate(time).getTime();
		long now = new Date().getTime();
		long milliSecond = 0;
		if(decTime > now){
			milliSecond = decTime - now;
		}else{
			milliSecond = now - decTime;
		}
		return milliSecond;
	}
	
	/**
	 * 
	 * @Title: getAccumulateDelayTime 
	 * @Description: 获取累加的延迟时间
	 * @author: lf 2015年6月10日
	 * @param time
	 * @return long
	 * @throws
	 */
	public static long getAccumulateDelayTime(String time){
		if(Checker.isEmpty(time)){
			return 3600000; // 一个小时以后
		}
		try{
			int t = Integer.parseInt(time);
			return (t*60*1000); // 分钟 * 60秒 * 1000
		}catch(Exception e){
			LogCvt.error("获取累加的延迟时间"+e.getMessage(), e);
			return 3600000; // 一个小时以后
		}
	}
	
	/**
	 * 
	 * @Title: getStartTime 
	 * @Description: 获取当天开始时间
	 * @author: longyunbo 2015年7月30日
	 * @param 
	 * @return long
	 * @throws
	 */
	public static Long getStartTime(){  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime().getTime();  
    } 
	
	
	/**
	 * 
	 * @Title: getEndTime 
	 * @Description: 获取当天结束时间
	 * @author: longyunbo 2015年7月30日
	 * @param 
	 * @return long
	 * @throws
	 */
	public static Long getEndTime(){  
	        Calendar todayEnd = Calendar.getInstance();  
	        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
	        todayEnd.set(Calendar.MINUTE, 59);  
	        todayEnd.set(Calendar.SECOND, 59);  
	        todayEnd.set(Calendar.MILLISECOND, 999);  
	        return todayEnd.getTime().getTime();  
	    } 
}
