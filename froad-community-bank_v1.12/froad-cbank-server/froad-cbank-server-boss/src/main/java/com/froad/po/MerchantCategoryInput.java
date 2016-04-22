/**
 * Project Name:froad-cbank-server-boss
 * File Name:MerchantCategoryInput.java
 * Package Name:com.froad.po
 * Date:2015年11月2日下午1:22:25
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:MerchantCategoryInput
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月2日 下午1:22:25
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class MerchantCategoryInput implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 22195513923132917L;
	
	// 序号
	private Long id;
	
	// 所属客户端
	private String clientName;
	
	// 商户分类
	private String merchantCategory;
	
	// 商户分类详细
	private String merchantCategryDetail;
	
	// 商户名称
	private String merchantName;
	
	// 商户ID
	private String merchantId;
	
	// 营业执照号
	private String license;
	
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

	public String getMerchantCategory() {
		return merchantCategory;
	}

	public void setMerchantCategory(String merchantCategory) {
		this.merchantCategory = merchantCategory;
	}

	public String getMerchantCategryDetail() {
		return merchantCategryDetail;
	}

	public void setMerchantCategryDetail(String merchantCategryDetail) {
		this.merchantCategryDetail = merchantCategryDetail;
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

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
