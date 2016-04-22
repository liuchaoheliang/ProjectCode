package com.froad.util.bean;

import java.lang.reflect.Method;

public class StringConverter implements Converter<String> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String convert(Object source) {
		if (source == null) {
			return null;
		}

		Class type = source.getClass();

		if (type.isEnum()) {
			Method m = null;
			try {
				m = type.getMethod("getCode");
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
			if (m != null) {
				Object result = BaseConverter.invoke(m, source);
				return result == null ? null : result.toString();
			}
		}

		return source.toString();
	}

}
