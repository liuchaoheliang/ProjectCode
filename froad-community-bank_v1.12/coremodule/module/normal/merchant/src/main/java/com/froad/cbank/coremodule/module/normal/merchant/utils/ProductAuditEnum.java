package com.froad.cbank.coremodule.module.normal.merchant.utils;

public enum ProductAuditEnum {
	
	dsh("0","审核中"),
	shtg("1","审核通过"),
	shbtg("2","审核不通过"),
	wtj("3","未提交"),
	shtggxz("4","审核通过(更新中)"),
	shtgshbtg("5", "审核通过(审核不通过)"),
	shtgwtj("6", "审核通过(未提交)");
	
	

	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private ProductAuditEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}


}
