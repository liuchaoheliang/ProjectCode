package com.froad.util.bean;

import java.lang.reflect.Method;
import java.math.BigDecimal;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class NumberConverter{

	public Number convertToNumber(Object sourceValue) {
		if (sourceValue == null) {
			return null;
		}
		
		if(sourceValue instanceof Number){
			return (Number) sourceValue;
		}

		String code = null;
		Class sourceType = sourceValue.getClass();
		if (sourceType.isEnum()) {

			Method m = null;
			try {
				m = sourceType.getMethod("getCode");
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
			if (m != null) {
				Object result = BaseConverter.invoke(m, sourceValue);
				code = result == null ? null : result.toString();
			}else{
				code = sourceValue.toString();
			}
		} else {
			code = sourceValue.toString();
		}

		return new BigDecimal(code);

	}
	

}
