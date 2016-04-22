package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

/**
 * 普通商品列表请求（非秒杀商品）
 * @author wangzhangxu
 * @date 2015-5-6 上午10:51:06
 */
public class OrdinaryProductVoReq implements Serializable {
	
	private static final long serialVersionUID = 1078694780830109727L;
	
	private String clientId;
	private String productType;
	private String productName;
	private String parentOrgCode;
	private String orgCode;
	
	private Integer pageNumber;  //当前页
	private Integer pageSize;    //每页条数
	
	public OrdinaryProductVoReq(){}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

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

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
