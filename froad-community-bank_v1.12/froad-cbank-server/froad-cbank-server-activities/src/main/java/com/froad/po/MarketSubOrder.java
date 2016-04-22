package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class MarketSubOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 子订单id */
	private String subOrderId;
    /** 子订单商品 - 列表 */
	private List<MarketSubOrderProduct> marketSubOrderProductList;
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public List<MarketSubOrderProduct> getMarketSubOrderProductList() {
		return marketSubOrderProductList;
	}
	public void setMarketSubOrderProductList(
			List<MarketSubOrderProduct> marketSubOrderProductList) {
		this.marketSubOrderProductList = marketSubOrderProductList;
	}
	
}
