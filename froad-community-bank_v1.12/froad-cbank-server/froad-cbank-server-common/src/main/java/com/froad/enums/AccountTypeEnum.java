package com.froad.enums;

/**
 * 银行账户类型
 * <p>Title: AccountTypeEnum.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月2日 下午5:22:37
 */
public enum AccountTypeEnum {
	
	SQ("1","收款账户"),
	FQ("2","付款账户")
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
	
	private AccountTypeEnum(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static AccountTypeEnum getType(String code){
	    for(AccountTypeEnum type : AccountTypeEnum.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
