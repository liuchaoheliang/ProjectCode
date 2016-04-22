package com.froad.cbank.coremodule.framework.common.util.bean;

import java.lang.reflect.Method;

public class EnumConverter {

	public static <T> T convertToEnum(Object value,Class<T> type){
		T[] objs = type.getEnumConstants();
		try {
			Method m = type.getMethod("getCode");
			m.setAccessible(true);
			Object enumCode = null;
			for(T obj : objs){
				enumCode = m.invoke(obj);
				if(value.toString().equals(enumCode.toString())){
					return obj;
				}
				
				if(value.toString().equals(obj.toString())){
					return obj;
				}
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
