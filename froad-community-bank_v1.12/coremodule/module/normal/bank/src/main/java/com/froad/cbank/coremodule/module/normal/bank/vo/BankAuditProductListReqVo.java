package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankAuditProductListReqVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -834345554629720813L;

	private String productName;// 商品名称
	private String merchantName;// 商户名称
	private String productCategory;// 所属分类
	private String orgCode; // 所属机构(多个使用英文的,拼接)

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
