package com.froad.fft.persistent.common.enums;

/**
 * 支付类型详情
 * @author FQ
 *
 */
public enum PayTypeDetails {

	PAY_AMOUNT("000","金额支付"),
	PAY_FFT_POINTS("001","分分通积分支付"),
	PAY_BANK_POINTS("002","银行积分支付"),
	
	BUY_POINTS_AMOUNT("100","购买积分的金额"),
	BUY_POINTS_FEE("101","购买积分的手续费"),
	REBATE_POINTS("102","给用户返积分");
	
	private String code;
	private String describe;
	
	private PayTypeDetails(String code,String describe){
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
