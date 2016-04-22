package com.froad.common;

public enum State {
	CREATE("00"),ORDERSUCCESS("10"),ORDERFAIL("20"),PAYSUCCESS("30"),PAYFAIL("40"),CANCEL("50");
	private String value;
	private State(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
}

