package com.froad.enums;

/**
 * 终端类型
 * <p>Title: TerminalType.java</p>    
 * <p>Description: 描述 </p>   
 * @author lf      
 * @version 1.0    
 * @created 2015年5月14日 下午4:33:37
 */
public enum TerminalType {
	

	PC("100","pc"),
	ABDRIOD("200","andriod"),
	IOS("300","ios")
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
	
	private TerminalType(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static TerminalType getType(String code){
	    for(TerminalType type : TerminalType.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
