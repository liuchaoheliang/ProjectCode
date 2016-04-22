/**
 * 
 */
package com.froad.cbank.coremodule.normal.boss.enums;


/**
 * 订单来源
 */
public enum CreateSource {

	pc("100","pc"),
	android("200","android"),
	iphone("300","iphone"),
	ipad("400","ipad");
	
	private String code;
	
	private String describe;
	
	private CreateSource(String code,String describe){
		this.code=code;
		this.describe=describe;
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
	
	@Override
    public String toString() {
        return this.code;
    }
}
