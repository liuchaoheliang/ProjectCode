package com.froad.po;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车响应商品
 */
public class ShoppingCartResProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 商品id */
	private String productId;
	/** 购物车响应活动 - 列表 */
	private List<ShoppingCartResActive> shoppingCartResActiveList;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<ShoppingCartResActive> getShoppingCartResActiveList() {
		return shoppingCartResActiveList;
	}
	public void setShoppingCartResActiveList(
			List<ShoppingCartResActive> shoppingCartResActiveList) {
		this.shoppingCartResActiveList = shoppingCartResActiveList;
	}
	
	
}
