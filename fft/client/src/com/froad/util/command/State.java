package com.froad.util.command;

public enum State {
	CREATE("10"),AVAILIABLE("30"),UNAVAILIABLE("40"),DEL("50");
	private String value;
	private State(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	
}
