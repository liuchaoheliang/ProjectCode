package com.froad.cbank.coremodule.normal.boss.pojo.report;

/**
 * 总分析报表
 * @ClassName SummaryReportRes
 * @author zxl
 * @date 2015年11月3日 上午11:01:38
 */
public class SummaryReportRes {
	
	/**
	 * 订单数
	 */
	private Integer orderCount;
	
	/**
	 * 订单总金额 
	 */
	private Double orderTotalPrice;
	
	/**
	 * 商品总数
	 */
	private Integer productCount;
	
	/**
	 * 商品总金额
	 */
	private Double productTotalPrice;
	
	/**
	 * 时间
	 */
	private String createTime;
	
	/**
	 * 订单平均价
	 */
	private Double orderAvgPrice;
	
	/**
	 * 商品平均价
	 */
	private Double productAvgPrice;
	
	/**
	 * 购买会员数
	 */
	private Integer buyCount;
	
	/**
	 * 业务类型数
	 */
	private Integer typeCount;
	
	/**
	 * 客单价
	 */
	private Double buyAvg;
	
	

	public Double getBuyAvg() {
		return buyAvg;
	}

	public void setBuyAvg(Double buyAvg) {
		this.buyAvg = buyAvg;
	}

	public Integer getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(Integer typeCount) {
		this.typeCount = typeCount;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Double getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(Double orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Double getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(Double productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getOrderAvgPrice() {
		return orderAvgPrice;
	}

	public void setOrderAvgPrice(Double orderAvgPrice) {
		this.orderAvgPrice = orderAvgPrice;
	}

	public Double getProductAvgPrice() {
		return productAvgPrice;
	}

	public void setProductAvgPrice(Double productAvgPrice) {
		this.productAvgPrice = productAvgPrice;
	}
	
	
}
