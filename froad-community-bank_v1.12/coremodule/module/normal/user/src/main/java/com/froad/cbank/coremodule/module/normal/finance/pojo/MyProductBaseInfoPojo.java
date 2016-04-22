package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**
 * 
 * <p>标题: —— 我的理财产品基本信息</p>
 * <p>说明: —— 简要描述职责、使用注意事项等</p>
 * <p>创建时间：2015-6-15下午04:21:38</p>
 * <p>作者: 吴菲</p>
 */
public class MyProductBaseInfoPojo {

	/** 订单ID */
	private String orderId;
	
	/** 理财产品名称 */
	private String productNmae;
	
	/** 预期年化收益率  */
	private double expectRate;
	
	/** 到期日 */
	private Long deadlineEndTime;
	
	/** 收益金额 */
	private double expectProfit;
	
	/** 预期收益 */
	private double profit;
	
	/** 购买金额 */
	private double buyAmount;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductNmae() {
		return productNmae;
	}
	public void setProductNmae(String productNmae) {
		this.productNmae = productNmae;
	}
	public double getExpectRate() {
		return expectRate;
	}
	public void setExpectRate(double expectRate) {
		this.expectRate = expectRate;
	}
	public Long getDeadlineEndTime() {
		return deadlineEndTime;
	}
	public void setDeadlineEndTime(Long deadlineEndTime) {
		this.deadlineEndTime = deadlineEndTime;
	}
	public double getExpectProfit() {
		return expectProfit;
	}
	public void setExpectProfit(double expectProfit) {
		this.expectProfit = expectProfit;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
	
}
