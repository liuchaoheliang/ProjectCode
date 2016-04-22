package com.froad.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

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
	 * 获取某天的开始时间
	 * @Title: getStartDay 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年5月26日
	 * @modify: froad-huangyihao 2015年5月26日
	 * @param amount 0-今天	1-明天	-1-昨天	以此类推
	 * @return
	 * @throws
	 */
	public static Date getStartDay(int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_YEAR, amount);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取某天的结束时间
	 * @Title: getEndDay 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年5月26日
	 * @modify: froad-huangyihao 2015年5月26日
	 * @param amount 0-今天	1-明天	-1-昨天	以此类推
	 * @return
	 * @throws
	 */
	public static Date getEndDay(int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_YEAR, amount);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
	
	/**
	 * 获取某天的开始时间
	 * @Title: getStartDay 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年5月26日
	 * @modify: froad-huangyihao 2015年5月26日
	 * @param date
	 * @return
	 * @throws
	 */
	public static Date getStartDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取某天的结束时间
	 * @Title: getEndDay 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年5月26日
	 * @modify: froad-huangyihao 2015年5月26日
	 * @param date
	 * @return
	 * @throws
	 */
	public static Date getEndDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
	
	/**
	 * get current date in yyyyMMdd format
	 * 
	 * @return
	 */
	public static Integer getCurrentDate(int date){
		Integer currentDate = null;
		String str = null;
		
		if(date == 0){
			str = DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, new Date());
			
			currentDate = Integer.parseInt(str);
		}else{
			Date batchDate = DateUtil.parse(DateUtil.DATE_FORMAT2, String.valueOf(date));
			Date nextDate = DateUtils.addDays(batchDate, 1);
			
			str = DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, nextDate);
			currentDate = Integer.parseInt(str);
		}
		
		return currentDate;
	}
	
	/**
	 * convert yyyyMMdd format to day end
	 * 
	 * @param date
	 * @return
	 */
	public static Date convertToDayEnd(int date){
		Calendar calendar = null;
		
		calendar = Calendar.getInstance();
		Date d = DateUtil.parse(DateUtil.DATE_FORMAT2, String.valueOf(date));
		calendar.setTime(d);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		return calendar.getTime();
	}
	
	/**
	 * convert yyyyMMdd format to day begin
	 * 
	 * @param date
	 * @return
	 */
	public static Date convertToDayBegin(int date){
		Calendar calendar = null;
		
		calendar = Calendar.getInstance();
		Date d = DateUtil.parse(DateUtil.DATE_FORMAT2, String.valueOf(date));
		calendar.setTime(d);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	
	public static Date convertToDay(int date){
		Date d = DateUtil.parse(DateUtil.DATE_FORMAT2, String.valueOf(date));
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		Calendar now = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
		c.set(Calendar.SECOND, now.get(Calendar.SECOND));
		
		return c.getTime();
	}
}
