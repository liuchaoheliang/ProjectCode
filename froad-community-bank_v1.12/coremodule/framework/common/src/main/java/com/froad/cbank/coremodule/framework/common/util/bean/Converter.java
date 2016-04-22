package com.froad.cbank.coremodule.framework.common.util.bean;

public interface Converter<T> {
	public T convert(Object sourceValue);
}
