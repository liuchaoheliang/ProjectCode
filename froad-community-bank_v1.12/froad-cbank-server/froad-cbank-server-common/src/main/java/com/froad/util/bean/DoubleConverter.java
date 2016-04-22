package com.froad.util.bean;

public class DoubleConverter extends NumberConverter implements Converter<Double>{

	@Override
	public Double convert(Object sourceValue) {
		Number number = super.convertToNumber(sourceValue);
		if(number!=null){
			return number.doubleValue();
		}
		return null;
	}
	
}
