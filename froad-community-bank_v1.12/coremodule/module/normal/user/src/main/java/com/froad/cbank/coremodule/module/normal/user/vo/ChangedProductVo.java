package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * 结算-变动商品VO
 * wwh
 */
public class ChangedProductVo {
	
	/**
	 * 商品ID
	 */
	private String productId;
	
	/**
	 * 错误码
	 */
	private String errCode;
	
	/**
	 * 商品状态
	 */
	private String status;
	
	/**
	 * 普通价格
	 */
	private double money;
	  
	/**
	 * 优惠价格
	 */
	private double vipMoney;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getVipMoney() {
		return vipMoney;
	}
	public void setVipMoney(double vipMoney) {
		this.vipMoney = vipMoney;
	}
}
