package com.froad.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

public class TestTime {
	
	
	public static void main(String[] args) {
//		Date date = new Date(1415804002000l);
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
		
		
		long s = System.currentTimeMillis();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		long d = DateUtils.addMinutes(new Date(s), 10).getTime();
		System.out.println(System.currentTimeMillis()-s);
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
	
}
