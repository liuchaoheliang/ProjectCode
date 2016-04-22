package com.froad.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 

/**
 * 日期
 * @author yfy
 * @description 日期工具类
 */
public class DateSystemUtil {
	
	public static final String DATE_TIME_FORMAT_01 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_01 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_02 = "yyyy/MM/dd";
	public static final String DATE_FORMAT_03 = "yyyy/MM/dd HH:mm";
	
	/**
	  * 日期转换
	  * @param date
	  * @return
	  * @throws ParseException
	  */
	public static long DateFormat(String date) throws ParseException{
		 String framat=null;
		 long reTime=0;
		if (date!=null && !"".equals(date)) {
			 if(date.indexOf(":")!=-1)framat="yyyy-MM-dd HH:mm:ss";
			 else if(date.indexOf("/")!=-1)framat="yyyy/MM/dd";
			 else if(date.indexOf("-")!=-1)framat="yyyy-MM-dd";
			 else framat="yyyyMMdd";
			 SimpleDateFormat sf=new SimpleDateFormat(framat);
			 Date time=sf.parse(date);
			 reTime=time.getTime();
		 }

		 return reTime;
	 }
	/** 
	* 获得指定日期的前一天 
	* @param specifiedDay 
	* @return 
	*/ 
	public static String formatDateBefore(String specifiedDay,String fmtDate){ 
		SimpleDateFormat df = new SimpleDateFormat(fmtDate); 
		Calendar c = Calendar.getInstance(); 
		Date date = null; 
		try { 
			date = df.parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace();
		} 
		c.setTime(date); 
		int day = c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 
		String dateBefore = df.format(c.getTime()); 
		return dateBefore; 
	} 
	 /**
	  * 日期加一天
	  * @param date
	  * @return
	  * @throws ParseException
	  */
	public static long DateFormatEnd(String date) throws ParseException {
		String framat = null;
		long reTime = 0;
		if (date!=null && !"".equals(date) ) {
			if (date.indexOf(":") != -1)
				framat = "yyyy-MM-dd HH:mm:ss";
			else if (date.indexOf("/") != -1)
				framat = "yyyy/MM/dd";
			else if (date.indexOf("-") != -1)
				framat = "yyyy-MM-dd";
			else
				framat = "yyyyMMdd";
			SimpleDateFormat sf = new SimpleDateFormat(framat);
			Date time = sf.parse(date);
			reTime=time.getTime() + Long.valueOf(86400 * 1000)-1;
		}
		return reTime;
	}
		 
	
    /**
     * @param dt
     * @param showTime
     * @return
     * 固定格式 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
     */
    public static String formatDate(java.util.Date dt, boolean showTime) {
        if (showTime){
        	return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(dt);
        }else{
        	return (new SimpleDateFormat("yyyy-MM-dd")).format(dt);
        }  
    }
	
    /**
     * @param dt Date日期类型
     * @param fmtDate 格式样式，如：yyyy-MM-dd HH:mm:ss
     * @return
     * 自定义格式
     */
    public static String formatDate(java.util.Date dt, String fmtDate) {
    	return (new SimpleDateFormat(fmtDate)).format(dt);
    }
    
    public static String formatDate(java.util.Date dt) {
    	return (new SimpleDateFormat("yyyy-MM-dd")).format(dt);
    }
    
    public static String formatDateTWO(java.util.Date dt) {
    	return (new SimpleDateFormat(DATE_TIME_FORMAT_01)).format(dt);
    }
    /**
     * 取当前时间
     * @return
     */
    public static String getCurrentDate() {
    	return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }
    
    /**
     * 取当前时间
     * @return
     */
    public static String getNewDate() {
    	return (new SimpleDateFormat("yyyyMMdd")).format(new Date());
    }
	
    /**
     * 获取当前年月
     * @return
     */
    public static String formatNowMonth() {
    	String m=(new SimpleDateFormat("yyyy/MM")).format(new java.util.Date());
    	String ms[]=m.split("\\/");
    	if(ms[1].substring(0,1).equals("0")){
    		m=ms[0]+"年"+ms[1].replace("0", "")+"月";
    	}else{
    		m=ms[0]+"年"+ms[1]+"月";
    	}
    	
       return m;
    }
    
    /**
     * @param d
     * @return
     * 取得指定日期的年的第一天
     */
    public static String getYearFirstDay(java.util.Date d ) { 
    	String m=(new SimpleDateFormat("yyyy")).format(d);
        return  m+"-01-01";  
    }
    
    /**
     * @param d
     * @return
     * 取得指定日期的年的最后一个月的最后一天
     */
    public static String getYearFirstMonthLastDay(java.util.Date d ) { 
    	String m=(new SimpleDateFormat("yyyy")).format(d);
        return  m+"-12-31";  
    }
    
