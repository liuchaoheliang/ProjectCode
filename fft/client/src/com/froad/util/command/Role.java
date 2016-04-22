package com.froad.util.command;

//可能发生资金流转的所有角色
public enum Role {
	SELLER("00"),BUYER("01"),BANK("02"),FFT("03"),USER("04");
	private String value;
	private Role(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	public static Role getRole(String value){
		if("00".equals(value)){
			return SELLER;
		}else if("01".equals(value)){
			return BUYER;
		}else if("02".equals(value)){
			return BANK;
		}else if("03".equals(value)){
			return FFT;
		}else if("04".equals(value)){
			return USER;
		}else{
			return null;
		}
	}
	
	
}
