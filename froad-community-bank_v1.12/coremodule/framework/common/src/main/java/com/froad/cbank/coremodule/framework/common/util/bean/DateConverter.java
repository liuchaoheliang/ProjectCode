package com.froad.cbank.coremodule.framework.common.util.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<Date>{
	static final String pattern_datetime = "yyyy-MM-dd HH:mm:ss";
	static final String pattern_date = "yyyy-MM-dd";
	static final SimpleDateFormat sdf = new SimpleDateFormat();

	@Override
	public Date convert(Object sourceValue) {
		if(sourceValue==null){
			return null;
		}
		if(sourceValue instanceof Date){
			return (Date) sourceValue;
		}
		
		String valueStr = sourceValue.toString();
		if(valueStr.matches("\\d+")){
			return new Date(Long.valueOf(sourceValue.toString()));
		}
		
		if(valueStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}( \\d{1,2}:\\d{1,2}:\\d{1,2})?")){
			synchronized (sdf) {
				try {
					sdf.applyPattern(pattern_datetime);
					return sdf.parse(valueStr);
				} catch (ParseException e) {
					sdf.applyPattern(pattern_date);
					try {
						return sdf.parse(valueStr);
					} catch (ParseException e1) {
					}
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws ParseException {
		DateConverter dc = new DateConverter();
		System.out.println(dc.convert("2015-11-12"));
	}
	
}
