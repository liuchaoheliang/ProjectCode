package com.froad.enums;

public enum PayType {

	froadPoint(1),
	cash(2),
	bankPoint(3);
	
	public int code;
	private PayType(int code){
		this.code = code;
	}
}
