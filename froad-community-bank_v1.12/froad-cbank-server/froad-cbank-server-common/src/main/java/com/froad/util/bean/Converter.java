package com.froad.util.bean;

public interface Converter<T> {
	public T convert(Object sourceValue);
}
