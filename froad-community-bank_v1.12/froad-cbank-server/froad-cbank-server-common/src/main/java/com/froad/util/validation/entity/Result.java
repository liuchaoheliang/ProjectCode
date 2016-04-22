package com.froad.util.validation.entity;

public class Result {

	private boolean result;
	
	private String message;

	public boolean isResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public Result(boolean result) {
		this.result = result;
	}

	public Result(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	@Override
	public String toString(){
		return "校验结果: " + result + " 描述信息: " + message;
	}
 	
	
}
