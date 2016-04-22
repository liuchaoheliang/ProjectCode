package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月16日 下午3:02:09
 * @version 1.0
 * @desc VIP规则状态变更请求参数
 */
public class VipRuleStatReq {
	@NotEmpty("VIP规则ID为空")
	private String vipId;		//VIP规则ID
	@NotEmpty("VIP规则状态为空")
	private String status;		//状态：1-已生效、2-已作废
	
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
