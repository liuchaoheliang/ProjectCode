package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Update_Product_Down_Req extends BasePojo{
	
	@NotEmpty(value="商品ID不为空")
	private String productId;
	
	@NotEmpty(value="状态不为空")
	private String status;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
