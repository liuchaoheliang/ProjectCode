package com.froad.cbank.coremodule.normal.boss.utils;

//import com.travelzen.framework.core.util.MoneyUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("deprecation")
public class FinanceFormatUtils {

	public static String formatLongType(Long num) {
		if (num == null)
			return "0.00";
		double temp = num;
		temp = temp / 100;
		return new DecimalFormat(",###,##0.00").format(temp);
	}

//	public static long parseStringNumber(String num) {
//		if (num == null || num.length() == 0)
//			return 0L;
//		return MoneyUtil.yuan2Cent(new BigDecimal(num));
//	}
//
//	public static long parseStringNumber(long num) {
//		return parseStringNumber(String.valueOf(num));
//	}

	public static String getSimpleDate(Date date) {
		if (date == null)
			return null;
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String getFromDate(Date from) {
		if (from == null)
			return null;
		Date result = new Date(from.getYear(), from.getMonth(), from.getDate(), 0, 0, 0);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(result);
	}

	public static Date getFromDateString(String date) throws ParseException {
		Date from = parseDate(date);
		if (from == null)
			return null;
		Date result = new Date(from.getYear(), from.getMonth(), from.getDate(), 0, 0, 0);
		return result;
	}

	public static Date getFromDateString(Date from) throws ParseException {
		if (from == null)
			return null;
		Date result = new Date(from.getYear(), from.getMonth(), from.getDate(), 0, 0, 0);
		return result;
	}

	public static String getToDate(Date to) {
		if (to == null)
			return null;
		Date result = new Date(to.getYear(), to.getMonth(), to.getDate(), 23, 59, 59);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(result);
	}

	public static Date getToDateString(String date) throws ParseException {
		Date to = parseDate(date);
		if (to == null)
			return null;
		Date result = new Date(to.getYear(), to.getMonth(), to.getDate(), 23, 59, 59);
		return result;
	}

	public static Date getToDateString(Date to) throws ParseException {
		if (to == null)
			return null;
		Date result = new Date(to.getYear(), to.getMonth(), to.getDate(), 23, 59, 59);
		return result;
	}

	public static String formatDate(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String formatSimpleDate(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static Date parseDate(String date) throws ParseException {
		if (date == null || date.length() == 0)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(date);
	}

	public static Date parseLongDate(String date) throws ParseException {
		if (date == null || date.length() == 0)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(date);
	}

	public static Date getEverlastingDate() throws ParseException {
		return new Date(2099, 12, 31, 23, 59, 59);
	}

	public static String parseString(String date) throws ParseException {
		if (date == null || date.length() == 0)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static int getDifferDays(Date begin, Date end) throws ParseException {
		begin = parseDate(getSimpleDate(begin));
		end = parseDate(getSimpleDate(end));
		return (int) ((begin.getTime() - end.getTime()) / (24 * 60 * 60 * 1000));
	}

	public static void main(String args[]) {
		try {
			String ticketNo = "[1]";
			ticketNo = ticketNo.replaceAll("\\[", "").replaceAll("\\]", "");
			System.out.println(ticketNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
