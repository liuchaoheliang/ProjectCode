package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * @Description: TODO
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年9月22日 下午4:17:54
 */
public class SmsVo {

	String mobile;
	Integer smsType;
	String code;
	String token;
	String imgCode;
	String imgToken;
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getSmsType() {
		return smsType;
	}
	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getImgCode() {
		return imgCode;
	}
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	public String getImgToken() {
		return imgToken;
	}
	public void setImgToken(String imgToken) {
		this.imgToken = imgToken;
	}
	
	
}
