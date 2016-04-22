package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class ReturnSubOrderRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 子订单id */
    String subOrderId;
    /** 退款子订单商品 - 列表 */
    List<ReturnSubOrderProductRes> returnSubOrderProductResList;
    
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public List<ReturnSubOrderProductRes> getReturnSubOrderProductResList() {
		return returnSubOrderProductResList;
	}
	public void setReturnSubOrderProductResList(
			List<ReturnSubOrderProductRes> returnSubOrderProductResList) {
		this.returnSubOrderProductResList = returnSubOrderProductResList;
	}
    
}
