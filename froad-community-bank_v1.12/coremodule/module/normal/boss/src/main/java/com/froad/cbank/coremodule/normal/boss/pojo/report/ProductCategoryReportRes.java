package com.froad.cbank.coremodule.normal.boss.pojo.report;


/**
 * 类目销售统计报表 res
 * @author liaopeixin
 *	@date 2015年11月3日 上午10:32:27
 */
public class ProductCategoryReportRes {

	//时间
	private String createTime;
	//类目总数
	private Integer categoryTotalCount;
	//类目总商品数
	private Integer productTotalCount;
	//类目总销售额
	private Double productTotalPrice;
	//平均类目价格=出单类目下出单商品销售金额/出单类目下出单商品销售金额
	private Double avgCategoryPerPrice;
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public Integer getCategoryTotalCount() {
		return categoryTotalCount;
	}
	public void setCategoryTotalCount(Integer categoryTotalCount) {
		this.categoryTotalCount = categoryTotalCount;
	}
	public Integer getProductTotalCount() {
		return productTotalCount;
	}
	public void setProductTotalCount(Integer productTotalCount) {
		this.productTotalCount = productTotalCount;
	}
	public Double getProductTotalPrice() {
		return productTotalPrice;
	}
	public void setProductTotalPrice(Double productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	public Double getAvgCategoryPerPrice() {
		return avgCategoryPerPrice;
	}
	public void setAvgCategoryPerPrice(Double avgCategoryPerPrice) {
		this.avgCategoryPerPrice = avgCategoryPerPrice;
	}
	
	
}
