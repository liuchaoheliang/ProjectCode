package com.froad.cbank.coremodule.module.normal.user.enums;

/**
 * @Description: 终端类型
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月14日 下午3:23:48
 */
public enum TerminalType {

	pc("100","pc"),
	android("200","android"),
	iphone("300","iphone")
	;
	
private String code;
	
	private String describe;
	
	private TerminalType(String code,String describe){
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
