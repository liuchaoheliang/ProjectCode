package com.froad.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 来源
 * @author ll 20150818 
 *
 */
public enum Source {

	orgSource("0", "继承自组织"),
	userSource("1", "用户直接分配"),
	;
	
	private String type;
	
	private String describe;

	private Source(String type, String describe) {
		this.type = type;
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static Source getByType(String type){
		if(StringUtils.isNotBlank(type)){
			for(Source b : values()){
				if(b.getType().equals(type)){
					return b;
				}
			}
		}
		return null;
	}
}

