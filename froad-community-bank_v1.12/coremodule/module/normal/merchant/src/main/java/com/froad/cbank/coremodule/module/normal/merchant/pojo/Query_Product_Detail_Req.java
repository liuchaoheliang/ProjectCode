package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Query_Product_Detail_Req extends BasePojo {

	@NotEmpty(value = "商品ID不能为空")
	private String productId;

	@NotEmpty(value = "商品类型不为空")
	private String type;
	// 操作类型1=修改，0=详情
	private String doType;

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
