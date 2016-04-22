package com.froad.enums;

public enum AdLocationPositionPage {
	
	THSH("THSH","特惠商户"),
	SHLM("SHLM","商户类目"),
	THSP("THSP","特惠商品"),
	SPLM("SPLM","商品类目"),
	INDEX("INDEX","首页"),
	froadMallHead("froadMallHead","精品商城头部首页"), 
	froadMallMid_0("froadMallMid_0","精品商城分类广告1"),
	froadMallMid_1("froadMallMid_1","精品商城分类广告2"), 
	froadMallMid_2("froadMallMid_2","精品商城分类广告3"),
	froadMallMid_3("froadMallMid_3","精品商城分类广告4"), 
	froadMallMid_4("froadMallMid_4","精品商城分类广告5"), 
	froadMallMid_5("froadMallMid_5","精品商城分类广告6"), 
	froadMallFoot("froadMallFoot","精品商城尾部广告");
	  
	private String code;
	private String describe;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	private AdLocationPositionPage(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static AdLocationPositionPage getType(String code){
	    for(AdLocationPositionPage type : AdLocationPositionPage.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
