package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Ticket_Code_Use_Req extends BasePojo{
	
	@NotEmpty(value="提货码不能为空")
	private String securitiesNo;
	
	private String type;
	
	@NotEmpty(value="商品不能为空")
	private String productId;

	public String getSecuritiesNo() {
		return securitiesNo;
	}
	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

}
