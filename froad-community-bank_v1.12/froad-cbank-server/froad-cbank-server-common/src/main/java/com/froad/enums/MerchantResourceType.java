package com.froad.enums;

/**
 * 商户资源类型
 * <p>Title: MerchantResourceType.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月11日 下午7:40:48
 */
public enum MerchantResourceType {
	
	module("1","模块"),
	page("2","页面"),
	button("3","按钮")
	;
	  
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
	
	private MerchantResourceType(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static MerchantResourceType getType(String code){
	    for(MerchantResourceType type : MerchantResourceType.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
