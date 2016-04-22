package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 新增VIP商品请求对象
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月24日 上午10:25:30
 */
public class BindProductAddReq {
	@NotEmpty("VIP规则ID为空")
	private String vipId;
	private List<VipProductAddReq> productList;
	
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public List<VipProductAddReq> getProductList() {
		return productList;
	}
	public void setProductList(List<VipProductAddReq> productList) {
		this.productList = productList;
	}
}
