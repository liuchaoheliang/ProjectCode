package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

public class PramasUtil {

	 public static long DateFormat(String date) throws ParseException{
		 String framat=null;
		 long reTime=0;
		 if(StringUtil.isNotEmpty(date)){
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
	 
	 //日期加一天
	public static long DateFormatEnd(String date) throws ParseException {
		String framat = null;
		long reTime = 0;
		if (StringUtil.isNotEmpty(date)) {
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
			reTime=time.getTime() + Long.valueOf(86400 * 1000) -1l;
		}
		return reTime;
	}
	 
	 
	 public static String DateFormat(long date,String fomat) throws ParseException{
		 if(date>0){
			 if(StringUtil.isEmpty(fomat)){
				 fomat="yyyy-MM-dd HH:mm:ss";
			 }
			 SimpleDateFormat sf=new SimpleDateFormat(fomat);
			 Date time=new Date(date);
			return  sf.format(time);
		 }
		 return null;
	 }
	 
	 public static Map<String,String> DateFormatTos(long date,String format){
		 Map<String,String> map=new HashMap<String,String>();
		 if(date>0){
			 if(StringUtil.isEmpty(format)){
				 format="yyyy-MM-dd";
			 }
			 SimpleDateFormat sf=new SimpleDateFormat(format);
			 Date time=new Date(date);
			 String da=sf.format(time);
			 map.put("year", da.substring(0,4));
			 map.put("month", da.substring(5,7));
			 map.put("day", da.substring(8));
			return  map;
		 }
		 return null;
	 }
	 
	 /**
	  * 当前时间的前89天
	  * @param date
	  * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 public static long DateFormatTos() throws NumberFormatException, ParseException{
		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		 String time=sf.format(new Date());
		 Date date=sf.parse(time);
		 long beginDate=date.getTime()-Long.valueOf(86400*1000)*Long.valueOf(89);
		 return beginDate;
	 }
	 

	 /**
	  * 开始时间为空 结束时间为空 /结束时间不为空
	  * @param date 结束
	  * @return 返回开始时间
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 public static String notEmpyBe(long date,boolean isEmpty) throws ParseException{
        //		 isEmpty 是 = 结束时间为空 ,否=结束时间不为空
		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		 long bug=0;
		 if(isEmpty)bug=(new Date().getTime())-(Long.valueOf(86400*1000)*Long.valueOf(90));
		 else bug=(date)-(Long.valueOf(86400*1000)*Long.valueOf(90));
		 return sf.format(bug);
	 } 
	 
	 /**
	  * 结束时间为空 开始时间为空 /开始时间不为空
	  * @param date 开始时间
	  * @param  endDate 结束时间
	  * @throws ParseException 
	  * @throws NumberFormatException 
	  */
	 public static String notEmpyEn_E(long date,boolean isEmpty) throws ParseException{
	    //		 isEmpty 是 = 开始时间为空 ,否=开始时间不为空
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		long bug=0;
		if(isEmpty) bug=(new Date().getTime());
		else bug=(date)+(Long.valueOf(86400*1000)*Long.valueOf(90));
		return sf.format(bug);
	 }
	 
	 /**
	  * 结束时间不为空 开始时间不为空
	  * @param date 开始时间
	  * @param  endDate 结束时间
	  * @return true =开始时间和结束时间超过90天
	  * @throws ParseException 
	  * @throws NumberFormatException 
	  */
	 public static boolean notEmpyEn_E(long date,long endDate) throws ParseException{
		boolean bug=false;
		 bug=(endDate-date)>(Long.valueOf(86400*1000)*Long.valueOf(90));
		return bug;
	 }
	 
	 /**
	  * 当前时间
	  * @param date
	  * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 public static long DateFormatToString(String from) throws NumberFormatException, ParseException{
		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
//		 String time=sf.format(/*new Date()*/);
		 Date date=sf.parse(from);
		long endDate=date.getTime() + Long.valueOf(86400 * 1000) -1l;
		return endDate;
	 }
	 
	 /**
	  * 当前时间
	  * @param date
	  * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 public static long DateFormatToString(Date day) throws NumberFormatException, ParseException{
		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		 String time=sf.format(day);
		 Date date=sf.parse(time);
		return date.getTime();
	 }
	 /**
	  * 获取当天时间的后一天的凌晨前一毫秒
	  * 说明   description of the class
	  * 创建日期  2015年10月23日  上午11:47:43
	  * 作者  artPing
	  * 参数  @param day
	  * 参数  @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 public static long totay(Date day) throws NumberFormatException, ParseException{
		 return DateFormatToString(day)+Long.valueOf(86400 * 1000) -1l;
	 }
	 
	 /**
	  * 获取当前时间的前6天作为本周
	  * 说明   description of the class
	  * 创建日期  2015年10月23日  上午11:52:16
	  * 作者  artPing
	  * 参数  @param day
	  * 参数  @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 @SuppressWarnings("deprecation")
	public static long week(Date day) throws NumberFormatException, ParseException{
		int today=day.getDay();
		 if(today<=0)today=7;
		 return totay(day)-Long.valueOf(86400*1000)*today+1L;
	 }
	 
	 /**
	  * 当前时间的前29天 查一个月
	  * 说明   description of the class
	  * 创建日期  2015年10月23日  下午12:00:50
	  * 作者  artPing
	  * 参数  @param day
	  * 参数  @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 @SuppressWarnings("deprecation")
	public static long month(Date day) throws NumberFormatException, ParseException{
		 return totay(day)-Long.valueOf(86400*1000)*day.getDate()+1L;
	 }
	 
	 /**
	  * 当前时间的前89天查询三个月
	  * 说明   description of the class
	  * 创建日期  2015年10月23日  上午11:56:58
	  * 作者  artPing
	  * 参数  @param day
	  * 参数  @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	  */
	 public static long month_3(Date day) throws NumberFormatException, ParseException{
		 return totay(day)-Long.valueOf(86400*1000)*89+1L;
	 } 
	 
	 
	 @SuppressWarnings("deprecation")
	public static void main(String[] a) throws ParseException{
		 SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
//		 String time=sf.format(/*new Date()*/);
		 Date date=sf.parse("2015-12-01");
//		 System.out.println(DateFormat(totay(date),null));
//		 System.out.println(DateFormat(week(date),null));
		 System.out.println(DateFormat(month(date),null));
//		 System.out.println(DateFormat(mont(date),null));
//		 System.out.println(DateFormat(month_3(date),null));
//		 Date day=new Date();
		 System.out.println(date.getDate());
	 }
	 
	 
	 
}
