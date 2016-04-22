package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 普通商品列表请求（非秒杀商品）
 * @author wangzhangxu
 * @date 2015-5-6 上午10:51:06
 */
public class OrdinaryProductVoReq extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = 1078694780830109727L;
	
	private String productType;
	private String productName;
	private String parentOrgCode;
	private String orgCode;
	
	public OrdinaryProductVoReq(){}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
