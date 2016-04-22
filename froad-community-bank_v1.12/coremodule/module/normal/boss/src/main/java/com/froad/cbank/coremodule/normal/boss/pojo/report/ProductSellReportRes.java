package com.froad.cbank.coremodule.normal.boss.pojo.report;

/**
 * 商品销售报表
 * @ClassName ProductSellReportRes
 * @author zxl
 * @date 2015年11月3日 上午11:01:38
 */
public class ProductSellReportRes {
	
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
	 * 商品平均价
	 */
	private Double productAvgPrice;

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

	public Double getProductAvgPrice() {
		return productAvgPrice;
	}

	public void setProductAvgPrice(Double productAvgPrice) {
		this.productAvgPrice = productAvgPrice;
	}
	
	
	
}
