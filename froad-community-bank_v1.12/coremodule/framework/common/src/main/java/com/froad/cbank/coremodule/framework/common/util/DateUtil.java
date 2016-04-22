package com.froad.cbank.coremodule.framework.common.util;


import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public DateUtil() {
	}

	/**
	 * 计算两个日期差, beginDate小于endDate时返回正数
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return days 两个日期相差天数
	 */
	public static long getDateDiff(Date beginDate, Date endDate) {
		long days = 0;
		days = endDate.getTime() - beginDate.getTime();
		days = days / (1000 * 60 * 60 * 24);
		return days;
	}

	/**
	 * 计算两个日期差,默认格式yyyyMMdd, beginDate小于endDate时返回正数
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return days 两个日期相差天数
	 */
	public static long getDateDiff(String beginDate, String endDate)
			throws Exception {

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		sf.setLenient(false);// 严格控制输入
		Date d1 = sf.parse(beginDate);
		Date d2 = sf.parse(endDate);

		long beginTime = d1.getTime();
		long endTime = d2.getTime();
		long days = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * 计算两个日期差,可以指定格式, beginDate小于endDate时返回正数
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param format
	 *            日期格式
	 * @return days 返回相差天数
	 * @throws Exception
	 */

	public static long getDateDiff(String beginDate, String endDate,
			String format) throws Exception {

		SimpleDateFormat sf = new SimpleDateFormat(format);
		sf.setLenient(false);// 严格控制输入
		Date d1 = sf.parse(beginDate);
		Date d2 = sf.parse(endDate);

		long beginTime = d1.getTime();
		long endTime = d2.getTime();
		long days = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * 日期格式转换
	 * @param date
	 			需要转换的日期
	 * @param srcFormat
	 			为原来的日期格式
	 * @param format
	 			转换后的日期格式
	 * @return
	 * @throws Exception
	 */
	public static String convertDateFormat(String date, String srcFormat,
			String format) throws Exception {
		SimpleDateFormat srcSdf = new SimpleDateFormat(srcFormat);
		// 将原日期转换成毫秒
		long srcDateTimes = srcSdf.parse(date).getTime();
		Date d = new Date();
		d.setTime(srcDateTimes);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d).toString();
	}
	
	
	  /**
     * 日期减去X小时，返回新的日期
     * @param date
     * @param hours
     * @param format
     * @return
     * @throws Exception
     */
    public static String getDateBySubHours(String date, int hours, String format)
		throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// 将原日期转换成毫秒
		long curDateTimes = sdf.parse(date).getTime();
		// 将需要减去的天数转换成毫秒
		long daysTimes = Long.parseLong(String.valueOf(hours)) * 3600 * 1000;
		// 对毫秒进行相减，最后转换成需要的格式
		long result = (curDateTimes - daysTimes);
		Date d = new Date();
		d.setTime(result);
		return sdf.format(d).toString();
	}
    /**
	 * 日期相减
	 * 
	 * @param date
	 *            原日期
	 * @param days
	 *            需要减去的天数
	 * @param format
	 *            日期的格式
	 * @return 返回减去后的日期，格式与原日期的格式相同
	 * @throws Exception
	 */
	public static String subDateByDays(String date, int days, String format)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// 将原日期转换成毫秒
		long curDateTimes = sdf.parse(date).getTime();
		// 将需要减去的天数转换成毫秒 一天有86400秒
		long daysTimes = Long.parseLong(String.valueOf(days)) * 86400 * 1000;
		// 对毫秒进行相减，最后转换成需要的格式
		long result = (curDateTimes - daysTimes);
		Date d = new Date();
		d.setTime(result);
		return sdf.format(d).toString();
	}
	/**	 
	 * @param 根据formatStr格式化date时间
	 * */
	public static String formatDate(Date date,String formatStr){
    	SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    	String today=sdf.format(date);
    	return today;
    }
	/**	 
	 * @param 获取当前日期格式 yyyyMMdd
	 * */
	public static String getCurDate(){
		return  CalendarUtil.getDate_d(
				new Long(System.currentTimeMillis()), CalendarUtil.format4);
	}
	/**	 
	 * @param 获取当前日期格式 yyyy-MM-dd HH:mm:ss
	 * */
	public static String getCurTotalDate(){
		return  CalendarUtil.getDate_d(
				new Long(System.currentTimeMillis()), CalendarUtil.format5);
	}
	/**	 
	 * @param 获取当前日期格式 yyyy-MM-dd
	 * */
	public static String getCurDate_YYYY_MM_DD(){
		return  CalendarUtil.getDate_d(
				new Long(System.currentTimeMillis()), CalendarUtil.format1);
	}
	/**	 
	 * @param 根据format格式化时间
	 * */
	public static String getCurDate(long cur_millis,String format){
		return CalendarUtil.getDate_d(cur_millis, format);
	}
	
	/**	 
	 * @param 获取当前日期格式 HH:mm:ss
	 * */
	public static String getCurTime(){
		return  CalendarUtil.getDate_d(
				new Long(System.currentTimeMillis()), CalendarUtil.HHmmss);
	}
	
	/**	 
	 * @param 获取当前日期格式 HHmm
	 * */
	public static String getCurHmTime(){
		return  CalendarUtil.getDate_d(
				new Long(System.currentTimeMillis()), CalendarUtil.HHmm);
	}
	
	/**	 
	 * @param 获取当前日期格式 HHmmss
	 * */
	public static String getDyshTime(){
		return  CalendarUtil.getDate_d(
				new Long(System.currentTimeMillis()), CalendarUtil.HHmmss1);
	}
	/**	 
	 * @param 获取当前日期格式 yyyyMMdd|HH:mm:ss
	 * */
	public static String getBillCurDate(){
		return  CalendarUtil.getDate_d(
				new Long(System.currentTimeMillis()),"yyyyMMdd|HH:mm:ss");
	}
	/**
     * 修改日期
     * @param baseDate 基础日期
     * @param field 修改日期字段 -年,月,日
     * @param amount 数量
     * @return
     */
    public static String dateModify(String baseDate,int field,int amount )
    {
       StringBuffer sb = new StringBuffer();
      
       SimpleDateFormat sdf=new SimpleDateFormat(CalendarUtil.format4); // 将字符串格式化
       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象
      
       Calendar c = Calendar.getInstance(); // 初始化一个Calendar
       c.setTime(dt); // 设置基准日期
       c.add(field, amount); //你要加减的日期
       Date dt1=c.getTime(); // 从Calendar对象获得基准日期增减后的日期
       sb=sdf.format(dt1,sb,new FieldPosition(0)); // 得到结果字符串

       return sb.toString();
    }
    
    /**
     * 
     * @param minute   增加的分钟
     * @return   有效时间   yyyyMMdd|HH:mm:ss
     */
    public static String addValidtime(int minute)
    {
        Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE,minute);
		SimpleDateFormat  df = new SimpleDateFormat("yyyyMMdd|HH:mm:ss");   
		String billplatform_validtime = df.format(calendar.getTime());
        return billplatform_validtime;
    }
    
    public static long getBillDateDiff(String beginDate, String endDate,
			String format) throws Exception {

		SimpleDateFormat sf = new SimpleDateFormat(format);
		sf.setLenient(false);// 严格控制输入
		Date d1 = sf.parse(beginDate);
		Date d2 = sf.parse(endDate);

		long beginTime = d1.getTime();
		long endTime = d2.getTime();
		long time = (long) ((endTime - beginTime));
		return time;
	}
    
    /**	 
	 * @param 1.先按照sourceFormat格式化date为Date类型，然后再根据targetformat格式化date
	 * @Example:date="20140324" sourceFormat="yyyyMMdd" targetformat="yyyy-MM-dd" return "";
	 * */
	public static String formatDate(String date,String sourceFormat,String targetformat){
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
			Date today = sdf.parse(date);
			return new SimpleDateFormat(targetformat).format(today);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	/**
     * @author xzw 取指定时间差的时间值
     * @param date
     * @param min
     * @param format
     * @return
     * @throws Exception
     */
    public static String getDateBySubMinute(String date , long min,String format) throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	
    	// 将原日期转化成毫秒
    	long curDateTimes = sdf.parse(date).getTime();
    	
    	// 将需要减去的分数转化成毫秒
    	long daysTimes = min *60*1000;
    	
    	long result = curDateTimes - daysTimes;
    	
    	Date date2 = new Date();
    	
    	date2.setTime(result);
    	
    	return sdf.format(date2).toString();
    }
}
