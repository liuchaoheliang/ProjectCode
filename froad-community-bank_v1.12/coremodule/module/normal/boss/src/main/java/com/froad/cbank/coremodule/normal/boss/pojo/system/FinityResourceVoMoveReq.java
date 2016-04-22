package com.froad.cbank.coremodule.normal.boss.pojo.system;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class FinityResourceVoMoveReq {

	@NotEmpty(value = "资源ID不能为空!")
	private String ids;//调整顺序的资源id
	
	@NotEmpty(value = "排序值不能为空!")
	private String orderValues;//调整顺序的排序值

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOrderValues() {
		return orderValues;
	}

	public void setOrderValues(String orderValues) {
		this.orderValues = orderValues;
	}
	
	
}
