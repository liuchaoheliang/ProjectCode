package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * 二维码返回实体
 * @author liuchao
 *
 */
public class QrcodePojo {
	
	private String  resultCode	;
	private String resultDesc ;
	private String  url;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
}
