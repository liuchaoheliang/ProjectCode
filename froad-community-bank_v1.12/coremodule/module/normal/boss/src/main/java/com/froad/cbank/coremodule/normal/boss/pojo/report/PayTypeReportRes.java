package com.froad.cbank.coremodule.normal.boss.pojo.report;


/**
 * 支付方式报表
 * @author liaopeixin
 *	@date 2015年11月3日 下午1:47:44
 */
public class PayTypeReportRes {

		//时间
		private String createTime;
		//支付方式个数,积分，快捷等
		private Integer payTypeTotalCount;
		//购买用户总数
		private Integer buyUserTotalCount;
		//订单总数
		private Integer orderTotalCount;
		//订单总金额
		private Double orderTotalPrice;
		//商品总数
		private Integer productTotalCount;
		//商品总金额
		private Double productTotalPrice;
		//平均订单金额 =总下单支付方式下成交订单金额/总下单支付方式下成交订单数量
		private Double avgOrderPrice;
		//客单价=总下单支付方式下成交订单金额/总下单支付方式下下单会员数量
		private Double perPrice;
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
			
		public Integer getPayTypeTotalCount() {
			return payTypeTotalCount;
		}
		public void setPayTypeTotalCount(Integer payTypeTotalCount) {
			this.payTypeTotalCount = payTypeTotalCount;
		}
		public Integer getBuyUserTotalCount() {
			return buyUserTotalCount;
		}
		public void setBuyUserTotalCount(Integer buyUserTotalCount) {
			this.buyUserTotalCount = buyUserTotalCount;
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
		public Double getAvgOrderPrice() {
			return avgOrderPrice;
		}
		public void setAvgOrderPrice(Double avgOrderPrice) {
			this.avgOrderPrice = avgOrderPrice;
		}
		public Double getPerPrice() {
			return perPrice;
		}
		public void setPerPrice(Double perPrice) {
			this.perPrice = perPrice;
		}
		
		
}
