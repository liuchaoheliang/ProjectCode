package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class ReturnSubOrderBackReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 子订单id */
	private String subOrderId;
    /** 退款子订单商品 - 列表 */
	private List<ReturnSubOrderProductBackReq> returnSubOrderProductBackReqList;
	
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public List<ReturnSubOrderProductBackReq> getReturnSubOrderProductBackReqList() {
		return returnSubOrderProductBackReqList;
	}
	public void setReturnSubOrderProductBackReqList(
			List<ReturnSubOrderProductBackReq> returnSubOrderProductBackReqList) {
		this.returnSubOrderProductBackReqList = returnSubOrderProductBackReqList;
	}
}
