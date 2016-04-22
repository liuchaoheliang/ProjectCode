package com.froad.cbank.coremodule.framework.common.util.bean;

public class IntConverter extends NumberConverter implements Converter<Integer>{

	@Override
	public Integer convert(Object sourceValue) {
		Number number = super.convertToNumber(sourceValue);
		if(number!=null){
			return number.intValue();
		}
		return null;
	}
	
}
