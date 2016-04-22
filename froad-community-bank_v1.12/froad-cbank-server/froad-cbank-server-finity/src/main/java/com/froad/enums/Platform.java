package com.froad.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 平台类型 
 * @author ll 20150818 
 *
 */
public enum Platform {

	boss("boss", "社区银行运营支撑管理系统"),
	bank("bank", "银行管理平台"),
	merchant("merchant", "商户管理平台"),
	;
	
	private String type;
	
	private String describe;

	private Platform(String type, String describe) {
		this.type = type;
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static Platform getByType(String type){
		if(StringUtils.isNotBlank(type)){
			for(Platform b : values()){
				if(b.getType().equals(type)){
					return b;
				}
			}
		}
		return null;
	}
}
