package com.froad.po;

import java.io.Serializable;
import java.util.List;

/**
 * 进入购物车 响应
 */
public class ShoppingCartRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 请求id
	 */
	private String reqId; 
    /**
	 * 客户端id
	 */
	private String clientId; 
	/**
	 * 用户编号
	 */
	private Long memberCode;
	/** 
	 * 购物车响应商品 - 列表 
	 */
	private List<ShoppingCartResProduct> shoppingCartResProductList;
	/**
	 * 购物车满赠 - 列表
	 */
	private List<ShoppingCartFullGive> shoppingCartFullGiveList;
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public List<ShoppingCartResProduct> getShoppingCartResProductList() {
		return shoppingCartResProductList;
	}
	public void setShoppingCartResProductList(
			List<ShoppingCartResProduct> shoppingCartResProductList) {
		this.shoppingCartResProductList = shoppingCartResProductList;
	}
	public List<ShoppingCartFullGive> getShoppingCartFullGiveList() {
		return shoppingCartFullGiveList;
	}
	public void setShoppingCartFullGiveList(
			List<ShoppingCartFullGive> shoppingCartFullGiveList) {
		this.shoppingCartFullGiveList = shoppingCartFullGiveList;
	}
}
