package com.froad.cbank.coremodule.module.normal.user.enums;

public enum BoutiqueProductType {
	
	recommend("1","推荐"),  
	hot("2","热销"),     
	newProduct("3","新品");  
	
	private String code;
	
	private String msg;
	
	private BoutiqueProductType(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString(){
		return this.code;
	}

}
