package com.froad.cbank.coremodule.module.normal.bank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.vo.DateForReportVo;

public class DateUtilForReport {
	
	public final static String ERROR = "message";
	public final static String CODE = "9999";
	/**
	 * @throws Exception 
	 * 
	 * 方法名称: verifyDate 
	 * 简要描述: 报表校验起始时间和结束时间
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月24日 下午6:21:33
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param beginDate
	 * 方法参数: @param endDate
	 * 方法参数: @return
	 * 返回类型: Map<String,String>
	 * @throws
	 */
	public static Map<String, String> verifyDate(String beginDate, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		// 其中一个日期为空
		if(StringUtil.isBlank(beginDate)&&StringUtil.isNotBlank(beginDate)){
			map.put(ERROR, "起始日期不能为空");
			return map;
		}
		if (StringUtil.isNotBlank(beginDate) && StringUtil.isBlank(beginDate)) {
			map.put(ERROR, "结束日期不能为空");
			return map;
		}
		// 查询时间都为空
		if (StringUtil.isBlank(beginDate) && StringUtil.isBlank(endDate)) {
			map.put(ERROR, "查询时间不能为空");
			return map;
		}
		// 起始时间大于当日结束时间
		if (DateUtil.dateToTheDayBeforeDawn(beginDate) > DateUtil.endOfTodDay()) {
			map.put(ERROR, "起始日期不能大于当前日期");
			return map;
		}
		//开始时间大于结束时间
		if (DateUtil.dateToTheDayBeforeDawn(beginDate) > DateUtil
				.dateToTheDayAfterDawn(endDate)) {
			map.put(ERROR, "起始时间不能大于结束时间");
			return map;
		}
		// 不大于90天
		/*if ((DateUtil.DateFormat(endDate) - DateUtil.DateFormat(beginDate)) > (90 * 24 * 60 * 60 * 1000L)) {
			map.put(ERROR, "查询时间不能超过90天");
			return map;
		}*/
		return map;
	}
	/**
	 * 
	 * 方法名称: getBeginDateAndEndDate 
	 * 简要描述: 返回指定时间的起始时间和结束时间 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月24日 下午5:59:33
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param beginDate
	 * 方法参数: @param endDate
	 * 方法参数: @return
	 * 返回类型: DateForReportVo
	 * @throws
	 */
	public static DateForReportVo getBeginDateAndEndDate(String beginDate,
			String endDate) {
		DateForReportVo dateForReportVo = null;
		// 起始时间和结束时间都不未空的时候才设置
		if (StringUtil.isNotBlank(beginDate) && StringUtil.isNotBlank(endDate)) {
			dateForReportVo = new DateForReportVo();
			// 设置对应的起始时间和结束时间
			dateForReportVo.setBeginDate(DateUtil
					.dateToTheDayBeforeDawn(beginDate));
			dateForReportVo.setEndDate(DateUtil.dateToTheDayAfterDawn(endDate));
		}
		return dateForReportVo;
	}

	/**
	 * 
	 * getWeekByDate:(根据日期返回一年中的第几周).
	 *
	 * @author wufei
	 * 2015-12-4 下午02:30:27
	 * @param dateTime
	 * @return
	 *
	 */
	public static String getWeekByDate(String dateTime){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null; 
		try {
			date = dateFormatter.parse(dateTime);
		} catch (ParseException e) {
			LogCvt.info("获取一年中的第几周："+e.getMessage(), e);
		}
		 dateFormatter.applyPattern("w");
		 String  week = dateFormatter.format(date);
		 return  week;
		
	}
	
	/**
	 * 
	 * getMonthByDate:(根据日期返回月份).
	 *
	 * @author wufei
	 * 2015-12-4 下午02:30:17
	 * @param dateTime
	 * @return
	 *
	 */
	public static String getMonthByDate(String dateTime){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		int month = 0;
		Date date = null; 
		try {
			date = dateFormatter.parse(dateTime);
			cal.setTime(date);
		} catch (ParseException e) {
			LogCvt.info("日期中的月份："+e.getMessage(), e);
		} 
		month = cal.get(Calendar.MONTH); 
		return String.valueOf(month+1);
	}

	/**
	 * 
	 * getYearByDate:(根据日期返回年).
	 *
	 * @author wufei
	 * 2015-12-5 上午11:54:44
	 * @param dateTime
	 * @return
	 *
	 */
	public static String getYearByDate(String dateTime){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		int year = 0;
		@SuppressWarnings("unused")
		Date date = null; 
		try {
			date = dateFormatter.parse(dateTime);
		} catch (ParseException e) {
			LogCvt.info("日期中的年："+e.getMessage(), e);
		}
		cal.setTime(new Date());
		year = cal.get(Calendar.YEAR); 
		return String.valueOf(year);
	}
	
	public static void main(String[] args) throws Exception {
		
		// System.out.println("一年中的第几周：" + getWeekByDate("2016-01-02"));
		
		System.out.println("日期中的月份："+getMonthByDate("2016-01-02"));
		
	}
}
