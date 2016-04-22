package com.froad.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;

import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.MD5Util;
import com.froad.util.TaskTimeUtil;

public class TestTime {
	
	
	public static void main(String[] args) {
//		StringBuffer strB = new StringBuffer();
//		strB.append("hello ");
//		strB.append("world");
//		long startTime = new Date().getTime();
//		String md5Str = MD5Util.getMD5Str(strB.toString());
//		long endTime = new Date().getTime();
//		System.out.println(endTime - startTime);
//		System.out.println(md5Str);
//		Date date = new Date(1436025600000l);
//		System.out.println(date);
//		Date s = TaskTimeUtil.getStartDay(-1);
//		System.out.println(s);
//		
//		Date e = TaskTimeUtil.getEndDay(-1);
//		System.out.println(e);
//		System.out.println("--------");
//		List<Date> list = getad();
//		for(Date d : list){
//			System.out.println(d);
//		}
//		String str1 = null;
//		System.out.println("2".equals(str1));
		
//		System.out.println(new Date(1441941717106l));
		System.out.println(TaskTimeUtil.convertToDayBegin(20150118).getTime());
		System.out.println(TaskTimeUtil.convertToDayEnd(20151118).getTime());
		
		System.out.println(DateUtil.parse(DateUtil.DATE_FORMAT1, "2015-01-18").getTime());
		
		System.out.println(DateUtils.addDays(new Date(), -1));
		
//		System.out.println(" -hello world- ".trim());
		
		List<String> strList = new ArrayList<String>();
		strList.add("A");
		strList.add("B");
		strList.add("C");
		strList.add("D");
		System.out.println(strList.subList(0, 1));
		System.out.println(strList.subList(0, 4));
		
		String str = "100000002";
		System.out.println(str.split(" ")[0]);
		
		System.out.println(TaskTimeUtil.convertToDayBegin(20150810).getTime());
		System.out.println(TaskTimeUtil.convertToDayEnd(20150810).getTime());
		
//		System.out.println("2015-08-07-00");
		System.out.println(convertToHourBegin("2015-08-07-00").getTime());
		System.out.println(convertToHourEnd("2015-08-07-00").getTime());
//		System.out.println();

//		System.out.println("2015-08-07-01");
		System.out.println(convertToHourBegin("2015-08-07-01").getTime());
		System.out.println(convertToHourEnd("2015-08-07-01").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-02");
		System.out.println(convertToHourBegin("2015-08-07-02").getTime());
		System.out.println(convertToHourEnd("2015-08-07-02").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-03");
		System.out.println(convertToHourBegin("2015-08-07-03").getTime());
		System.out.println(convertToHourEnd("2015-08-07-03").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-04");
		System.out.println(convertToHourBegin("2015-08-07-04").getTime());
		System.out.println(convertToHourEnd("2015-08-07-04").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-05");
		System.out.println(convertToHourBegin("2015-08-07-05").getTime());
		System.out.println(convertToHourEnd("2015-08-07-05").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-06");
		System.out.println(convertToHourBegin("2015-08-07-06").getTime());
		System.out.println(convertToHourEnd("2015-08-07-06").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-07");
		System.out.println(convertToHourBegin("2015-08-07-07").getTime());
		System.out.println(convertToHourEnd("2015-08-07-07").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-08");
		System.out.println(convertToHourBegin("2015-08-07-08").getTime());
		System.out.println(convertToHourEnd("2015-08-07-08").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-09");
		System.out.println(convertToHourBegin("2015-08-07-09").getTime());
		System.out.println(convertToHourEnd("2015-08-07-09").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-10");
		System.out.println(convertToHourBegin("2015-08-07-10").getTime());
		System.out.println(convertToHourEnd("2015-08-07-10").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-11");
		System.out.println(convertToHourBegin("2015-08-07-11").getTime());
		System.out.println(convertToHourEnd("2015-08-07-11").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-12");
		System.out.println(convertToHourBegin("2015-08-07-12").getTime());
		System.out.println(convertToHourEnd("2015-08-07-12").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-13");
		System.out.println(convertToHourBegin("2015-08-07-13").getTime());
		System.out.println(convertToHourEnd("2015-08-07-13").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-14");
		System.out.println(convertToHourBegin("2015-08-07-14").getTime());
		System.out.println(convertToHourEnd("2015-08-07-14").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-15");
		System.out.println(convertToHourBegin("2015-08-07-15").getTime());
		System.out.println(convertToHourEnd("2015-08-07-15").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-16");
		System.out.println(convertToHourBegin("2015-08-07-16").getTime());
		System.out.println(convertToHourEnd("2015-08-07-16").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-17");
		System.out.println(convertToHourBegin("2015-08-07-17").getTime());
		System.out.println(convertToHourEnd("2015-08-07-17").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-18");
		System.out.println(convertToHourBegin("2015-08-07-18").getTime());
		System.out.println(convertToHourEnd("2015-08-07-18").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-19");
		System.out.println(convertToHourBegin("2015-08-07-19").getTime());
		System.out.println(convertToHourEnd("2015-08-07-19").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-20");
		System.out.println(convertToHourBegin("2015-08-07-20").getTime());
		System.out.println(convertToHourEnd("2015-08-07-20").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-21");
		System.out.println(convertToHourBegin("2015-08-07-21").getTime());
		System.out.println(convertToHourEnd("2015-08-07-21").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-22");
		System.out.println(convertToHourBegin("2015-08-07-22").getTime());
		System.out.println(convertToHourEnd("2015-08-07-22").getTime());
//		System.out.println();
		
//		System.out.println("2015-08-07-23");
		System.out.println(convertToHourBegin("2015-08-07-23").getTime());
		System.out.println(convertToHourEnd("2015-08-07-23").getTime());
//		System.out.println();
		
//		double a = 4415.2;
//		double b = 4109.5;
//		System.out.println((int)Arith.div(Double.valueOf("1.9"), Double.valueOf("0.1")));
//		
//		System.out.println(new Date(1438131404101L));
//		int totalSize = 3000;
//		BigDecimal bigDecimal = new BigDecimal(totalSize);
//		int noOfChunk = bigDecimal.divide(new BigDecimal(1000), BigDecimal.ROUND_UP).intValue();
//		System.out.println(noOfChunk);
//		
//		int date = 20140723;
//		System.out.println(TaskTimeUtil.skipDateTime(date, -1));
//		System.out.println(new Date(1437644316729l));
//		Date curDate = new Date();
//		System.out.println(curDate);
//		System.out.println(new Date(getDayBegin(curDate)));
//		System.out.println(new Date(getFirstDayOfMonth(curDate)));
//		TaskTimeUtil.convertToDayEnd(20150616);
		
//		long s = System.currentTimeMillis();
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
////		long d = DateUtils.addMinutes(new Date(s), 10).getTime();
//		System.out.println(System.currentTimeMillis()-s);
	}
	
	private static long getDayBegin(Date date){
		long dayBegin = 0l;
    	Calendar calendar = null;
    	
    	calendar = Calendar.getInstance();
    	calendar.setTime(DateUtil.parse(DateUtil.DATE_FORMAT2, DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, date)));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		dayBegin = calendar.getTimeInMillis();
		
		return dayBegin;
	}

	/**
	 * get first day of the month
	 * 
	 * @param date
	 * @return
	 */
	private static long getFirstDayOfMonth(Date date){
		long dayOfMonth = 0l;
    	Calendar calendar = Calendar.getInstance();
    	
    	calendar.setTime(DateUtil.parse(DateUtil.DATE_FORMAT2, DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, date)));
    	calendar.add(Calendar.MONTH, 0);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	
    	dayOfMonth = getDayBegin(calendar.getTime());
		
		return dayOfMonth;
	}
	
	public static List<Date> getad(){
		List<Date> list = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();
		now.setTime(DateUtils.addDays(new Date(), 3));
		
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.addDays(new Date(), 3));
		c.setFirstDayOfWeek(Calendar.MONDAY);
		// 三个月之前的日期
		c.add(Calendar.MONTH, -3);
//		System.out.println(c.getTime());
		int weekCount = now.get(Calendar.WEEK_OF_YEAR) - c.get(Calendar.WEEK_OF_YEAR);
//		System.out.println(weekCount);
		for(int i=0; i<=weekCount; i++){
			int day = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()-day);
			list.add(c.getTime());
			c.add(Calendar.WEEK_OF_YEAR, 1);
		}
		
		return list;
	}
	
	public static Date convertToHourBegin(String date){
		Calendar calendar = null;
		
		calendar = Calendar.getInstance();
		Date d = DateUtil.parse(DateUtil.DATE_TIME_FROMAT6, date);
		calendar.setTime(d);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date convertToHourEnd(String date){
		Calendar calendar = null;
		
		calendar = Calendar.getInstance();
		Date d = DateUtil.parse(DateUtil.DATE_TIME_FROMAT6, date);
		calendar.setTime(d);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		return calendar.getTime();
	}
}
