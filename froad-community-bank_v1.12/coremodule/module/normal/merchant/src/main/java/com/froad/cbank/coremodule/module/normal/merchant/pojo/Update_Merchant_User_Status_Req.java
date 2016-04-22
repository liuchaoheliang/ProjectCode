package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Update_Merchant_User_Status_Req extends BasePojo{
	
	@NotEmpty(value="ID不能为空")
	private String id; 
	
	@NotEmpty(value="禁启用状态不能为空")
	private Boolean isDelete;
	
	
	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
