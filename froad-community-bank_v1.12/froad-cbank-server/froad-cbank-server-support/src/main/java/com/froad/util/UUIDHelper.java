package com.froad.util;

import java.util.UUID;

public class UUIDHelper {

	private static String createUUIDStr(){
		UUID uid = UUID.randomUUID();
		return uid.toString().replace("-", "");
	}
	
	public static String getUUID(){
		return createUUIDStr();
	}
	
	public static String getUUID(int length){
		if(length > 32){
			length = 32;
		}
		return createUUIDStr().substring(0, length);
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID().toUpperCase());
	}
}
