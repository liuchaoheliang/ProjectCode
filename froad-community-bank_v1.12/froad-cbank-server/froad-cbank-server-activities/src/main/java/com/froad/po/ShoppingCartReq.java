package com.froad.po;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车请求
 * */
public class ShoppingCartReq implements Serializable {

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
	 * 购物车请求商品 - 列表 
	 */
	private List<ShoppingCartReqProduct> shoppingCartReqProductList;
	
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
	public List<ShoppingCartReqProduct> getShoppingCartReqProductList() {
		return shoppingCartReqProductList;
	}
	public void setShoppingCartReqProductList(List<ShoppingCartReqProduct> shoppingCartReqProductList) {
		this.shoppingCartReqProductList = shoppingCartReqProductList;
	}
	
	
}
