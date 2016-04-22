package com.froad.cbank.coremodule.framework.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
  * @ClassName: CalendarUtil
  * @Package com.froad.platform.utils
  * @Description: 日期时间操作公共类
  * @author froad-zxp
  * @date May 3, 2012 1:18:13 PM
  * @modifyDate May 3, 2012 1:18:13 PM
  *
  * Copyright: Copyright (c) 2012 
  * Company:f-road.com.cn
 */
public class CalendarUtil {
	/**
	 * @Fields format1 : yyyy-MM-dd（格式） 
	 */
	public final static String format1 = "yyyy-MM-dd";
	/**
	 * @Fields format2 : yyyy/MM/dd（格式） 
	 */
	public final static String format2 = "yyyy/MM/dd";
	/**
	 * @Fields format3 : yyyy年M月d日（格式） 
	 */
	public final static String format3 = "yyyy年M月d日";
	/**
	 * @Fields format4 : yyyyMMdd（格式） 
	 */
	public final static String format4 = "yyyyMMdd";
	public final static String yyyyMMdd = "yyyyMMdd";
	/**
	 * @Fields format5 : yyyy-MM-dd HH:mm:ss（格式） 
	 */
	public final static String format5 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * @Fields format6 : HH:mm:ss SSS（格式） 
	 */
	public final static String format6 = "HH:mm:ss SSS";
	/**
	 * @Fields format7 : MMddHHmmss（格式） 
	 */
	public final static String format7 = "MMddHHmmss";
	/**
	 * @Fields format8 : ddHHmmssSSS（格式） 
	 */
	public final static String format8 = "ddHHmmssSSS";
	/**
	 * @Fields format9 : yyyyMMddHHmmssSSS（格式） 
	 */
	public final static String format9 = "yyyyMMddHHmmssSSS";
	/**
	 * @Fields format10 : yyyy-MM-dd HH:mm:ss E（格式） 
	 */
	public final static String format10 = "yyyy-MM-dd HH:mm:ss E";
	/**
	 * @Fields format11 : yyyy-MM-dd HH:mm:ss SSS（格式） 
	 */
	public final static String format11 = "yyyy-MM-dd HH:mm:ss SSS";
	/**
	 * @Fields HH : HH（格式） 
	 */
	public final static String HH = "HH";
	/**
	 * @Fields HHmmss : HH:mm:ss（格式） 
	 */
	public final static String HHmmss = "HH:mm:ss";
	/**
	 * @Fields HHmmss1 : HHmmss（格式） 
	 */
	public final static String HHmmss1 = "HHmmss";// dysh需求
	/**
	 * @Fields HHmm : HHmm（格式） 
	 */
	public final static String HHmm = "HHmm";// dysh需求
	/**
	 * @Fields MMddHHmmss : MMddHHmmss（格式） 
	 */
	public final static String MMddHHmmss = "MMddHHmmss";
	/**
	 * @Fields ddHHmmssSSS : ddHHmmssSSS（格式） 
	 */
	public final static String ddHHmmssSSS = "ddHHmmssSSS";
	/**
	 * @Fields yyyyMMddHHmmssSSS : yyyyMMddHHmmssSSS（格式） 
	 */
	public final static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	/**
	 * @Fields yyyyMMddHHmmss : yyyyMMddHHmmss（格式） 
	 */
	public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	/**
	 * @Fields YYYYMM : YYYYMM（格式） 
	 */
	public final static String yyyyMM = "yyyyMM";//dysh需求
	/**
	 * @Fields format12 : M月d日HH:mm（格式） 
	 */
	public final static String format12 = "M月d日HH:mm";//dysh需求
	
	
	/**
	  * 创建一个新的实例 CalendarUtil. 
	  * <p>Title: 构造方法</p>
	  * <p>Description: 构造这个日历处理类</p>
	 */
	public CalendarUtil() {
	}
	/**
	  * formatDateByFormat(通过日期对象，按照指定格式格式化日期为String形式)
	  *
	  * @Title: formatDateByFormat
	  * @Description: 通过日期对象，按照指定格式格式化日期为String形式
	  * @Date May 3, 2012 1:23:41 PM
	  * @modifyDate May 3, 2012 1:23:41 PM
	  * @param date 日期对象 java.util.Date
	  * @param format 转换的格式 java.lang.String
	  * @return String 转换后的String日期
	 */
	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}
	/**
	  * addMonth(将String日期中按照格式添加月)
	  * TODO(注意：内部捕捉了异常，用e.printStackTrace()输出了堆栈信息)
	  * TODO(注意：这里的添加是在原来的月份上加上需要添加的月数)
	  *
	  * @Title: addMonth
	  * @Description: 将String日期中按照格式添加月
	  * @Date May 3, 2012 1:26:41 PM
	  * @modifyDate May 3, 2012 1:26:41 PM
	  * @param dateStr 需要添加月份的日期 java.lang.String
	  * @param month 添加月数 int
	  * @param format 格式 java.String
	  * @return String 最后得到的结果 java.lang.String
	 */
	public static String addMonth(String dateStr, int month, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		Date nowDate = calendar.getTime();
		return dateFormat.format(nowDate);
	}
	/**
	  * pkDate(获取批扣日期)
	  *
	  * @Title: pkDate
	  * @Description: 获取批扣日期
	  * @Date May 3, 2012 1:34:02 PM
	  * @modifyDate May 3, 2012 1:34:02 PM
	  * @return String 批扣日期 java.lang.String
	 */
	public static String pkDate() {
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DAY_OF_MONTH, -1);
		Date dc = cal1.getTime();
		return CalendarUtil.getDate_d(dc.getTime(), CalendarUtil.format4);
	}

	/**
	  * formatDate(格式化日期 格式为 yyyy-MM-dd)
	  *
	  * @Title: formatDate
	  * @Description: 格式化日期 格式为 yyyy-MM-dd
	  * @Date May 3, 2012 1:36:11 PM
	  * @modifyDate May 3, 2012 1:36:11 PM
	  * @param date 需要格式化的时间对象
	  * @return String 格式化后的String java.lang.String
	 */
	public static String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}
	/**
	  * getDate(通过字符类型的日期跟格式得到该时间的毫秒数)
	  * TODO(注意：如果格式化错误的话，返回的为null，并e.printStackTrace()打印异常堆栈信息)
	  *
	  * @Title: getDate
	  * @Description: 通过字符类型的日期跟格式得到该时间的毫秒数
	  * @Date May 3, 2012 1:37:13 PM
	  * @modifyDate May 3, 2012 1:37:13 PM
	  * @param date String的日期 java.lang.String
	  * @param format 格式 java.lang.String
	  * @return long 时间的毫秒数 long
	 */
	public static Long getDate(String date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date dd = sdf.parse(date);
			return new Long(dd.getTime());
		} catch (ParseException e) {
			return null;
		}

	}
	/**
	  * getLongD(将date类型的日期转换为yyyyMMdd格式的long类型的时间)
	  * TODO(注意：如果格式化错误的话，返回的为null，并e.printStackTrace()打印异常堆栈信息)
	  *
	  * @Title: getLongD
	  * @Description: 将date类型的日期转换为yyyyMMdd格式的long类型的时间
	  * @Date May 3, 2012 1:39:23 PM
	  * @modifyDate May 3, 2012 1:39:23 PM
	  * @param date 需要转换的日期 java.util.Date
	  * @return Long 得到的long日期 java.lang.Long
	 */
	public static Long getLongD(java.util.Date date) {
		SimpleDateFormat sdf_d = new SimpleDateFormat("yyyyMMdd");
		String yymmdd = sdf_d.format(date);
		try {
			long l = sdf_d.parse(yymmdd).getTime();
			return new Long(l);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	  * getLongD_s(根据日期得到毫秒数)
	  *
	  * @Title: getLongD_s
	  * @Description: 根据日期得到毫秒数
	  * @Date May 3, 2012 1:41:49 PM
	  * @modifyDate May 3, 2012 1:41:49 PM
	  * @param date 日期对象 java.util.Date
	  * @return Long 得到的Long日期 java.lang.Long
	 */
	public static Long getLongD_s(java.util.Date date) {
		return new Long(date.getTime());
	}
	/**
	  * getLongD_t(将date类型的日期转换为HH:mm:ss SSS格式的long类型的时间)
	  * TODO(注意：如果格式化错误的话，返回的为null，并e.printStackTrace()打印异常堆栈信息)
	  *
	  * @Title: getLongD_t
	  * @Description: 将date类型的日期转换为HH:mm:ss SSS格式的long类型的时间
	  * @Date May 3, 2012 1:39:23 PM
	  * @modifyDate May 3, 2012 1:39:23 PM
	  * @param date 需要转换的日期 java.util.Date
	  * @return Long 得到的long日期 java.lang.Long
	 */
	public static Long getLongD_t(java.util.Date date) {
		SimpleDateFormat sdf_t = new SimpleDateFormat("HH:mm:ss SSS");
		String time = sdf_t.format(date);
		try {
			long l = sdf_t.parse(time).getTime();
			return new Long(l);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	  * getDate_d(按照格式将Long类型的日期转换为String为日期)
	  *
	  * @Title: getDate_d
	  * @Description: 按照格式将Long类型的日期转换为String为日期
	  * @Date May 3, 2012 1:43:59 PM
	  * @modifyDate May 3, 2012 1:43:59 PM
	  * @param l_d Long类型的日期 java.lang.Long
	  * @param format 格式 java.lang.String
	  * @return String 得到的String日期 java.lang.String
	 */
	public static String getDate_d(Long l_d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dd = new Date(l_d.longValue());
		return sdf.format(dd);
	}
	/**
	  * getLong_d(根据String日期得到Long类型日期)
	  *
	  * @Title: getLong_d
	  * @Description: 根据String日期得到Long类型日期
	  * @Date May 3, 2012 1:46:12 PM
	  * @modifyDate May 3, 2012 1:46:12 PM
	  * @param date String日期 java.lang.String
	  * @return Long Long类型日期 java.lang.Long
	 */
	public static Long getLong_d(String date) {

		String year;
		String month;
		String day;
		String hour = "0";
		String second = "0";
		String minute = "0";

		date = date.replaceAll("/", "");
		date = date.replaceAll("-", "");
		// date = date.replaceAll(".","") ;
		date = date.replaceAll(":", "");
		date = date.replaceAll(" ", "");
		date = date.trim();
		year = date.substring(0, 4);
		month = date.substring(4, 6);
		day = date.substring(6, 8);
		if (date.length() > 8) {
			hour = date.substring(8, 10);
			minute = date.substring(10, 12);
			second = date.substring(12, 14);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
		cal.set(Calendar.SECOND, Integer.parseInt(second));
		cal.set(Calendar.MINUTE, Integer.parseInt(minute));
		cal.set(Calendar.MILLISECOND, 0);
		Date dc = cal.getTime();

		return new Long(dc.getTime());

	}
	/**
	  * getMonthBegin(根据传入的日期，得到该月的第一天（也就是一号）)
	  *
	  * @Title: getMonthBegin
	  * @Description: 根据传入的日期，得到该月的第一天（也就是一号）
	  * @Date May 3, 2012 1:47:57 PM
	  * @modifyDate May 3, 2012 1:47:57 PM
	  * @param strdate 传入的日期 java.lang.String
	  * @return String 得到的日期 java.lang.String
	 */
	public static String getMonthBegin(String strdate) {
		java.util.Date date = parseFormatDate(strdate);
		return formatDateByFormat(date, "yyyy-MM") + "-01";
	}
	/**
	  * getMonthEnd(根据传入的日期，得到该月的最后一天)
	  *
	  * @Title: getMonthEnd
	  * @Description: 根据传入的日期，得到该月的最后一天
	  * @Date May 3, 2012 1:48:34 PM
	  * @modifyDate May 3, 2012 1:48:34 PM
	  * @param strdate 传入的日期 java.lang.String
	  * @return String 得到的日期 java.lang.String
	 */
	public static String getMonthEnd(String strdate) {
		java.util.Date date = parseFormatDate(getMonthBegin(strdate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}
	/**
	  * parseFormatDate(将String类型的日期转换成date类型的日期)
	  *
	  * @Title: parseFormatDate
	  * @Description: 将String类型的日期转换成date类型的日期
	  * @Date May 3, 2012 1:49:09 PM
	  * @modifyDate May 3, 2012 1:49:09 PM
	  * @param strdate 传入的日期 java.lang.String
	  * @return Date 得到的日期 java.util.Date
	 */
	public static Date parseFormatDate(String strDate) {
		java.util.Date date = null;
		if (strDate.indexOf("-") > 0) {
			date = parseFormatDateBy(strDate, "-");

		} else if (strDate.indexOf(".") > 0) {
			date = parseFormatDateBy(strDate, ".");

		} else if (strDate.indexOf(".") > 0) {
			date = parseFormatDateBy(strDate, ".");

		}
		return date;

	}
	/**
	  * parseFormatDateBy(通过相应的年月日分割符来进行相应的格式化)
	  *
	  * @Title: parseFormatDateBy
	  * @Description: 通过相应的年月日分割符来进行相应的格式化
	  * @Date May 3, 2012 1:50:04 PM
	  * @modifyDate May 3, 2012 1:50:04 PM
	  * @param strDate 传入的日期 java.lang.String
	  * @param division 分隔符 java.lang.String
	  * @return Date 得到的日期 java.util.Date
	 */
	public static java.util.Date parseFormatDateBy(String strDate,
			String division) {
		java.util.Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + division
					+ "MM" + division + "dd");
			date = sdf.parse(strDate);
		} catch (Exception e) {
		}
		return date;
	}
	/**
	  * getYear(根据日期得到年)
	  *
	  * @Title: getYear
	  * @Description: 根据日期得到年
	  * @Date May 3, 2012 1:52:03 PM
	  * @modifyDate May 3, 2012 1:52:03 PM
	  * @param date 传入的日期 java.util.Date
	  * @return int 得到的年 int
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}
	/**
	  * getMonth(根据日期得到月份)
	  *
	  * @Title: getMonth
	  * @Description: 根据日期得到月份
	  * @Date May 3, 2012 2:10:15 PM
	  * @modifyDate May 3, 2012 2:10:15 PM
	  * @param date 传入的日期 java.util.Date
	  * @return int 得到的月份 int
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}
	/**
	  * getDay(根据日期得到天)
	  *
	  * @Title: getDay
	  * @Description: 根据日期得到天
	  * @Date May 3, 2012 2:11:03 PM
	  * @modifyDate May 3, 2012 2:11:03 PM
	  * @param date 传入的日期 java.util.Date
	  * @return int 得到的天 int
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}
	/**
	  * getHour(根据日期得到小时数)
	  *
	  * @Title: getHour
	  * @Description: 根据日期得到小时数
	  * @Date May 3, 2012 2:11:41 PM
	  * @modifyDate May 3, 2012 2:11:41 PM
	  * @param date 传入的日期 java.util.Date
	  * @return int 得到的小时数 int
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}
	/**
	  * getMinute(根据日期得到分钟数)
	  *
	  * @Title: getMinute
	  * @Description: 根据日期得到分钟数
	  * @Date May 3, 2012 2:12:25 PM
	  * @modifyDate May 3, 2012 2:12:25 PM
	  * @param date 传入的日期 java.util.Date
	  * @return int 得到的分钟数 int
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}
	/**
	  * getSecond(根据日期得到秒数)
	  *
	  * @Title: getSecond
	  * @Description: 根据日期得到秒数
	  * @Date May 3, 2012 2:13:08 PM
	  * @modifyDate May 3, 2012 2:13:08 PM
	  * @param date 传入的日期 java.util.Date
	  * @return int 得到的秒数 int
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}
	/**
	  * getMillis(根据日期得到毫秒数)
	  *
	  * @Title: getMillis
	  * @Description: 根据日期得到毫秒数
	  * @Date May 3, 2012 2:14:08 PM
	  * @modifyDate May 3, 2012 2:14:08 PM
	  * @param date 传入的日期 java.util.Date
	  * @return int 得到的毫秒数 int
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}
	/**
	  * addDate(在date类型的时间上加上给定的天数)
	  *
	  * @Title: addDate
	  * @Description: 在date类型的时间上加上给定的天数
	  * @Date May 3, 2012 2:14:54 PM
	  * @modifyDate May 3, 2012 2:14:54 PM
	  * @param date  传入的日期 java.util.Date
	  * @param day 天数 int
	  * @return Date 得到的日期 java.util.Date
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}
	/**
	  * addDate(将传入的String日期根据需要添加的类型(type)进行加入(minute)，并按照相应的格式返回String)
	  * TODO(Type的参数通过Calendar的静态变量得到，如：Calendar.DAY_OF_MONTH)
	  *
	  * @Title: addDate
	  * @Description: TODO
	  * @Date May 3, 2012 2:16:19 PM
	  * @modifyDate May 3, 2012 2:16:19 PM
	  * @param dateStr 传入的日期 java.lang.String
	  * @param type 需要添加的类型 int (通过Calendar的静态变量得到，如：Calendar.DAY_OF_MONTH)
	  * @param minute 加入的数值 int
	  * @param format 格式
	  * @return String 得到的日期值 java.lang.String
	 */
	public static String addDate(String dateStr, int type, int minute,
			String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, minute);
		Date nowDate = calendar.getTime();
		return dateFormat.format(nowDate);
	}
	/**
	  * subDate(计算2个时间相差的天数)
	  *
	  * @Title: subDate
	  * @Description: 计算2个时间相差的天数
	  * @Date May 3, 2012 2:19:41 PM
	  * @modifyDate May 3, 2012 2:19:41 PM
	  * @param date 第一个日期 java.util.Date
	  * @param date1 第二个日期 java.util.Date
	  * @return int 得到第一个日期-第二个日期的相差天数 int
	 */
	public static int subDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}
	/**
	  * addDate(在传入的String日期中加入天数并按照相应的格式得到String日期)
	  *
	  * @Title: addDate
	  * @Description: 在传入的String日期中加入天数并按照相应的格式得到String日期
	  * @Date May 3, 2012 2:21:09 PM
	  * @modifyDate May 3, 2012 2:21:09 PM
	  * @param date 传入的String日期 java.lang.String
	  * @param format 格式 java.lang.String
	  * @param day 天数 java.lang.String
	  * @return String 得到的String日期 java.lang.String
	  * @throws ParseException 转换异常
	 */
	public static String addDate(String date, String format, int day)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long d1 = sdf.parse(date).getTime() + (long) day * 24 * 3600 * 1000;

		return formatDateByFormat(new Date(d1), format);
	}
	/*
	 * 日期比较函数 传入date1 date2 format return -1 date1<date2 return 0 date1=date2
	 * return 1 date1>date2
	 */
	/**
	  * compareDate(日期比较函数)
	  * TODO(注意：结果为： 传入date1 date2 format return -1 date1<date2 return 0 date1=date2 1 date1>date2)
	  *
	  * @Title: compareDate
	  * @Description: 日期比较函数 传入date1 date2 format return -1 date1<date2 return 0 date1=date2 1 date1>date2
	  * @Date May 3, 2012 2:23:48 PM
	  * @modifyDate May 3, 2012 2:23:48 PM
	  * @param date1 第一个日期 java.lang.String
	  * @param date2 第二个日期 java.lang.String
	  * @param format 两个日期转换的格式 java.lang.String
	  * @return int 得到的比较结果 1 date1>date2 0 date1=date2 -1 date1<date2
	  * @throws Exception 异常
	 */
	public static int compareDate(String date1, String date2, String format)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long d1 = sdf.parse(date1).getTime();
		long d2 = sdf.parse(date2).getTime();
		if (d1 < d2)
			return -1;
		else if (d1 == d2)
			return 0;
		else
			return 1;
	}
	/**
	  * getCurWeek(当前日期的星期)
	  *
	  * @Title: getCurWeek
	  * @Description: 当前日期的星期
	  * @Date May 3, 2012 2:25:52 PM
	  * @modifyDate May 3, 2012 2:25:52 PM
	  * @return String 得到星期 java.lang.String (如:星期日)
	 */
	public static String getCurWeek() {
		int k = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		switch (k) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		}
		return "";
	}
	/**
	  * subDateByDays(日期相减)
	  * TODO(返回减去后的日期，格式与原日期的格式相同)
	  *
	  * @Title: subDateByDays
	  * @Description: 日期相减
	  * @Date May 3, 2012 2:26:57 PM
	  * @modifyDate May 3, 2012 2:26:57 PM
	  * @param date 原日期 java.lang.String
	  * @param days 需要减去的天数
	  * @param format 日期的格式
	  * @return String 得到减去的日期 java.lang.String (返回减去后的日期，格式与原日期的格式相同)
	  * @throws Exception 异常
	 */
	public static String subDateByDays(String date, int days, String format)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// 将原日期转换成毫秒
		long curDateTimes = sdf.parse(date).getTime();
		// 将需要减去的天数转换成毫秒
		long daysTimes = Long.parseLong(String.valueOf(days)) * 24 * 3600 * 1000;
		// 对毫秒进行相减，最后转换成需要的格式
		long result = (curDateTimes - daysTimes);
		Date d = new Date();
		d.setTime(result);
		return sdf.format(d).toString();
	}
	/**
	  * subYear(年数相减 返回减去后的日期，格式与原日期的格式相同)
	  *
	  * @Title: subYear
	  * @Description: 年份相减
	  * @Date Jun 4, 2014 15:04:0 PM
	  * @param date 原日期 java.lang.String
	  * @param year 需要减去的年数
	  * @param format 日期的格式
	  * @return String 得到减去的日期 java.lang.String (返回减去后的日期，格式与原日期的格式相同)
	  * @throws Exception 异常
	 */
	public static String subYear(String dateStr,int year,String format)
	  {
		  SimpleDateFormat dateFormat = new SimpleDateFormat(format); 
		  Date date = null; 
		  try { 
		      date = dateFormat.parse(dateStr); 
		  } catch(ParseException e) { 
		      e.printStackTrace(); 
		  } 
		  Calendar calendar = Calendar.getInstance(); 
		  calendar.setTime(date); 
		  calendar.add(Calendar.YEAR, -year); 
		  Date nowDate = calendar.getTime(); 
		  return dateFormat.format(nowDate); 
	  }
	
	public static String  subMonth(String dateStr,int month) throws ParseException{
		  Calendar c = Calendar.getInstance();
		  SimpleDateFormat sf  = new  SimpleDateFormat(CalendarUtil.format1);
		  Date  date = sf.parse(dateStr);
		  c.setTime(date);
		  c.add(Calendar.MONTH, -month);
		  return sf.format(c.getTime());
	}
	/**
	  * subAddDateByDays(日期相加)
	  *
	  * @Title: subAddDateByDays
	  * @Description: 日期相加
	  * @Date Jun 27, 2012 3:19:24 PM
	  * @modifyDate Jun 27, 2012 3:19:24 PM
	  * @param date 原日期
	  * @param days 需要加上的天数
	  * @param format 日期的格式
	  * @return 返回相加后的日期，格式与原日期的格式相同
	  * @throws Exception
	 */
  	public static String subAddDateByDays(String date, int days, String format)
  			throws Exception {
  		SimpleDateFormat sdf = new SimpleDateFormat(format);
  		// 将原日期转换成毫秒
  		long curDateTimes = sdf.parse(date).getTime();
  		// 将需要相加的天数转换成毫秒
  		long daysTimes = Long.parseLong(String.valueOf(days)) * 24 * 3600 * 1000;
  		// 对毫秒进行加，最后转换成需要的格式
  		long result = (curDateTimes + daysTimes);
  		Date d = new Date();
  		d.setTime(result);
  		return sdf.format(d).toString();
  	}
  	/**
  	  * subDateGetMonth(两个时间相减，得到月份)
  	  *
  	  * @Title: subDateGetMonth
  	  * @Description: 两个时间相减，得到月份
  	  * @Date Jul 25, 2012 9:54:22 AM
  	  * @modifyDate Jul 25, 2012 9:54:22 AM
  	  * @param MaxDate 大的时间对象 java.util.Date
  	  * @param MinDate 小的时间对象 java.util.Date
  	  * @return int 得到的月份
  	 */
  	public static int subDateGetMonth(java.util.Date MaxDate, java.util.Date MinDate) {
  	  
  	  int iMaxDateYear=getYear(MaxDate);
  	  int iMinDateYear=getYear(MinDate);
  	  
  	  int iMaxDateMonth=getMonth(MaxDate);
  	  int iMinDateMonth=getMonth(MinDate);
  	  
  	  
  	  int iSubYear=(iMaxDateYear-iMinDateYear);
  	  
  	  int iSubMonth=(iMaxDateMonth-iMinDateMonth);
  	  
  	  int iSubTotalMonth=(iSubYear*12)+iSubMonth;
  	  
        return iSubTotalMonth;
        
     }
	
	/**
 	 * 日期的月份。和日份。不能是日历上没有的日期
 	 * 验证输入日期格式
 	 * @param sess
 	 */
	public static  String DateIsR(String date){
 		int year = Integer.parseInt(date.substring(0,4));
 		int mon = Integer.parseInt( date.substring(4,6));
 		int day = Integer.parseInt( date.substring(6,8));
 		String M2="28";
		if(year%4 == 0){
			  M2="29";
 		}
		String [] Mday=new String[]{"31",M2,"31","30","31","30","31","31","30","31","30","31"};
		if(mon > 12 || mon == 0){
			return "输入时间月份有误";
			
		}
		int tDay = Integer.parseInt(Mday[(mon-1)]);
		if(day >tDay || day ==0){
			return "输入时间日期有误";
		}
		return "";
 		
	}
	
	/**
	   * 在日期的年份上加年，得到最新的日期
	   * @param dateStr
	   * @param year
	   * @param format
	   * @return
	   */
	public static String addYear(String dateStr,int year,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format); 
		Date date = null; 
		try { 
			date = dateFormat.parse(dateStr); 
		} catch(ParseException e) { 
		} 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date); 
		calendar.add(Calendar.YEAR, year);
		Date nowDate = calendar.getTime(); 
		return dateFormat.format(nowDate); 
	}
}
