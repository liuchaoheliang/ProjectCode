package com.froad.cbank.coremodule.normal.boss.pojo.report;

/**
 * 精品预售销售报表
 * @ClassName PresellOrderReportRes
 * @author zxl
 * @date 2015年11月3日 上午11:01:38
 */
public class PresellOrderReportRes {
	
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
	 * 客单价
	 */
	private Double buyAvg;
	
	/**
	 * 二级机构数量
	 */
	private Integer level2Count;
	
	/**
	 * 三级机构数量
	 */
	private Integer level3Count;
	
	
	public Double getBuyAvg() {
		return buyAvg;
	}

	public void setBuyAvg(Double buyAvg) {
		this.buyAvg = buyAvg;
	}

	public Integer getLevel2Count() {
		return level2Count;
	}

	public void setLevel2Count(Integer level2Count) {
		this.level2Count = level2Count;
	}

	public Integer getLevel3Count() {
		return level3Count;
	}

	public void setLevel3Count(Integer level3Count) {
		this.level3Count = level3Count;
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
