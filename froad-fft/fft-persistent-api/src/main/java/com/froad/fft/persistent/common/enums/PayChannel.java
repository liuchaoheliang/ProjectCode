package com.froad.fft.persistent.common.enums;


	/**
	 * 类描述：支付渠道
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年2月13日 下午5:30:56 
	 */
public enum PayChannel {
	
	/**
	 * 枚举的code与openapi的PayType保持一致
	 * **/

	filmCard("20","贴膜卡支付"),
	
	alipay("53","支付宝支付"),
	
	internetBank("50","网银支付");
	
	
	private String code;
	private String describe;
	
	private PayChannel(String code,String describe){
		this.code=code;
		this.describe=describe;
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
