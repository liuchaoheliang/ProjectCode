package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class FindMarketSubOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 子订单id */
	private String subOrderId;
    /** 查询营销活动子订单商品 - 列表 */
	private List<FindMarketSubOrderProduct> findMarketSubOrderProductList;
	
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public List<FindMarketSubOrderProduct> getFindMarketSubOrderProductList() {
		return findMarketSubOrderProductList;
	}
	public void setFindMarketSubOrderProductList(
			List<FindMarketSubOrderProduct> findMarketSubOrderProductList) {
		this.findMarketSubOrderProductList = findMarketSubOrderProductList;
	}
	
}
