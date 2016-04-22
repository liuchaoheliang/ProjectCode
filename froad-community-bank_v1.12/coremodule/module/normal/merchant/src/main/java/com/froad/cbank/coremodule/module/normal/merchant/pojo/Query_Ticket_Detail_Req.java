package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Query_Ticket_Detail_Req extends BasePojo{
	
	@NotEmpty(value="提货码不能为空")
	private String securitiesNo;

	public String getSecuritiesNo() {
		return securitiesNo;
	}

	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}
	  
	
}
