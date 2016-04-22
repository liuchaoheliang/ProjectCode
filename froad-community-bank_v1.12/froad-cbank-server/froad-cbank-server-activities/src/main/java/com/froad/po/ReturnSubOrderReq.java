package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class ReturnSubOrderReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 子订单id */
	private String subOrderId;
    /** 退款子订单商品 - 列表 */
	private List<ReturnSubOrderProductReq> returnSubOrderProductReqList;
	
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public List<ReturnSubOrderProductReq> getReturnSubOrderProductReqList() {
		return returnSubOrderProductReqList;
	}
	public void setReturnSubOrderProductReqList(
			List<ReturnSubOrderProductReq> returnSubOrderProductReqList) {
		this.returnSubOrderProductReqList = returnSubOrderProductReqList;
	}

}
