package com.froad.util.validation.entity;

public enum FieldVerfyType {

	required(0,"该字段不能为空"),
	regular(1,"正则校验失败"), 
	maxLength(2,"超过最大长度"), 
	minLength(3,"不足最小长度")
	
	;

	public int code;
	public String errorMsg;
	
	
	private FieldVerfyType(int code,String errorMsg) {
		this.code = code;
		this.errorMsg = errorMsg;
	}

}
