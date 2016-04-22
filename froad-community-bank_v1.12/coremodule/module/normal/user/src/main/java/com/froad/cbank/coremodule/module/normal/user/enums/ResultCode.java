package com.froad.cbank.coremodule.module.normal.user.enums;

public enum ResultCode {

	success("0000","操作成功"),
	failed("9999","操作失败"),
	syserr("500","系统繁忙，请稍后再试"),
	
	thriftException("9998","通讯异常"),
	missingParam("9997","缺少必要参数"),
	payTimeout("9996","支付请求超时"),
	
	wrongToken("9987","用户身份token校验失败"),
	notExistUser("9986","用户不存在"),
	notBoundMobile("9985","用户未绑定手机号"),
	
	orderGenerateFailed("8888","创建订单失败"),
	activeCheckFailed("2011","满减活动检验不通过")
	
	
	;
	
	
	private String code;
	
	private String msg;
	
	private ResultCode(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

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
	
	@Override
	public String toString(){
		return this.code;
	}
}
