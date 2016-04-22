package com.froad.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 审核类型 
 * @author ll 20150818 
 *
 */
public enum AuditType {

	merchant("1", "商户"),
	outlet("2", "门店"),
	group_product("3", "团购商品"),
	presell_product("4", "预售商品"),
    special_product("5","名优特惠"),
    onlinePoint_product("6","在线积分兑换"),
    dotGift_product("7","网点礼品");
	;
	
	private String type;
	
	private String describe;

	private AuditType(String type, String describe) {
		this.type = type;
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static AuditType getByType(String type){
		if(StringUtils.isNotBlank(type)){
			for(AuditType b : values()){
				if(b.getType().equals(type)){
					return b;
				}
			}
		}
		return null;
	}
}
