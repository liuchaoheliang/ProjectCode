package com.froad.cbank.coremodule.normal.boss.pojo.report;


/**
 * 商户信息统计表res
 * @author liaopeixin
 *	@date 2015年11月3日 上午10:53:00
 */

public class MerchantReportRes {

	//总商户数
	private Integer merchantTotalCount;
	//订单总数
	private Integer orderTotalCount;
	//订单总金额
	private Double orderTotalPrice;
	//平均订单金额=总出单商户名下订单金额/总出单商户名下订单数量
	private Double avgOrderPrice;
	//商品总数
	private Integer productTotalCount;
	//商品总金额
	private Double productTotalPrice;
	//平均商品金额=总出单商户名下出单商品金额/总出单商户名下出单商品金额
	private Double avgProductPrice;
	//创建时间
	private String createTime;
	//平均发货周期=单一商户名发货时间之和/总商户名数
	private String avgDeliveryCycle;
	public Integer getMerchantTotalCount() {
		return merchantTotalCount;
	}
	public void setMerchantTotalCount(Integer merchantTotalCount) {
		this.merchantTotalCount = merchantTotalCount;
	}
	public Integer getOrderTotalCount() {
		return orderTotalCount;
	}
	public void setOrderTotalCount(Integer orderTotalCount) {
		this.orderTotalCount = orderTotalCount;
	}
	public Double getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(Double orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public Double getAvgOrderPrice() {
		return avgOrderPrice;
	}
	public void setAvgOrderPrice(Double avgOrderPrice) {
		this.avgOrderPrice = avgOrderPrice;
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
	public Double getAvgProductPrice() {
		return avgProductPrice;
	}
	public void setAvgProductPrice(Double avgProductPrice) {
		this.avgProductPrice = avgProductPrice;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAvgDeliveryCycle() {
		return avgDeliveryCycle;
	}
	public void setAvgDeliveryCycle(String avgDeliveryCycle) {
		this.avgDeliveryCycle = avgDeliveryCycle;
	}
	
	
}
