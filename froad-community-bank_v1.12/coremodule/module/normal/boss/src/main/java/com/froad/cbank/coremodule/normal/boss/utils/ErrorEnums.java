package com.froad.cbank.coremodule.normal.boss.utils;

public enum ErrorEnums {
	
	syserr("500","系统繁忙,请稍后再试!"),
	timeout("300","登录超时,请重新登录"),
	success("0000", "操作成功"), 
	fail("6666", "操作失败"),
	empty("6668","参数不能为空"),
	other("6667","系统错误,请联系管理员！"),
	noresult("6665","查询无记录"),
	img_upload_fail("6664","图片上传失败!"),
	thrift_err("6663","通讯异常"),
	noauth("400","无权限")
	;
	

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
	private ErrorEnums(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}


}
