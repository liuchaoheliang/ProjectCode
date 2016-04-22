package com.froad.fft.persistent.common.enums;

/**
 * 交易状态
 * @author FQ
 *
 */
public enum TransState {
	
	processing("20","交易处理中"),
	success("30","交易成功"),
	fail("40","交易失败"),
	close("50","交易关闭"),
	exception("60","交易异常");
	
	private String code;
	private String describe;
	
	private TransState(String code,String describe){
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
