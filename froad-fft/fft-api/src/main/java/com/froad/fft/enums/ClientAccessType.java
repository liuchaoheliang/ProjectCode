package com.froad.fft.enums;

import java.io.Serializable;

/**
 * 客户端接入类型
 * @author FQ
 *
 */
public enum ClientAccessType implements Serializable{
	
	/**
	 * 管理平台
	 */
	management("999","管理平台"),
	
	
	/**
	 * 珠海
	 */
	zhuhai("203","珠海农商银行"),
	
	/**
	 * 重庆
	 */
	chongqing("243","重庆农村商业银行"),
	
	/**
	 * 德州
	 */
	dezhou("223","德州商行");
	
	private String code;
	private String describe;
	
	private ClientAccessType(String code,String describe){
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
