package com.froad.enums;

/**
 * 券状态
 * @author lyj
 *
 */
public enum QrCodeType {

	FACE2FACE("0","面对面商品"),
	PRODUCT("1","普通商品"),
	TICKET("2","提货码"),
	GROUP("3","团购商品"),
	PRESELL("4","预售商品"),
	SPECIAL("5","名优特惠商品"),
	ONLINEPOINT("6","在线积分兑换商品"),
	DOTGIFT("7","网点礼品商品"),
	URLFACE2FACE("8","面对面商品url直接扫描"),
	OUTLET("9","门店扫码");
	private String code;
	
	private String description;
	
	private QrCodeType(String code,String description){
		this.code=code;
		this.description=description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public String toString() {
        return this.code;
    }
    
}
