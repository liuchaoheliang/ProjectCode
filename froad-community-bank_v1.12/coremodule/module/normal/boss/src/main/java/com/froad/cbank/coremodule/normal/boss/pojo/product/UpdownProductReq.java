package com.froad.cbank.coremodule.normal.boss.pojo.product;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 上下架商品请求类
 * @author luwanquan@f-road.com.cn
 * @date 2015年7月29日 下午5:26:12
 */
public class UpdownProductReq {
	@NotEmpty("商品ID为空")
	private String productId;
	@NotEmpty("上下架状态为空")
	private String marketableStatus;//1-上架、2-下架
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getMarketableStatus() {
		return marketableStatus;
	}
	public void setMarketableStatus(String marketableStatus) {
		this.marketableStatus = marketableStatus;
	}
}
