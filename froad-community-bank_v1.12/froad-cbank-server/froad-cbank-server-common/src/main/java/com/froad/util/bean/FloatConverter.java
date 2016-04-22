package com.froad.util.bean;

public class FloatConverter extends NumberConverter implements Converter<Float>{

	@Override
	public Float convert(Object sourceValue) {
		Number number = super.convertToNumber(sourceValue);
		if(number!=null){
			return number.floatValue();
		}
		return null;
	}
	
}
