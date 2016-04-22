package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Product_Qrcode_Req extends BasePojo {
	
	private String qrcodeUrl;
	//下载传
    private String token;
    
    private String uid;
    
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	
}
