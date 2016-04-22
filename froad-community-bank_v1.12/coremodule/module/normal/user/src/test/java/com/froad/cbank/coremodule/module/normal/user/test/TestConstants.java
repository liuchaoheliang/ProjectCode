package com.froad.cbank.coremodule.module.normal.user.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class TestConstants {
	
	public static Map<String,String> map;
	static{
		map = new HashMap<String,String>();
		map.put("key", "value");
		map.put("user.token.cookie.key", "token");
		qq = "qwerty";
	}
	
	public static final String test = get("key");
	
	public static String qq;
	
	
	
	public static String get(String key){
		return map.get(key);
	}
	
	public static void main(String[] args) {
		System.out.println(test);
		System.out.println(qq);
		System.out.println(Token.COOKIE_KEY);
	}
	
	public static final class Token {
		public static final String COOKIE_KEY = get("user.token.cookie.key");
	}
}
