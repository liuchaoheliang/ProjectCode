/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:LatitudeTupeEnum.java
 * Package Name:com.froad.enums
 * Date:2015年12月3日下午4:26:21
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.enums;

/**
 * ClassName:LatitudeTupeEnum Reason: Date: 2015年12月3日 下午4:26:21
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public enum LatitudeTupeEnum {

	AMOUNT("amountLat"), ORDER("orderLat"), MERCHANT("merchantLat"), PRODUCT(
			"productLat"), MERMBER("memberLat");
	private String description;

	private LatitudeTupeEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
