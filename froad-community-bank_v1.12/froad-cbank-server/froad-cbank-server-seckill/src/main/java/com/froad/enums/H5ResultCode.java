package com.froad.enums;

public enum H5ResultCode {

	success("0000","操作成功"),
	failed("9999","操作失败"),
	
	thriftException("9998","模块调用异常"),
	missingParam("9997","缺少必要参数"),
	
	wrongSmsCode("9989","短信验证码错误"),
	wrongImgCode("9988","图片验证码错误"),
	errorToken("9987","用户身份token校验失败"),
	
	productNotExist("9801", "商品信息不存在或未上架"),
	productLimit("9802", "用户购买数量大于商品购买限额"),
	productStore("9803", "商品库存不足"),
	
	acceptOrderFailed("9804", "创建受理订单失败"),
	
	userLoginCenterException("9805", "访问用户登录中心异常"),
	userPayCenterException("9806", "访问用户支付中心异常"),
	payPasswordNotExist("9807", "支付密码未设置"),
	errorPayPassword("9808", "支付密码错误"),
	userPointCenterException("9809", "访问用户积分中心异常"),
	userPointFaild("9810", "获取用户积分失败"),
	userPointNotEnough("9811", "用户积分余额不足"),
	userPointOrgNoNotMatch("9812", "用户积分机构号不匹配"),
	
	orgNotExist("9813", "提货网点不存在"),
	orgDisabled("9814", "提货网点被禁用"),
	
	foilCardNumFailed("9815", "贴膜卡校验失败"),
	
	;
	
	
	private String code;
	
	private String msg;
	
	private H5ResultCode(String code, String msg){
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
