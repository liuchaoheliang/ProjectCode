package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;


/**
 * 获取可绑定商品请求类
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月23日 下午2:45:59
 */
public class BindProductReq extends Page {
	@NotEmpty("VIP规则ID为空")
	private String vipId;
	private String name;
	private Integer priceStart;
	private Integer priceEnd;
	
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		name = name == null ? "" : name;
		this.name = name;
	}
	public Integer getPriceStart() {
		return priceStart;
	}
	public void setPriceStart(Integer priceStart) {
		priceStart = priceStart == null ? 0 : priceStart;
		this.priceStart = priceStart;
	}
	public Integer getPriceEnd() {
		return priceEnd;
	}
	public void setPriceEnd(Integer priceEnd) {
		priceEnd = priceEnd == null ? 0 : priceEnd;
		this.priceEnd = priceEnd;
	}
}
