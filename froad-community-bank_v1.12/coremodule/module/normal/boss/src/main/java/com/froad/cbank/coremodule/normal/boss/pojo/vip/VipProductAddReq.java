package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 新增的VIP商品请求对象
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月24日 上午10:29:59
 */
public class VipProductAddReq {
	@NotEmpty("商品ID为空")
	private String productId;
	@NotEmpty("VIP价格为空")
	private Double vipPrice;
	private Integer vipLimit;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
		vipLimit = vipLimit == null ? 0 : vipLimit;
		this.vipLimit = vipLimit;
	}
}
