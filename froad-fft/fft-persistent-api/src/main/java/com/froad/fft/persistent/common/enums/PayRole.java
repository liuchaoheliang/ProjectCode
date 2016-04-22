package com.froad.fft.persistent.common.enums;

/**
 * 支付角色（资金和积分的流转）
 * @author FQ
 *
 */
public enum PayRole {
	
	member("1","用户"),
	merchant("2","商户"),
	bank("3","银行或者银行积分池"),
	fft("4","方付通或者方付通积分池");
	
	private String code;
	private String describe;
	
	private PayRole(String code,String describe){
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
