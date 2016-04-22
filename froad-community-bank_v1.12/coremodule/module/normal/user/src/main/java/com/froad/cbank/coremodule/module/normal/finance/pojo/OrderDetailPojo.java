package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**
 * 
 * <p>标题: —— 我的交易记录详情</p>
 * <p>说明: —— 简要描述职责、使用注意事项等</p>
 * <p>创建时间：2015-6-15下午04:27:02</p>
 * <p>作者: 吴菲</p>
 */
public class OrderDetailPojo extends OrderPojo{


	/** 产品状态   1-未成立 2-成立 3-不成立 4-已赎回 */
	private String productStatus;
	/** 产品成立日  */
	private long productSetUpDate;
	/** 确认未成立日   */
	private long closeTime;
	/** 支付时间   */
	private long payTime;
	/** 会员支付行   */
	private String cardOrgName;
	/** 产品发售行   */
	private String productReleaseBankName;
	/** 撤销时间   */
	private long cancelTime;
	
	
	
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public long getProductSetUpDate() {
		return productSetUpDate;
	}
	public void setProductSetUpDate(long productSetUpDate) {
		this.productSetUpDate = productSetUpDate;
	}
	public long getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}
	public long getPayTime() {
		return payTime;
	}
	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}
	public String getCardOrgName() {
		return cardOrgName;
	}
	public void setCardOrgName(String cardOrgName) {
		this.cardOrgName = cardOrgName;
	}
	public String getProductReleaseBankName() {
		return productReleaseBankName;
	}
	public void setProductReleaseBankName(String productReleaseBankName) {
		this.productReleaseBankName = productReleaseBankName;
	}
	public long getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(long cancelTime) {
		this.cancelTime = cancelTime;
	}
	

	
}
