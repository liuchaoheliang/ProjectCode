package com.froad.fft.enums.trans;

/**
 * 交易创建来源
 * @author FQ
 *
 */
public enum TransCreateSource {
	
	pc("100","pc"),
	android("200","android客户端"),
	iphone("300","iphone客户端");
	
	private String code;
	private String describe;
	
	private TransCreateSource(String code,String describe){
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
