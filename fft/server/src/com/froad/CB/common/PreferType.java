package com.froad.CB.common;


	/**
	 * 类描述：优惠类型
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 7, 2013 4:35:03 PM 
	 */
public enum PreferType {

	/**现金优惠**/
	cash("1"),
	
	/**积分优惠**/
	points("2");

	private String value;
	
	private PreferType(String value){
		this.value=value;
	}
	
	public String value(){
		return value;
	}
}
