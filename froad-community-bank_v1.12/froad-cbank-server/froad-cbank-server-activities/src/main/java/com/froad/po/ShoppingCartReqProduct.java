package com.froad.po;

import java.io.Serializable;

/**
 * 购物车请求商品
 * */
public class ShoppingCartReqProduct implements Serializable, Comparable<ShoppingCartReqProduct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品id
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品总金额
	 */
	private Double productTotalMoney;
	/**
	 * 商品vip总金额
	 */   
	private Double vipTotalMoney;
	/** 
	 * 普通满减活动id 后台逻辑使用-前台不要传 
	 */
	private String activeId_ptmj;
	/** 
	 * 首单满减活动id 后台逻辑使用-前台不要传 
	 */
	private String activeId_sdmj;
	/** 
	 * 满赠活动id 后台逻辑使用-前台不要传 
	 */
	private String activeId_mz;
	
	@Override
	public int compareTo(ShoppingCartReqProduct o) {
    	String productName = o.productName;
		return this.productName.compareTo(productName);
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Double getProductTotalMoney() {
		return productTotalMoney;
	}
	public void setProductTotalMoney(Double productTotalMoney) {
		this.productTotalMoney = productTotalMoney;
	}
	public Double getVipTotalMoney() {
		return vipTotalMoney;
	}
	public void setVipTotalMoney(Double vipTotalMoney) {
		this.vipTotalMoney = vipTotalMoney;
	}

	public String getActiveId_ptmj() {
		return activeId_ptmj;
	}

	public void setActiveId_ptmj(String activeId_ptmj) {
		this.activeId_ptmj = activeId_ptmj;
	}

	public String getActiveId_sdmj() {
		return activeId_sdmj;
	}

	public void setActiveId_sdmj(String activeId_sdmj) {
		this.activeId_sdmj = activeId_sdmj;
	}

	public String getActiveId_mz() {
		return activeId_mz;
	}

	public void setActiveId_mz(String activeId_mz) {
		this.activeId_mz = activeId_mz;
	}
	
	
}
