package com.froad.fft.enums;

/**
 * 客户端接入版本号
 * @author FQ
 *
 */
public enum ClientVersion {
	
	version_1_0("1.0","接入版本号-1.0");
	
	private String code;
	private String describe;
	
	private ClientVersion(String code,String describe){
		this.code=code;
		this.describe=describe;
	}
	
	public String getCode() {
		return code;
	}


	public String getDescribe() {
		return describe;
	}
}
