package com.froad.enums;

/**
 * 门店不可用状态
 * <p>Title: OutletDisableStatusEnum.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月11日 下午7:40:24
 */
public enum OutletDisableStatusEnum {
	
	normal("0","正常"),
	disable("1","禁用"),
	delete("2","删除")
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
	
	private OutletDisableStatusEnum(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static OutletDisableStatusEnum getType(String code){
	    for(OutletDisableStatusEnum type : OutletDisableStatusEnum.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
