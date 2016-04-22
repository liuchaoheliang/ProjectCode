package com.froad.util;


import java.util.*;

public class Assert {
	public static boolean empty(String str){
		if(str==null||"".equals(str))
			return true;
		else
			return false;
	}
	
	public static boolean empty(String[] str){
		if(str==null||str.length==0)
			return true;
		else
			return false;
	}
	
	public static boolean empty(List list){
		if(list==null||list.size()==0)
			return true;
		else
			return false;
	}
	public static boolean empty(Map map){
		if(map==null||map.isEmpty())
			return true;
		else
			return false;
	}
	
	public static boolean empty(Integer data){
		if(data==null||data==0)
			return true;
		else
			return false;
	}
}