    /**
     * @param d
     * @return
     * 取得指定日期的上一个月的最后一天
     */
    public static String getDateLastDay(java.util.Date d ) {
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar    
                .getActualMaximum(Calendar.DAY_OF_MONTH));    
        return DateSystemUtil.formatDate(calendar.getTime(),false);    
    }
    
    /**
     * @param d
     * @return
     * 得到当前年的第一个月的第一天 
     */
    public static String getYearLastMonthLastDay(java.util.Date d) {    
    	String m=(new SimpleDateFormat("yyyy")).format(d);
        return  m+"-01-01";  
    }    
    
    /**
     * @param d
     * @return
     * 取得指定日期的上一个月的第一天
     */
    public static String getDateFisrtDay(java.util.Date d ) {
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar    
                .getActualMinimum(Calendar.DAY_OF_MONTH));    
        return DateSystemUtil.formatDate(calendar.getTime(),false);    
    }
    
    /**
     * @return
     * 取得本月上一个月的最后一天
     */
    public static String getNowDateBeforeMonthLastDay( ) {    
    	java.util.Date d=new java.util.Date();
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar    
                .getActualMaximum(Calendar.DAY_OF_MONTH));    
        return DateSystemUtil.formatDate(calendar.getTime(),false);    
    }
    
    /**
     * @param d
     * @return
     * 返回指定月中第一天
     */
    public static String getCurrentMonthFirstDay(java.util.Date d){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d);
    	c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH));
    	String minDay = DateSystemUtil.formatDate(c1.getTime(), false);
    	return minDay;
    }
    
    /**
     * @param d
     * @return
     * 返回指定月中最后一天
     */
    public static String getCurrentMonthLastDay(java.util.Date d){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d);
    	c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));
    	String maxDay = DateSystemUtil.formatDate(c1.getTime(), false);
    	return maxDay;
    }
    
    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static String getCurrentWeekFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                      calendar.getFirstDayOfWeek()); // Sunday
        return DateSystemUtil.formatDate(calendar.getTime(), false);
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static String getCurrentWeekLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                     calendar.getFirstDayOfWeek() + 6); // Saturday
        return DateSystemUtil.formatDate(calendar.getTime(), false);
    }
    
    /**
     * @param startDate 开始日期时间
     * @param endDate   结束日期时间
     * @return
     * 获取日期天数
     */
    @SuppressWarnings("unused")
	public static String getDiffDays(Date startDate, Date endDate) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	try{
    	    long diff = endDate.getTime() - startDate.getTime();
    	    long days = diff / (1000 * 60 * 60 * 24);
    	    return String.valueOf(days);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	return "0";
    }
    
    
    /**
     * 年份加减计算
     */
    public static Date yearAddCalculate(Date currDate, int year) {
    	Calendar calendar = Calendar.getInstance();
    	try {
			calendar.setTime(currDate);
			calendar.add(Calendar.YEAR, year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calendar.getTime();
    }
    
    /**
     * 字符串yyyy-MM-dd转换到Date类型
     * @param dateStr yyyy-MM-dd
     * @return Date
     */
    public static Date strDate2Date(String dateStr) {
        return str2Date(dateStr, "yyyy-MM-dd");
    }
    
    public static Date strDate2DateTWO(String dateStr) {
        return str2Date(dateStr, DATE_TIME_FORMAT_01);
    }
    
    /**
     * 字符串转换到Date类型
     * @param dateStr 需要转换的字符串
     * @param format 转换格式
     * @return Date
     */
    public static Date str2Date(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        Date date = dateFormat.parse(dateStr, new ParsePosition(0));
        return date;
    }
    
    
    /**
     * 字符串yyyy-MM-dd转换到Calendar类型
     * @param dateStr yyyy-MM-dd
     * @return Calendar
     */
    public static Calendar strDate2Calendar(String dateStr) {
        return str2Calendar(dateStr, "yyyy-MM-dd");
    }

    
    /**
     * 获得两个日期字符串相差多少天
     * @param tarDate 日期字符串日期格式为yyyy-MM-dd·
     * @param curDate 日期字符串
     * @return 天数
     */
    public static int dateStrCalculate(String tarDate, String curDate) {
    	return (int)((DateSystemUtil.strDate2Calendar(tarDate).getTimeInMillis()
    			- DateSystemUtil.strDate2Calendar(curDate).getTimeInMillis()) / (24 * 60 * 60 * 1000));
    }

    /**
     * 字符串转换到Calendar类型
     * @param dateStr 需要转换的字符串
     * @param format 转换格式
     * @return Calendar
     */
    public static Calendar str2Calendar(String dateStr, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str2Date(dateStr, format));
        return calendar;
    }
    
    
    public static int timeStrCalculate(String tarDate, String curDate, String formatStr) {
    	return (int)((str2Calendar(tarDate, formatStr).getTimeInMillis() 
    			- str2Calendar(curDate, formatStr).getTimeInMillis()) / 1000);
    }
    
    public static String getTimeByPointYmhms(Date date, int year, int month, int day, int hour, int minute, int second) {
    	Calendar calendar = Calendar.getInstance();
    	if(date == null) {
    		calendar.setTime(new Date());
    	} else {
    		calendar.setTime(date);
    	}
    	calendar.set(Calendar.YEAR,  calendar.get(Calendar.YEAR) + year);
    	calendar.set(Calendar.MONTH,  calendar.get(Calendar.MONTH) + month);
    	calendar.set(Calendar.DAY_OF_MONTH,  calendar.get(Calendar.DAY_OF_MONTH) + month);
    	calendar.set(Calendar.HOUR_OF_DAY,  calendar.get(Calendar.HOUR_OF_DAY) + day);
    	calendar.set(Calendar.MINUTE,  calendar.get(Calendar.MINUTE) + minute);
    	calendar.set(Calendar.SECOND,  calendar.get(Calendar.SECOND) + second);
    	
    	return formatDate(calendar.getTime(), true);
    }
    
    
    /**
	 * 
	 * long值转成Date类型
	 * @return
	 */
	public static Date longToDate(long time) {
		return new Date(time);
	}
    
	/**
	 * 获取凌晨的时间值long
	 * @param date yyyy-MM-dd
	 * @return
	 * @throws ParseException 
	 */
	public static long dateToTheDayBeforeDawn(String date){
		String t = date+" 00:00:00";
		SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long datmp = null;
		try {
			datmp = re.parse(t).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datmp == null ? startOfTodDay() : datmp;
	}
	
	
	/**
	 * 获取晚上23:59:59的时间值long
	 * @param date yyyy-MM-dd
	 * @return
	 * @throws ParseException 
	 */
	public static long dateToTheDayAfterDawn(String date){
		String t = date+" 23:59:59";
		SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long datmp = null;
		try {
			datmp = re.parse(t).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datmp == null ? endOfTodDay() : datmp;
	}
	
	public static String getWeekInfoFirstDay(int year,int week){
		Calendar calendar = Calendar.getInstance(); 
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.WEEK_OF_YEAR,week);
    	return DateSystemUtil.getCurrentWeekFirstDay(calendar.getTime());
	}
	
	public static String getWeekInfoLastDay(int year,int week){
		Calendar calendar = Calendar.getInstance(); 
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.WEEK_OF_YEAR,week);
    	return DateSystemUtil.getCurrentWeekLastDay(calendar.getTime());
	}
	
	public static String getWeekInfoLastMonth(int year,int month){
    	Calendar cal = Calendar.getInstance();
     	cal.set(Calendar.YEAR,year);
	    cal.set(Calendar.MONTH, month-1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	    Date lastDate = cal.getTime(); 
    	return DateSystemUtil.dateFormart2(lastDate.getTime());
	}
	
    public static void main(String args[]) {
    	System.out.println(getCurrentMonthFirstDay(new Date()));
    	System.out.println(getCurrentMonthLastDay(new Date()));
    	System.out.println(getCurrentWeekFirstDay(new Date()));
    	System.out.println(getCurrentWeekLastDay(new Date()));
    	System.out.println(getWeekInfoLastMonth(2016,2));
    }
  
	/**
	 * 当天的结束时间
	 * 
	 * @return
	 */
	public static long endOfTodDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date date = calendar.getTime();
		return date.getTime();
	}
  
	/**
	 * 
	 * 方法名称: startOfTodDay 
	 * 简要描述: 获取当天的起始时间 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月24日 下午5:20:50
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @return
	 * 返回类型: long
	 * @throws
	 */
	public static long startOfTodDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = calendar.getTime();
		return date.getTime();
	}
	
	/**
	 * 
	 * dateFormart:返回格式为"yyyy-MM-dd HH:mm:ss"的日期字符串
	 *
	 * @author 明灿
	 * 2015年8月17日 下午5:03:34
	 * @param time
	 * @return
	 *
	 */
	public static String dateFormart(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_01);
		return sdf.format(time);
	}
	
	/**
	 * 
	 * dateFormart:返回格式为"yyyy-MM-dd"的日期字符串
	 *
	 * @author 明灿
	 * 2015年8月17日 下午5:03:34
	 * @param time
	 * @return
	 *
	 */
	public static String dateFormart2(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_01);
		return sdf.format(time);
	}
	
	/**
	 * 
	 * dateFormart4Report:订单优化的时间格式
	 *
	 * @author 明灿
	 * 2015年8月26日 下午6:34:32
	 * @param time
	 * @return
	 *
	 */
	public static String dateFormart4Report(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_03);
		return sdf.format(time);
	}

	public static Date stringToDate(String d,String fomat){
		SimpleDateFormat sdf = new SimpleDateFormat(fomat);
		Date date=new Date();
		try {
			date = sdf.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
}