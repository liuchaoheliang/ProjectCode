package com.froad.thirdparty.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author FQ
 *
 */
public class DateUtil {
	
	/**  
     * 静态常量  
     */  
    public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";   
    public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";   
    public static final String C_DATE_PATTON_DEFAULT1 = "yyyy年MM月dd日";
    public static final String C_TIME_PATTON_DEFAULT1 = "yyyyMMddhhmmss";
    public static final String MILLS_TIME_PATTERN="yyMMddHHmmssSSS";
    
    
    public static String formatDateTime(String pattern, long time) {
		return formatDateTime(pattern, new Date(time));
	}
	
    /**
     * 以指定格式返回指定日期的字符串
     * @param pattern - 日期显示格式
     * @param date - 需要格式 化的时间
     * @return the formatted date-time string
     * @see java.text.SimpleDateFormat
     */
    public static String formatDateTime(String pattern, Date date) {
        String strDate = null;
        String strFormat = pattern;
        SimpleDateFormat dateFormat = null;

        if (date == null) return "";

        dateFormat = new SimpleDateFormat(strFormat);
        strDate = dateFormat.format(date);

        return strDate;
    }
    
    /**
     * Parses a string to produce a Date.
     * @param pattern - the pattern of the string
     * @param strDateTime - the string to be parsed
     * @return A Date parsed from the string. In case of error, returns null.
     */
    public static Date parse(String pattern, String strDateTime) {
        java.util.Date date = null;
        if (strDateTime == null || pattern == null) return null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            date = formatter.parse(strDateTime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**  
     * 转换当前时间为默认格式  
     * @return  
     */  
    public static String formatDate2Str() {   
        return formatDate2Str(new Date());   
    }   
       
    /**  
     * 转换指定时间为默认格式  
     * @param date  
     * @return  
     */  
    public  static String formatDate2Str(Date date) {   
        return formatDate2Str(date, C_TIME_PATTON_DEFAULT);   
    }   
    
    /**  
     * 转换指定时间为指定格式  
     * @param date  
     * @param format  
     * @return  
     */  
    public static String formatDate2Str(Date date, String format) {   
        if (date == null) {   
            return null;   
        }   
           
        if (format == null || format.equals("")) {   
            format = C_TIME_PATTON_DEFAULT;   
        }   
        SimpleDateFormat sdf = getSimpleDateFormat(format);   
        return sdf.format(date);   
    } 
    
    /**  
     * 拿到指定输出格式的SimpleDateFormat  
     * @param format  
     * @return  
     */  
    public static SimpleDateFormat getSimpleDateFormat(String format) {   
        SimpleDateFormat sdf;   
        if (format == null) {   
            sdf = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);   
        } else {   
            sdf = new SimpleDateFormat(format);   
        }   
           
        return sdf;   
    }   
    
    /**
	 * 得到当前时间yyyyMMddhhmmss
	 * @return
	 */
	public static String currentTimeToString(){
		SimpleDateFormat sdf = new SimpleDateFormat(C_TIME_PATTON_DEFAULT1);
		String date = sdf.format(new Date());
		return date;
	}
	
	public static String currentMillsTimeToString(){
		SimpleDateFormat sdf = new SimpleDateFormat(MILLS_TIME_PATTERN);
		String date = sdf.format(new Date());
		return date;
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
