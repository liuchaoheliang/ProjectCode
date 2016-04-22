package com.froad.util.bean;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ConvertRegister {
	private static Map<String,Converter> converters = new HashMap<String,Converter>();
	static {
		Converter temp = null;
		converters.put(makeKey(Object.class, String.class), new StringConverter());
		converters.put(makeKey(Object.class, java.util.Date.class), new DateConverter());
		temp = new IntConverter();
		converters.put(makeKey(Object.class, int.class), temp);
		converters.put(makeKey(Object.class, Integer.class), temp);
		temp = new LongConverter();
		converters.put(makeKey(Object.class, long.class), temp);
		converters.put(makeKey(Object.class, Long.class), temp);
		temp = new DoubleConverter();
		converters.put(makeKey(Object.class, double.class), temp);
		converters.put(makeKey(Object.class, Double.class), temp);
		temp = new FloatConverter();
		converters.put(makeKey(Object.class, float.class), temp);
		converters.put(makeKey(Object.class, Float.class), temp);
	}
	public static Converter regist(Class source,Class target,Converter converter){
		return converters.put(makeKey(source, target), converter);
	}

	private static String makeKey(Class source, Class target) {
		return String.format("source:%s:target:%s", source.getName(),target.getName());
	}
	
	public static boolean isExistsConverter(Class source,Class target){
		return converters.containsKey(makeKey(source, target));
	}
	
	public static Converter remove(Class source,Class target){
		return converters.remove(makeKey(source, target));
	}

	public static Converter findConverter(Class<?> source, Class<?> target) {
		Converter converter = converters.get(makeKey(source, target));
		if(converter==null){
			converter = converters.get(makeKey(Object.class, target));
		}
		
		return converter;
	}
}
