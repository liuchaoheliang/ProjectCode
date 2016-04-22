package com.froad.cbank.coremodule.normal.boss.pojo.vip;

import org.apache.commons.lang.StringUtils;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月16日 下午3:02:09
 * @version 1.0
 * @desc VIP规则修改请求参数
 */
public class VipRuleUpdReq {
	@NotEmpty("VIP规则ID为空")
	private String vipId;		//VIP规则ID
	@NotEmpty("VIP规则名称为空")
	private String activitiesName;//VIP规则名称
	
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public String getActivitiesName() {
		return activitiesName;
	}
	public void setActivitiesName(String activitiesName) {
		activitiesName = StringUtils.isBlank(activitiesName) ? null : activitiesName;
		this.activitiesName = activitiesName;
	}
}
