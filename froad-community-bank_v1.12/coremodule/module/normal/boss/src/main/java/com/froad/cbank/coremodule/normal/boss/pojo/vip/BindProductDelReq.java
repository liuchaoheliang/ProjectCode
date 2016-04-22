package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * VIP绑定商品删除请求对象
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月24日 上午10:40:20
 */
public class BindProductDelReq {
	@NotEmpty("VIP规则ID为空")
	private String vipId;
	private String productId;
	
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

}
