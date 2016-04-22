package com.froad.util.validation.regular;

public class StringVerify {

	public static boolean isEmpty(String str){
	
		if(str == null || str.length() == 0){
			return true;
		}
		
		return false;
	}
	
	public static boolean regular(String str,String regular){
		if(isEmpty(str)){
			str = "";
		}
		if(str.matches(regular)){
			return true;
		}
		return false;
	}
}
