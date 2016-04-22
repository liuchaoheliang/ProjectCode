package com.froad.cbank.coremodule.module.normal.merchant.utils;

public enum EnumTypes {
	
	syserr("500","系统错误"),
	timeout("300","登录超时,请重新登录"),
	noauth("301","无权限"),
	success("0000", "操作成功"), 
	fail("6666", "操作失败"),
	empty("6668","参数不能为空"),
	other("6667","系统错误,请联系管理员！"),
	noresult("6665","查询无记录"),
	img_upload_fail("6664","图片上传失败!"),
	img_type_fail("6662","图片格式错误，只支持png,jgp,jpeg格式图片!"),
	thrift_err("6663","通讯异常"),
	authority("6660","没有权限"),
	noAuthority("4013","对不起，权限不够"),
	qrcode_down_fail("6669","二维码下载失败")
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
	private EnumTypes(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}


}
