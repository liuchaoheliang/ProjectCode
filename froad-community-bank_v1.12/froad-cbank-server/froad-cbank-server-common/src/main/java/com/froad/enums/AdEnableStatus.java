package com.froad.enums;

/**
 * 启用类型(广告位、广告)
 * <p>Title: AdEnableStatus.java</p>    
 * <p>Description: 描述 </p>   
 * <p>0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中</p>
 * @author lf      
 * @version 1.0    
 * @created 2015年9月18日
 */
public enum AdEnableStatus {
	
	enable("0","启用"),
	disabled("1","禁用"),
	addCheckIng("2","新增审核中"),
	editCheckIng("3","编辑审核中"),
	disabledCheckIng("4","禁用审核中")
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
	
	private AdEnableStatus(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static AdEnableStatus getType(String code){
	    for(AdEnableStatus type : AdEnableStatus.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
}
