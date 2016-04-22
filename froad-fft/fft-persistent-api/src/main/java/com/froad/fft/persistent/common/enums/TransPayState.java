package com.froad.fft.persistent.common.enums;

/**
 * 交易表的支付状态
 * @author FQ
 *
 */
public enum TransPayState {
	
	unpaid("10","未支付"),
	partPayment("20","部分支付"),
	paid("30","已支付"),
	refunding("40","退款中"),
	refunded("50","全额退款");
	
	private String code;
	private String describe;
	
	private TransPayState(String code,String describe){
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
