package com.froad.fft.enums;

/**
 * *******************************************************
 *<p> 工程: fft-api </p>
 *<p> 类名: SmsType.java </p>
 *<p> 描述: *-- <b>对应服务端SmsType枚举，只给客户端可用的枚举类型</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月29日 上午10:09:42 </p>
 ********************************************************
 */
public enum SmsType{
	
	authcodeRegister("1001","用户注册短信验证码"),
	authcodeResetPwd("1002","用户重置密码短信验证码"),
	authcodeModifiedMobile("1003","用户修改手机号码短信验证码"),
	presellDelivery("1004","精品预售提货短信"),
	presellRefund("1005","精品预售退款短信"),
    presellClusterFail("1006","精品预售不成团短信"),
    presellReturnSale("1007","精品预售申请退货");
	
	private String code;
	private String describe;
	
	private SmsType(String code,String describe){
		this.code = code;
		this.describe = describe;
	}
	
	public String getCode() {
		return code;
	}

	public String getDescribe() {
		return describe;
	}

	@Override
	public String toString() {
		return this.code;
	}
	
}
