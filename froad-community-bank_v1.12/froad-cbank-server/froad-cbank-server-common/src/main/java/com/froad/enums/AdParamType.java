package com.froad.enums;

/**
 * 参数类型(广告位)
 * <p>Title: AdParamType.java</p>    
 * <p>Description: 描述 </p>   
 * <p>1-地区 2-商户类型 3-商品类型</p>
 * @author lf      
 * @version 1.0    
 * @created 2015年9月18日
 */
public enum AdParamType {
	
	area("1","地区"),
	merchant("2","商户类型"),
	goods("3","商品类型"),
	T_Mall("4","精品商城");
	  
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
	
	private AdParamType(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static AdParamType getType(String code){
	    for(AdParamType type : AdParamType.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
}
