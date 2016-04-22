package com.froad.CB.service.common;


	/**
	 * 类描述：用户类型枚举类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:20:09 PM 
	 */
public enum UserType {

	buyer("1"),seller("2"),all("3");
	
	private UserType(String value){
		this.value=value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
}
