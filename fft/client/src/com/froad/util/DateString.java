package com.froad.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateString {
	public  static String getStrDate(String inDate,String outDate){
		StringBuffer sb=new StringBuffer();
		String inYear=inDate.substring(0,4);
		String inMouth=inDate.substring(4,6);
		String inDay=inDate.substring(6,inDate.length());
		
		String outYear=outDate.substring(0,4);
		String outMouth=outDate.substring(4,6);
		String outDay=outDate.substring(6,outDate.length());
		sb.append(inYear).append("-").append(inMouth).append("-").append(inDay);
		sb.append("~").append(outYear).append("-").append(outMouth).append("-").append(outDay);
		return sb.toString();
	}

	public static String getStrTime(String arg1,String arg2){
		StringBuffer sb=new StringBuffer();
		String hour1=arg1.substring(0,2);
		String minute1=arg2.substring(2,4);
		
		String hour2=arg2.substring(0,2);
		String minute2=arg2.substring(2,4);
		sb.append(hour1).append(":").append(minute1);
		sb.append("-").append(hour2).append(":").append(minute2);
		return sb.toString();
	}
	
	
	/**
	  * 方法描述：日期 字符串格式转换
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-22 下午1:20:04
	  */
	public static String createDate(String nowPattern,String dateStr,String newPattern){
		SimpleDateFormat df = new SimpleDateFormat(nowPattern);
		Date date = null;
		try {
			date = df.parse(dateStr);
			SimpleDateFormat dFormat = new SimpleDateFormat(newPattern);
			dateStr = dFormat.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dateStr = "";
		}
		
		return dateStr;
	}
	
	public static void main(String[] args) {
		String str=new DateString().getStrTime("1700", "1300");
		System.out.println(str);
	}
}
