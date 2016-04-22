package com.froad.cbank.coremodule.framework.common.util.bean;

public class LongConverter extends NumberConverter implements Converter<Long>{

	@Override
	public Long convert(Object sourceValue) {
		Number number = super.convertToNumber(sourceValue);
		if(number!=null){
			return number.longValue();
		}
		return null;
	}
	
}
