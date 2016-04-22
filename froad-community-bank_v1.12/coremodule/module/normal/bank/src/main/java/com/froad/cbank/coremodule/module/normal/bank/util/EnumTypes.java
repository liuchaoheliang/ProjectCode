package com.froad.cbank.coremodule.module.normal.bank.util;

public enum EnumTypes {
	
	syserr("500","系统错误"),
	timeout("300","由于您长时间未操作登录已超时，为保障您的账户安全请重新登录。"),
	success("0000", "操作成功"), 
	fail("9999", "操作失败"),
	empty("6666","参数不全"),
	other("6667","系统错误,请联系管理员"),
	thrift_err("6663","网络连接失败，请检查网络是否正常。"),
	img_upload_fail("6664","图片上传失败，请检查大小是否超出限制。"),
	img_type_fail("6662","图片格式错误，只支持png,jgp,jpeg格式图片!"),
	noresult("6665","查询无记录"),
	noEmpty("6668","您提交的信息不完整，请补全后重新提交。"),
	noRole("6669","无权限"),
	verify_code("6670","验证码输入错误"),
	illlegal("9005","请求参数有误"),
	noAuthority("4013","对不起，权限不够"),
	noReferer("608","未能 获取Referer"),
	noClientId("608","未能 获取clientId"),
	;
	
	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private EnumTypes(String code, String message) {
		this.code = code;
		this.message = message;
	}


}
