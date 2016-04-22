package com.froad.common;

public enum OrderType {
	PREFERENTIAL("10"),POINTS("20");
	
	private OrderType(String value){
		this.value=value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
}
