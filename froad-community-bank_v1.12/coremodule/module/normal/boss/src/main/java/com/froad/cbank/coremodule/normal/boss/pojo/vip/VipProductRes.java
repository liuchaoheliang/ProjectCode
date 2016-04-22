package com.froad.cbank.coremodule.normal.boss.pojo.vip;

/**
 * VIP商品响应实例
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月23日 下午4:14:36
 */
public class VipProductRes {
	private String productId;	//商品ID
	private String name;		//商品名称
	private Double price;		//销售价
	private Long rackTime;		//上架时间
	private String isMarketable;//状态
	private Long createTime;	//创建时间
	private Double vipPrice;	//VIP价
	private Integer vipLimit;	//VIP限购数
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getRackTime() {
		return rackTime;
	}
	public void setRackTime(Long rackTime) {
		this.rackTime = rackTime;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getVipLimit() {
		return vipLimit;
	}
	public void setVipLimit(Integer vipLimit) {
		this.vipLimit = vipLimit;
	}
}
