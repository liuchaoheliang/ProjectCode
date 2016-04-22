package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 获取已绑定商品请求类
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月23日 下午2:47:53
 */
public class BoundProductReq extends Page {
	@NotEmpty("VIP规则ID为空")
	private String vipId;

	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
}
