package com.froad.util.validation.entity;

public class SortResult {

	private String errorMsg;
	
	private Object regular;

	public String getErrorMsg() {
		return errorMsg;
	}

	public Object getRegular() {
		return regular;
	}

	public SortResult(String errorMsg, Object regular) {
		this.errorMsg = errorMsg;
		this.regular = regular;
	}
	
	
}
