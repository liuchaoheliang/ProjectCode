/**
 * Project Name:coremodule-bank
 * File Name:ProductVo4List.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年9月2日上午9:40:38
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:ProductVo4List
 * Reason:	 
 * Date:     2015年9月2日 上午9:40:38
 * @author   明灿
 * @version  
 * @see 	 
 */
public class ProductVo4List implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = -98761942131812826L;
	private String productId;// 商品id
	private String productName;// 商品名称
	private String orgName;// 所属机构

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
