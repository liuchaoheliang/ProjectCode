package com.froad.fft.persistent.common.enums;

/**
 * 支付类型
 * @author FQ
 *
 */
public enum PayType {
	
	/**
	 * 资金
	 */
	cash("10","资金支付"),
	
	/**
	 * 积分
	 */
	points("20","积分支付");
	
	
	private String code;
	private String describe;
	
	private PayType(String code,String describe){
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
