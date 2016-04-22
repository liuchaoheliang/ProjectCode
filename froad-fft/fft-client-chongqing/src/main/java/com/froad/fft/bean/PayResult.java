package com.froad.fft.bean;

public class PayResult {

	/**支付状态*/
	public enum PayCode{
		success("success"),
		error("error"),
		wait("wait");//支付对应状态信息   success : 支付完成  error : 支付异常  waiting : 等待支付(支付宝)
		private String code;
		private PayCode(String code){
			this.code = code;
		}
		@Override
		public String toString() {
			return this.code;
		}
	}
	
	private String msg;  //支付对应的描述信息
	private PayCode code; //具体状态
	private String url; //转跳地址
	
	public String getMsg() {
		return msg;
	}

	public PayCode getCode() {
		return code;
	}

	public PayResult(String msg, PayCode code) {
		this.msg = msg;
		this.code = code;
	}
	
	public PayResult(String msg, PayCode code,String url) {
		this.msg = msg;
		this.code = code;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	
}
