package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Del_Merchant_User_Req extends BasePojo{
	
	@NotEmpty(value="ID不能为空")
	private String id; 
	
	private String outletId; 
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
}
