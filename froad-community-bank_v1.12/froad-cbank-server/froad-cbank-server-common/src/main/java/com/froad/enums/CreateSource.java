/**
 * 
 */
package com.froad.enums;

import com.froad.util.Checker;


/**
 * 订单来源
 */
public enum CreateSource {

	pc("100","pc", "PC"),
	android("200","android", "安卓"),
	iphone("300","iphone", "苹果"),
	ipad("400","ipad", "IPAD");
	
	private String code;
	
	private String describe;
	
	private String bossDescribe;
	
	private CreateSource(String code,String describe, String bossDescribe){
		this.code=code;
		this.describe=describe;
		this.bossDescribe = bossDescribe;
		
	}

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
	
	public String getBossDescribe() {
		return bossDescribe;
	}

	public void setBossDescribe(String bossDescribe) {
		this.bossDescribe = bossDescribe;
	}

	@Override
    public String toString() {
        return this.code;
    }
	
	public static CreateSource getByCode(String code){
		if(Checker.isNotEmpty(code)){
			for(CreateSource c : values()){
				if(c.getCode().equals(code)){
					return c;
				}
			}
		}
		return null;
	}
	
}
