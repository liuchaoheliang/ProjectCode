/**
 * Project Name:froad-cbank-server-boss
 * File Name:ProductCategoryInput.java
 * Package Name:com.froad.po
 * Date:2015年11月3日下午6:35:25
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:ProductCategoryInput
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月3日 下午6:35:25
 * @author   asus
 * @version  
 * @see 	 
 */
public class ProductCategoryInput implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	// 序号
	private Long id;
		
	// 所属客户端
	private String clientName;
	
	// 商品分类
	private String productCategory;
	
	// 商品分类详细
	private String productCategryDetail;
	
	// 商品名称
	private String productName;
	
	// 商品ID
	private String productId;
	
	//所属商户
	private String merchantName;
	
	//所属商户ID
	private String merchantId;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductCategryDetail() {
		return productCategryDetail;
	}

	public void setProductCategryDetail(String productCategryDetail) {
		this.productCategryDetail = productCategryDetail;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
